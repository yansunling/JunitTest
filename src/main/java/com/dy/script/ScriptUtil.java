package com.dy.script;

import com.yd.utils.common.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

public class ScriptUtil {
    private static final Logger logger= LoggerFactory.getLogger(ScriptUtil.class);
    /**
     * 执行shell脚本
     * @param logFileName
     * @param path
     */
    public static void executeShell(String logFileName,String path){
        log(logFileName,"----shell--path:"+path+"---start----");
        String[] cmdA = {path };
        try {
            //执行脚本并等待脚本执行完成
            Process process = Runtime.getRuntime().exec(cmdA);
            //写出脚本执行中的过程信息
            BufferedReader infoInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = "";
            while ((line = infoInput.readLine()) != null) {
                log(logFileName,line);
            }
            while ((line = errorInput.readLine()) != null) {
                log(logFileName,"异常:"+line);
            }
            infoInput.close();
            errorInput.close();
            //阻塞执行线程直至脚本执行完成后返回
             process.waitFor();
            log(logFileName,"----shell-----end----");

        } catch (Throwable e) {
            log(logFileName,e);
        }
    }

    /**
     * 执行bat脚本
     * @param logFileName
     * @param path
     */
    public static void executeBat(String logFileName,String path){

        try {
            log(logFileName,"----bat--path:"+path+"---start----");
            Process child = Runtime.getRuntime().exec(path);
            InputStream in = child.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in,"GBK"));
           //显示正常的内容
            String line;
            while((line=bufferedReader.readLine())!=null){
                log(logFileName,line);

            }
            bufferedReader.close();
            //打印异常的内容
            BufferedReader errorDes = new BufferedReader(new InputStreamReader(child.getErrorStream(),"GBK"));
            while ((line = errorDes.readLine()) != null){
                byte[] gbks = line.getBytes("GBK");
                System.out.println(new String(gbks));
                System.out.println(line);
                log(logFileName,"异常:");
            }
            errorDes.close();
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            log(logFileName,"----bat--end----");
        } catch (IOException e) {
            log(logFileName,e);
        }
    }
    /**
     * 将日志写入文件
     * @param logFileName
     * @param appendLog
     */
    private static void log(String logFileName,  String appendLog) {
        try {
            createFile(logFileName);
            logger.info(appendLog);
            StringBuffer stringBuffer = new StringBuffer();
            StackTraceElement callInfo = new Throwable().getStackTrace()[1];
            stringBuffer.append(DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SSS")).append(" INFO ")
                    .append(Thread.currentThread().getName()).append(" ")
                    .append("("+callInfo.getClassName() + " "+callInfo.getLineNumber() +") ")
                    .append("- ").append(appendLog).append("\r\n");
            Files.write(Paths.get(logFileName), stringBuffer.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将异常写入文件
     * @param logFileName
     * @param e
     */
    public static void log(String logFileName,Throwable e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        String appendLog = stringWriter.toString();
        log(logFileName, appendLog);
    }

    /**
     * 创建文件夹和文件
     * @param fileName
     * @return
     */
    private static boolean createFile(String fileName) {
        Path filePath = Paths.get(fileName);
        try {
            Path dir = filePath.getParent();
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            // 文件不存在，新建文件
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
