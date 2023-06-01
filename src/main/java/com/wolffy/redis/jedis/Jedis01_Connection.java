package com.wolffy.redis.jedis;

import redis.clients.jedis.Jedis;

public class Jedis01_Connection {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("hadoop102",6379);
        String ping = jedis.ping();
        System.out.println(ping);
    }
}
