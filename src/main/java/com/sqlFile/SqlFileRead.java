package com.sqlFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SqlFileRead {
    public static void main(String[] args) throws Exception{
        File file=new File("C:\\Users\\yansunling\\Desktop\\address.sql");
        List<String> list = FileUtils.readLines(file, "utf-8");
        List<String> outLineList=new ArrayList<>();
        list.forEach(line->{
            if(StringUtils.isNotBlank(line)){
                String startWord="customer_id=";
                String customerId = line.substring(line.indexOf(startWord)+startWord.length(), line.length() - 1)+",";
                System.out.println(customerId);
                outLineList.add(customerId);
            }
        });
        File outfile=new File("C:\\Users\\yansunling\\Desktop\\customer.text");
        FileUtils.writeLines(outfile,"utf-8",outLineList);

    }
}
