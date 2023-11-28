package com.encryption;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class Md5Base64Util {

	public static void main(String[] args) throws Exception{

		System.out.println(getMD5("0834"));
	}

	public static String getMD5(String input) {
		try {
			// 获取一个MD5算法的实例
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 计算输入字符串的MD5值
			byte[] messageDigest = md.digest(input.getBytes());
			// 将字节转换为十六进制的字符串
			StringBuilder hexString = new StringBuilder();
			for (byte aMessageDigest : messageDigest) {
				String shaHex = Integer.toHexString(aMessageDigest & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}







	@SneakyThrows
	public static String md5Base64(String data){
		// 创建 MessageDigest 实例，选择 MD5 加密算法
		MessageDigest md = MessageDigest.getInstance("MD5");
		// 对数据进行 MD5 加密
		byte[] md5Bytes = md.digest(data.getBytes());
		// 使用 Base64 进行编码
		String base64Encoded = Base64.getEncoder().encodeToString(md5Bytes);
		// 输出结果
		System.out.println("MD5 Hash: " + base64Encoded);

		return base64Encoded;
	}





}
