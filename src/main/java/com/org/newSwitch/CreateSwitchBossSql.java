package com.org.newSwitch;


import com.alibaba.fastjson.JSON;
import com.excel.CJExcelUtil;
import com.org.data.BossData;
import com.org.data.PositionData;
import com.org.util.SwitchUtil;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import com.yd.utils.datasource.YDDriverManagerDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.*;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateSwitchBossSql implements ApplicationContextAware {
    ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac = applicationContext;

    }


    @Qualifier("jdbcTemplateYL")
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void test() throws Exception {
        String filePath = "C:/Users/yansunling/Desktop/boss.xlsx";
        File file=new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据

        List<BossData> importDataList = CJExcelUtil.initImportExcelDatas(BossData.titleMap, listResult, BossData.class);
        Map<String, String> newMap = SwitchUtil.newMap;
        Set<String> newSqlList=new LinkedHashSet<>();
        Map<String,String> bossMap=new HashMap<>();

        Set<String> bossSet=new HashSet<>();

        for(BossData item:importDataList){
            if(StringUtils.isBlank(item.getOrg_name())){
                continue;
            }
            item.setOrg_id(newMap.get(item.getOrg_name()));
            if(StringUtils.isBlank(item.getOrg_id())){
                throw new RuntimeException("机构错误："+item.getOrg_name());
            }
            String sql="select emp_id from hcm.hcm_emp_ent where dept='"+item.getOrg_id()+"' and emp_id!='"+item.getBoss_id()+"'";
            List<String> userIds = jdbcTemplate.queryForList(sql, String.class);
            if(CollectionUtil.isNotEmpty(userIds)){
                newSqlList.add("update hcm.hcm_emp_ent set boss_id='"+item.getBoss_id()+"' where emp_id in('"+StringUtils.join("','",userIds.toArray())+"');");
            }
            bossMap.put(item.getOrg_id(),item.getBoss_id());

            bossSet.add(item.getBoss_id());
        }

        for(String empId:bossSet){
            String sql="select dept from hcm.hcm_emp_ent where emp_id='"+empId+"'";
            List<String> orgIds = jdbcTemplate.queryForList(sql, String.class);
            if(CollectionUtil.isNotEmpty(orgIds)){
                String orgId = orgIds.get(0);
                String parentOrgId = orgId.substring(0, orgId.length() - 2);
                String bossId = bossMap.get(parentOrgId);
                if(StringUtils.isNotBlank(bossId)){
                    newSqlList.add("update hcm.hcm_emp_ent set boss_id='"+bossId+"' where emp_id in('"+empId+"');");
                }
            }
        }
        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\hcm_boss.sql");
        FileUtils.writeLines(allFile,"utf-8",newSqlList);





    }










}
