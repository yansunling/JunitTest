package com.str;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.org.FindOrgSql;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMain {
    public static void main(String[] args) {
        String str=" orgTransferMap.put(\"250110020401\", Sets.newHashSet(\"532901\", \"53290101\"));";
        str = str.replaceAll("\"" + "250110020401", "\"55");
        System.out.println(str);


    }




}
