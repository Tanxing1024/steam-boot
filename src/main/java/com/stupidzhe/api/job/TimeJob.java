package com.stupidzhe.api.job;

import com.alibaba.fastjson.JSONObject;
import com.stupidzhe.api.bean.HttpBean;
import com.stupidzhe.api.domain.Http;
import com.stupidzhe.api.util.TimeUtil;
import org.apache.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by Mr.W on 2017/8/5.
 * 时差
 */
@Component
public class TimeJob {

    private final static long SECOND = 1000;

    @Scheduled(fixedDelay = SECOND * 60)
    public void stayLogin0() {
        Http http = new Http();
        HashMap<String, String> params = new HashMap<>();
        params.put("steamid", "0");
        HttpBean res = null;
        Long unixTime = System.currentTimeMillis() / 1000;
        res = http.request("https://api.steampowered.com/ITwoFactorService/QueryTime/v0001/", "POST",
                params, null, false, null, false);
        if (HttpStatus.SC_OK != (res != null ? res.getCode() : 0)) {
            return;
        }
        JSONObject json = JSONObject.parseObject(res.getResponse());
        Long serverTime = json.getJSONObject("response").getLong("server_time");
        TimeUtil.diff = serverTime - unixTime;
    }
}
