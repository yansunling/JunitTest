package com.module.mq;

import com.other.redis.MyRedisUtil;

public class MQConstant {


    public static void isSuccess(String key){
        try {
            MyRedisUtil.putWithStringKey(key,"true",-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void isERROR(String key){

        try {
            MyRedisUtil.putWithStringKey(key,"false",-1);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static String getKey(String key){

        try {
            return MyRedisUtil.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
