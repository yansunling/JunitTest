package com.word.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.yd.common.data.CIPResponseMsg;
import com.yd.common.runtime.CIPErrorCode;
import com.yd.common.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class AssetUtil {

    //接口返回成功对象
    public static CIPResponseMsg success(){
        CIPResponseMsg msg=new CIPResponseMsg();
        msg.errorCode= CIPErrorCode.CALL_SUCCESS.code;
        msg.msg=CIPErrorCode.CALL_SUCCESS.name;
        return msg;
    }

    /**
     * 转json排除字段
     * @param obj
     * @param property
     * @return
     */
    public static String excludeJSON(Object obj,String ... property){
        String[] excludeAttributes = {"update_user_id","update_time","create_user_id","create_time","version","op_user_id","creator"};

        int array1Length = excludeAttributes.length; // 获取array01的长度
        int array2Length = property.length; // 获取array02的长度
        // 向array01中拷贝元素并为array01扩容
        excludeAttributes = Arrays.copyOf(excludeAttributes, array1Length + array2Length);
        System.arraycopy(property, 0, excludeAttributes, array1Length, array2Length);
        //添加要排除的属性
        PropertyPreFilters propertyPreFilters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter exclude = propertyPreFilters.addFilter();
        exclude.addExcludes(excludeAttributes);
        return JSONObject.toJSONString(obj,exclude, SerializerFeature.PrettyFormat);
    }

    /**
     * 数字补0
     * @param num 数字
     * @param length 长度
     * @return
     */
    public static String appendZero(Integer num,Integer length){
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(length);
        formatter.setGroupingUsed(false);
        return formatter.format(num);
    }

    public static Map<String,Object> beanToMapSkipNull(Object bean){
        Map<String, Object> beanToMap = BeanUtil.beanToMap(bean);
        //过滤空值
        Map<String, Object> collect = beanToMap.entrySet().stream().filter(e -> e.getValue() != null).collect(Collectors.toMap((e) -> e.getKey(),
                (e) -> e.getValue()));
        return collect;
    }

    public static String getRedisDayNo(String busiType,int length){
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        String redisKey="tlwl:asset-api:public:cache:num:"+today+":"+busiType;
        String value = RedisUtils.get(redisKey, String.class);
        if(StringUtils.isBlank(value)){
            value="1";
            RedisUtils.putWithStringKey(redisKey,1,86400);
        }
        RedisUtils.incr(redisKey);
        //数据累加
        return today+ StringUtils.leftPad(value,length,"0");
    }








}
