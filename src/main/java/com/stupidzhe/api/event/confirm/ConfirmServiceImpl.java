package com.stupidzhe.api.event.confirm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stupidzhe.api.bean.HttpBean;
import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.domain.Confirmation;
import com.stupidzhe.api.domain.Http;
import com.stupidzhe.api.event.BaseService;
import com.stupidzhe.api.event.login.LoginService;
import com.stupidzhe.api.redis.BotCache;
import com.stupidzhe.api.redis.TradeCache;
import com.stupidzhe.api.util.ConfirmUtil;
import com.stupidzhe.api.util.TimeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.W on 2017/6/17.
 * 接口
 */
@Service
public class ConfirmServiceImpl extends BaseService implements ConfirmService {

    private static final Logger log = LoggerFactory.getLogger(Confirmation.class);
    private final TradeCache tradeCache;

    @Autowired
    public ConfirmServiceImpl(BotCache botCache, LoginService loginService, TradeCache tradeCache) {
        super(botCache, loginService);
        this.tradeCache = tradeCache;
    }

    @Override
    public String autoConfirm(Boolean accept, Bot bot, Confirmation confirmation) {
        String botRealNum = bot.getApiId() + ":" + bot.getBotNum();
        String offerId = tradeCache.getRecord(botRealNum);
        String allow = accept ? "allow" : "cancel";
        String url = "https://steamcommunity.com/mobileconf/ajaxop";
        Map<String, String> params = new HashMap<>();
        params.put("p", bot.getDeviceId());
        params.put("a", bot.getSteamId());
        params.put("m", "android");
        params.put("tag", "allow");
        params.put("op", allow);
        params.put("ck", confirmation.getKey());
        params.put("cid", confirmation.getConfId());
        long time = TimeUtil.getTimeStamp();
        String allowKey = ConfirmUtil.getKey(bot.getIdentitySecret(), "allow", time);
        params.put("k", allowKey);
        params.put("t", String.valueOf(time));
        Http Http = bot.getHttp();
        HttpBean res = Http.request(url, "GET", params, bot.getCookies().toString(), true, null, false);
        if (!validateCode(res, bot)) {
            log.error("session invalid:" + botRealNum);
            tradeCache.addRecord(botRealNum, offerId);
            return null;
        }
        if (!res.getResponse().contains("true")) {
            log.error("确认交易报价失败:" + botRealNum + ":" + offerId + ":" + confirmation.getConfId());
            tradeCache.addRecord(botRealNum, offerId);
            return null;
        }
        log.info("确认交易报价成功:" + botRealNum + ":" + offerId + ":" + confirmation.getConfId());
        // 用户有180秒时间接受报价
        return offerId;
    }

    /**
     * 获取需确认信息
     *
     * @param bot 机器人
     */
    public List<Confirmation> getConfirmations1(Bot bot) {

        String url = "https://steamcommunity.com/mobileconf/getlist";
        Map<String, String> params = new HashMap<>();
        params.put("p", bot.getDeviceId());
        params.put("a", bot.getSteamId());
        long time = TimeUtil.getTimeStamp();
//        long time = 1498376368L;
        String confKey = ConfirmUtil.getKey(bot.getIdentitySecret(), "conf", time);
        params.put("k", confKey);
        params.put("t", String.valueOf(time));
        params.put("m", "android");
        params.put("tag", "conf");

        List<Confirmation> confirmations = new LinkedList<>();
        Http http = bot.getHttp();

        HttpBean res = http.request(url, "GET", params, bot.getCookies().toString(), true, null, false);
        int code = res.getCode();
        if (!validateCode(res, bot)) {
            return null;
        }
        if (429 == code) {
            log.error(bot.getApiId() + ":" + bot.getBotNum() + " 访问过于频繁");
            return null;
        }
        String content = res.getResponse();
        if (null == content) {
            return null;
        }
        Document doc = Jsoup.parse(content);
        if (null != doc.getElementById("mobileconf_empty")) {
            if (content.contains("Invalid authenticator")) {
                log.error("time:" + time + " " + bot.getApiId() + ":" + bot.getBotNum() + " 认证器无效");
                return null;
            } else if (content.contains("Oh nooooooes!")) {
                log.error(bot.getApiId() + ":" + bot.getBotNum() + " 请求失败");
                return null;
            } else {
                return confirmations;
            }
        }
        Elements elements = doc.getElementsByClass("mobileconf_list_entry");
        for (Element element : elements) {
            Confirmation confirmation = new Confirmation();
            String img = element.getElementsByClass("mobileconf_list_entry_icon").get(0).getElementsByTag("img").attr("src").trim();
            confirmation.setIcon(img);
            confirmation.setConfId(element.attr("data-confid"));
            confirmation.setType(element.attr("data-type"));
            confirmation.setCreator(element.attr("data-creator"));
            confirmation.setKey(element.attr("data-key"));
            confirmation.setTitle(element.getElementsByClass("mobileconf_list_entry_description").get(0).getAllElements().get(1).text().trim());
            confirmation.setReceiving(element.getElementsByClass("mobileconf_list_entry_description").get(0).getAllElements().get(2).text().trim());
            confirmation.setTime(element.getElementsByClass("mobileconf_list_entry_description").get(0).getAllElements().get(3).text().trim());
            confirmations.add(confirmation);
        }
        return confirmations;
    }

    public List<Confirmation> getConfirmations(Bot bot) {

        String url = "https://steamcommunity.com/mobileconf/getlist";
        Map<String, String> params = new HashMap<>();
        params.put("p", bot.getDeviceId());
        params.put("a", bot.getSteamId());
        long time = TimeUtil.getTimeStamp();
        String confKey = ConfirmUtil.getKey(bot.getIdentitySecret(), "conf", time);
        params.put("k", confKey);
        params.put("t", String.valueOf(time));
        params.put("m", "android");
        params.put("tag", "conf");

        List<Confirmation> confirmations = new LinkedList<>();
        Http http = bot.getHttp();

        HttpBean res = http.request(url, "GET", params, bot.getCookies().toString(), true, null, false);
        JSONObject parse = (JSONObject) JSONObject.parse(res.getResponse());
        if (parse.getBoolean("success")) {
            JSONArray conf = parse.getJSONArray("conf");
            for (int i = 0; i < conf.size(); i++) {
                JSONObject jsonObject = conf.getJSONObject(i);
                Confirmation confirmation = new Confirmation();
                confirmation.setIcon(jsonObject.getString("icon"));
                confirmation.setConfId(jsonObject.getString("id"));
                confirmation.setType(jsonObject.getString("type"));
                confirmation.setCreator(jsonObject.getString("creator_id"));
                confirmation.setKey(jsonObject.getString("nonce"));
                confirmation.setTitle(jsonObject.getString("type_name"));
                confirmation.setReceiving(jsonObject.getString("accept"));
                confirmation.setTime(jsonObject.getString("creation_time"));
                confirmations.add(confirmation);
            }
        }
        return confirmations;
    }

//    public static void main(String... args) {
//        long time = TimeUtil.getTimeStamp();
//        String key = ConfirmUtil.getKey("VKIHHYD/X6q7lLoj725EVnytnAk=", "conf", 1498376143);
//        System.out.println(key);
//    }
}
