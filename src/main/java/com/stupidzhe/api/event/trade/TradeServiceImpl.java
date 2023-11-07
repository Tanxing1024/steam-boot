package com.stupidzhe.api.event.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.stupidzhe.api.bean.AssertBean;
import com.stupidzhe.api.bean.HttpBean;
import com.stupidzhe.api.bean.ResultBean;
import com.stupidzhe.api.bean.TradeSendBean;
import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.domain.Http;
import com.stupidzhe.api.domain.TradeResult;
import com.stupidzhe.api.event.BaseService;
import com.stupidzhe.api.event.login.LoginService;
import com.stupidzhe.api.redis.*;
import com.stupidzhe.api.util.HttpUtil;
import com.stupidzhe.api.util.SSLClientFactory;
import com.stupidzhe.api.util.TimeUtil;
import com.stupidzhe.api.util.TradeUtil;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Mr.W on 2017/5/23.
 * 交易相关
 */
@Service
public class TradeServiceImpl extends BaseService implements TradeService {

    private static final Logger log = LoggerFactory.getLogger(TradeServiceImpl.class);

    private final Gson gson = new Gson();

    private final TradeCache tradeCache;
    private final AcceptCache acceptCache;
    private final TradeCheckCache tradeCheckCache;
    private final AssetCache assetCache;

    public TradeServiceImpl(BotCache botCache, LoginService loginService, TradeCache tradeCache, AcceptCache acceptCache, TradeCheckCache tradeCheckCache, AssetCache assetCache) {
        super(botCache, loginService);
        this.tradeCache = tradeCache;
        this.acceptCache = acceptCache;
        this.tradeCheckCache = tradeCheckCache;
        this.assetCache = assetCache;
    }

    @Override
    public TradeResult tradeOffer(String url, String partner, AssertBean botSend, AssertBean himSend, String botNum, Integer apiId) {
        Bot bot = selectBot(botNum);
        if (null == bot) {
            return new TradeResult(false);
        }
        Http Http = bot.getHttp();
        HttpBean res;

        TradeSendBean tradeSendBean = new TradeSendBean();
        tradeSendBean.setNewversion(true);
        tradeSendBean.setVersion(1 + (botSend.getAssetid() != null ? botSend.getAssetid().size() : 0) + (himSend.getAssetid() != null ? himSend.getAssetid().size() : 0));

        TradeSendBean.Me me = tradeSendBean.getMe();
        TradeSendBean.Me them = tradeSendBean.getThem();
        List<TradeSendBean.Me.Asset> assetList = new LinkedList<>();

        addAsset(botSend, assetList, apiId);

        me.setAssets(assetList);
        me.setCurrency(new LinkedList<>());
        me.setReady(false);
        tradeSendBean.setMe(me);

        assetList = new LinkedList<>();
        addAsset(himSend, assetList, apiId);
        them.setAssets(assetList);
        them.setCurrency(new LinkedList<>());
        them.setReady(false);
        tradeSendBean.setThem(them);
        Map<String, String> params = new HashMap<>();
//        params.put("json_tradeoffer", "{\"newversion\":true,\"version\":2,\"me\":{\"assets\":[{\"appid\":730,\"contextid\":\"2\",\"amount\":1,\"assetid\":\"31302129333\"}],\"currency\":[],\"ready\":false},\"them\":{\"assets\":[],\"currency\":[],\"ready\":false}}");
        params.put("json_tradeoffer", gson.toJson(tradeSendBean, TradeSendBean.class));
        params.put("partner", partner);
        params.put("trade_offer_create_params", "{\"trade_offer_access_token\":\"" + TradeUtil.getSendToken(url) + "\"}");
//        params.put("trade_offer_create_params", "{}");
        params.put("tradeoffermessage", ("哇哈哈哈哈"));
        params.put("sessionid", bot.getSessionId());
        params.put("serverid", "1");
        log.info(params.toString());
        String co = bot.getCookies().toString();
        res = Http.request("https://steamcommunity.com/tradeoffer/new/send", HttpUtil.METHOD_POST, params, co, true, "https://steamcommunity.com/tradeoffer/new/?partner=", false);

        log.info(gson.toJson(res));
        if (!validateCode(res, bot)) {
            log.error("发送报价失败:" + res.getCode() + ":" + res.getResponse());
            return new TradeResult(false);
        }
        if (200 != res.getCode()) {
            log.error("发送报价失败:" + res.getCode() + ":" + res.getResponse());
            return new TradeResult(false);
        }
        TradeResult tradeResult = gson.fromJson(res.getResponse(), TradeResult.class);
        if (null != tradeResult.getStrError()) {
            tradeResult.setSuccess(false);
        }

        // 加入缓存
        tradeCache.addRecord(botNum, tradeResult.getTradeofferid());
//        tradeCheckCache.addRecord(botNum, tradeResult.getTradeofferid());
        StringBuilder assetIds = new StringBuilder();
        for (String assetId : botSend.getAssetid()) {
            assetIds.append(assetId).append("/");
        }
        assetCache.addRecord(botNum, assetIds.toString(), tradeResult.getTradeofferid(), String.valueOf(TimeUtil.getUnixTime()));

        tradeResult.setSuccess(true);
        return tradeResult;
    }

