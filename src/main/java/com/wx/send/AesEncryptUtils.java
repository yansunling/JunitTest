package com.wx.send;

import com.yd.utils.mq.ExchangeEnum;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/** 
* @author fukaijian
* @since 2018年12月3日
* @Description AES-128加解密
*
*/
public class AesEncryptUtils {


    //秘钥
    private static String key = "51Lu3538k/PyKMh8ZRSJcw==";

    private AesEncryptUtils(){}

    /**
     * AES加密字符串
     *
     * @param content
     *            需要被加密的字符串
     * @return 密文
     */
    public static String encrypt(String content){
        return encrypt(key, content);
    }

    /**
     * AES加密字符串
     *
     * @param content
     *            需要被加密的字符串
     * @return 密文
     */
    public static String decrypt(String content){
        return decrypt(key, content);
    }

    /**
     * AES加密字符串
     * 
     * @param skey 密钥
     * @param content 需要被加密的字符串
     * @return 密文
     */
	public static String encrypt(String skey, String content) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(skey.getBytes());
            kgen.init(128, secureRandom);// 利用用户密码作为随机数初始化出128位的key生产者
            //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器
//            Base64  encoder = new Base64();    //需要加上
            byte[] result = cipher.doFinal(byteContent);// 加密
            return parseByte2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/**
     * 解密AES加密过的字符串
     * 
     * @param content
     *            AES加密过过的内容
     * @return 明文
     */
	public static String decrypt(String skey, String content) {
		if(content == null || skey == null){
			return content;
		}
        try {
        	//Base64 decoder = new Base64();
        	//byte[] bytes = decoder.decode(parseHexStr2Byte(content));
        	byte[] bytes = parseHexStr2Byte(content);
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(skey.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
            byte[] result = cipher.doFinal(bytes);  
            return new String(result); // 明文   
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/**将二进制转换成16进制 
     * @param buf 
     * @return 
     */  
    private static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    } 
    
    /**将16进制转换为二进制 
     * @param hexStr 
     * @return 
     */  
    private static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
                return null;  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
    }
    
    public static void main(String[] args) {
		/*String enc = encrypt("4QrcOUm6Wau+VuBX8g+IPg==","dy");
		String denc = decrypt("4QrcOUm6Wau+VuBX8g+IPg==", "1FECCBF8C819C3F05F361BCE0323868C");
		System.out.println(enc);
		System.out.println(denc);*/


        String decrypt = decrypt("52A35E31F85E8FFAF9340A2E8E9F07C17DD4B37DCE57531FC186CBD98B6BEBC841F59005B0C1FCBD908657E9872A3FD4");
        System.out.println(decrypt);

        System.out.println(ExchangeEnum.DIRECT.name());
    }
}
