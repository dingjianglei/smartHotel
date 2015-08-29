package com.changhewl.hotel.util;

import redis.clients.jedis.Jedis;

/**
 * Created by Administrator on 2015/5/20 0020.
 */
public class JedisUtil {
    /**
     * 默认从本机获取连接
     * @return
     */
    public static Jedis createJedis(){
        Jedis jedis = new Jedis("127.0.0.1");
        return jedis;
    }

    public static Jedis createJedis(String host, int port) {
        Jedis jedis = new Jedis(host, port);

        return jedis;
    }
    public static Jedis createJedis(String host, int port, String passwrod) {
        Jedis jedis = new Jedis(host, port);

        if (passwrod!=null&&!"".equals(passwrod.trim()))
            jedis.auth(passwrod);
        return jedis;
    }
}
