package com.img;

import org.apache.commons.codec.binary.Base64;
import org.aspectj.util.FileUtil;
import sun.misc.BASE64Decoder;

import java.io.*;

public class Base64ToImgTest {

	/**
	 * @param args
	 * @throws Exception 
	 */

	public static void main(String[] args) throws Exception {
		String filePath = "I:/img/2.text";
		byte[] b1 = image2Bytes("I:/img/3.jpg"); //path是绝对路径
		byte[] b2 = Base64.encodeBase64(b1);
//		saveAsFileWriter(new String(b2),filePath);//将base64编码的数据保存到文件中


		//base64转图片
		Base64ToImage(FileUtil.readAsString(new File(filePath)),"I:/img/4.jpg");




	}

	public static void saveAsFileWriter(String content,String filePath) {
		FileWriter fwriter = null;
		try {
			// true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
			fwriter = new FileWriter(filePath);
			fwriter.write(content);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fwriter.flush();
				fwriter.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	static void buff2Image(byte[] b,String tagSrc) throws Exception {
		FileOutputStream fout = new FileOutputStream(tagSrc);
		//将字节写入文件
		fout.write(b);
		fout.close();

	}

	public static byte[] image2Bytes(String imgSrc) throws Exception {
		FileInputStream fin = new FileInputStream(new File(imgSrc));
		//可能溢出,简单起见就不考虑太多,如果太大就要另外想办法，比如一次传入固定长度byte[]
		byte[] bytes  = new byte[fin.available()];
		//将文件内容写入字节数组，提供测试的case
		fin.read(bytes);

		fin.close();
		return bytes;
	}

	public static boolean Base64ToImage(String base64, String imgFilePath) {
		// 对字节数组字符串进行Base64解码并生成图片
		if (base64 == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(base64);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}

	}





}
