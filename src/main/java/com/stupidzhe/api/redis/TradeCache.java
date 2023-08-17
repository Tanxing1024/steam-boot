package com.stupidzhe.api.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Mr.W on 2017/7/17.
 * 交易报价缓存
 */
@Component
public class TradeCache {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public TradeCache(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public synchronized void addRecord(String botNum, String val) {
        redisTemplate.opsForList().leftPush("send_success:" + botNum, val);
    }
    public synchronized void rightPushRecord(String botNum, String val) {
        redisTemplate.opsForList().rightPush("send_success:" + botNum, val);
    }

    public synchronized String getRecord(String botNum) {
        return redisTemplate.opsForList().rightPop("send_success:" + botNum);
    }

    public synchronized boolean hasRecord(String botNum) {
        String res = redisTemplate.opsForList().rightPop("send_success:" + botNum);
        if (null != res) {
            redisTemplate.opsForList().rightPush("send_success:" + botNum, res);
            return true;
        }
        return false;

    }

    public long size(String botNum) {
        return redisTemplate.opsForList().size("send_success:" + botNum);
    }
}
