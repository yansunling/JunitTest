package com.dy.test.zip;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;



public class ZipUtil {
    /**
     * 将字符串压缩后Base64
     * @param primStr 待加压加密函数
     * @return
     */
    private static Logger log= LoggerFactory.getLogger(ZipUtil.class);

    public static String zipString(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }
        ByteArrayOutputStream out = null;
        ZipOutputStream zout = null;
        try{
            out = new ByteArrayOutputStream();
            zout = new ZipOutputStream(out);
            zout.putNextEntry(new ZipEntry("0"));
            zout.write(primStr.getBytes("UTF-8"));
            zout.closeEntry();
            return new BASE64Encoder().encode(out.toByteArray());
        } catch (IOException e) {
            log.error("对字符串进行加压加密操作失败：", e);
            return null;
        } finally {
            if (zout != null) {
                try {
                    zout.close();
                } catch (IOException e) {
                    log.error("对字符串进行加压加密操作，关闭zip操作流失败：", e);
                }
            }
        }
    }

    /*
     * 将压缩并Base64后的字符串进行解密解压
     * @param compressedStr 待解密解压字符串
     * @return
     */
    public static final String unzipString(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        ZipInputStream zin = null;
        String decompressed = null;
        try {
            byte[] compressed = new BASE64Decoder().decodeBuffer(compressedStr);
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(compressed);
            zin = new ZipInputStream(in);
            zin.getNextEntry();
            byte[] buffer = new byte[1024];
            int offset = -1;
            while((offset = zin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString("UTF-8");
        } catch (IOException e) {
            log.error("对字符串进行解密解压操作失败：", e);
            decompressed = null;
        } finally {
            if (zin != null) {
                try {
                    zin.close();
                } catch (IOException e) {
                    log.error("对字符串进行解密解压操作，关闭压缩流失败：", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("对字符串进行解密解压操作，关闭输入流失败：", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("对字符串进行解密解压操作，关闭输出流失败：", e);
                }
            }
        }
        return decompressed;
    }

    public static void main(String[] args) throws Exception{
        /*StringBuffer sb=new StringBuffer();
        for(int i=0;i<100;i++){
            sb.append("tes中国t").append(i);
        }

        String teststs = zipString(sb.toString());
        System.out.println(teststs);

        String s = unzipString(teststs);
        System.out.println(s);

        String baseStr="test中国";

        String encode = new BASE64Encoder().encode(baseStr.getBytes());
        System.out.println(encode);*/

        String message="a+fTVzkV0fg=";
        String kStr = "1441194498741aAbBcCeEfFg";
        byte[] code = Base64.decodeBase64(message);
        String data = toHexString(code);
        System.out.println(data.toUpperCase());
        byte[] decode = decryptMode(kStr.getBytes("utf8"),code);
        System.out.println(new String(decode, "utf8"));
    }

    private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();
    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 3);
        for (int b : bytes) {
            b &= 0xff;
            sb.append(HEXDIGITS[b >> 4]);
            sb.append(HEXDIGITS[b & 15]);
        }
        return sb.toString();
    }


    private static byte[] decryptMode(byte[] keybyte, byte[] src) throws Exception {
        SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
        Cipher c1 = Cipher.getInstance("DESede");
        c1.init(2, deskey);
        return c1.doFinal(src);
    }


}
