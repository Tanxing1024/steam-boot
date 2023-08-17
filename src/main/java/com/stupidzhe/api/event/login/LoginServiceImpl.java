package com.stupidzhe.api.event.login;

import com.google.gson.Gson;
import com.stupidzhe.api.bean.DoLoginResultBean;
import com.stupidzhe.api.bean.HttpBean;
import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.domain.Http;
import com.stupidzhe.api.domain.RsaKey;
import com.stupidzhe.api.exception.LoginException;
import com.stupidzhe.api.redis.BotCache;
import com.stupidzhe.api.util.ConfirmUtil;
import com.stupidzhe.api.util.RSA;
import com.stupidzhe.api.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.W on 2017/5/23.
 * 实现
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    private static final Gson gson = new Gson();
    private final BotCache botCache;

    public LoginServiceImpl(BotCache botCache) {
        this.botCache = botCache;
    }

    @Override
    public boolean login(Bot bot) throws LoginException {

        Http Http = new Http();
        StringBuilder cookies = new StringBuilder("");

        //需要发送的数据 need-send data
        Map<String, String> data = new HashMap<>();
        try {

            //获取sessionId
            HttpBean res = Http.request("https://steamcommunity.com", "GET", data, null, true,
                    null, false);
            if (null != res.getCookies()) {
                cookies.append(res.getCookies());
                bot.setSessionId(res.getSession());
            }

            data.put("username", bot.getUserName());
            data.put("donotcache", Long.toString(TimeUtil.getTimeStamp() * 1000L));
//            data.put("donotcache", Long.toString(System.currentTimeMillis()));
            final String rsaResponse1;
            res = Http.request("https://steamcommunity.com/login/getrsakey", "POST", data, cookies.toString(), true,
                    "https://steamcommunity.com/login/home/?goto=", false);
            rsaResponse1 = String.valueOf(res.getResponse());
            if (null != res.getCookies()) {
                cookies.append(res.getCookies());
            }
            RsaKey rsaKey = gson.fromJson(rsaResponse1, RsaKey.class);

            // 第一次验证 first validate
            if (null == rsaKey || !rsaKey.isSuccess()) {
                log.error(rsaResponse1);
                throw new LoginException("get rsa_key1 error");
            }
            // 第一次获取Rsa公钥 first get key
            RSA rsa = new RSA(rsaKey.getPublickey_mod(), rsaKey.getPublickey_exp());
            //公钥加密 lock
            final String password64 = rsa.encrypt(bot.getUserPsw());

            log.info("登录中...");

            String time = URLEncoder.encode(rsaKey.getTimestamp(), "UTF-8");

            data.clear();
            data.put("password", password64);
            data.put("username", bot.getUserName());
            data.put("emailauth", "");
            data.put("emailsteamid", "");
            data.put("twofactorcode", "");
            data.put("rsatimestamp", time);
            data.put("remember_login", "true");
            data.put("donotcache", Long.toString(TimeUtil.getTimeStamp() * 1000L));
            res = Http.request("https://steamcommunity.com/login/dologin/", "POST", data, cookies.toString(), true,
                    "https://steamcommunity.com/login/home/?goto=", false);
            final String loginResponse1 = String.valueOf(res.getResponse());

            DoLoginResultBean doLoginResult1 = gson.fromJson(loginResponse1, DoLoginResultBean.class);

            if (!doLoginResult1.isRequires_twofactor()) {
                throw new LoginException(doLoginResult1.getMessage());
            }

            data.clear();
            data.put("username", bot.getUserName());
            data.put("donotcache", Long.toString(TimeUtil.getTimeStamp() * 1000L));
            res = Http.request("https://steamcommunity.com/login/getrsakey", "POST", data, cookies.toString(), true,
                    "https://steamcommunity.com/login/home/?goto=", false);
            String rsaResponse2 = String.valueOf(res.getResponse());

            if (null != res.getCookies()) {
                cookies.append(res.getCookies());
            }

            rsaKey = gson.fromJson(rsaResponse2, RsaKey.class);
            if (null == rsaKey || !rsaKey.isSuccess()) {
                throw new LoginException("get rsa_key2 error");
            }
            log.info("验证码自动输入中...");
            data = new HashMap<>();

            // 第二次获取Rsa公钥 second get key
            rsa = new RSA(rsaKey.getPublickey_mod(), rsaKey.getPublickey_exp());
            time = URLEncoder.encode(rsaKey.getTimestamp(), "UTF-8");

            final String password64_2 = rsa.encrypt(bot.getUserPsw());
            data.clear();
            data.put("password", password64_2);
            data.put("username", bot.getUserName());
            data.put("emailauth", "");
            data.put("emailsteamid", "");
            data.put("rsatimestamp", time);
            data.put("twofactorcode", ConfirmUtil.getGuard(bot.getSharedSecret()));
            data.put("remember_login", "false");
            data.put("donotcache", Long.toString(TimeUtil.getTimeStamp() * 1000L));
            res = Http.request("https://steamcommunity.com/login/dologin/", "POST", data, cookies.toString(), true,
                    "https://steamcommunity.com/login/home/?goto=", false);
            final String loginResponse2 = String.valueOf(res.getResponse());
            if (null != res.getCookies()) {
                cookies.append(res.getCookies());
            }
            DoLoginResultBean doLoginResult2 = gson.fromJson(loginResponse2, DoLoginResultBean.class);
            if (!doLoginResult2.isLogin_complete()) {
                log.error("登录失败:" + loginResponse2);
                return false;
            }
            res = Http.request("https://steamcommunity.com/market/eligibilitycheck/?goto=%2Fid%2F" + bot.getPlayName() + "%2Ftradeoffers%2F",
                    "GET", null, cookies.toString(), true, "http://steamcommunity.com/id/" + bot.getPlayName() + "/tradeoffers/sent/", true);
            if (null != res.getCookies()) {
                cookies.append(res.getCookies());
            }
            bot.setCookies(cookies);
            bot.setHttp(Http);
            bot.setDeviceId(ConfirmUtil.getDeviceID(bot.getSteamId()));
            botCache.rmBufferBot(bot.getApiId() + ":" + bot.getBotNum());
            botCache.addBot(bot.getApiId() + ":" + bot.getBotNum(), bot);
            log.info(bot.getApiId() + ":" + bot.getBotNum() + " 登录成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("登录失败:" + e.getMessage());
            return false;
        }
//            log.info(bot.getCookies());
        return true;

    }
}
