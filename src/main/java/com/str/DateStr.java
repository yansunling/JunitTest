package com.str;

import com.alibaba.fastjson.JSON;
import com.util.FileUtils;
import com.util.HttpUtils;
import org.aspectj.util.FileUtil;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

public class DateStr {
    public static void main(String[] args) throws Exception{
        String s = FileUtil.readAsString(new File("C:\\Users\\yansunling\\Desktop\\1.txt"));
        // 将字面的 \n 和 \t 转换为真正的换行符和制表符
        s = s.replace("\\n", "\n").replace("\\t", "\t");
        System.out.println(s);
    }
}
