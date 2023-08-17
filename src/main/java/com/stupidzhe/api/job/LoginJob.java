package com.stupidzhe.api.job;

import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.event.login.LoginService;
import com.stupidzhe.api.redis.BotCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.W on 2017/6/14.
 * 保持登录用
 */
@Component
public class LoginJob extends Thread {

    private final LoginService loginService;

    private final BotCache botCache;

    private final static long SECOND = 1000;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Bot bot;

    private static final Logger log = LoggerFactory.getLogger(LoginJob.class);

    @Autowired
    public LoginJob(LoginService loginService, BotCache botCache) {
        this.loginService = loginService;
        this.botCache = botCache;
    }

    @Scheduled(fixedDelay = SECOND * 5)
    public void stayLogin0() {
        LoginJob job = new LoginJob(loginService, botCache);
        job.run();
    }

    @Scheduled(cron = "50 0 3 * * *")
    public void reLogin() {
        log.info("----------------bot socket----------------");
        log.info("机器人重新登陆:" + format.format(new Date()));
        log.info("------------------------------------------");
        Map<String, Bot> botMap = botCache.getAllBot();
        for (String botId : botMap.keySet()) {
            bot = botMap.get(botId);
            botCache.removeBot(bot.getApiId() + ":" + bot.getBotNum());
            botCache.addBufferBot(bot.getApiId() + ":" + bot.getBotNum(), bot);
        }
    }

    @Override
    public void run() {
        List<Bot> botList = botCache.getBufferBot();
        for (Bot bot : botList) {
            Date date = new Date();
            String startTime = format.format(date);
            try {
                boolean res = loginService.login(bot);
                date = new Date();
                String endTime = format.format(date);
                if (res) {
                    log.info("----------------bot socket----------------");
                    log.info("开始时间:" + startTime);
                    log.info("结果: "+ bot.getUserName() + "重新登陆成功");
                    log.info("结束时间:" + endTime);
                    log.info("------------------------------------------");

                } else {
                    log.error("----------------bot socket----------------");
                    log.error("开始时间:" + startTime);
                    log.error("结果: "+ bot.getUserName() + "重新登陆失败");
                    log.error("结束时间:" + endTime);
                    log.error("------------------------------------------");
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("----------------bot socket----------------");
                log.error("开始时间:" + startTime);
                log.error("结果: 重新登陆异常");
                log.error("------------------------------------------");
            }
        }
    }
}
