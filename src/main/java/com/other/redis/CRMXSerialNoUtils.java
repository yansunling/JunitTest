package com.other.redis;


import com.yd.common.cache.CIPRedisUtils;
import redis.clients.jedis.Jedis;


public class CRMXSerialNoUtils {

	public final static CRMXSerialNoUtils CRM_CUSTOMER_ID = new CRMXSerialNoUtils("crm_customer_id", "1040350");
	public final static CRMXSerialNoUtils CRM_COUPON_CODE_ZY = new CRMXSerialNoUtils("crm_coupon_code_zy", "10000001");
	public final static CRMXSerialNoUtils CRM_COUPON_CODE_JJ = new CRMXSerialNoUtils("crm_coupon_code_jj", "10000001");
	public final static CRMXSerialNoUtils CRM_COUPON_CODE_FW = new CRMXSerialNoUtils("crm_coupon_code_fw", "10000001");
	public final static CRMXSerialNoUtils CRM_COUPON_CODE_XL = new CRMXSerialNoUtils("crm_coupon_code_xl", "10000001");

	private static Jedis jedis = null;

	public String key;
	public String value;


	public CRMXSerialNoUtils(String key, String value){
		this.key = key;
		this.value = value;
	}

	public static String getSerial(CRMXSerialNoUtils serialNoUtils){
		 /** 构造redis的key */
        String key=serialNoUtils.key;
        String value=serialNoUtils.value;
        /** 自增 */
        long sequence = incr(key,value);
        String str = String.valueOf(sequence);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        String serial = sb.toString();
        return serial;
	}
	
	private static long incr(String key,String value) {
		if(jedis == null){
			try {
				jedis = CIPRedisUtils.getJedisResource();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		jedis.setnx(key,value); 
		Long no = jedis.incr(key);
		System.out.println(no + "");
		return no;
	}
	
	
	
}

