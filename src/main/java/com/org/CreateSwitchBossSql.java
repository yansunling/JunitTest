package com.org;


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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        Set<String> newSqlList=new LinkedHashSet<>();
        for(BossData item:importDataList){
            String bossId="";
            String regex = "\\((.*?)\\)"; // 正则表达式，匹配括号内的内容
            Pattern pattern = Pattern.compile(regex);

            Matcher matcher = pattern.matcher(item.getOld_boss_name());
            if (matcher.find()) {
                bossId = matcher.group(1); // 使用group(1)获取括号内的内容
            }
            if(StringUtils.equals("——",item.getNew_boss_name())){
                continue;
            }
            if(StringUtils.isNotBlank(item.getNew_boss_name())){
                String sql="select emp_id from hcm.hcm_emp_ent where status_type='Job' and emp_name='"+item.getNew_boss_name()+"'";
                List<String> list = jdbcTemplate.queryForList(sql, String.class);
                if(list.size()!=1){
                    if(StringUtils.equals(item.getNew_boss_name(),"闫晓阳")){
                        bossId="T0074";
                    }else{
                        throw new RuntimeException("机构错误:"+ JSON.toJSONString(item));
                    }
                }
                bossId=list.get(0);

            }
            newSqlList.add("update hcm.hcm_emp_ent set boss_id='"+bossId+"' where emp_id ='"+item.getEmp_id()+"';");

        }
        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\hcm_boss.sql");
        FileUtils.writeLines(allFile,"utf-8",newSqlList);





    }










}
