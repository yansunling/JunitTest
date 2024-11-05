package com.encryption;

import org.bouncycastle.util.encoders.Base64;

import java.nio.charset.StandardCharsets;

public class Base64DecodeTest {
    public static void main(String[] args) {
        //base64解码
        String base64="YTAzZmEzMDgyMTk4NmRmZjEwZmM2NjY0N2M4NGM5YzM=";
        byte[] decode = Base64.decode(base64);

        System.out.println("decodeData:"+new String(decode));
        //base64编码
        String data = new String(decode);
        byte[] encode = Base64.encode(data.getBytes());
        String encodeStr = new String(encode);
        System.out.println(new String(encode));
        System.out.println(base64.equals(encodeStr));
        String urlEncode = java.util.Base64.getUrlEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
        System.out.println(urlEncode);

        String encodedString = "ZDU5NGIxYTk0NWI1ZDY0NWU1OWUyMWY4OGJkMmQ4M2I=";

        // 使用Base64解码器进行解码
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(encodedString);

        // 将解码后的字节转换为字符串
        String decodedString = new String(decodedBytes);

        System.out.println("解码后的字符串: " + decodedString);


    }
}
