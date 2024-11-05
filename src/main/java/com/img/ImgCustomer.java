package com.img;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.http.DownFsmFileUtil;
import com.http.GetDownFileUtil;
import com.yd.utils.common.StringUtils;
import lombok.Data;
import org.aspectj.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ImgCustomer {
    public static void main(String[] args) throws Exception{
        String filePath="C:\\Users\\yansunling\\Desktop\\无标题.json";

        String json = FileUtil.readAsString(new File(filePath));
        RecordData recordData = JSON.parseObject(json, RecordData.class);
        List<CheckImgData> records = recordData.getRECORDS();


        for(int i=0;i<records.size();i++){
            CheckImgData item = records.get(i);
            DownFsmFileUtil.downFile("https://kp.tuolong56.com",item.getFile_seq_no(),item.getCustomer_id());

        }



    }
    @Data
    static class RecordData{
        private List<CheckImgData> RECORDS;
    }
    @Data
    static class CheckImgData{
        private String customer_id;
        private String file_seq_no;
        static Map<String,String> title=new LinkedHashMap<>();
        static {
            title.put("customer_id","用户工号");
            title.put("file_seq_no","用户名称");
        }

    }
}
