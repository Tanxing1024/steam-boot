package com.stupidzhe.api.job;

import com.google.gson.Gson;
import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.domain.Inventory;
import com.stupidzhe.api.event.inventory.InventoryService;
import com.stupidzhe.api.event.trade.TradeService;
import com.stupidzhe.api.redis.AssetCache;
import com.stupidzhe.api.redis.BotCache;
import com.stupidzhe.api.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Mr.W on 2017/7/17.
 * 获取用户是否确认交易报价定时器
 */
@Component
public class UserConfirmValidateJob {

    private final BotCache botCache;
    private final AssetCache assetCache;
    private final InventoryService inventoryService;
    private final TradeService tradeService;

    private final static long SECOND = 1000;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Logger log = LoggerFactory.getLogger(TradeOfferAcceptJob.class);

    private final Gson gson = new Gson();

    private Bot bot;


    @Autowired
    public UserConfirmValidateJob(BotCache botCache, AssetCache assetCache, InventoryService inventoryService, TradeService tradeService) {
        this.botCache = botCache;
        this.assetCache = assetCache;
        this.inventoryService = inventoryService;
        this.tradeService = tradeService;
    }


    @Scheduled(fixedDelay = SECOND * 20)
    public void validate() {
        Map<String, Bot> botMap = botCache.getAllBot();
        for (String botId : botMap.keySet()) {
            this.run(botMap.get(botId));
        }
    }

    private void run(Bot bot) {
        Date date = new Date();
        String startTime = format.format(date);
        String botRealNum = bot.getApiId() + ":" + bot.getBotNum();
        long size = assetCache.size(botRealNum);
        if (0 == size) {
            return;
        }
        String i = inventoryService.getInventory(bot, bot.getApiId());
        Inventory inventory = gson.fromJson(i, Inventory.class);
        if (null == inventory) {
            return;
        }
        Map<String, Inventory.RgInventory> rgInventoryMap = inventory.getRgInventory();
        int res = 0;
        for (int n = 0; n < size; n++) {
            int m = 0;
            String ss = assetCache.getRecord(bot.getApiId() + ":" + bot.getBotNum());
            String offerId = ss.split("[|]")[0];
            String assetIds = ss.split("[|]")[1];
            String time = ss.split("[|]")[2];
            if (420 < (TimeUtil.getUnixTime() - Long.valueOf(time))) {
                continue;
            }
            for (String assetId : assetIds.split("/")) {
                for (String key : rgInventoryMap.keySet()) {
                    if (key.equals(assetId)) {
                        break;
                    }
                    m++;
                }
                if (m == rgInventoryMap.size()) {
                    try {
                        tradeService.sendConfirmMessage(offerId, "accepted", bot);
                        res++;
                    } catch (Exception e) {
                        assetCache.addRecord(botRealNum, assetId, offerId, time);
                        e.printStackTrace();
                    }
                } else {
                    assetCache.addRecord(botRealNum, assetId, offerId, time);
                }
            }
        }
        if (0 == res) {
            return;
        }

        date = new Date();
        String endTime = format.format(date);
        log.info("-------------get user confirm-------------");
        log.info("获取交易报价确认状态");
        log.info("机器人:" + bot.getApiId() + ":" + bot.getBotNum());
        log.info("开始时间:" + startTime);
        log.info("获取到" + res + "条信息已被用户确认");
        log.info("结束时间:" + endTime);
        log.info("------------------------------------------");
    }
}