    @Override
    public synchronized boolean cancelTradeOffer(String tradeOfferId, String botNum) throws Exception {
        Bot bot = selectBot(botNum);
        if (null == bot) {
            throw new Exception("cancelTradeOffer error: bot is null:" + botNum);
        }
        String url = "https://steamcommunity.com/tradeoffer/" + tradeOfferId + "/cancel";
        String sessionId = bot.getSessionId();
        HttpBean res;
        Map<String, String> params = new HashMap<>();
        params.put("sessionid", sessionId);
        Http Http = bot.getHttp();
        res = Http.request(url, "POST", params, bot.getCookies().toString(), true, "https://steamcommunity.com/id/" + bot.getPlayName() + "/tradeoffers/", false);
        if (!validateCode(res, bot)) {
            throw new Exception("cancelTradeOffer error: session invalid");
        }
        log.info(res.getResponse());
        // 表示用户已接收或用户已拒绝
        if (res.getResponse().contains("{\"success\":11}")) {
            log.info("用户已接收或已拒绝:" + tradeOfferId);
            acceptCache.addRecord(bot.getApiId() + ":" + bot.getBotNum(), tradeOfferId);
            return false;
        }
        // 表示offerid有误
        if (res.getResponse().contains("{\"success\":42}")) {
            log.info("参数有误:" + tradeOfferId);
            return false;
        }
        // 表示成功取消
        return true;
    }

    @Override
    public Map<String, String> getTradeOffer(Bot bot) {
        Http Http = bot.getHttp();
        HttpBean res;

        res = Http.request("https://steamcommunity.com/id/" + bot.getPlayName() + "/tradeoffers/", "GET", null, bot.getCookies().toString(), true, "http://steamcommunity.com/id/" + bot.getPlayName() + "/tradeoffers/sent/", false);
        if (!validateCode(res, bot)) {
            return null;
        }
        String content = res.getResponse();
        if (null == content) {
            return null;
        }
        Document document = Jsoup.parse(content);
        Elements elements = document.getElementsByClass("active");
        if (0 == elements.size()) {
            return null;
        }
        Map<String, String> tradeOfferIdMap = new HashMap<>();
        for (Element element : elements) {
            String partner = element.parent().child(0).attr("onclick").split("'")[1];
            String offerId = element.getElementsByClass("link_overlay").get(0).attr("onclick").split("\'")[1].split("\'")[0];
            if (0 == element.getElementsByClass("secondary").get(0).getElementsByClass("trade_item").size()) {
                tradeOfferIdMap.put(offerId, partner);
            } else {

                // 如果自己需要饰品，则取消
                acceptTradeOffer(offerId, partner, bot.getApiId() + ":" + bot.getBotNum(), false);
            }
        }
        if (0 == elements.size()) {
            return null;
        }
        return tradeOfferIdMap;
    }


