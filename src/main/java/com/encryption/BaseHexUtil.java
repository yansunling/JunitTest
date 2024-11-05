package com.encryption;


import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import java.io.UnsupportedEncodingException;

public class BaseHexUtil {


    public static void main(String[] args) throws Exception{
        String data="92516680216BB9083DC729BD81E9D310";


        byte[] decode = Hex.decode(data);


        byte[] encode = Base64.encode(decode);

        System.out.println(new String(encode));
        System.out.println(Strings.fromByteArray(encode));


        String toBase64String = Base64.toBase64String(decode);
        System.out.println(toBase64String);


        String md5Str="mgw5WYdutuWqE/WZZ4ksmw==";

        byte[] decode1 = Base64.decode(md5Str);

        System.out.println(Hex.toHexString(decode1));







    }


    private static String toHexString(byte bytes[]) {
        StringBuilder hs = new StringBuilder();
        String stmp = "";
        for (int n = 0; n < bytes.length; n++) {
            stmp = Integer.toHexString(bytes[n] & 0xff);
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }

        return hs.toString();
    }



    public static String encodeBase64(String data) throws UnsupportedEncodingException {
        byte[] encode = Base64.encode(data.getBytes());
        return new String(encode);
    }

    public static String decodeBase64(String data) throws UnsupportedEncodingException {
        byte[] decode = Base64.decode(data.getBytes());
        return new String(decode);
    }

    public static String encodeHex(String data) throws UnsupportedEncodingException {
        byte[] encode = Hex.encode(data.getBytes());
        return new String(encode);
    }

    public static String decodeHex(String data) throws UnsupportedEncodingException {
        byte[] decode = Hex.decode(data.getBytes());
        return new String(decode);
    }






}
