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
        String inputString="org";
        String regex = "'big_area','region_id'";

        System.out.println(regex.replaceAll("','","|"));


    }




}
