package com.word.util;

import cn.hutool.core.date.DateUtil;
import com.yd.common.data.CIPResponseMsg;
import com.yd.common.exttype.Money;
import com.yd.common.runtime.CIPErrorCode;
import com.yd.common.utils.RedisUtils;
import com.yd.utils.common.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

@Slf4j
public class CrmxCommonUtil {
    public static int getRate(int itemNum,int total){
        if(total==0){
            return 0;
        }
        BigDecimal a = new BigDecimal(itemNum*100);
        BigDecimal b = new BigDecimal(total);
        BigDecimal divide = a.divide(b, 0, ROUND_HALF_DOWN);
        //最小占比1
        int round = divide.intValue();
        if(round==0){
            return 1;
        }
        return round;

    }

    public static Double divide(double molecule,double denominator){
        if(denominator==0){
            return 0.0;
        }
        BigDecimal a = new BigDecimal(molecule);
        BigDecimal b = new BigDecimal(denominator);
        BigDecimal divide = a.divide(b, 2, ROUND_HALF_DOWN);
        return divide.doubleValue();

    }

    public static int divideReturnInt(double molecule,double denominator){
        if(denominator==0){
            return 0;
        }
        BigDecimal a = new BigDecimal(molecule);
        BigDecimal b = new BigDecimal(denominator);
        BigDecimal divide = a.divide(b, 0, ROUND_HALF_DOWN);
        return divide.intValue();

    }



    //接口返回成功对象
    public static CIPResponseMsg success(){
        CIPResponseMsg msg=new CIPResponseMsg();
        msg.errorCode= CIPErrorCode.CALL_SUCCESS.code;
        msg.msg= CIPErrorCode.CALL_SUCCESS.name;
        return msg;
    }

    //按5向下取整
    public static Money floorFive(Money num){
        log.info("floorFive start num:"+num);
        //整数转double,不然不会有小数
        BigDecimal bigDecimal = num.fen2Yuan();
        double currentScore=bigDecimal.doubleValue()/10;
        System.out.println(currentScore);
        double floor = Math.floor(currentScore);
        if (floor + 0.5 > currentScore) {
            currentScore = floor;
        }else {
            currentScore = floor + 0.5;
        }
        Money value = Money.ofYuan(currentScore * 10);
        log.info("floorFive end num:"+value.yuan2Fen());
        return value;


    }

    public static boolean compareMax(String max,String value){
        if(StringUtils.isBlank(max)|| StringUtils.isNotBlank(value)){
            return false;
        }
        try {
            int maxNum = Integer.parseInt(max);
            int valueNum = Integer.parseInt(value);
            return maxNum>=valueNum;
        } catch (Exception e) {

        }
        return false;
    }

    public static List<String> strToList(String str){
        if(StringUtils.isBlank(str)){
            return new ArrayList<>();
        }
        String[] arr = str.split(",");
        List<String> list = Arrays.asList(arr);
        List<String> result = new ArrayList<>();
        result.addAll(list);
        return result;
    }

    public static String getRedisDayNo(String busiType,int length){
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        String redisKey="tlwl:crmx:public:cache:num:"+today+":"+busiType;
        String value = RedisUtils.get(redisKey, String.class);
        if(org.apache.commons.lang.StringUtils.isBlank(value)){
            value="1";
            RedisUtils.putWithStringKey(redisKey,1,86400);
        }
        RedisUtils.incr(redisKey);
        //数据累加
        return busiType+today+ org.apache.commons.lang.StringUtils.leftPad(value,length,"0");
    }



    public static <T extends Serializable> T deepClone(T obj) {
        T cloneObj = null;
        try {
            // 写入字节流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(obj);
            obs.close();

            // 分配内存，写入原始对象，生成新对象
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);
            // 返回生成的新对象
            cloneObj = (T) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
    }





}
