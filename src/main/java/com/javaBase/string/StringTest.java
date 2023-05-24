package com.javaBase.string;

import com.yd.utils.common.StringUtils;

import java.util.*;

public class StringTest {

    private static Map<String,String> map=new HashMap<>();

    public static void main(String[] args) {
        String orgId="33078201-520201,33078201-522301,32060001-520500,32060001-52030002,32060001-52030001,32060001-520300,32060001-520400,32060001-522601,32060001-520201,32060001-522301,32060001-52010005,32060001-52010004,32060001-52010003,32060001-52010001,32060001-53010003,32060001-53010002,32060001-53010001,33100003-33078208,33060002-52010006";
        String[] split = orgId.split(",");
        List<String> list=new ArrayList<>();

        Set<String> set=new HashSet<>();
        for(String str:split){
            String trim = str.trim();
            String[] split1 = trim.split("-");
            String old1=split1[0];
            String old2=split1[1];
            set.add(old1);
            set.add(old2);
            String newOld1 = map.get(old1);
            String newOld2 = map.get(old2);
            if(StringUtils.isBlank(newOld2)){
                System.out.println(old2);
                continue;
            }

            String newStr="'"+newOld1+"-"+newOld2+"'";
            list.add(newStr);
        }
        System.out.println(set);
        System.out.println(list);
    }

    static {
        map.put("32060001","2501080402");
        map.put("32060001","2501080405");
        map.put("33060002","250108010204");
        map.put("33078201","250108010301");
        map.put("33078201","2501080105");
        map.put("52010003","250110030301");
        map.put("52010005","250110040601");
        map.put("52010006","250108010204");
        map.put("520201","250110040502");
        map.put("520300","250110040403");
        map.put("520400","250110040401");
        map.put("520500","250110040503");
        map.put("522301","250110040501");
        map.put("522601","250110040402");
        map.put("53010001","250110010301");
        map.put("53010002","250110020601");
        map.put("53010003","250110010302");
    }



}
