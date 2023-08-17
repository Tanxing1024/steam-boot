package com.stupidzhe.api.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Mr.W on 2017/8/9.
 */
@Component
public class TradeCheckCache {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public TradeCheckCache(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public synchronized void addRecord(String botNum, String val) {
        redisTemplate.opsForList().leftPush("send_check:" + botNum, val);
    }

    public synchronized String getRecord(String botNum) {
        return redisTemplate.opsForList().rightPop("send_check:" + botNum);
    }

    public synchronized long size(String botNum) {
        return redisTemplate.opsForList().size("send_check:" + botNum);
    }
}
