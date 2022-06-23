package com.sqlFile;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yd.common.exception.CIPServiceException;
import com.yd.common.runtime.CIPErrorCode;
import com.yd.utils.datasource.DruidComboPoolDataSource;
import com.yd.utils.datasource.YDDriverManagerDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SqlProcess implements ApplicationContextAware{
	ApplicationContext ac;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac=applicationContext;
		
	}
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Qualifier("dataSource")
    @Autowired
	private YDDriverManagerDataSource dataSource;

	@Test
	public  void test() throws Exception {


        DruidComboPoolDataSource sourceObject = (DruidComboPoolDataSource)dataSource.getObject();


        sourceObject.setMaxActive(100);
        sourceObject.setMaxWait(10000000L);


        //表名
		String tableSchema="crm";
		String tableSql="select table_name from information_schema.`TABLES` where table_schema=? and table_type='BASE TABLE' ";
        List<String> tableList = jdbcTemplate.queryForList(tableSql, String.class, new Object[]{tableSchema});

        ExecutorService executorService = Executors.newFixedThreadPool(30);
        final CountDownLatch latch = new CountDownLatch(tableList.size());

        List<String> notDownTable=new ArrayList<>();
        notDownTable.addAll(tableList);

        List<FutureTask> futureTaskList=new ArrayList<>();

        for(String tableName:tableList){


            FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
                @Override
                public String call() throws Exception {

                    String sql="select column_name from information_schema.`COLUMNS` where table_schema=? and table_name=? ";
                    List<String> columnList = jdbcTemplate.queryForList(sql, String.class, new Object[]{tableSchema, tableName});
                    String columnSql= StringUtils.join(columnList.toArray(),",");
                    String selectSql="select "+columnSql+" from "+tableSchema+"."+tableName+"  ";
                    jdbcTemplate.setQueryTimeout(6000);
                    jdbcTemplate.execute(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                            return conn.prepareStatement(selectSql, ResultSet.TYPE_FORWARD_ONLY,
                                    ResultSet.CONCUR_READ_ONLY);
                        }}, new PreparedStatementCallback<T>() {
                        @Override
                        public T doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
                            ResultSet rs =null;
                            try {
                                pstmt.setFetchSize(Integer.MIN_VALUE);
                                pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
                                rs= pstmt.executeQuery();
                                BufferedWriter bufferedWriter = SqlFileUtil.getBufferedWriter(tableName);
                                while(rs.next()){
                                    List<String> valueList=new ArrayList<>();
                                    for(String column:columnList){
                                        String value = rs.getObject(column)+"";
                                        if(StringUtils.equalsIgnoreCase("null",value)){
                                            valueList.add("null");
                                        }else{
                                            value=value.replace("'","''");

                                            valueList.add("'"+value+"'");
                                        }
                                    }
                                    String insertSql="insert ignore into " +tableSchema+"."+tableName+"("+columnSql+")values("
                                            +StringUtils.join(valueList.toArray(),",")+");\n";
                                    bufferedWriter.write(insertSql);
                                }
                                bufferedWriter.close();
                            } catch (Exception e) {
                                log.info("downLoad error",e);
                                throw new CIPServiceException(new CIPErrorCode(9991,e.getMessage()));

                            }finally {
                                if(rs!=null) {
                                    rs.close();
                                }
                            }
                            return null;
                        }
                    });
                    return null;
                }
            });
            executorService.submit(futureTask);
            futureTaskList.add(futureTask);
        }

       for(FutureTask futureTask:futureTaskList){
           futureTask.get();
       }
        System.out.println(executorService.isTerminated()+"------------------end----------");

        executorService.shutdown();


	}	


}
