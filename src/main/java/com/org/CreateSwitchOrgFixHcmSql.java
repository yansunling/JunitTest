package com.org;


import com.org.data.OrgData;
import com.org.util.SwitchUtil;
import com.yd.utils.common.CollectionUtil;
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
public class CreateSwitchOrgFixHcmSql implements ApplicationContextAware {
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
        List<OrgData> orgDataList = SwitchUtil.readExcel(excelFilePath);
//        SwitchUtil.deleteFolder(new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\hcm\\"));
        jdbcTemplate.setQueryTimeout(500);
        DruidComboPoolDataSource dataSource = (DruidComboPoolDataSource) ydDriverManagerDataSource.getObject();
        dataSource.setMaxActive(100);
        List<OrgData> newOrgDataList = new ArrayList<>();
        newOrgDataList.add(orgDataList.get(0));
        String filePath = getClass().getClassLoader().getResource("").getPath();
        List<String> tableFiles = FileUtils.readLines(new File(filePath + "java/table/hcm/hcm.sql"), "utf-8");
        Set<String> newSqlList=new LinkedHashSet<>();

        for(OrgData orgData:orgDataList){
            for(String item:tableFiles){
                String newItem = SwitchUtil.replaceName(item, orgData);
                if(StringUtils.isNotBlank(newItem)){
                    newSqlList.add(newItem);
                }
            }
        }
        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\hcm\\hcm_org_info.sql");
        FileUtils.writeLines(allFile, "utf-8", newSqlList);


    }

    @SneakyThrows
    public Map<String,String> buildBaseSql(String newTable) {
        String orgSql = "select org_id,org_name from hcm.hcm_org_info where org_id not in('25','990000011')";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(orgSql);
        List<String> orgList = new ArrayList<>();
        List<String> orgNameList = new ArrayList<>();
        maps.forEach(item -> {
            orgList.add(item.get("org_id") + "");
            orgNameList.add(item.get("org_name") + "");
        });
        Map<String,String> sqlList = new HashMap();
        String[] tables = newTable.split("\\.");
        String table = tables[1];
        try {
            String columnsSql = "select column_name from  information_schema.COLUMNS where table_name='" + table + "' " +
                    "and column_name not in('serial_no','create_user_id','update_user_id','remark','salesman_id','price_remark') and data_type not in('decimal','datetime','date','int') ";
            List<String> columnList = jdbcTemplate.queryForList(columnsSql, String.class);
            if (CollectionUtil.isNotEmpty(columnList)) {

                columnList.forEach(column -> {
                    String dataSql = "select `" + column + "` from " + newTable + " where  length(`" + column + "`)>=4   limit 1";
                    List<String> valueList = jdbcTemplate.queryForList(dataSql, String.class);
                    if (CollectionUtil.isEmpty(valueList)) {
                        return;
                    }
                    String newValue = valueList.get(0);
                    if (StringUtils.isBlank(newValue)) {
                        return;
                    }
                    boolean concat = false;
                    if (newValue.indexOf(",") > 0) {
                        String[] split = newValue.split(",");
                        newValue = split[0];
                        concat = true;
                    }
                    if (SwitchUtil.containsChinese(column)) {
                        column = "`" + column + "`";
                    }
                    if (orgList.contains(newValue)) {
                        sqlList.put(column,SwitchUtil.matchColumn(column, newTable, "ID", concat));

                    } else if (orgNameList.contains(newValue)) {
                        sqlList.put(column,SwitchUtil.matchColumn(column, newTable, "名称", concat));
                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlList;
    }


}
