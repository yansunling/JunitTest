package com.other.javaFile;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JarFileRebuild {
    public static void main(String[] args) throws Exception{


        List<File> allFile = getAllFile("I:\\github\\engine\\src\\main");

        System.out.println(allFile.size());
        allFile.forEach(file->{
            String name = file.getName();
            System.out.println(name);
            if(name.indexOf(".java")>=0){
                try {
                    //读取文本文件的所有行到一个集合
                    List<String> lines= FileUtils.readLines(file,"utf-8");
                    List<String> newLines= new ArrayList<>();
                    for (String line : lines) {
                        if(line.indexOf("/*")>=0&&line.indexOf("*/")>=0){
                            String newLine = line.substring(line.indexOf("*/")+2);
                            System.out.println(newLine);
                            newLines.add(newLine);
                        }
                    }
                    FileUtils.writeLines(file,"utf-8",newLines);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static List<File>  getAllFile(String fileDir) {
        List<File> fileList = new ArrayList<>();
        File file = new File(fileDir);
        File[] files = file.listFiles();// 获取目录下的所有文件或文件夹
        if (files == null) {// 如果目录为空，直接退出
            return null;
        }
        // 遍历，目录下的所有文件
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            } else if (f.isDirectory()) {
                fileList.addAll(getAllFile(f.getAbsolutePath()));
            }
        }
        return fileList;
    }
}
