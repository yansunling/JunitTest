package com.dy.test.util;

import com.alibaba.fastjson.JSON;
import com.yd.common.cache.CIPRedisUtils;
import com.yd.common.utils.RedisUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.*;
import java.util.Properties;
import java.util.Set;

public class RedisUtil {
    private static Logger log = Logger.getLogger(RedisUtils.class);

    private static JedisPool jedisPool=null;

    private static JedisPool tlJedisPool=null;


    public RedisUtil() {
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

    public static Long  decrBy(String key) {

        Jedis jedis =  creatDataSource();

        Long aLong = jedis.decrBy(key, 1);



        jedisPool.returnBrokenResource(jedis);

        return aLong;


    }


    public static String  getString(String key) {

        Jedis jedis =  creatDataSource();
        String value = jedis.get(key);
        jedisPool.returnBrokenResource(jedis);

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





    public static Jedis creatDataSource() {
        if(jedisPool!=null){
            Jedis resource = jedisPool.getResource();
            return resource;
        }

        String dbConfigPath = "/root/properties/";
        InputStream is = null;
        File file = new File(dbConfigPath);
        if (!file.exists()) {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("redis.properties");
        } else {
            if (!dbConfigPath.endsWith("/")) {
                dbConfigPath = dbConfigPath + "/";
            }

            try {
                is = new FileInputStream(new File(dbConfigPath + "mq.properties"));
            } catch (FileNotFoundException var8) {
                throw new RuntimeException("加载数据库文件失败 : " + var8.getMessage(), var8);
            }
        }

        Properties prop = new Properties();

        try {
            prop.load((InputStream)is);
        } catch (IOException var7) {
            var7.printStackTrace();

        }
        String redisHost = prop.getProperty("redis.host");
        Integer redisPort = Integer.parseInt(prop.getProperty("redis.port"));
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        jedisPool = new JedisPool(poolConfig, redisHost, redisPort);
        return jedisPool.getResource();
    }

    public static  void returnDataSource(Jedis resource){
        jedisPool.returnResource(resource);
    }


}
