package com.changhewl.hotel.redis;


import com.changhewl.hotel.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Administrator on 2015/5/20 0020.
 */
public class Test {
    public static void main(String[] args) {
        JedisPoolUtils.createJedisPool();
        // 从池中获取一个Jedis对象
        JedisPool pool=JedisPoolUtils.pool;
        Jedis jedis = pool.getResource();
        jedis.set("name","dingjianglei");
        if(jedis.exists("name")){
            System.out.println("存在KEY=name");
        }
        String keys = "name";

        // 删数据
        jedis.del(keys);
        // 存数据
        jedis.set(keys, "snowolf");
        // 取数据
        String value = jedis.get(keys);

        System.out.println(value);

        // 释放对象池

        pool.returnResource(jedis);
    }
}
