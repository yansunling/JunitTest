package com.org.old;


import com.excel.CJExcelUtil;
import com.org.data.OrgData;
import com.org.util.SwitchUtil;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.ExcelReader;
import com.yd.utils.common.StringUtils;
import com.yd.utils.datasource.DruidComboPoolDataSource;
import com.yd.utils.datasource.YDDriverManagerDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
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
import java.util.stream.Collectors;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateSwitchOrgJava implements ApplicationContextAware {
    ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac = applicationContext;

    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Qualifier("dataSource")
    @Autowired
    private YDDriverManagerDataSource ydDriverManagerDataSource;

    @Test
    public void test() throws Exception {
        String excelFilePath = "C:\\Users\\yansunling\\Desktop\\1.xlsx";
        List<OrgData> orgDataList = readExcel(excelFilePath);
        String filePath = getClass().getClassLoader().getResource("").getPath();
        String fileName="BMSP_report_daily_staff_detailMapper.xml";
        List<String> tableFiles = FileUtils.readLines(new File(filePath + "java/table/file/"+fileName), "utf-8");
        List<String> newJava = new ArrayList<>();
        for (String line : tableFiles) {
            for (OrgData item : orgDataList) {
                System.out.println(line);
                line = line.replaceAll("\"" + item.getOldOrgId()+"\"", "\"" + item.getNewOrgId()+"\"");
                line = line.replaceAll(item.getOldOrgName(), item.getNewOrgName());
//                line = line.replaceAll("\"" + item.getOldRegionId(), "\"" + item.getNewRegionId());
//                line = line.replaceAll("\"" + item.getOldRegionName(), "\"" + item.getNewRegionName());
//                line = line.replaceAll("\"" + item.getOldDistrictId(), "\"" + item.getNewDistrictId());
//                line = line.replaceAll("\"" + item.getOldDistrictName(), "\"" + item.getNewDistrictName());
                line = line.replaceAll("_" + item.getOldOrgId()+"\\(", "_"  + item.getNewOrgId()+"\\(");
                line = line.replaceAll("_" + item.getOldOrgId()+"=", "_"  + item.getNewOrgId()+"=");
                line = line.replaceAll("_" + item.getOldOrgId()+",", "_"  + item.getNewOrgId()+",");
                line = line.replaceAll("'" + item.getOldOrgId()+"'", "'"  + item.getNewOrgId()+"'");

                System.out.println(line);
            }
            newJava.add(line);
        }

        File javaFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\java\\"+fileName);
        FileUtils.writeLines(javaFile,"utf-8",newJava);

    }

    @SneakyThrows
    public static List<OrgData> readExcel(String filePath) {
        File file = new File(filePath);

        ExcelReader excelReader = new ExcelReader(file);
        excelReader.openFile();//打开文件
        List<Object[]> listResult = excelReader.getAllRow();//读取Excel的数据

        listResult = listResult.subList(1, listResult.size());

        List<OrgData> importDataList = CJExcelUtil.initImportExcelDatas(OrgData.titleMap, listResult, OrgData.class);
        Map<String, List<OrgData>> mapRef = new HashMap<>();
        for (int i = 0; i < importDataList.size(); i++) {
            OrgData importData = importDataList.get(i);
            if (StringUtils.isBlank(importData.getOldOrgId())) {
                continue;
            }
            if (StringUtils.isBlank(importData.getNewOrgId())) {
                importData.setNewOrgId(importDataList.get(i - 1).getNewOrgId());
            }
            List<OrgData> orgDataList = mapRef.get(importData.getNewOrgId());
            if (CollectionUtil.isEmpty(orgDataList)) {
                orgDataList = new ArrayList<>();
            }
            if (StringUtils.isBlank(importData.getNewRegionId())) {

                importData.setNewRegionId(SwitchUtil.newMap.get(importData.getNewRegionName()));
                importData.setNewDistrictId(SwitchUtil.newMap.get(importData.getNewDistrictName()));
                //设置旧大小区ID
                importData.setOldRegionId(SwitchUtil.newMap.get(importData.getOldRegionName()));
                importData.setOldDistrictId(SwitchUtil.newMap.get(importData.getOldDistrictName()));

            }
            orgDataList.add(importData);
            mapRef.put(importData.getNewOrgId(), orgDataList);
        }

        return importDataList;

    }


}
