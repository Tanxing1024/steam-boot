package com.stupidzhe.api.controller;

import com.stupidzhe.api.bean.ResultBean;
import com.stupidzhe.api.constant.PathConst;
import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.event.login.LoginService;
import com.stupidzhe.api.exception.LoginException;
import com.stupidzhe.api.redis.BotCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Mr.W on 2017/6/24.
 * 机器人相关控制器
 */
@RestController
@RequestMapping(value = PathConst.BASE1 + "/{api_id}/bot")
public class BotController extends BaseController {

    private final LoginService loginService;
    private final BotCache botCache;

    @Autowired
    public BotController(LoginService loginService, BotCache botCache) {
        this.loginService = loginService;
        this.botCache = botCache;
    }

    /**
     * 添加bot
     *
     * @return 结果
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResultBean addBot(@RequestParam("user_account") String userAccount, @RequestParam("play_name") String playName,
                             @RequestParam("user_psw") String userPsw, @RequestParam("steam_id") String steamId,
                             @RequestParam("shared_secret") String sharedSecret, @RequestParam("identity_secret") String identitySecret,
                             @RequestParam("bot_id") Integer botId, @RequestParam("callBackUrl") String callBackUrl, @PathVariable("api_id") String apiId) {
        Bot bot = new Bot();
        bot.setUserName(userAccount);
        bot.setUserPsw(userPsw);
        bot.setPlayName(playName);
        bot.setBotNum(botId);
        bot.setIdentitySecret(identitySecret);
        bot.setSharedSecret(sharedSecret);
        bot.setSteamId(steamId);
        bot.setApiId(apiId);
        bot.setCallBackUrl(callBackUrl);
        try {
            boolean res = loginService.login(bot);
            if (!res) {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    return new ResultBean(false, "{message:'登录失败:请重试;'}");
                }
                res = loginService.login(bot);
                if (!res) {
                    return new ResultBean(false, "{message:'登录失败:请重试;'}");
                } else {
                    return new ResultBean(true, gson.toJson(bot));
                }
            }
            botCache.addBot(apiId + ":" + botId, bot);
            return new ResultBean(true, gson.toJson(bot));
        } catch (LoginException e) {
            return new ResultBean(false, "登录失败:1.identity_secret错误;2.user_account或user_psw错误;");
        }
    }

    /**
     * 更新bot
     *
     * @return 结果
     */
    @RequestMapping(path = "/{botId}", method = RequestMethod.PUT)
    public ResultBean updateBot(@RequestParam("user_account") String userAccount, @RequestParam("play_name") String playName,
                                @RequestParam("user_psw") String userPsw, @RequestParam("steam_id") String steamId,
                                @RequestParam("shared_secret") String sharedSecret, @RequestParam("identity_secret") String identitySecret,
                                @RequestParam("bot_id") Integer botId, @PathVariable("api_id") String apiId) {
        Bot bot = botCache.getBot(apiId + ":" + botId);
        if (null == bot) {
            return new ResultBean(false, "更新失败:bot_id或api_id不匹配;");
        }
        bot.setUserName(userAccount);
        bot.setUserPsw(userPsw);
        bot.setPlayName(playName);
        bot.setBotNum(botId);
        bot.setIdentitySecret(identitySecret);
        bot.setSharedSecret(sharedSecret);
        bot.setSteamId(steamId);
        return new ResultBean(true, gson.toJson(bot));
    }

    /**
     * 删除指定bot
     *
     * @return 结果
     */
    @RequestMapping(path = "/{bot_id}", method = RequestMethod.DELETE)
    public ResultBean deleteBot(@PathVariable("bot_id") Integer botId, @PathVariable("api_id") String apiId) {
        Bot bot = botCache.getBot(apiId + ":" + botId);
        if (null == bot) {
            return new ResultBean(false, "更新失败:bot_id或api_id不匹配;");
        }
        botCache.removeBot(apiId + ":" + botId);
        botCache.rmBufferBot(apiId + ":" + botId);
        return new ResultBean(true);
    }

}
