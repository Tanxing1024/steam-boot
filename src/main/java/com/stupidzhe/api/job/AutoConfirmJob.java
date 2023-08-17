package com.stupidzhe.api.job;

import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.domain.Confirmation;
import com.stupidzhe.api.event.confirm.ConfirmService;
import com.stupidzhe.api.event.trade.TradeService;
import com.stupidzhe.api.redis.BotCache;
import com.stupidzhe.api.redis.CancelCache;
import com.stupidzhe.api.redis.ConfirmCache;
import com.stupidzhe.api.redis.TradeCache;
import com.stupidzhe.api.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.W on 2017/6/20.
 * 自动确认功能
 */
@Component
public class AutoConfirmJob extends Thread {

    private static final Logger log = LoggerFactory.getLogger(AutoConfirmJob.class);

    private final ConfirmService confirmService;
    private final TradeCache tradeCache;
    private final TradeService tradeService;
    private final CancelCache cancelCache;
    private final ConfirmCache confirmCache;

    private String offerId;

    private Confirmation confirmation;

    private final BotCache botCache;

    private final static long SECOND = 1000;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Bot bot;

    @Autowired
    public AutoConfirmJob(ConfirmService confirmService, TradeCache tradeCache, TradeService tradeService, CancelCache cancelCache, ConfirmCache confirmCache, BotCache botCache) {
        this.confirmService = confirmService;
        this.tradeCache = tradeCache;
        this.tradeService = tradeService;
        this.cancelCache = cancelCache;
        this.confirmCache = confirmCache;
        this.botCache = botCache;

    }

    public AutoConfirmJob(Bot bot, BotCache botCache, TradeCache tradeCache, TradeService tradeService, CancelCache cancelCache, ConfirmCache confirmCache) {
        this.bot = bot;
        this.botCache = botCache;
        this.tradeCache = tradeCache;
        this.tradeService = tradeService;
        this.cancelCache = cancelCache;
        this.confirmCache = confirmCache;
        confirmService = null;
    }

    /**
     * 定时获取确认信息
     */
    @Scheduled(fixedDelay = SECOND * 24)
    public void auto() {
        Map<String, Bot> botMap = botCache.getAllBot();
        for (String botId : botMap.keySet()) {
            Bot bot = botMap.get(botId);
            String botRealNum = bot.getApiId() + ":" + bot.getBotNum();
            if (tradeCache.hasRecord(botRealNum)) {
                List<Confirmation> confirmations = confirmService.getConfirmations(bot);
                if (null == confirmations) {
                    continue;
                }
                if (0 == confirmations.size()) {
                    continue;
                }
                for (int n = confirmations.size() - 1; n > -1; n--) {
                    AutoConfirmJob job = new AutoConfirmJob(botMap.get(botId), botCache, tradeCache, tradeService, cancelCache, confirmCache);
                    job.confirmation = confirmations.get(n);
                    job.offerId = confirmService.autoConfirm(true, job.bot, job.confirmation);
                    if (null == job.offerId) {
                        continue;
                    }
                    job.start();
                }
            }
        }
    }

    @Override
    public void run() {
        log.info(bot.getApiId() + ":" + bot.getBotNum() + ":延时开始");
        try {
            Thread.sleep(180000L);
        } catch (InterruptedException e) {
            log.error("180s延迟失败:" + bot.getApiId() + ":" + bot.getBotNum() + ":" + confirmation.getConfId());
            e.printStackTrace();
        }
        try {
            boolean cancelSuccess = tradeService.cancelTradeOffer(offerId, bot.getApiId() + ":" + bot.getBotNum());
            if (cancelSuccess) {
                log.info("用户未接受报价,已取消:" + offerId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("取消交易报价失败" + bot.getApiId() + ":" + bot.getBotNum() + ":" + offerId);
            cancelCache.addRecord(bot.getApiId() + ":" + bot.getBotNum(), offerId);
        }
    }
}
