package com.stupidzhe.api.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Mr.W on 2017/8/8.
 * 用于存放待取消的交易报价
 */
@Component
public class CancelCache {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public CancelCache(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public synchronized void addRecord(String botNum, String val) {
        redisTemplate.opsForList().leftPush("confirm_cancel:" + botNum, val);
    }

    public synchronized String getRecord(String botNum) {
        return redisTemplate.opsForList().rightPop("confirm_cancel:" + botNum);
    }

    public synchronized long size(String botNum) {
        return redisTemplate.opsForList().size("confirm_cancel:" + botNum);
    }
}
