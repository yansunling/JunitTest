package com.engine;

import cn.hutool.core.io.FileUtil;
import com.yd.utils.common.DateUtils;
import com.yd.utils.common.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.util.*;

public class FileToJson {
    public static void main(String[] args) throws Exception{
        File file=new File("C:\\Users\\yansunling\\Desktop\\condition.txt");
        List<String> list = FileUtil.readLines(file, "UTF-8");
        Map<String,Object> map=new LinkedHashMap<>();
        String today = DateUtils.format(new Date());
        list.forEach(item->{
            if(StringUtils.isNotBlank(item)){
                if(item.indexOf("time")>0){
                    map.put(item,"1");
                    if(item.indexOf("time_f")>0){
                        map.put(item,today+" 00:00:00");
                    }
                    if(item.indexOf("time_t")>0){
                        map.put(item,today+" 23:59:59");
                    }

                }else{
                    map.put(item,"1");
                }

            }
        });

        Map<String,Object> condition=new HashMap<>();
        condition.put("condition",map);

        ObjectMapper mapper = new ObjectMapper();
        String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(condition);
        System.out.println(indented);

        indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        System.out.println(indented);

    }
}
