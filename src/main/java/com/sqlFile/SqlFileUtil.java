package com.sqlFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SqlFileUtil {

    private static Integer num=0;

    public static void insertFile(List<String> dataList) {
        try {
            File file=new File("C:\\Users\\yansunling\\Desktop\\sql\\insert"+num+".sql");
            if(!file.exists()){
                file.createNewFile();
            }
            //true = append file
            FileWriter fileWritter = new FileWriter(file,true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);


            for(String data:dataList){
                bufferWritter.write(data);
            }
            bufferWritter.close();
            num++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static  void resetFileNum(){
        num=0;
    }

    public static BufferedWriter getBufferedWriter(String tableName) {
        try {
            File file=new File("C:\\Users\\yansunling\\Desktop\\sql\\"+tableName+".sql");
            if(!file.exists()){
                file.createNewFile();
            }
            //true = append file
            FileWriter fileWritter = new FileWriter(file);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            return bufferWritter;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
















}
