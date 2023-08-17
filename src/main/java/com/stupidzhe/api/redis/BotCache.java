package com.stupidzhe.api.redis;

import com.stupidzhe.api.domain.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Mr.W on 2017/6/26.
 * bot放入redis中
 */
@Component
public class BotCache {
    private final RedisTemplate<String, Bot> redisTemplate;
    private final Logger log = LoggerFactory.getLogger(BotCache.class);

    private Set<String> botSet = new HashSet<>();

    @Autowired
    public BotCache(RedisTemplate<String, Bot> redisTemplate) {
        this.redisTemplate = redisTemplate;
        botSet = redisTemplate.keys("bot_cache*");
    }

    public synchronized void addBot(String key, Bot bot) {
        redisTemplate.opsForValue().set("bot_cache:" + key, bot);
        botSet.add("bot_cache:" + key);
    }

    public synchronized Bot getBot(String key) {
        Bot bot = redisTemplate.opsForValue().get("bot_cache:" + key);
        return bot;
    }

    public synchronized Map<String, Bot> getAllBot() {
        HashMap<String, Bot> botMap = new HashMap<>();
        for (String val : botSet) {
            botMap.put(val, redisTemplate.opsForValue().get(val));
        }
        return botMap;
    }

    public synchronized void removeBot(String key) {
        redisTemplate.delete("bot_cache:" + key);
        botSet = redisTemplate.keys("bot_cache*");
    }

    public synchronized void addBufferBot(String key, Bot bot) {
        redisTemplate.opsForValue().set("bot_buffer_cache:" + key, bot);
    }

    public synchronized List<Bot> getBufferBot() {
        Set<String> bufferBotSet;
        List<Bot> botList = new LinkedList<>();
        try {
            bufferBotSet = redisTemplate.keys("bot_buffer_cache*");
            for (String key: bufferBotSet) {
                botList.add(redisTemplate.opsForValue().get(key));
            }
            return botList;
        }catch (RedisConnectionFailureException e) {
            log.error("超时");
            return botList;

        }
    }
    public synchronized void rmBufferBot(String key) {
        redisTemplate.delete("bot_buffer_cache:" + key);
    }
}
