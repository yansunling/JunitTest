package com.oa;

import cn.hutool.http.HttpUtil;
import com.dy.components.logs.api.logerror.GlobalErrorInfoException;
import com.yd.common.runtime.CIPRuntimeConfigure;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;


@Slf4j
public class ImageDownUtil {

    public static void main(String[] args) {

       /* String refreshUrl="http://oa.uat.tuolong56.com/weaver/weaver.file.FileDownload?fileid=520270&&requestid=227743&download=1";

        HttpClient httpClient = new HttpClient();
        GetMethod refreshMethod = new GetMethod(refreshUrl);  //建立连接

        String cookies = getCookie("T1113", "0834");

        refreshMethod.setRequestHeader("cookie", cookies);  //设置访问cookie
        refreshMethod.getParams().setContentCharset("utf-8");
        try{
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            int i = httpClient.executeMethod(refreshMethod);//进行访问
            System.out.println(i);

        }catch (Exception e) {
            e.printStackTrace();
        }*/

       downImg();











    }

    @SneakyThrows
    public static void downImg() {











        CloseableHttpClient httpclient = HttpClients.createDefault();



        String host="https://oa.uat.tuolong56.com";
        HttpGet httpGet = new HttpGet(host+"/weaver/weaver.file.FileDownload?fileid=520270&&requestid=227743&download=1");

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("cookie","loginfileweaver=%2Fwui%2Ftheme%2Fecology8%2Fpage%2Flogin.jsp%3FtemplateId%3D3%26logintype%3D1%26gopage%3D; languageidweaver=7; testBanCookie=test; ecology_JSessionId=abcOglyub3hYz_sVcxnly; JSESSIONID=abcOglyub3hYz_sVcxnly; ecology_JSessionid=abcOglyub3hYz_sVcxnly; loginidweaver=10440");

        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {

            Header firstHeader = response.getFirstHeader("Content-Disposition");
            String fileName = firstHeader.getValue().replace("attachment; filename=","").replaceAll("\"","");
            fileName=URLDecoder.decode(fileName, "utf-8");
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                InputStream is = resEntity.getContent();
                File file = new File("C:/Users/admin/Desktop/"+fileName);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int len = -1;
                while((len = is.read(buffer) )!= -1){
                    fos.write(buffer, 0, len);
                }
                fos.close();
                is.close();
            }
            EntityUtils.consume(resEntity);
        } finally {
            response.close();
        }
    }



    public static String getCookie(String username , String password){
        //String loginUrl = OATask.conf.getProperty("client.url")+"login/VerifyLogin.jsp";  //登陆 Url
        String loginUrl = "https://oa.uat.tuolong56.com/login/VerifyLogin.jsp";  //登陆 Url
        String tmpcookies= "";  //存储cookie值
        //初始cookie值
        HttpClient httpClient = new HttpClient();
        PostMethod loginMethod = new PostMethod(loginUrl);
        //设置登陆时要求的信息
        NameValuePair[] logindata = {
                new NameValuePair("loginfile", "/wui/theme/ecology8/page/login.jsp?templateId=3&logintype=1&gopage="),
                new NameValuePair("logintype", "1"),
                new NameValuePair("fontName", "微软雅黑"),
                new NameValuePair("formmethod", "post"),
                new NameValuePair("isie", "false"),
                new NameValuePair("islanguid", "7"),
                new NameValuePair("loginid", username),
                new NameValuePair("userpassword", password),
//				new NameValuePair("dongyu",dongyuKey),
                new NameValuePair("submit", "登录")
        };

        loginMethod.setRequestBody(logindata);
        loginMethod.getParams().setContentCharset("utf-8");

        try {
            //设置 HttpClient 接收 Cookie,用与浏览器一样的策略
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            int  statusCode=httpClient.executeMethod(loginMethod);
            //判断登录是否成功
            if  (statusCode  !=  HttpStatus.SC_OK) {
                return null;
            }
            //获得登陆后的 Cookie
            Cookie[] cookies=httpClient.getState().getCookies();
            for(Cookie c:cookies){
                tmpcookies = tmpcookies+c.toString()+";";
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放连接
            loginMethod.releaseConnection();
        }
        return tmpcookies;
    }
}
