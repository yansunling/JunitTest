package com.junit;

import java.io.RandomAccessFile;
import java.math.BigDecimal;

public class TestFile {
	public static void main(String[] args) throws Exception{
//
//		String path="E:/3.txt";
//		String header = "--文件头--";
//		appendFileHeader(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF },path);
//		System.out.println("dd\tdd");


		BigDecimal big=new BigDecimal("2153");
		long longValue = big.movePointRight(2).setScale(0, 4).longValue();
		System.out.println(longValue);
		System.out.println(BigDecimal.ROUND_HALF_UP);




	}
	private static void appendFileHeader(byte[] header,String srcPath) throws Exception{
		RandomAccessFile src = new RandomAccessFile(srcPath, "rw");
		int srcLength = (int)src.length() ;
		byte[] buff = new byte[srcLength];
			src.read(buff , 0, srcLength);
			src.seek(0);
			src.write(header);
			src.seek(header.length);
			src.write(buff);

	}
}
