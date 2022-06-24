package com.http;


import com.util.CommonUtil;
import com.yd.common.data.CIPResponseMsg;
import com.yd.common.runtime.CIPRuntimeConfigure;
import com.yd.utils.common.StringUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@RestController
@Component
public class HttFileForwardController {

    @RequestMapping(value = "/forward/start")
    public CIPResponseMsg importData(HttpServletRequest request) throws Exception{
        CIPResponseMsg success = CommonUtil.success();
        String urlStr="http://localhost:8080/test/forward/end.do";
        Map<String, String> textMap=new HashMap<>();
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        String responseData = doPostUploadUtils(urlStr, textMap, multiRequest, request.getContentType());

        System.out.println(responseData);

        return success;
    }

    @RequestMapping(value = "/forward/end")
    public CIPResponseMsg forwardData(HttpServletRequest request) throws Exception{
        CIPResponseMsg success = CommonUtil.success();
        request.setCharacterEncoding("UTF-8");
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        // 获取multiRequest 中所有的文件名
        Iterator<String> iter = multiRequest.getFileNames();
        while (iter.hasNext()) {
            // 一次遍历所有文件
            MultipartFile file = multiRequest.getFile(iter.next());
            if (file != null) {
                String filename = file.getOriginalFilename();
                String path = "C:\\Users\\admin\\Desktop\\sql\\" + filename;
                // 上传
                try {
                    file.transferTo(new File(path));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }





    public static String doPostUploadUtils(String urlStr, Map<String, String> textMap, MultipartHttpServletRequest multipartRequest, String contentType) {
        String res = "";
        HttpURLConnection conn = null;
        String[] strArray = contentType.split("=");
        String BOUNDARY = strArray[1]; //boundary就是request头和上传文件内容的分隔符

        try {
            multipartRequest.setCharacterEncoding("UTF-8");
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
//            conn.setRequestProperty("referer",referer);
            conn.setRequestProperty("Content-Type", contentType);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf1 = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf1.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf1.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf1.append(inputValue);
                }
                out.write(strBuf1.toString().getBytes());
            }

            Iterator<String> iter = multipartRequest.getFileNames();
            MultipartFile file = null;

            while (iter.hasNext()) {
                file = multipartRequest.getFile(iter.next());
                CommonsMultipartFile cf= (CommonsMultipartFile)file;
                String name = cf.getName();

                DiskFileItem fi = (DiskFileItem)cf.getFileItem();
                File f = fi.getStoreLocation();


                StringBuffer strBuf2 = new StringBuffer();
                strBuf2.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                strBuf2.append("Content-Disposition: form-data; name=\"" + f.getName() + "\"; filename=\"" + file.getOriginalFilename() + "\"\r\n");
                strBuf2.append("Content-Type:" + "application/octet-stream" + "\r\n\r\n");

                out.write(strBuf2.toString().getBytes());

                DataInputStream in = new DataInputStream(new FileInputStream(f));
                int bytes = 0;
                byte[] bufferOut = new byte[1024];
                while ((bytes = in.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                in.close();
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }





}
