package com.stupidzhe.api.event;

import com.stupidzhe.api.bean.HttpBean;
import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.event.login.LoginService;
import com.stupidzhe.api.redis.BotCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Mr.W on 2017/5/28.
 * base
 */
@Service
public class BaseService {

    protected final BotCache botCache;
    protected final LoginService loginService;

    @Autowired
    public BaseService(BotCache botCache, LoginService loginService) {
        this.botCache = botCache;
        this.loginService = loginService;
    }

    public boolean validateCode(HttpBean httpBean, Bot bot) {
        if (httpBean.isSessionInvalid()) {
            botCache.removeBot(bot.getApiId() + ":" + bot.getBotNum());
            botCache.addBufferBot(bot.getApiId() + ":" + bot.getBotNum(), bot);
            return false;
        }
        return true;
    }
}
