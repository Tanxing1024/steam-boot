package com.stupidzhe.api.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Mr.W on 2017/8/8.
 * 存放用户接受的报价
 */
@Component
public class AcceptCache {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public AcceptCache(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public synchronized void addRecord(String botNum, String val) {
        redisTemplate.opsForList().leftPush("trade_accept:" + botNum, val);
    }

    public synchronized String getRecord(String botNum) {
        return redisTemplate.opsForList().rightPop("trade_accept:" + botNum);
    }

    public synchronized long size(String botNum) {
        return redisTemplate.opsForList().size("trade_accept:" + botNum);
    }

}
