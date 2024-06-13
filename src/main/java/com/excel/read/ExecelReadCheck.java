package com.excel.read;

import cn.hutool.core.bean.BeanUtil;
import com.excel.ExcelsUtil;
import com.excel.XLSXCovertCSVReader;
import com.excel.data.CheckData;
import com.util.ImgUtil;
import com.yd.utils.common.ImageUtil;
import com.yd.utils.common.StringUtils;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecelReadCheck {
    public static void main(String[] args) throws Exception{
       /* String filePath="C:\\Users\\yansunling\\Desktop\\check.xls";
        List<String[]> list = XLSXCovertCSVReader.readerExcel(filePath, "Sheet1", 3);
        List<CheckData> checkData = ExcelsUtil.excelToObj(list, CheckData.class);
        String dir="C:\\Users\\yansunling\\Desktop\\pic";
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs(); // 创建目录
        }
        List<Map<String,Object>> listData=new ArrayList();
        checkData.forEach(item->{
            Map<String, Object> objectMap = BeanUtil.beanToMap(item);
            String img = item.getImg();
            if(StringUtils.isNotBlank(img)){
                String content = img.split(",")[1];
                String type = img.split(";")[0].split("/")[1];
                String picPath=dir+"/"+item.getUser_name()+item.getUser_id()+"."+type;
                ImgUtil.Base64ToImage(content,picPath);
                objectMap.put("img",new File(picPath));
            }
            listData.add(objectMap);
        });*/

        List<Map<String,Object>> listData=new ArrayList();
        Map<String,Object> data=new HashMap<>();
        data.put("user_id","ddd\r\ndd");
        listData.add(data);

        String excelPath="C:\\Users\\yansunling\\Desktop\\checkImg.xls";
        ExcelsUtil.createExcel(excelPath,listData,CheckData.title);


    }
}
