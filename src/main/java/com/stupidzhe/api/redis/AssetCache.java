package com.stupidzhe.api.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Mr.W on 2017/8/9.
 * 存放交易饰品缓存
 */
@Component
public class AssetCache {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public AssetCache(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public synchronized void addRecord(String botNum, String assetId, String val, String time) {
        redisTemplate.opsForList().leftPush("asset_cache:" + botNum , val + "|" + assetId + "|" + time);
    }

    public synchronized String getRecord(String botNum) {
        return redisTemplate.opsForList().rightPop("asset_cache:" + botNum);
    }

    public synchronized Long size(String botNum) {
        return redisTemplate.opsForList().size( "asset_cache:" + botNum);
    }
}
