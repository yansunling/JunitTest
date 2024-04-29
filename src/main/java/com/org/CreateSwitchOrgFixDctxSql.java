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
public class CreateSwitchOrgFixDctxSql implements ApplicationContextAware {
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
      /*  String orgSql="select org_id from tmsp.tmsp_net_org where org_status='run' and org_id not in('25010301') ";
        List<String> oldOrgList = jdbcTemplate.queryForList(orgSql, String.class);
        List<OrgData> orgDataList=new ArrayList<>();
        for(OrgData orgData:excelOrgDataList){
            String orgDataSource = orgData.getOldOrgId().replaceAll("'", "");
            String[] split = orgDataSource.split(",");
            for(String str:split){
                if(oldOrgList.contains(str)){
                    orgDataList.add(orgData);
                    break;
                }
            }
        }*/
        jdbcTemplate.setQueryTimeout(500);
        DruidComboPoolDataSource dataSource = (DruidComboPoolDataSource) ydDriverManagerDataSource.getObject();
        dataSource.setMaxActive(100);

        String sql = "select concat(table_schema,'.',table_name) from information_schema.`TABLES` where table_schema='dctx'  and table_type!='VIEW' " +
                " and table_name not regexp  's2024|_copy|cip_|foc_|crm_customer_market_report1' and table_name not in('dctx_plan_node_log','tmsp_region_goods_rpt','dctx_log_msg','dctx_theme_task','dctx_plan_log','cip_admin_codes','dctx_file_dir_file','dctx_theme_version','quartz_entity','dctx_theme_info','dctx_base_admin','cip_admin_domain','dctx_theme_task_notice','dctx_base_category','dctx_base_admin_user','dctx_base_plugin_param','dctx_base_plugin','dctx_log_data','dctx_log_resp','dctx_plan_node','dctx_plan_info_params','dctx_base_category2','dctx_file_dir','dctx_log_api_job_calback','dctx_base_category1','foc_plugins_kernel_plugins_info','foc_plugins_kernel_plugins_info_history','dctx_plan_info','dctx_log_api_job','dctx_log_api_log','dctx_api_task','cip_admin_access_ctrl','cip_admin_access_rel','cip_admin_access_res','cip_admin_auth_act2obj','cip_admin_auth_attr','cip_admin_auth_obj','cip_admin_auth_role','cip_admin_auth_user','cip_admin_busi_code','cip_admin_busi_code_model','cip_admin_commonquery','cip_admin_interface_whitelist','cip_admin_log_access','cip_admin_log_job','cip_admin_log_mdm','cip_admin_meta_str','cip_admin_op_log','cip_admin_question','cip_admin_queue','cip_admin_queue_type_dm','cip_admin_resource','cip_admin_resource_2_resource','cip_admin_role_2_resource','cip_admin_roles','cip_admin_settings_group','cip_admin_settings_item','cip_admin_settings_value','cip_admin_sql','cip_admin_text','cip_admin_user','cip_admin_user2res','cip_admin_user_2_roles','cip_admin_user_setting','cip_admin_views_node','cip_admin_views_node_2_node','dctx_api_task_params','dctx_base_group','dctx_base_group_user','dctx_file_dir_file_temp','dctx_plan_node_condition','dctx_theme_task_params','foc_plugins_auth_company','foc_plugins_auth_data_settings','foc_plugins_auth_data_settings_query','foc_plugins_auth_data_settings_value','foc_plugins_auth_emp','foc_plugins_auth_hr_org','foc_plugins_auth_org_2_org','foc_plugins_auth_position','foc_plugins_auth_position2role','foc_plugins_auth_resource','foc_plugins_auth_role_2_resource','foc_plugins_auth_roles','foc_plugins_auth_user','foc_plugins_auth_user_2_roles','foc_plugins_conf_common_setting','foc_plugins_kernel_core_version','foc_plugins_review_config','foc_plugins_review_config_detail','foc_plugins_review_config_detail_copy2','foc_plugins_review_config_detail_copy3','foc_plugins_review_config_detail_copy4','foc_plugins_review_log','foc_plugins_review_report','foc_plugins_review_rule','foc_plugins_review_rule_user','foc_plugins_review_rule_user_copy2','foc_plugins_review_sequence','tmsp_back_end_return_day','tmsp_back_end_return_month','tmsp_back_end_sign_day','tmsp_back_end_sign_month','tmsp_back_end_sign_t_day','tmsp_back_end_sign_t_month','tmsp_hand_order_st_fee')  ";
        List<String> tableFiles=jdbcTemplate.queryForList(sql, String.class);
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        Set<String> allSet=new LinkedHashSet<>();
        for(String table:tableFiles){
            Map<String, String> sqlList = buildBaseSql(table);
            if(CollectionUtil.isNotEmpty(sqlList)){
                Set<String> totalSet=new LinkedHashSet<>();

                CountDownLatch countDownLatch = new CountDownLatch(orgDataList.size());
                for (OrgData orgData : orgDataList) {
                    executorService.submit(new FutureTask<String>(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            try {
                                String title = orgData.getNewOrgName() + "[" + orgData.getNewOrgId() + "]切为" + orgData.getOldOrgName() + "[" + orgData.getOldOrgId() + "]";
                                Set<String> newSqlList=new LinkedHashSet<>();


                                sqlList.forEach((column,item)->{
                                    String sql="select 1 as value from "+table+" where "+column+" in( "+orgData.getOldOrgId()+") limit 1";
                                    List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
                                    if(CollectionUtil.isEmpty(result)){
                                        sql="select 1 as value from "+table+" where "+column+" regexp "+orgData.getOldOrgName().replaceAll("','","|")+" limit 1";
                                        result = jdbcTemplate.queryForList(sql);
                                    }
                                    if(CollectionUtil.isNotEmpty(result)){
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
                if(CollectionUtil.isNotEmpty(totalSet)){
                    String[] tables = table.split("\\.");
                    File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\dctx\\" +  tables[1] + ".sql");
                    FileUtils.writeLines(allFile, "utf-8", totalSet);
                    allSet.addAll(totalSet);
                }

            }
        }

        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\dctx\\dctx.sql");
        FileUtils.writeLines(allFile, "utf-8", allSet);



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
        String filePath = getClass().getClassLoader().getResource("").getPath();
        List<String> tableFiles = FileUtils.readLines(new File(filePath + "java/table/fixTable.tab"), "utf-8");

        try {
            String columnsSql = "select column_name from  information_schema.COLUMNS where table_name='" + table + "' and table_schema='"+tables[0]+"' " +
                    "and column_name not in('serial_no','create_user_id','update_user_id','remark','salesman_id','price_remark','product_type') and data_type not in('decimal','datetime','date','int') " +
                    " and column_name not REGEXP '送货片区|计费类型|送货大区|特殊|结|销售|否|件数|体积|日|品|月|重|性质|时间|城市|总|客户|数|算|单号|区域|运号|交货方式|交接类型|方式|分类|载体|一线|运输类型|车长|司机姓名|运输线路|营销标准|车型|车牌号|二线标记|贵阳' ";
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

                                String dataSql = "select `" + column + "` from " + newTable + " where  length(`" + column + "`)>=4   limit 1";
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
                                if (SwitchUtil.containsChinese(column)) {
                                    column = "`" + column + "`";
                                }
                                String type="ID";
                                if (orgNameList.contains(newValue)) {
                                    type="名称";
                                }
                                if(tableFiles.contains(newTable)){
                                    sqlList.put(column,SwitchUtil.replaceSql(column, newTable, type));
                                }else{
                                    sqlList.put(column,SwitchUtil.matchColumn(column, newTable, type, concat));
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
