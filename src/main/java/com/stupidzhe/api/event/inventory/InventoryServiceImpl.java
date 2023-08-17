package com.stupidzhe.api.event.inventory;

import com.stupidzhe.api.bean.HttpBean;
import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.domain.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Mr.W on 2017/7/9.
 * impl
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Override
    public String getInventory(Bot bot, String apiId) {
        Http http = bot.getHttp();
        String url = "https://steamcommunity.com/profiles/" + bot.getSteamId() + "/inventory/json/" + apiId + "/2/true/?l=english";
        HttpBean res = http.request(url, "GET", null, bot.getCookies().toString(), true, null, false);
        int code = res.getCode();
        if (200 != code) {
            log.error("请求库存失败:code=" + code);
            return null;
        }
        return res.getResponse();
    }
}