    @Override
    public ResultBean getBotTradeOffer(Bot bot) {
        try {

            Http Http = bot.getHttp();
            HttpBean res;
            String url = "https://api.steampowered.com/IEconService/GetTradeOffers/v1/?key=" + bot.getApiKey() + "&get_sent_offers=true&get_received_offers=true&get_descriptions=true&active_only=true&historical_only=false";
            res = Http.request(url, "GET", null, bot.getCookies().toString(), true, "", false);
            int code = res.getCode();
            if (200 != code) {
                return new ResultBean(false, "{message:'获取交易列表失败;'}");
            }

            String content = res.getResponse();
            log.info("返回：" + content);
            JSONObject result = JSON.parseObject(content);
            return new ResultBean(true, result.getString("response"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取交易列表失败:" + e.getMessage());
        }
        return new ResultBean(false, "{message:'获取交易列表失败;'}");
    }


    @Override
    public ResultBean getBotTradeOfferDetails(Bot bot, String offerId) {
        try {

            Http Http = bot.getHttp();
            HttpBean res;
            String url = "https://api.steampowered.com/IEconService/GetTradeOffer/v1/?key=" + bot.getApiKey() + "&tradeofferid=" + offerId + "&get_descriptions=true";
            res = Http.request(url, "GET", null, bot.getCookies().toString(), true, "", false);
            int code = res.getCode();
            if (200 != code) {
                return new ResultBean(false, "{message:'获取交易详情失败;'}");
            }
            String content = res.getResponse();
            JSONObject result = JSON.parseObject(content);
            return new ResultBean(true, result.getString("response"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取交易详情失败:" + e.getMessage());
        }
        return new ResultBean(false, "{message:'获取交易详情失败;'}");
    }


    @Override
    public boolean acceptTradeOffer(String tradeOfferId, String partner, String botNum, boolean accept) {
        Bot bot = selectBot(botNum);
        if (null == bot) {
            return false;
        }
        Map<String, String> params = new HashMap<>();
        params.put("captcha", "");
        params.put("partner", partner);
        params.put("serverid", "1");
        params.put("sessionid", bot.getSessionId());
        params.put("tradeofferid", tradeOfferId);
        HttpBean res;
        String acc = "/accept";
        if (!accept) {
            acc = "/decline";
        }
        try {
            Http Http = bot.getHttp();
            res = Http.request("https://steamcommunity.com/tradeoffer/" + tradeOfferId + acc, "POST", params, bot.getCookies().toString(), true, "https://steamcommunity.com/tradeoffer/" + tradeOfferId + "/", false);
            if (200 != res.getCode()) {
                validateCode(res, bot);
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            botCache.removeBot(bot.getApiId() + ":" + bot.getBotNum());
            botCache.addBufferBot(bot.getApiId() + ":" + bot.getBotNum(), bot);
            return false;
        }
    }

    @Override
    //http://steamcommunity.com/id/stupid_zhe/tradeoffers/sent/
    public boolean sendConfirmMessage(String offerId, String status, Bot bot) throws Exception {
        List<NameValuePair> postData = new LinkedList<>();
        postData.add(new BasicNameValuePair("offer_id", offerId));
        postData.add(new BasicNameValuePair("status", status));
        HttpPost post = new HttpPost(bot.getCallBackUrl());
        post.setEntity(new UrlEncodedFormEntity(postData, Consts.UTF_8));

        HttpClient client = SSLClientFactory.getNewHttpClient();
        try {
            client.execute(post);
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
        return true;
    }

    private Bot selectBot(String botNum) {
        return botCache.getBot(botNum);
    }

    private void addAsset(AssertBean assertBean, List<TradeSendBean.Me.Asset> assetList, Integer apiId) {
        if (null != assertBean.getAssetid()) {
            for (String assetId : assertBean.getAssetid()) {
                if (Objects.equals("", assetId)) {
                    break;
                }
                TradeSendBean.Me.Asset asset = new TradeSendBean.Me.Asset();
                asset.setAmount(1);
//                asset.setAppid(730);
                asset.setAppid(apiId);
                asset.setAssetid(assetId);
                asset.setContextid("2");
                assetList.add(asset);
            }
        }
    }
}
//        Map<String, String> params = new HashMap<>();
//        params.put("json_tradeoffer", "{\"newversion\":true,\"version\":2,\"me\":{\"assets\":[{\"appid\":730,\"contextid\":\"2\",\"amount\":1,\"assetid\":\"10383549337\"}],\"currency\":[],\"ready\":false},\"them\":{\"assets\":[],\"currency\":[],\"ready\":false}}");
//        params.put("json_tradeoffer", "{\"newversion\":true,\"version\":3,\"me\":{\"assets\":[{\"appid\":730,\"contextid\":\"2\",\"amount\":1,\"assetid\":\"\"}],\"currency\":[],\"ready\":false},\"them\":{\"assets\":[{\"appid\":730,\"contextid\":\"2\",\"amount\":1,\"assetid\":\"\"}],\"currency\":[],\"ready\":false}}");
//        params.put("json_tradeoffer", "{\"newversion\":true,\"version\":1,\"me\":{\"assets\":[],\"currency\":[],\"ready\":false},                                                                            \"them\":{\"assets\":[],\"currency\":[],\"ready\":false}}");
//        params.put("json_tradeoffer", "{\"newversion\":true,\"version\":2,\"me\":{\"assets\":[],\"currency\":[],\"ready\":false},\"them\":{\"assets\":[],\"currency\":[],\"ready\":false}}");
//        params.put("json_tradeoffer", "\"json_tradeoffer\" -> \"{\"newversion\":true,\"version\":2,\"me\":{\"assets\":[],\"currency\":[],\"ready\":false},\"them\":{\"assets\":[{\"appid\":730,\"contextid\":\"2\",\"amount\":1,\"assetid\":\"10433024830\"}],\"currency\":[],\"ready\":false}}\"");
//        params.put("partner", "76561198087970338");
//        params.put("trade_offer_create_params", "{\"trade_offer_access_token\":\"IrJdMOtQ\"}");
//        params.put("tradeoffermessage", ("csgo.ouruniv.com"));
//        params.put("sessionid", "ddbc60c69d7ce4ba6181a230");
//        params.put("serverid", "1");
//        Map<String, Object>res = null;
//        try {
//        res = Http.request("https://steamcommunity.com/tradeoffer/new/send",
//        "POST", params, "sessionid=ddbc60c69d7ce4ba6181a230; steamCountry=CN%7C2286fdad6698e8c1f7d609ccab78004a; steamLogin=76561198340230851%7C%7CF61EE274D130079844A7FC7DE1DB3569CB74E3BE; steamLoginSecure=76561198340230851%7C%7CE64FCCEA7D09AF3602F801D26F472FC22A2F0E6C; steamMachineAuth76561198340230851=EB953808A79D1751DA4BDB3C5019D54CEB2FA200; 29-May-2027 18:15:46 GMT=null; ", false,
//        "http://steamcommunity.com/tradeoffer/new/?partner=");
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
//        if (null != res) {
//        System.out.println(res.get("response"));
//        }