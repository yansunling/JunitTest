package com.other.cmd;

public class CmdUtil {
    public static void closeWps() throws Exception{
        Runtime run =Runtime.getRuntime();
        Process p = run.exec("taskkill /f /im wps.exe");
        p.waitFor();
    }
    public static void openWps(String fileName) throws Exception{
        Runtime run =Runtime.getRuntime();
        if(fileName.startsWith("/")){
            fileName=fileName.substring(1);
        }
        Process p = run.exec("cmd /C start "+fileName);
        p.waitFor();
    }


}
