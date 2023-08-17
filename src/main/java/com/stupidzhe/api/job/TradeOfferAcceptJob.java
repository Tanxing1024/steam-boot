package com.stupidzhe.api.job;

import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.event.trade.TradeService;
import com.stupidzhe.api.redis.BotCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Mr.W on 2017/6/23.
 * 自动接收交易报价
 */
@Component
public class TradeOfferAcceptJob extends Thread {

    private final TradeService tradeService;

    private final BotCache botCache;

    private final static long SECOND = 1000;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Logger log = LoggerFactory.getLogger(TradeOfferAcceptJob.class);

    private Bot bot;

    @Autowired
    public TradeOfferAcceptJob(TradeService tradeService, BotCache botCache) {
        this.tradeService = tradeService;
        this.botCache = botCache;
    }

    public TradeOfferAcceptJob(TradeService tradeService, Bot bot, BotCache botCache) {
        this.tradeService = tradeService;
        this.bot = bot;
        this.botCache = botCache;
    }

    /**
     * 自动接收对方发送来的交易报价（自己的饰品为空时）
     */
    @Scheduled(fixedDelay = SECOND * 30)
    public void autoAccept() {
        Map<String, Bot> botMap = botCache.getAllBot();
        for (String botId : botMap.keySet()) {
            TradeOfferAcceptJob job = new TradeOfferAcceptJob(tradeService, botMap.get(botId), botCache);
            job.run();
        }
    }

    @Override
    public void run() {
        Date date = new Date();
        String startTime = format.format(date);
        Map<String, String> offerIdMap = tradeService.getTradeOffer(bot);
        if (null == offerIdMap || 0 == offerIdMap.size()) {
            return;
        }
        int count = 0;
        for (String offerId : offerIdMap.keySet()) {
            if (tradeService.acceptTradeOffer(offerId, offerIdMap.get(offerId), bot.getApiId() + ":" + bot.getBotNum(), true))
                count++;
        }
        date = new Date();
        String endTime = format.format(date);
        log.info("----------------auto accept---------------");
        log.info("自动接收交易报价");
        log.info("开始时间:" + startTime);
        log.info(count + " / " + offerIdMap.size());
        log.info("结束时间:" + endTime);
        log.info("------------------------------------------");
    }

    //    @Async
//    @Scheduled(fixedRate = SECOND * 30)
    public void autoDecline() {
        Map<String, Bot> botMap = botCache.getAllBot();
        for (String botId : botMap.keySet())
            test(botMap.get(botId));
    }

    private void test(Bot bot) {
//        Map<String, String> offerIdMap = tradeService.getBotTradeOffer(bot);
//        if (null == offerIdMap || 0 == offerIdMap.size()) {
//            return;
//        }
//        for (String offerId : offerIdMap.keySet()) {
//            if (tradeService.cancelTradeOffer(offerId, offerIdMap.get(offerId), bot.getApiId() + ":" + bot.getBotNum())) ;
    }

}
