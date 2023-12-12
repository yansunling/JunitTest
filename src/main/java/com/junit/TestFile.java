package com.junit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yd.crm.credit.pojo.po.CRM_credit_custPO;
import io.swagger.models.auth.In;

import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.net.URLDecoder;

public class TestFile {
	public static void main(String[] args) throws Exception{
//
//		String path="E:/3.txt";
//		String header = "--文件头--";
//		appendFileHeader(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF },path);
//		System.out.println("dd\tdd");


//		BigDecimal big=new BigDecimal("2153");
//		long longValue = big.movePointRight(2).setScale(0, 4).longValue();
//		System.out.println(longValue);
//		System.out.println(BigDecimal.ROUND_HALF_UP);


		String json="{\"blacklist_type\":\"0\",\"create_time\":1675312635000,\"creator\":\"T2171\",\"creator_name\":\"\",\"credit_grade\":\"3\",\"customer_id\":\"2364294\",\"op_user_id\":\"T2171\",\"op_user_name\":\"\",\"owing_amount\":6.7382088E8,\"owing_long\":300,\"remark\":\"\",\"salesman_id\":\"\",\"serial_no\":\"24885823988437036\",\"update_time\":1701195025000}";
		CRM_credit_custPO creditCustPO = JSONObject.parseObject(json, CRM_credit_custPO.class);
		BigDecimal bigDecimal=new BigDecimal(creditCustPO.getOwing_amount());
		//bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		System.out.println(bigDecimal);


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
