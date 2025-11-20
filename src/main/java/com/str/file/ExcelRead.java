package com.str.file;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class ExcelRead  {
    public static void main(String[] args) throws Exception{


       /* String filePath="C:/Users/yansunling/Desktop/106.xlsx";
        File file=new File(filePath);
        List<ExcelData> importDataList = CJExcelUtil.readData(file,ExcelData.titleMap, ExcelData.class);
        File textFile=new File("C:/Users/yansunling/Desktop/1.txt");
        String content = FileUtil.readAsString(textFile);
        importDataList.forEach(item->{
            if(content.indexOf(item.getName())<0){
                System.out.println(item.getName());
            }
        });*/

        String randomNum = ThreadLocalRandom.current().nextInt(100000, 1000000)+"";
        System.out.println(randomNum);
    }



    @Data
    public static class ExcelData{
        public static Map<String,String> titleMap=new LinkedHashMap<String,String>();
        static {
            titleMap.put("clazz","大区");
            titleMap.put("stu_no","省");
            titleMap.put("name","市");
        }
        private String clazz;
        private String stu_no;
        private String name;
        
    }

}
