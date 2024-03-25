package com.str;


import cn.hutool.core.io.FileUtil;
import com.yd.utils.common.StringUtils;

import java.io.File;
import java.util.List;

public class FileRead {
    public static void main(String[] args) {
        String filePath="C:\\Users\\yansunling\\Desktop\\1.txt";
        File file=new File(filePath);
        List<String> lines = FileUtil.readLines(file, "GBK");

        lines.forEach(line->{
            if(StringUtils.isNotBlank(line)){
                String[] split = line.split("：");
                System.out.println("select '"+split[0].trim()+"' org_name,'"+split[1].trim()+"' url union all");


            }

        });


    }
}
