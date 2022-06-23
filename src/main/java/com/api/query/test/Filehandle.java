package com.api.query.test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Filehandle {
    public static void main(String[] args) throws Exception {
        File dir=new File("E:/vo");
        File[] files = dir.listFiles();
        for(File file:files){
            writeNewFile(file);
        }
    }


    public static void writeNewFile(File file) throws Exception{
        List<String> strings = FileUtils.readLines(file, "utf-8");
        List<String> newStrings=new ArrayList<>();
        for(String str:strings){
            StringBuffer sb=new StringBuffer();
            String test = subStrForMath(str,sb);
            System.out.println(sb.toString());
            newStrings.add(test);
        }
        File newFile=new File("E:/po/"+file.getName());
        FileUtils.writeLines(newFile,newStrings,"\n");
    }



    public static String subStrForMath(String str,StringBuffer sb) {
        String string = "";

        for (int i = 0; i < str.length(); i++) {
            String str0 = "";
            if (str.substring(i, i + 1).matches("[\u4e00-\u9fa5]+")) {
                String substring = str.substring(i, i + 1);
                sb.append(substring);
            } else {
                str0 = str.substring(i, i + 1) + "";
            }
            string += str0;
        }
        return string;

    }


    public static boolean readfile(String filepath) throws FileNotFoundException, IOException {
        try {

            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                System.out.println("absolutepath=" + file.getAbsolutePath());
                System.out.println("name=" + file.getName());

            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        System.out.println("path=" + readfile.getPath());
                        System.out.println("absolutepath=" + readfile.getAbsolutePath());
                        System.out.println("name=" + readfile.getName());

                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
    }














}
