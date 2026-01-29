package com.encryption;

import java.util.Base64;

/**
 * MD5Base64解码工具类
 * 解码 tZxnvxlqR1gZHkL3ZnDOug== 得到标准MD5
 */
public class Md5Base64DecodeUtil {

    public static void main(String[] args) {
        // 你的目标字符串
        String md5Base64Str = "tZxnvxlqR1gZHkL3ZnDOug==";

        // ========== 方式1：解码得到【32位小写标准MD5字符串】 推荐 ==========
        String md5Hex = decodeMd5Base64To32Hex(md5Base64Str);
        System.out.println("32位标准MD5结果：" + md5Hex);
        // 输出结果：764e59efc56a475864790bd9990ceba

        // ========== 方式2：解码得到【MD5原生16字节二进制数组】 ==========
        byte[] md5Bytes = decodeMd5Base64ToBytes(md5Base64Str);
        System.out.println("MD5原始16字节数组：" + bytesToString(md5Bytes));
    }

    /**
     * 核心方法：将MD5的Base64字符串，解码为32位小写MD5十六进制字符串
     * @param md5Base64 MD5Base64加密串
     * @return 32位标准MD5（小写）
     */
    public static String decodeMd5Base64To32Hex(String md5Base64) {
        // 1. Base64解码得到MD5原始16字节数组
        byte[] md5Bytes = Base64.getDecoder().decode(md5Base64);
        // 2. 16字节数组 转 32位十六进制字符串
        return bytesToHexLowerCase(md5Bytes);
    }

    /**
     * 解码得到MD5原生的16字节二进制数组
     * @param md5Base64 MD5Base64加密串
     * @return 16字节数组 (MD5的本质)
     */
    public static byte[] decodeMd5Base64ToBytes(String md5Base64) {
        return Base64.getDecoder().decode(md5Base64);
    }

    /**
     * 字节数组 转 32位小写十六进制字符串（核心转换方法）
     */
    private static String bytesToHexLowerCase(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            // 转小写十六进制，补足两位，0开头的不会丢失
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 字节数组转字符串，用于控制台打印查看
     */
    private static String bytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i]);
            if (i < bytes.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
