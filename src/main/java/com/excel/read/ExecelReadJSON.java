package com.excel.read;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.excel.ExcelsUtil;
import com.excel.XLSXCovertCSVReader;
import com.excel.data.CheckData;
import com.util.ImgUtil;
import com.yd.utils.common.StringUtils;
import lombok.Data;
import org.aspectj.util.FileUtil;

import java.io.File;
import java.util.*;

public class ExecelReadJSON {
    public static void main(String[] args) throws Exception{
        String filePath="C:\\Users\\yansunling\\Desktop\\无标题.json";

        String json = FileUtil.readAsString(new File(filePath));
        RecordData recordData = JSON.parseObject(json, RecordData.class);
        List<CheckImgData> records = recordData.getRECORDS();
        String dir="C:\\Users\\yansunling\\Desktop\\pic";
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs(); // 创建目录
        }
        List<Map<String,Object>> listData=new ArrayList();
        records.forEach(item->{
            Map<String, Object> objectMap = BeanUtil.beanToMap(item);
            String img = item.getMediaids();
            if(StringUtils.isNotBlank(img)){
                String content = img.split(",")[1];
                String type = img.split(";")[0].split("/")[1];
                String picPath=dir+"/"+item.getEmp_name()+item.getEmp_id()+"."+type;
                ImgUtil.Base64ToImage(content,picPath);
                objectMap.put("mediaids",new File(picPath));
            }
            listData.add(objectMap);
        });

        String excelPath="C:\\Users\\yansunling\\Desktop\\checkImg.xls";
        ExcelsUtil.createExcel(excelPath,listData,CheckImgData.title);

    }
    @Data
    static class RecordData{
        private List<CheckImgData> RECORDS;
    }
    @Data
    static class CheckImgData{
        private String emp_id;
        private String emp_name;
        private String mediaids;
        static Map<String,String> title=new LinkedHashMap<>();
        static {
            title.put("emp_id","用户工号");
            title.put("emp_name","用户名称");
            title.put("mediaids","用户照片");
        }

    }
}
