package com.other.redis;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.yd.common.utils.RedisUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

public class MyRedisUtil {
    private static Logger log = Logger.getLogger(RedisUtils.class);

    private static JedisPool jedisPool=null;

    private static JedisPool tlJedisPool=null;


    public MyRedisUtil() {
    }

    public static <T> T getSingleMapValue(String key, String mapkey, Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = creatDataSource();
            String value = jedis.hget(key, mapkey);
            return com.yd.utils.common.StringUtils.isNotEmpty(value) ? JSON.parseObject(value, clazz) : null;
        } catch (Exception t) {
            t.printStackTrace();
        }finally {
            jedisPool.returnBrokenResource(jedis);
        }
        return null;
    }


    public static boolean putWithStringKey(String key, Object value, int expireTime) {
        boolean flag = true;
        boolean isJedisConnectExceptionOccured = false;
        String valueStr = JSON.toJSONString(value);
        Jedis jedis = null;

        try {
            jedis = creatDataSource();
            if (expireTime > 0) {
                jedis.setex(key, expireTime, valueStr);
            } else {
                jedis.set(key, valueStr);
            }
        } catch (Exception t) {

        }finally {
            jedisPool.returnBrokenResource(jedis);
        }
        return flag;
    }



    public static String asynGetRedisDayNo(String busiType, int length) {
        String today = DateUtil.format(new Date(), "yyyyMMdd");
        String redisKey = "tlwl:tmsp:public:cache:numstart:" + today + ":" + busiType;
        String value="";
        Jedis jedis = null;
        try {
            jedis = creatDataSource();

            String script = "local val = redis.call('incr', KEYS[1]) return val";
            // 执行Lua脚本
            Object result = jedis.eval(script, 1, redisKey);
            value = result.toString();
        } catch (Exception e) {
            log.info("getRedisDayNo error",e);
        }finally {
            if(jedis!=null){
                jedis.close();
            }


        }
        //数据累加
        return busiType + today + StringUtils.leftPad(value, length, "0");
    }











    public static Long  decrBy(String key) {

        Jedis jedis =  creatDataSource();

        Long aLong = jedis.decrBy(key, 1);



        jedisPool.returnResource(jedis);

        return aLong;


    }


    public static String  getString(String key) {

        Jedis jedis =  creatDataSource();
        String value = jedis.get(key);
        jedis.close();


        return value;
    }


    public static Set<String>  patternKey(String key) {

        Jedis jedis =  creatDataSource();

        Set<String> keys = jedis.keys(key);


        jedisPool.returnBrokenResource(jedis);

        return keys;
    }




    public static Long delByStr(String key) {
        boolean isJedisConnectExceptionOccured = false;
        Jedis jedis = null;

        Long var4;
        try {
            jedis = creatDataSource();
            long result = jedis.del(key);
            Long var5 = result;
            return var5;
        } catch (JedisConnectionException var10) {
            log.error(var10.getMessage());
            var10.printStackTrace();
            isJedisConnectExceptionOccured = true;
            var4 = -1L;
        } catch (Exception var11) {
            log.error(var11.getMessage());
            var11.printStackTrace();
            var4 = -1L;
            return var4;
        } finally {
            jedisPool.returnBrokenResource(jedis);
        }

        return var4;
    }





    public static boolean tlPutWithStringKey(String key, String valueStr, int expireTime) {
        boolean flag = true;
        boolean isJedisConnectExceptionOccured = false;
        Jedis jedis = null;

        try {
            jedis = tlCreatDataSource();
            if (expireTime > 0) {
                jedis.setex(key, expireTime, valueStr);
            } else {
                jedis.set(key, valueStr);
            }
        } catch (Exception t) {

        }finally {
            tlJedisPool.returnBrokenResource(jedis);
        }
        return flag;
    }


    private static Jedis tlCreatDataSource() {
        if(tlJedisPool!=null){
            Jedis resource = tlJedisPool.getResource();
            return resource;
        }


        String redisHost = "10.11.12.98";
        Integer redisPort = 6379;
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        tlJedisPool = new JedisPool(poolConfig, redisHost, redisPort);
        return tlJedisPool.getResource();
    }




    @SneakyThrows
    public static Jedis creatDataSource() {
        if (jedisPool != null) {
            Jedis resource = jedisPool.getResource();
            return resource;
        }
        String rootConfigFilePath = "/config.properties";
        Resource res = new ClassPathResource(rootConfigFilePath);
        File file = res.getFile();
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(file);
        prop.load(fis);
        fis.close();

        String redisHost = prop.getProperty("redis.host");
        Integer redisPort = Integer.parseInt(prop.getProperty("redis.port"));
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(10);
        poolConfig.setMaxTotal(1000);
        poolConfig.setMaxWaitMillis(100000L);
        poolConfig.setMinEvictableIdleTimeMillis(60000L);
        poolConfig.setTimeBetweenEvictionRunsMillis(30000L);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        jedisPool = new JedisPool(poolConfig, redisHost, redisPort);
        return jedisPool.getResource();
    }

    public static  void returnDataSource(Jedis resource){
        jedisPool.returnResource(resource);
    }


}
