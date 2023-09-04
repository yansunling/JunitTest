package com.img;

import org.aspectj.util.FileUtil;

import java.io.*;
import java.util.Base64;

public class Base64ToImgCheck {

	/**
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		String filePath = "C:\\Users\\yansunling\\Desktop/1.txt";
//		byte[] b1 = image2Bytes("I:/img/3.jpg"); //path是绝对路径
//		byte[] b2 = Base64.encodeBase64(b1);
//		saveAsFileWriter(new String(b2),filePath);//将base64编码的数据保存到文件中
		String str = FileUtil.readAsString(new File(filePath));
		str=str.replaceAll("\n","").replaceAll("\r","");
		//base64转图片
		Base64ToImage(str,"C:\\Users\\yansunling\\Desktop/4.jpg");




	}



	public  static void Base64ToImage(String base64ImageString,String imgFilePath){
		try {
			// 解码Base64图片数据
			byte[] imageBytes = Base64.getDecoder().decode(base64ImageString);

			// 将解码后的二进制数据写入文件
			FileOutputStream outputStream = new FileOutputStream(imgFilePath);
			outputStream.write(imageBytes);
			outputStream.close();

			System.out.println("Base64图片数据已成功转换为文件！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}






}
