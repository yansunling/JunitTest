package com.org.newSwitch;


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
public class CreateSqlUpdate implements ApplicationContextAware {
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
        String excelFilePath = "C:\\Users\\yansunling\\Desktop\\1.xlsx";
        List<OrgData> orgDataList = SwitchUtil.readExcel(excelFilePath);
        jdbcTemplate.setQueryTimeout(500);
       /* List<String> tableFiles = Arrays.asList("bmsp.bmsp_archives_purchase_contract","bmsp.bmsp_balance_outcost","bmsp.bmsp_bank_claim_detail",
                "bmsp.bmsp_bank_detail","bmsp.bmsp_borrow_out_statement_info","bmsp.bmsp_borrow_repay_oa_item","bmsp.bmsp_invoice_apply",
                "bmsp.bmsp_invoice_balance","bmsp.bmsp_oa_payment","bmsp.bmsp_process_invoice"
                ,"bmsp.bmsp_should_outcost_clear",
                "bmsp.bmsp_should_outorder","bmsp.bmsp_should_outorder_clear","bmsp.bmsp_ticket_arrive_cancel","bmsp.bmsp_ticket_arrive_clear");*/
       /* String path="C:/Users/yansunling/Desktop/switchOrg/notSwitchhcm.sql";
        List<String> fileList = FileUtils.readLines(new File(path), "utf-8");
        Set<String> tempTables=new LinkedHashSet<>();
        List<String> tableFiles=new ArrayList<>();
        for(String file:fileList){
            if(StringUtils.isNotBlank(file)){
                String[] split = file.split(":");
                tempTables.add(split[0]);
            }
        }
        tableFiles.addAll(tempTables);*/

        List<String> tableFiles= Arrays.asList("crm.crm_base_cust_goods_name_limit");


        Map<String,Map<String,String>> defaultSqlMap=new HashMap<>();
        Map<String,String> map= new LinkedHashMap<>();
        map.put("start_org_id","replace into  tmsp.tmsp_hand_schedule_car select serial_no,schedule_no,vehicle_id,driver_id,'<新机构ID>',start_date,end_date,route_way,route_way_id,end_org_id,line_orgs,version,remark,update_user_id,update_time,create_user_id,create_time from tmsp.tmsp_hand_schedule_car where start_org_id in('<老机构ID>');");
        map.put("end_org_id","replace into  tmsp.tmsp_hand_schedule_car select serial_no,schedule_no,vehicle_id,driver_id,start_org_id,start_date,end_date,route_way,route_way_id,'<新机构ID>',line_orgs,version,remark,update_user_id,update_time,create_user_id,create_time from tmsp.tmsp_hand_schedule_car where end_org_id in('<老机构ID>');");
        map.put("route_way_id","update tmsp.tmsp_hand_schedule_car set route_way_id = REPLACE(route_way_id,'<替换老机构ID集合>','<新机构ID>') where route_way_id regexp '<老机构ID集合>' ;");
        map.put("route_way","update tmsp.tmsp_hand_schedule_car set route_way=REPLACE(route_way,'<替换老机构名称集合>','<新机构名称>') where route_way regexp '<老机构名称集合>' ;");
        defaultSqlMap.put("tmsp.tmsp_hand_schedule_car",map);




        defaultSqlMap.put("mpp.mpp_prise_cust_disc", Collections.singletonMap("depart_org", "update mpp.mpp_prise_cust_disc set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID>');"));
        defaultSqlMap.put("mpp.mpp_prise_cust_version", Collections.singletonMap("depart_org", "update mpp.mpp_prise_cust_version set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID>');"));

        defaultSqlMap.put("mpp2.mpp2_offer_external", Collections.singletonMap("big_area", "update mpp2.mpp2_offer_external set big_area='<新机构大区ID>' where depart_org is null and big_area='<老机构大区ID单个>';"));

        defaultSqlMap.put("auth.auth_permission_settings_rule_item",Collections.singletonMap("rule_value", "update auth.auth_permission_settings_rule_item set rule_value=REPLACE(rule_value,'<替换老机构名称集合>','<新机构名称>') where  rule_value regexp '<老机构名称集合>' ;"));


        //默认条件
        Map<String,String> defaultWhere=new HashMap<>();
        defaultWhere.put("mpp2.mpp2_offer_external","depart_org is null");


