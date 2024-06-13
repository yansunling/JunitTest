package com.org.old;


import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.StringUtils;
import com.yd.utils.datasource.DruidComboPoolDataSource;
import com.yd.utils.datasource.YDDriverManagerDataSource;
import lombok.Data;
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
public class FindOrgItemInsertSql implements ApplicationContextAware {
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
        jdbcTemplate.setQueryTimeout(500);
        DruidComboPoolDataSource dataSource = (DruidComboPoolDataSource) ydDriverManagerDataSource.getObject();
        dataSource.setMaxActive(100);
        String orgSql = "select org_id,org_name from hcm.hcm_org_info where org_id!='25'";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(orgSql);
        List<String> orgList = new ArrayList<>();
        List<String> orgNameList = new ArrayList<>();
        maps.forEach(item -> {
            orgList.add(item.get("org_id") + "");
            orgNameList.add(item.get("org_name") + "");
        });
        List<String> skipColumns=Arrays.asList("big_area","small_area");
        String filePath = getClass().getClassLoader().getResource("").getPath();
        List<String> tableList = FileUtils.readLines(new File(filePath + "java/table/mpp_his.txt"), "utf-8");
        List<String> sqlList = new ArrayList<>();
        for (String table : tableList) {
            String newTable = "mpp." + table;
            String dataSql = "select * from  " + newTable + "  limit 1";

            List<Map<String, Object>> valueList = jdbcTemplate.queryForList(dataSql);
            if (CollectionUtil.isEmpty(valueList)) {
                continue;
            }
            Map<String,Object> newValue = valueList.get(0);
            Map<String,String> columnMap=new LinkedHashMap<>();

            newValue.forEach((column,value)->{
                if (orgList.contains(value)&&!skipColumns.contains(column)) {
                    columnMap.put(column,"'<新机构ID>'");
                }
            });

            if(CollectionUtil.isEmpty(columnMap)){
                continue;
            }
            StringBuffer sb=new StringBuffer();
            Set<String> keySet = newValue.keySet();
            sb.append("insert ignore into ").append(newTable);

            String join = StringUtils.join(",", keySet.toArray());
            sb.append("(").append(join).append(")select ");

            Set<String> columns = columnMap.keySet();

            for(String column:columns){
                join=join.replaceAll(column,columnMap.get(column));
                join=join.replaceAll("big_area","'<新机构大区ID>'");
                join=join.replaceAll("small_area","'<新机构小区ID>'");
                join=join.replaceAll("create_time","now()");
                join=join.replaceAll("update_time","now()");
            }
            String primarySql="SELECT COLUMN_NAME  " +
                    "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                    "WHERE TABLE_SCHEMA = 'mpp'   \n" +
                    "AND TABLE_NAME = '"+table+"'  " +
                    "AND CONSTRAINT_NAME = 'PRIMARY';";

            List<String> primaryKeys = jdbcTemplate.queryForList(primarySql, String.class);
            for(String primaryKey:primaryKeys){
                join=join.replaceAll(primaryKey,"concat(9,"+primaryKey+")");
            }
            sb.append(" ").append(join).append(" from ").append(newTable).append(" where ");
            String sql=sb.toString();
            for(String column:columns){
                String newSql=sql+column+" in('<老机构ID>');";
                sqlList.add(newSql);
            }



            String simpleItemTable=table.replaceAll("_his","_item_his");
            String itemTableSql="SELECT COUNT(*) " +
                    "FROM information_schema.tables " +
                    "WHERE table_schema = 'mpp' " +
                    "AND table_name = '"+simpleItemTable+"';";
            Integer num = jdbcTemplate.queryForObject(itemTableSql, Integer.class);
            if(num>0){
                String hisTable=newTable.replaceAll("_his","_item_his");
                String itemDataSql = "select * from  " + hisTable + "  limit 1";
                valueList = jdbcTemplate.queryForList(itemDataSql);
                if (CollectionUtil.isEmpty(valueList)) {
                    continue;
                }


                Map<String,Object> itemValue = valueList.get(0);
                String itemPrimarySql="SELECT COLUMN_NAME  " +
                        "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                        "WHERE TABLE_SCHEMA = 'mpp'   \n" +
                        "AND TABLE_NAME = '"+simpleItemTable+"'  " +
                        "AND CONSTRAINT_NAME = 'PRIMARY';";

                Set<String> itemKey = itemValue.keySet();
                StringBuffer itemSb=new StringBuffer();
                itemSb.append("insert ignore into ").append(hisTable);

                String itemJoin = StringUtils.join(",", itemKey.toArray());
                itemSb.append("(").append(itemJoin).append(")select ");



                List<String> itemPrimaryKeys = jdbcTemplate.queryForList(itemPrimarySql, String.class);
                itemPrimaryKeys.addAll(primaryKeys);

                Set<String> hashSet=new HashSet<>();
                hashSet.addAll(itemPrimaryKeys);
                hashSet.addAll(primaryKeys);

                for(String primaryKey:hashSet){
                    itemJoin=itemJoin.replaceAll(primaryKey,"concat(9,"+primaryKey+")");
                }

                itemSb.append(itemJoin).append(" from ").append(hisTable).append(" where ").append(primaryKeys.get(0)).append(" in(");
                itemSb.append("select "+primaryKeys.get(0)+" from "+newTable+" where ");
                for(String column:columns){
                    itemSb.append(column+" in('<老机构ID>'));");
                }
                sqlList.add(itemSb.toString());

            }



        }
        File outfile=new File("C:\\Users\\yansunling\\Desktop\\mpp_his.sql");
        FileUtils.writeLines(outfile,"utf-8",sqlList);



    }

    public boolean containsChinese(String str) {
        if (str == null) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (c >= '\u4e00' && c <= '\u9fa5') {
                return true;
            }
        }
        return false;
    }

    public static boolean deleteFolder(File folder) {
        if (folder == null) {
            return false;
        }
        if (!folder.exists()) {
            return false;
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    // 递归删除子文件夹
                    deleteFolder(f);
                } else {
                    // 删除文件
                    f.delete();
                }
            }
        }
        // 删除空文件夹
        return folder.delete();
    }

    public static boolean matchTables(String input) {
        if (input.startsWith("dtcx.s202") || input.startsWith("dctx.s202")) {
            return true;
        }
        return false;
    }





}
