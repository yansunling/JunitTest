package com.dts;

import com.dy.test.util.RedisUtil;
import com.tlwl.dts.data.WorkflowTableUpdateData;
import com.tlwl.dts.util.WorkFlowUtil;
import com.yd.common.runtime.CIPRuntimeOperator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class DtsUtilTest implements ApplicationContextAware {
    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }

    @Test
    public  void test1() throws Exception{
        WorkflowTableUpdateData updateData=new WorkflowTableUpdateData();
        CIPRuntimeOperator operator=new CIPRuntimeOperator();
        operator.setSubject_id("T1113");
        operator.setSubject_name("颜孙令");
        updateData.setTableId("formtable_main_258");
        updateData.setRequestId("248401");
        Map<String, Object> fields=new HashMap<>();
        fields.put("pch","");
        updateData.setFields(fields);

        WorkFlowUtil.updateWorkFlowTable(updateData,operator);

    }

}
