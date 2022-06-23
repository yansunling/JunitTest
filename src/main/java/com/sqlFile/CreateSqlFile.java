package com.sqlFile;

import com.yd.utils.common.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class CreateSqlFile {
    public static void main(String[] args)throws Exception {
        File file=new File("C:\\Users\\admin\\Desktop\\insert.sql");
        if(!file.exists()){
            file.createNewFile();
        }


        String data = " This content will 大幅度的 to the end of the file\n";
        //true = append file
        FileWriter fileWritter = new FileWriter(file,true);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

        for(int i=0;i<5;i++){
            bufferWritter.write(data);
        }

        bufferWritter.close();

        List<String> fdgd = Arrays.asList("33", "fdgd");
        System.out.println(StringUtils.join(",",fdgd.toArray()));


    }
}
