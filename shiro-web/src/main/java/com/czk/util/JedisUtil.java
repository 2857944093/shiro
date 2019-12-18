package com.czk.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author: Created by ChenZK
 * @Create: 2019/12/17 11:04
 */
@Component
public class JedisUtil {

    @Resource
    private JedisPool jedisPool;

    private Jedis getResource() {
        return jedisPool.getResource();
    }

    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = getResource();
        jedis.set(key, value);
        jedis.close();
        return value;
    }


    public void expire(byte[] key, int i) {
        Jedis jedis = getResource();
        jedis.expire(key, i);
        jedis.close();
    }

    public byte[] get(byte[] key) {
        Jedis jedis = getResource();
        try {
            return jedis.get(key);
        }finally {
            jedis.close();
        }
    }

    public void del(byte[] key) {
        Jedis jedis = getResource();
        try {
            jedis.del(key);
        }finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String key) {
        Jedis jedis = getResource();
        try {
            return jedis.keys((key+"*").getBytes());
        }finally {
            jedis.close();
        }
    }

    public void delAll(String...args) {
        Jedis jedis = getResource();
        try {
            jedis.del(args);
        }finally {
            jedis.close();
        }
    }
}
