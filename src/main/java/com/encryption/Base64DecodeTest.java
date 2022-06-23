package com.encryption;

import org.bouncycastle.util.encoders.Base64;

import java.nio.charset.StandardCharsets;

public class Base64DecodeTest {
    public static void main(String[] args) {
        //base64解码
        String base64="eyJhbGdvcml0aG0iOiJIUzI1NiIsInR5cGUiOiJKV1QifQ==";
        byte[] decode = Base64.decode(base64);

        System.out.println(new String(decode));
        //base64编码
        String data = new String(decode);
        byte[] encode = Base64.encode(data.getBytes());
        String encodeStr = new String(encode);
        System.out.println(new String(encode));
        System.out.println(base64.equals(encodeStr));
        String urlEncode = java.util.Base64.getUrlEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
        System.out.println(urlEncode);

        ThreadLocal<String> threadLocal=new ThreadLocal<>();


    }
}
