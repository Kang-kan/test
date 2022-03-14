package com.xxgame.admin.web.util;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * Jedis工具类
 */
public class JedisUtils {

    private static JedisPool pool;

    /**
     * 静态代码块中获取连接池
     */
    static {
        //读取属性文件的类，方法的参数是属性文件的主文件名，没有扩展名，从类路径下读取
        ResourceBundle bundle = ResourceBundle.getBundle("jedis");
        //通过bundle的方法获取值
        String host = bundle.getString("host");
        int maxWaitMillis = Integer.parseInt(bundle.getString("maxWaitMillis"));
        int maxTotal = Integer.parseInt(bundle.getString("maxTotal"));
        int port = Integer.parseInt(bundle.getString("port"));

        //1.创建配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //最长等待时间
        config.setMaxWaitMillis(maxWaitMillis);
        //最大连接数
        config.setMaxTotal(maxTotal);

        //2.通过配置对象创建连接池
        pool = new JedisPool(config, host, port);
    }

    /**
     * 从连接池中获取连接对象
     */
    public static Jedis getJedis() {
        return pool.getResource();
    }
}