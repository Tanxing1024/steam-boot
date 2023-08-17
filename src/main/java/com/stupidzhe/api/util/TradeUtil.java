package com.stupidzhe.api.util;

import com.google.gson.Gson;

/**
 * Created by Mr.W on 2017/5/27.
 * 发送交易报价相关工具
 */
public class TradeUtil {

    private static final Gson gson = new Gson();

    /**
     * 获取url中的token
     */
    public static String getSendToken(String url) {
        return url.split("&token=")[1];
    }

    /**
     * 获取url中的partner
     */
    public static String getPartner(String url) {
        return url.split("partner=")[1].split("&token=")[0];
    }
}
