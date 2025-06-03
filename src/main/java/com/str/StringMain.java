package com.str;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yd.common.busi.builder.base.BusiNo;
import com.yd.utils.common.BeanConvertUtils;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class StringMain {
    public static void main(String[] args) {

    /*    String value="rO0ABXNyACZjb20ueWQuY29tbW9uLmJ1c2kuYnVpbGRlci5iYXNlLkJ1c2lOb5M4JIeCnO4fAgAFSQAFaW5kZXhMAAlidXNpQXV0b3N0ABBMamF2YS91dGlsL0xpc3Q7TAAKYnVzaVBhcmFtc3EAfgABTAAGY29kZUlkdAASTGphdmEvbGFuZy9TdHJpbmc7TAAFZml4ZWRxAH4AAnhwAAAAAHNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAABdwQAAAABc3IAKGNvbS55ZC5jb21tb24uYnVzaS5idWlsZGVyLmJhc2UuQnVzaUF1dG8ztuEZ0gUnFwIACEwAEWFzc2VydF92YWx1ZV90eXBlcQB+AAJMAAthdXRvQnVpbGRlcnQALUxjb20veWQvY29tbW9uL2J1c2kvYnVpbGRlci9iYXNlL0F1dG9CdWlsZGVyO0wACWJhc2VfdHlwZXEAfgACTAAKYmFzZV92YWx1ZXEAfgACTAAKYnVpbGRfYWxnb3EAfgACTAADcmVmdAAPTGphdmEvdXRpbC9NYXA7TAAJc3RlcF90eXBlcQB+AAJMAApzdGVwX3ZhbHVlcQB+AAJ4cgAoY29tLnlkLmNvbW1vbi5idXNpLmJ1aWxkZXIuYmFzZS5CdXNpQmFzZdJ5ZR1kK/hSAgAHSQAGbGVuZ3RoTAANZGVmYXVsdF92YWx1ZXEAfgACTAAJZmlsbF90ZXh0cQB+AAJMAAlmaWxsX3R5cGVxAH4AAkwACW1heF92YWx1ZXEAfgACTAAJbWluX3ZhbHVlcQB+AAJMAApyZXNldF90eXBlcQB+AAJ4cAAAAAdwcHBwcHBwc3IAMGNvbS55ZC5jb21tb24uYnVzaS5idWlsZGVyLmltcGwuQXV0b1JlZGlzQnVpbGRlcpSSQnd2z8aTAgAAeHBwdAAHMjYyMDY2M3QAATFzcgARamF2YS51dGlsLkhhc2hNYXAFB9rBwxZg0QMAAkYACmxvYWRGYWN0b3JJAAl0aHJlc2hvbGR4cD9AAAAAAAABdwgAAAACAAAAAXQACXJlZGlzX2tleXQAOnRsd2w6Y3JtOnNlcmlhbDpyZWRpc19hdXRvOmNybV9jdXN0b21lcl9pZF9jcm1fY3VzdG9tZXJfaWR4cHQAATF4c3EAfgAEAAAAAHcEAAAAAHh0AA9jcm1fY3VzdG9tZXJfaWR0AAA=";
        byte[] decode = Base64.getDecoder().decode(value);
        BusiNo object =(BusiNo) BeanConvertUtils.toObject(decode);
        System.out.println(JSON.toJSONString(object));
        System.out.println(object.genByEngine());*/



//        System.out.println(daysTime);

//        Timestamp now=new Timestamp(System.currentTimeMillis());
//        System.out.println(getMonthsLaterFirstDay(now,-3));


       /* String json="{\n" +
                "  \"driverId\":\"KM00151\",\n" +
                "  \"sendTransCustId\":\"2505210005\",\n" +
                "  \"outList\":[\n" +
                "    {\n" +
                "      \"goodsOutCount\":5,\n" +
                "      \"orderId\":\"602108628\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":110.0,\n" +
                "      \"goodsOutCube\":0.831,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":1,\n" +
                "      \"orderId\":\"602106701\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":8.0,\n" +
                "      \"goodsOutCube\":0.01,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":1,\n" +
                "      \"orderId\":\"602108702\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":15.0,\n" +
                "      \"goodsOutCube\":0.06,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":1,\n" +
                "      \"orderId\":\"602108944\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":75.0,\n" +
                "      \"goodsOutCube\":0.298,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":1,\n" +
                "      \"orderId\":\"602109846\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":70.0,\n" +
                "      \"goodsOutCube\":0.388,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":14,\n" +
                "      \"orderId\":\"602106608\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":230.0,\n" +
                "      \"goodsOutCube\":0.56,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":1,\n" +
                "      \"orderId\":\"602107079\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":43.0,\n" +
                "      \"goodsOutCube\":0.227,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":1,\n" +
                "      \"orderId\":\"602108943\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":45.0,\n" +
                "      \"goodsOutCube\":0.217,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":30,\n" +
                "      \"orderId\":\"602107313\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":740.0,\n" +
                "      \"goodsOutCube\":1.47,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":1,\n" +
                "      \"orderId\":\"701675098\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":40.0,\n" +
                "      \"goodsOutCube\":0.1,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":1,\n" +
                "      \"orderId\":\"602108286\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":25.0,\n" +
                "      \"goodsOutCube\":0.094,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":3,\n" +
                "      \"orderId\":\"701674266\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":90.0,\n" +
                "      \"goodsOutCube\":0.4,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":11,\n" +
                "      \"orderId\":\"701675189\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":289.0,\n" +
                "      \"goodsOutCube\":0.5,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":1,\n" +
                "      \"orderId\":\"701674542\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":110.0,\n" +
                "      \"goodsOutCube\":0.8,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"goodsOutCount\":1,\n" +
                "      \"orderId\":\"701674554\",\n" +
                "      \"signinResult\":\"1\",\n" +
                "      \"goodsOutWeight\":110.0,\n" +
                "      \"goodsOutCube\":0.8,\n" +
                "      \"fileList\":[\n" +
                "      ]\n" +
                "    }],\n" +
                "  \"fileList\":[\n" +
                "    \"portal_tms_5a137757-7395-47af-8e76-b61fc740342a_1\"]\n" +
                "}\n";


        JSONObject jsonObject = JSON.parseObject(json);

        List<JSONObject> outList = (List<JSONObject>)jsonObject.get("outList");

        outList.forEach(item->{
            String orderId = item.getString("orderId");
            System.out.println(orderId);
        });*/


        Timestamp startTime=Timestamp.valueOf("2025-04-01 00:00:00");
        System.out.println(startTime);

    }

    public static Timestamp getMonthsLaterFirstDay(Timestamp timestamp, int month) {
        // 转换为 LocalDate（丢弃时间部分）
        LocalDate localDate = timestamp.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // 增加四个月并设置为 1 日
        LocalDate futureDate = localDate.plusMonths(month).withDayOfMonth(1);

        // 组合为 LocalDateTime（00:00:00）
        LocalDateTime futureDateTime = futureDate.atStartOfDay();

        // 转回 Timestamp
        return Timestamp.valueOf(futureDateTime);
    }




}
