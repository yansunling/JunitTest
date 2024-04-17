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
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateSwitchOrgFixTmspSql implements ApplicationContextAware {
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
        String excelFilePath = "C:\\Users\\yansunling\\Desktop\\TL_TMSP.xlsx";
        List<OrgData> orgDataList = SwitchUtil.readExcel(excelFilePath);
        SwitchUtil.deleteFolder(new File("C:\\Users\\yansunling\\Desktop\\table\\"));
        jdbcTemplate.setQueryTimeout(500);
        DruidComboPoolDataSource dataSource = (DruidComboPoolDataSource) ydDriverManagerDataSource.getObject();
        dataSource.setMaxActive(100);
        List<OrgData> newOrgDataList = new ArrayList<>();
        newOrgDataList.add(orgDataList.get(0));
        String filePath = getClass().getClassLoader().getResource("").getPath();
        List<String> tableFiles = FileUtils.readLines(new File(filePath + "java/table/tmsp.tab"), "utf-8");
        List<String> templateList=new ArrayList<>();

        Map<String,List<String>> defaultSqlMap=new HashMap<>();
        List<String> list= new ArrayList<>();
        list.add("update tmsp.tmsp_hand_schedule_car set start_org_id = '<新机构ID>' where start_org_id in('<老机构ID单个>');");
        list.add("update tmsp.tmsp_hand_schedule_car set end_org_id = '<新机构ID>' where end_org_id in('<老机构ID单个>');");
        list.add("update tmsp.tmsp_hand_schedule_car set route_way_id = REPLACE(route_way_id,'<替换老机构ID集合>','<新机构ID>') where route_way_id regexp '<老机构ID集合>' ;");
        list.add("update tmsp.tmsp_hand_schedule_car set route_way=REPLACE(route_way,'<替换老机构名称集合>','<新机构名称>') where route_way_id regexp '<老机构ID集合>' ;");
        defaultSqlMap.put("tmsp.tmsp_hand_schedule_car",list);


        for(String table:tableFiles){
            List<String> columns=new ArrayList<>();
            List<String> sqlList = defaultSqlMap.get(table);
            if(CollectionUtil.isEmpty(sqlList)){
                sqlList = buildBaseSql(table,columns);
            }
            templateList.addAll(sqlList);
            if(CollectionUtil.isNotEmpty(sqlList)){
                Set<String> newSqlList=new LinkedHashSet<>();
                for (OrgData orgData : orgDataList) {
                    String title = orgData.getNewOrgName() + "[" + orgData.getNewOrgId() + "]切为" + orgData.getOldOrgName() + "[" + orgData.getOldOrgId() + "]";
                    newSqlList.add("--   "+title);
                    newSqlList.add("\n\n");
                    sqlList.forEach(item->{
                        String newItem = SwitchUtil.replaceName(item, orgData);
                        if(StringUtils.isNotBlank(newItem)){
                            newSqlList.add(newItem);
                        }
                    });
                    newSqlList.add("\n\n");
                }
                String[] tables = table.split("\\.");
                File allFile = new File("C:\\Users\\yansunling\\Desktop\\table\\" +  tables[1] + ".sql");
                FileUtils.writeLines(allFile, "utf-8", newSqlList);
            }
        }

        File allFile = new File("C:\\Users\\yansunling\\Desktop\\table\\templateSql.sql");
        FileUtils.writeLines(allFile, "utf-8", templateList);

    }

    @SneakyThrows
    public List<String> buildBaseSql(String newTable,List<String> columns) {
        String orgSql = "select org_id,org_name from hcm.hcm_org_info where org_id not in('25','990000011')";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(orgSql);
        List<String> orgList = new ArrayList<>();
        List<String> orgNameList = new ArrayList<>();
        maps.forEach(item -> {
            orgList.add(item.get("org_id") + "");
            orgNameList.add(item.get("org_name") + "");
        });
        List<String> sqlList = new ArrayList<>();
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
                        sqlList.add(SwitchUtil.matchColumn(column, newTable, "ID", concat));

                    } else if (orgNameList.contains(newValue)) {
                        sqlList.add(SwitchUtil.matchColumn(column, newTable, "名称", concat));
                    }
                    columns.add(column);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlList;
    }


}