package com.redis;

import com.dy.test.util.RedisUtil;
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

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class RedisTest implements ApplicationContextAware {
    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }

    private static JedisPool jedisPool=null;

    @Test
    public  void test() throws Exception{
        Jedis jedis = null;
        try {
            jedis = RedisUtil.creatDataSource();
            String script="f redis.call('setNx','test','1') then if redis.call('get','test')=='1' then return redis.call('expire','test',-1) else return 0 end end";
            String result = jedis.scriptLoad(script);
            System.out.println(result);


        } finally {
            RedisUtil.returnDataSource(jedis);
        }

    }

}
