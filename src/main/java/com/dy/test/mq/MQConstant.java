package com.dy.test.mq;

import com.dy.test.util.RedisUtil;
import com.yd.common.utils.RedisUtils;

public class MQConstant {


    public static void isSuccess(String key){
        try {
            RedisUtil.putWithStringKey(key,"true",-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void isERROR(String key){

        try {
            RedisUtil.putWithStringKey(key,"false",-1);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static String getKey(String key){

        try {
            return RedisUtil.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
