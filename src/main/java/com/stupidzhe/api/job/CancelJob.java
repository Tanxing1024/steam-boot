package com.stupidzhe.api.job;

import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.event.trade.TradeService;
import com.stupidzhe.api.redis.BotCache;
import com.stupidzhe.api.redis.CancelCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Mr.W on 2017/8/8.
 * 定时撤销交易报价
 */
@Component
public class CancelJob extends Thread {
    private static final Logger log = LoggerFactory.getLogger(AutoConfirmJob.class);

    private final TradeService tradeService;
    private final BotCache botCache;
    private final CancelCache cancelCache;

    private final static long SECOND = 1000;

    @Autowired
    public CancelJob(TradeService tradeService, BotCache botCache, CancelCache cancelCache) {
        this.tradeService = tradeService;
        this.botCache = botCache;
        this.cancelCache = cancelCache;
    }

    private Bot bot;

    public CancelJob(Bot bot, TradeService tradeService, BotCache botCache, CancelCache cancelCache) {
        this.bot = bot;
        this.tradeService = tradeService;
        this.botCache = botCache;
        this.cancelCache = cancelCache;
    }

    /**
     * 定时
     */
    @Scheduled(fixedDelay = SECOND * 5)
    public void check() {
        Map<String, Bot> botMap = botCache.getAllBot();
        for (String botId : botMap.keySet()) {
            CancelJob job = new CancelJob(botMap.get(botId), tradeService, botCache, cancelCache);
            job.start();
        }
    }

    @Override
    public void start() {
        String botRealNum = bot.getApiId() + ":" + bot.getBotNum();
        long count = cancelCache.size(botRealNum);
        if (0 == count) {
            return;
        }
        for (int n = 0; n < count; n++) {

            String offerId = cancelCache.getRecord(botRealNum);
            boolean res = false;
            try {
                res = tradeService.cancelTradeOffer(botRealNum, offerId);
            } catch (Exception e) {
                cancelCache.addRecord(botRealNum, offerId);
            }
            if (!res) {
                cancelCache.addRecord(botRealNum, offerId);
            }
        }
    }

}
