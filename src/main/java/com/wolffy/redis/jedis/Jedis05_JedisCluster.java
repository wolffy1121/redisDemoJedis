package com.wolffy.redis.jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * 集群的Jedis开发
 */
public class Jedis05_JedisCluster {
    public static void main(String[] args) {

        JedisCluster jedisCluster = getJedisCluster();
        jedisCluster.set("k1", "v1");
        String k1 = jedisCluster.get("k1");
        System.out.println(k1);
    }

    public static JedisCluster jc  = null ;

    public static JedisCluster getJedisCluster(){
        if(jc == null ){
            Set<HostAndPort> hostAndPorts = new HashSet<>() ;
            hostAndPorts.add(new HostAndPort("192.168.202.202", 6379));
            hostAndPorts.add(new HostAndPort("192.168.202.203", 6379));
            // ...
            //主要配置
            JedisPoolConfig jedisPoolConfig =new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(10); //最大可用连接数
            jedisPoolConfig.setMaxIdle(5); //最大闲置连接数
            jedisPoolConfig.setMinIdle(5); //最小闲置连接数
            jedisPoolConfig.setBlockWhenExhausted(true); //连接耗尽是否等待
            jedisPoolConfig.setMaxWaitMillis(2000); //等待时间
            jedisPoolConfig.setTestOnBorrow(true); //取连接的时候进行一下测试 ping pong

            jc = new JedisCluster(hostAndPorts,jedisPoolConfig);
        }
        return jc;
    }
}
