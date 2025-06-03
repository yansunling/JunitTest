package com.sqlFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SqlFileRead {
    public static void main(String[] args) throws Exception{
        File file=new File("C:\\Users\\yansunling\\Desktop\\allSql.sql");
        List<String> list = FileUtils.readLines(file, "utf-8");
        List<String> outLineList=new ArrayList<>();
        list.forEach(line->{
            if(line.indexOf("tmm.")<0&&line.indexOf("isp.")<0&&line.indexOf("pmp.")<0&&line.indexOf("bds.")<0&&line.indexOf("comp.")<0){

                outLineList.add(line);
            }
        });
        File outfile=new File("C:\\Users\\yansunling\\Desktop\\newSql.sql");
        FileUtils.writeLines(outfile,"utf-8",outLineList);

    }
}
