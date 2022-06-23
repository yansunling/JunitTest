package com.mybatis.test;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mybatis.mapper.TMSP_driver_message_configMapper;
import com.mybatis.po.TMSP_driver_message_configPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MybatisPlusTest implements ApplicationContextAware {

    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }
    @Autowired
    TMSP_driver_message_configMapper configMapper;

    @Test
    public  void test() throws Exception{


        List<TMSP_driver_message_configPO> pos = configMapper.selectList(Wrappers.<TMSP_driver_message_configPO>lambdaQuery()
                .ne(TMSP_driver_message_configPO::getOrg_id, "1"));
        System.out.println(JSONObject.toJSONString(pos));



    }


}
