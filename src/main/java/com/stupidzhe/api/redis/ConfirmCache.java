package com.stupidzhe.api.redis;

import com.stupidzhe.api.domain.Confirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Mr.W on 2017/8/8.
 * 确认完毕后放入该缓存
 */
@Component
public class ConfirmCache {
    private final RedisTemplate<String, Confirmation> redisTemplate;

    @Autowired
    public ConfirmCache(RedisTemplate<String, Confirmation> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public synchronized void addRecord(String botNum, Confirmation val) {
        redisTemplate.opsForList().leftPush("confirm_cache:" + botNum, val);
    }

    public synchronized Confirmation getRecord(String botNum) {
        return redisTemplate.opsForList().rightPop("confirm_cache:" + botNum);
    }

    public synchronized long size(String botNum) {
        return redisTemplate.opsForList().size("confirm_cache:" + botNum);
    }
}