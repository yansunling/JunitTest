package com.org.newSwitch;


import com.org.data.OrgData;
import com.org.util.SwitchUtil;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.StringUtils;
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
public class CreateSqlUpdateFoc implements ApplicationContextAware {
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


        List<String> tableFiles= Arrays.asList("crm.crm_base_cust_goods_name_limit");
        List<String> allTotalSet=new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for(String table:tableFiles){
                Set<String> totalSet=new LinkedHashSet<>();
                CountDownLatch countDownLatch = new CountDownLatch(orgDataList.size());
                for (OrgData orgData : orgDataList) {
                    executorService.submit(new FutureTask<String>(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            try {
                                String sql="select serial_no from "+table+" where review_org_id in("+orgData.getOldOrgId()+")";
                                List<String> result = jdbcTemplate.queryForList(sql, String.class);
                                if(CollectionUtil.isNotEmpty(result)){
                                    String title = "-- "+orgData.getNewOrgName() + "[" + orgData.getNewOrgId() + "]切" + orgData.getOldOrgName() + "[" + orgData.getOldOrgId() + "]\n\n\n";
                                    String updateSql="update "+table+" set review_org_id="+orgData.getNewOrgId()+" where serial_no in('"+StringUtils.join("','",result.toArray())+"');";
                                    totalSet.add(title);
                                    totalSet.add(updateSql+"\n\n");
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
            allTotalSet.addAll(totalSet);


        }

        String[] split = tableFiles.get(0).split("\\.");
        File allFile = new File("C:\\Users\\yansunling\\Desktop\\switchOrg\\" +  split[0] + "_next_update_temp.sql");
        FileUtils.writeLines(allFile, "utf-8", allTotalSet);



    }

}