        List<String> allTotalSet=new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for(String table:tableFiles){
            Map<String,String> sqlListTemp = defaultSqlMap.get(table);
            if(CollectionUtil.isEmpty(sqlListTemp)){
                sqlListTemp = buildBaseSql(table);
            }
            Map<String,String> sqlList=new LinkedHashMap<>();
            sqlList.putAll(sqlListTemp);
            if(CollectionUtil.isNotEmpty(sqlList)){
                Set<String> totalSet=new LinkedHashSet<>();
                CountDownLatch countDownLatch = new CountDownLatch(orgDataList.size());
                for (OrgData orgData : orgDataList) {
                    executorService.submit(new FutureTask<String>(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            try {
                                String title = orgData.getNewOrgName() + "[" + orgData.getNewOrgId() + "]切" + orgData.getOldOrgName() + "[" + orgData.getOldOrgId() + "]";
                                Set<String> newSqlList=new LinkedHashSet<>();


                                sqlList.forEach((column,item)->{
                                    String sql="select 1 as value from "+table+" where "+column+" in( "+orgData.getOldOrgId()+" ) limit 1";
                                    if(StringUtils.equals("route_way_id",column)){
                                        sql="select 1 as value from "+table+" where "+column+" regexp  "+orgData.getOldOrgId().replaceAll("','","|")+"  limit 1";
                                    }else if(StringUtils.equals("auth.auth_permission_settings_rule_item",table)){
                                        sql="select 1 as value from "+table+" where "+column+" regexp  "+orgData.getOldOrgName().replaceAll("','","|")+"  limit 1";
                                    }



                                    if(item.indexOf("单个")>0){
                                        String whereStr = defaultWhere.get(table);
                                        if(StringUtils.isNotBlank(whereStr)){
                                            whereStr=" and "+whereStr;
                                        }
                                        sql="select "+column+" as value from "+table+" where "+column+" in( "+orgData.getOldOrgId()+" ) "+whereStr+" group by "+column+" order by count(*) desc";
                                    }
                                    List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
                                    if(CollectionUtil.isNotEmpty(result)){
                                        if(item.indexOf("单个")>0){
                                            orgData.setOldRegionId("'"+result.get(0).get("value")+"'");
                                        }
                                        String newItem = SwitchUtil.replaceName(item, orgData);
                                        if(StringUtils.isNotBlank(newItem)){
                                            newSqlList.add(newItem);
                                        }
                                    }
                                });
                                if(CollectionUtil.isNotEmpty(newSqlList)){
                                    List<String> titleList=new ArrayList<>();
                                    titleList.add("\n\n--   "+title+"\n\n");
                                    titleList.addAll(newSqlList);
                                    totalSet.addAll(titleList);

                                }


                            } catch (Exception e) {
                                e.printStackTrace();

                            }finally {
                                countDownLatch.countDown();
                            }
                            return "";
                        }
                    }));
                }
                countDownLatch.await();
                String[] tables = table.split("\\.");
                File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\table\\" +  tables[1] + ".sql");
                FileUtils.writeLines(allFile, "utf-8", totalSet);
                allTotalSet.addAll(totalSet);
            }
        }

        String[] split = tableFiles.get(0).split("\\.");
        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\" +  split[0] + "_next_update_temp.sql");
        FileUtils.writeLines(allFile, "utf-8", allTotalSet);



    }

    @SneakyThrows
    public Map<String,String> buildBaseSql(String newTable) {
        List<String> orgList = new ArrayList<>();
        List<String> orgNameList = new ArrayList<>();
        String orgSql = "select org_id,org_name from hcm.hcm_org_info where org_id not in('25','990000011')";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(orgSql);
        maps.forEach(item -> {
            orgList.add(item.get("org_id") + "");
            orgNameList.add(item.get("org_name") + "");
        });
        Map<String,String> sqlList = new HashMap();
        String[] tables = newTable.split("\\.");
        String table = tables[1];
        try {
            String columnsSql = "select column_name from  information_schema.COLUMNS where table_name='" + table + "' " +
                    "and column_name not in('serial_no','create_user_id','update_user_id','remark','salesman_id','price_remark','product_type') and data_type not in('decimal','datetime','date','int') ";
            List<String> columnList = jdbcTemplate.queryForList(columnsSql, String.class);
            if (CollectionUtil.isNotEmpty(columnList)) {

                ExecutorService executorService = Executors.newFixedThreadPool(50);
                CountDownLatch countDownLatch = new CountDownLatch(columnList.size());

                columnList.forEach(tempColumn -> {

                    executorService.submit(new FutureTask<String>(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            try {

                                String column=tempColumn;

                                String dataSql = "select `" + column + "` from " + newTable + " where  length(`" + column + "`)>=4  limit 1";
                                List<String> valueList = jdbcTemplate.queryForList(dataSql, String.class);
                                if (CollectionUtil.isEmpty(valueList)) {
                                    return "";
                                }
                                String newValue = valueList.get(0);
                                if (StringUtils.isBlank(newValue)) {
                                    return "";
                                }
                                boolean concat = false;
                                if (newValue.indexOf(",") > 0) {
                                    String[] split = newValue.split(",");
                                    newValue = split[0];
                                    concat = true;
                                }
                                if(newValue.indexOf(">")>0){
                                    String[] split = newValue.split(">");
                                    newValue = split[0];
                                    concat = true;
                                }
                                if (SwitchUtil.containsChinese(column)) {
                                    column = "`" + column + "`";
                                }
                                if (orgList.contains(newValue)||(newValue.startsWith("25")&&newValue.indexOf(".")<0)||(newValue.startsWith("35")&&newValue.indexOf(".")<0)) {
                                    sqlList.put(column,SwitchUtil.matchColumn(column, newTable, "ID", concat));

                                } else if (orgNameList.contains(newValue)) {
                                    sqlList.put(column,SwitchUtil.matchColumn(column, newTable, "名称", concat));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }finally {
                                countDownLatch.countDown();
                            }
                            return "";
                        }
                    }));
                });
                countDownLatch.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlList;
    }


}
