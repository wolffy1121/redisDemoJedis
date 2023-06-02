package com.wolffy.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class Jedis04_Sentinel {

    public static void main(String[] args) {
        //Jedis jedis = new Jedis("hadoop202",6379);
        Jedis jedis = getJedisFromSentinel();
        String ping = jedis.ping();
        System.out.println(ping);
    }

    public static JedisSentinelPool jsPool = null;

    public static Jedis getJedisFromSentinel() {
        if (jsPool == null) {
            Set<String> sentinelSet = new HashSet<>();
            sentinelSet.add("172.16.245.103:26379");
            sentinelSet.add("172.16.245.104:26379");
            //主要配置
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(10); //最大可用连接数
            jedisPoolConfig.setMaxIdle(5); //最大闲置连接数
            jedisPoolConfig.setMinIdle(5); //最小闲置连接数
            jedisPoolConfig.setBlockWhenExhausted(true); //连接耗尽是否等待
            jedisPoolConfig.setMaxWaitMillis(2000); //等待时间
            jedisPoolConfig.setTestOnBorrow(true); //取连接的时候进行一下测试 ping pong
            jsPool = new JedisSentinelPool("mymaster", sentinelSet, jedisPoolConfig);
        }

        return jsPool.getResource();
    }
}
