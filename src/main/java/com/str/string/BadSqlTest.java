package com.str.string;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yd.common.runtime.CIPRuntimeOperator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BadSqlTest {


    public static void main(String[] args) {
        CIPRuntimeOperator user=new CIPRuntimeOperator();
        user.setSubject_id("11");
        CIPRuntimeOperator user1=new CIPRuntimeOperator();
        user1.setSubject_id("11");

        List<CIPRuntimeOperator> list=new ArrayList<>();
        list.add(user);
        list.add(user1);

        Map<String, CIPRuntimeOperator> collect = list.stream().filter(i -> StrUtil.isNotEmpty(i.getSubject_id()))
                .collect(Collectors.toMap(CIPRuntimeOperator::getSubject_id, Function.identity(),(newValue,OldValue)->newValue));

        Map<String, CIPRuntimeOperator> collect1 = list.stream().filter(i -> StrUtil.isNotEmpty(i.getSubject_id()))
                .collect(Collectors.groupingBy(CIPRuntimeOperator::getSubject_id,Collectors.reducing(
                        null,
                        Function.identity(),
                        (existing, replacement) -> existing // 保留第一个值
                )));


        System.out.println(JSON.toJSONString(collect));


    }






    //效验
    protected static boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";//过滤掉的sql关键字，可以手动添加
        String[] badStrs = badStr.split("\\|");
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < badStrs.length; i++) {
            sb.append(badStrs[i]).append(" |");
//            if (str.indexOf(badStrs[i]) >= 0) {
//                return true;
//            }
        }
        System.out.println(sb.toString());
        return false;
    }
}
