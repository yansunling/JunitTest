package com.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wx.data.*;
import com.yd.utils.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author fukaijian
 * 发送企业微信信息
 */
public class WechatUtils {
    private static Logger logger= LoggerFactory.getLogger(WechatUtils.class);
    
    public static String oauth2_access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={0}&corpsecret={1}";
	
    public static String userinfo_url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token={0}&agentid={1}&code={2}";
	
    public static String send_url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token={0}";

	public static Map<String, WechatToken> tokenCache = new HashMap<>();

	private WechatUtils(){}

	/**
	 * 获取accessToken
	 * @return
	 * @throws Exception
	 */
    public static String getAccessToken(String corpID, String secret){
        WechatToken token = tokenCache.get(corpID+"_"+secret);
        if(token != null && token.getExpiresTime() > System.currentTimeMillis()){
        	return token.getAccessToken();
        }
        MessageFormat format=new MessageFormat(oauth2_access_token_url);
        Object[] args = {corpID, secret};
        String url=format.format(args);
        JSONObject object= HttpUtils.httpRequest(url,"GET",null);
        if(null!=object){
            Integer errorCode=object.getInteger("errcode");
            if(!new Integer(0).equals(errorCode)){
                logger.info("corpid={},secret={}返回accesstoken失败，返回错误信息为：{}", corpID, secret, object.getString("errmsg"));
                return null;
            }
            token=new WechatToken();
            token.setAccessToken(object.getString("access_token"));
            token.setErrorCode(object.getInteger("errcode"));
            //微信返回的是多少秒后该token过期，这里加上当前时间便于判断。减1200是为了确保token不会过期
            token.setExpiresTime((object.getLong("expires_in")-1200)*1000 + System.currentTimeMillis());
            tokenCache.put(corpID+"_"+secret, token);
            return token.getAccessToken();
        }else{

            logger.info("corpid={},secret={}返回accesstoken失败",corpID, secret);

        }
        return null;
    }

    /**
     * 
     * @param code,通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
     * @return
     * @throws Exception 
     */
    public static String getUserId(String code, String corpID, String agentId, String secret){
    	String token = null;
		try {
			token = getAccessToken(corpID, secret);
		} catch (Exception e) {
			logger.error("获取accessToken失败，{}", e.getLocalizedMessage());
			e.printStackTrace();
		}

		if(StringUtils.isEmpty(token)){
			return null;
		}
        MessageFormat format=new MessageFormat(userinfo_url);
        Object[] args = {token, agentId, code};
        String url=format.format(args);
        JSONObject object=HttpUtils.httpRequest(url,"GET",null);
        logger.info("获取员工信息返回结果：{}", object.toJSONString());
        if(null != object.get("UserId")){
            return object.getString("UserId");
        }else{
            logger.error("获取员工信息失败，errmsg：{}", object.get("errmsg"));        	
        	return null;
        }
    }

    /**
     * 发送文本消息
     * @param receiver  多个而用户用"|"隔开，不能超过1000个
     * @param message
     * @return
     */
    public static MMS_wechat_wea_result sendMessage(String receiver, String message, String corpID, String agentId, String secret){
    	
		String token = null;
		try {
			token = getAccessToken(corpID, secret);
		} catch (Exception e) {
			logger.error("获取accessToken失败，{}", e.getLocalizedMessage());
			e.printStackTrace();
		}

		if(StringUtils.isEmpty(token)){
			throw new RuntimeException("accessToken为空");
		}
        WechatMessage wechat = new WechatMessage();
        wechat.setTouser(receiver);
        wechat.setAgentid(Integer.parseInt(agentId));
        WechatText text = new WechatText(message);
        wechat.setText(text);

        MessageFormat format=new MessageFormat(send_url);
        Object[] args = {token};
        String url=format.format(args);

        JSONObject object=HttpUtils.httpRequest(url,"POST",JSON.toJSONString(wechat));
        MMS_wechat_wea_result result = JSONObject.toJavaObject(object, MMS_wechat_wea_result.class);
        logger.debug("企业微信发送结果：{}", result);
        return result;
    }



