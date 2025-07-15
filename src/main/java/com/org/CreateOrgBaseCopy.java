package com.org;


import cn.hutool.core.util.IdUtil;
import com.org.data.OrgData;
import com.org.data.TableLinkData;
import com.org.util.SwitchUtil;
import com.yd.utils.common.SerialNoUtils;
import com.yd.utils.common.StringUtils;
import com.yd.utils.datasource.DruidComboPoolDataSource;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.*;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateOrgBaseCopy implements ApplicationContextAware {
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
        String excelFilePath = "C:\\Users\\yansunling\\Desktop\\1.xlsx";
        List<OrgData> orgDataList = SwitchUtil.readExcel(excelFilePath);
//        SwitchUtil.deleteFolder(new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\org\\"));
        jdbcTemplateYL.setQueryTimeout(500);
        DruidComboPoolDataSource dataSource = (DruidComboPoolDataSource) ydDriverManagerDataSource.getObject();
        dataSource.setMaxActive(100);
        String filePath = getClass().getClassLoader().getResource("").getPath();
        List<String> SqlList = FileUtils.readLines(new File(filePath + "java/table/tmsp_copy_base_table.sql"), "utf-8");
        for (OrgData orgData : orgDataList) {
            List<String> newSqlList = new ArrayList<>();
            String fileName = "新组织:"+orgData.getNewOrgName() + "[" + orgData.getNewOrgId() + "]旧组织:" + orgData.getOldOrgName() + "[" + orgData.getOldOrgId() + "]";
            String newFileName = fileName.replaceAll("'", "");
            SqlList.forEach(item->{
                String newItem = SwitchUtil.replaceName(item, orgData);
                newSqlList.add(newItem);
            });
            List<TableLinkData> linkDataList=new ArrayList<>();
//            linkDataList.add(new TableLinkData("mpp2.mpp2_prise_ver_cust","prise_cust_ver_id","depart_org","mpp2.mpp2_prise_ver_cust_item","prise_cust_item_id"));

            linkDataList.add(new TableLinkData("mpp2.mpp2_prise_dept_ver","prise_dept_ver_id","depart_org","mpp2.mpp2_prise_dept_ver_item","prise_dept_item_id"));


            linkDataList.forEach(item->{
                newSqlList.addAll(getLinkTable(orgData,item));
            });



            File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\org\\" + newFileName.replaceAll(":","") + ".sql");
            FileUtils.writeLines(allFile, "utf-8", newSqlList);
        }
    }

    private List<String> getLinkTable(OrgData orgData,TableLinkData linkData){

        String sql="select * from "+linkData.getMain_table()+" where "+linkData.getMain_org()+"="+orgData.getOldOrgId();
        List<Map<String, Object>> maps = jdbcTemplateYL.queryForList(sql);
        List<String> sqlList=new ArrayList<>();
        sqlList.add("\n\n");
        Map<String,String> columnRef=new HashMap<>();
        columnRef.put("depart_org","<新机构ID>");
        columnRef.put("big_area","<新机构大区ID>");
        columnRef.put("small_area","<新机构小区ID>");
        maps.forEach(row->{
            columnRef.put(linkData.getMain_column(), SerialNoUtils.getTimeSerialNo()+"");
            sqlList.add(buildSql(row,columnRef,linkData.getMain_table(),orgData));
            String linkSql="select * from "+linkData.getItem_table()+" where "+linkData.getMain_column()+"='"+row.get(linkData.getMain_column())+"' order by "+linkData.getItem_column();
            List<Map<String, Object>> itemMaps = jdbcTemplateYL.queryForList(linkSql);
            itemMaps.forEach(item->{
                columnRef.put(linkData.getItem_column(), SerialNoUtils.getTimeSerialNo()+"");
                sqlList.add(buildSql(item,columnRef,linkData.getItem_table(),orgData));
            });
            sqlList.add("\n\n");
        });

        return sqlList;

    }

    private String buildSql(Map<String, Object> item, Map<String,String> columnRef,String table,OrgData orgData){
        List<String> column=new ArrayList<>();
        List<Object> columnValue=new ArrayList<>();
        item.forEach((key,value)->{
            column.add(key);
            String refValue = columnRef.get(key);
            if(StringUtils.isNotBlank(refValue)){
                columnValue.add(refValue);
            }else{
                columnValue.add(value);
            }
        });
        String insertSql="insert into "+table+"("+StringUtils.join(",",column.toArray())+") VALUES('"+StringUtils.join("','",columnValue.toArray())+"');";
        insertSql = SwitchUtil.replaceName(insertSql, orgData);
        insertSql=insertSql.replaceAll("'NULL'","NULL");
        insertSql=insertSql.replaceAll("'null'","null");
        return insertSql;
    }




}
