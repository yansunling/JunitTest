package com.str.json;

import com.yd.utils.common.DateUtils;

import java.sql.Timestamp;
import java.util.Date;

public class JsonTest {
	public static void main(String[] args) {
		Date date = DateUtils.parseDate("2024-08-12");
		System.out.println(date.getTime());


	}
}
