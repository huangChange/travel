package com.ricard.util;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JedisUtils {
    private static JedisPool jedisPool;
    static {
        // 加载资源文件
        InputStream is = JedisUtils.class.getClassLoader().getResourceAsStream("jedis.properties");
        // 创建Properties对象
        Properties prop = new Properties();
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取数据，设置到JedisPoolConfig中
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(prop.getProperty("maxTotal")));
        config.setMaxIdle(Integer.parseInt(prop.getProperty("maxIdle")));

        jedisPool = new JedisPool(config, prop.getProperty("host"), Integer.parseInt(prop.getProperty("port")));
    }

    // 返回jedis对象
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
    // 释放资源
    public static void close(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }
}
