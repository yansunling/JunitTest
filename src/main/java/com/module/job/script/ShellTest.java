package com.module.job.script;

public class ShellTest {

    public static void main(String[] args) {
        String path="E:/3.bat";
        String logFileName="E:/test.log";
        ScriptUtil.executeBat(logFileName,path);
    }

}
