package com.sqlFile;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.org.data.OrgData;
import com.org.util.SwitchUtil;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.HttpUtils;
import com.yd.utils.common.StringUtils;
import com.yd.utils.datasource.DruidComboPoolDataSource;
import com.yd.utils.datasource.YDDriverManagerDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UpdateRegionSql implements ApplicationContextAware {
    ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac = applicationContext;

    }

    @Qualifier("jdbcTemplateYL")
    @Autowired
    private JdbcTemplate jdbcTemplateYL;
    @Qualifier("dataSource")
    @Autowired
    private YDDriverManagerDataSource ydDriverManagerDataSource;

    @Test
    public void test() throws Exception {
        String sql = "select * from crm.crm_base_address where examine_flag='1' and ifnull(send_region_id,'')='' and address_type='stock' and longitude!='' and serial_no in('243d50d14a4878444a5942b737e6de3e','5147926d0db58ed14ec22ea91ed2aab0','6bc8f754f598b73f41b226db4dfc2e50','771418e8c0e7b46cc9aa0a312b5bb8f5','80e2ba8b457eb55495f2c42ed3d018de','c5ee24d52da5576213d9d97ff6622f47','cd222f290bb72eaaeae01032b9d6a12d','dc64fb5d8bc9dd96d539c7d9b8fae112','dc8ef4d0c8d798dfe5933b90acdaece9') ";
        List<Map<String, Object>> maps = jdbcTemplateYL.queryForList(sql);
        List<String> updateSql=new ArrayList<>();
        Map<String,String> busiType=new HashMap<>();
        busiType.put("520000","5");
        busiType.put("530000","3");
        busiType.put("650000","4");
        List<String> serialNoList=new ArrayList<>();

        maps.forEach(map->{
            try {
                String serialNo=map.get("serial_no")+"";
                String longitude=map.get("longitude")+"";
                String latitude=map.get("latitude")+"";
                String prov_code=map.get("prov_code")+"";

                String url="https://kp.tuolong56.com/gis/api/gis_region_region_info/location.do?actionId=gis_region_region_info_location";
                Map<String,String> param=new HashMap<>();
                param.put("busi_type",busiType.get(prov_code));
                param.put("lat",latitude.substring(0,2));
                param.put("lng",longitude.substring(0,2));
                String json = HttpUtils.postJSON(url, param);
                log.info("json:"+json);
                JSONObject jsonObject = JSON.parseObject(json);
                JSONArray data = jsonObject.getJSONArray("data");
                if(data==null||data.size()==0){
                    serialNoList.add(serialNo);
                    return;
                }
                JSONObject obj = data.getJSONObject(0);
                String regionId = obj.getString("region_id");
                updateSql.add("update crm.crm_base_address  set send_region_id='"+regionId+"' where serial_no='"+serialNo+"';");



            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        File allFile = new File("C:\\Users\\yansunling\\Desktop\\updateRegion.sql");
        FileUtils.writeLines(allFile, "utf-8", updateSql);

        System.out.println(StringUtils.join("','",serialNoList.toArray()));

    }


}