    public static MMS_wechat_wea_result sendTextCardMessage(String receiver, WechatTextcard textCard, String corpID, String agentId, String secret){

        String token = null;
        try {
            token = getAccessToken(corpID, secret);
        } catch (Exception e) {
            logger.error("获取accessToken失败，{}", e.getLocalizedMessage());
            e.printStackTrace();
        }

        if(StringUtils.isEmpty(token)){
            throw new RuntimeException("accessToken为空");
        }
        WechatMessage wechat = new WechatMessage();
        wechat.setTouser(receiver);
        wechat.setAgentid(Integer.parseInt(agentId));
        wechat.setTextcard(textCard);
        wechat.setMsgtype("textcard");
        MessageFormat format=new MessageFormat(send_url);
        Object[] args = {token};
        String url=format.format(args);

        JSONObject object=HttpUtils.httpRequest(url,"POST",JSON.toJSONString(wechat));
        MMS_wechat_wea_result result = JSONObject.toJavaObject(object, MMS_wechat_wea_result.class);
        logger.debug("企业微信发送结果：{}", result);
        return result;
    }









        /**
         * 新增临时素材
         *
         * @param
         * @param filePath
         * @return
         * @throws Exception
         */
        public static MMS_wechat_wea_result sendFileMessage(String receiver,String filePath,String corpID,String agentId, String secret) throws Exception {
                // 返回结果
            String result = null;
            File file = new File(filePath);
            if (!file.exists() || !file.isFile()) {

                throw new IOException("文件不存在");
            }

            String token = getAccessToken(corpID,secret);
            if (token == null) {
                throw new IOException("未获取到token");
            }
            String uploadTempMaterial_url="https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
            uploadTempMaterial_url = uploadTempMaterial_url.replace("ACCESS_TOKEN", token).replace("TYPE", "file");
            URL url = new URL(uploadTempMaterial_url);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// 以POST方式提交表单
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);// POST方式不能使用缓存
            // 设置请求头信息
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            // 请求正文信息
            // 第一部分
            StringBuilder sb = new StringBuilder();
            sb.append("--");// 必须多两条道
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\"; filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            // 获得输出流
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 输出表头
            out.write(sb.toString().getBytes("UTF-8"));
            // 文件正文部分
            // 把文件以流的方式 推送道URL中
            DataInputStream din = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] buffer = new byte[1024];
            while ((bytes = din.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
            }
            din.close();
            // 结尾部分
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");// 定义数据最后分割线
            out.write(foot);
            out.flush();
            out.close();
            if (HttpsURLConnection.HTTP_OK == conn.getResponseCode()) {

                StringBuffer strbuffer = null;
                BufferedReader reader = null;
                try {
                    strbuffer = new StringBuffer();
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String lineString = null;
                    while ((lineString = reader.readLine()) != null) {
                        strbuffer.append(lineString);

                    }
                    if (result == null) {
                        result = strbuffer.toString();

                    }
                } catch (IOException e) {

                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                }

            }
            //推送文件
            JSONObject jsonObject = JSONObject.parseObject(result);
            String mediaId = jsonObject.get("media_id")+"";
            WechatMessage wechat = new WechatMessage();
            wechat.setTouser(receiver);
            wechat.setAgentid(Integer.parseInt(agentId));
            wechat.setFile(new WechatFile(mediaId));
            wechat.setMsgtype("file");
            MessageFormat format=new MessageFormat(send_url);
            Object[] args = {token};
            String fileSendUrl=format.format(args);
            System.out.println(JSON.toJSONString(wechat));
            JSONObject object=HttpUtils.httpRequest(fileSendUrl,"POST",JSON.toJSONString(wechat));
            MMS_wechat_wea_result fileSendResult = JSONObject.toJavaObject(object, MMS_wechat_wea_result.class);
            logger.debug("企业微信发送结果：{}", fileSendResult);

            return fileSendResult;
        }


}
