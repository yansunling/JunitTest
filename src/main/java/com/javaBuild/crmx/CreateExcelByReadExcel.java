package com.javaBuild.crmx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.excel.CJExcelUtil;
import com.excel.ExcelsUtil;
import com.excel.data.CheckData;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CreateExcelByReadExcel implements ApplicationContextAware {

    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public  void test() throws Exception {

        String filePath="C:\\Users\\yansunling\\Desktop\\各大区区域划分1.0.xlsx";
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据
        List<ExcelData> importDataList = CJExcelUtil.initImportExcelDatas(titleMap, listResult, ExcelData.class);
        List<ExcelData> newDataList=new ArrayList<>();
        for(int i=0;i<importDataList.size();i++){
            ExcelData item = importDataList.get(i);
            if(StringUtils.equals(item.getArea_name(),"全部")){
                String sql="select area.area_code_name from mdm.mdm_ddic_geo_area area " +
                        "left join mdm.mdm_ddic_geo_city city on area.city_code=city.city_code " +
                        "where city.city_code_name=? ";
                List<String> areaList = jdbcTemplate.queryForList(sql, new Object[]{item.getCity_name()}, String.class);
                areaList.forEach(area->{
                    ExcelData data=new ExcelData();
                    BeanUtils.copyProperties(item,data);
                    data.setArea_name(area);
                    if(StringUtils.isNotBlank(item.getExcept_name())&&item.getExcept_name().indexOf(area)>=0){
                        return;
                    }
                    newDataList.add(data);
                });
            }else{
                newDataList.add(item);
            }
        }
        String json=JSON.toJSONString(newDataList);
        List<Map<String, Object>> listData = JSONArray.parseObject(json, List.class);
        String excelPath="C:\\Users\\yansunling\\Desktop\\checkImg.xls";

        ExcelsUtil.createExcel(excelPath,listData,titleMap );

    }

    public static Map<String,String> titleMap=new LinkedHashMap<String,String>();

    static {
        titleMap.put("region_name","大区");
        titleMap.put("prov_name","省");
        titleMap.put("city_name","市");
        titleMap.put("area_name","区");
        titleMap.put("except_name","排除");
    }
    @Data
    public static class ExcelData{
        private String region_name;
        private String prov_name;
        private String city_name;
        private String area_name;
        private String except_name;
    }

}
