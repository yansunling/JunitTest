package com.dy.test.cmd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SqlSoarTest {

    public static void main(String[] args) {
        try {

            String sql="\"select user_id from query.cip_admin_codes\"";
            String cmd="F:/soar/soar.windows-amd64 -query "+sql;
            Process child = Runtime.getRuntime().exec(cmd);
            InputStream in = child.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in,"UTF8"));
            //显示正常的内容
            String line;
            while((line=bufferedReader.readLine())!=null){
                System.out.println(line);

            }
            bufferedReader.close();
            //打印异常的内容
            BufferedReader errorDes = new BufferedReader(new InputStreamReader(child.getErrorStream(),"UTF8"));
            while ((line = errorDes.readLine()) != null){
                System.out.println(line);
            }
            errorDes.close();
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
           e.printStackTrace();
        }
    }

}
