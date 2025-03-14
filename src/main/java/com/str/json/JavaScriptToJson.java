package com.str.json;

import org.aspectj.util.FileUtil;

import java.io.File;

public class JavaScriptToJson {

    public static void main(String[] args) throws Exception{
        String filePath = "C:\\Users\\yansunling\\Desktop/1.json";
        String json = FileUtil.readAsString(new File(filePath));
    }

}
