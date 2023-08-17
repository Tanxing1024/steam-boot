package com.stupidzhe.api.util;

/**
 * Created by Mr.W on 2017/5/16.
 * get sth is related with Steam
 */
public class SteamUtil {

//    private static final Logger log = LoggerFactory.getLogger(SteamUtil.class);
//
//    private static final Gson gson = new Gson();
//
//    /**
//     * getSteamIdByClaimedId
//     *
//     * @param claimedId which is gotten by url which is sent by the Third website
//     * @return steamId
//     */
//    public static String getSteamIdByClaimedId(String claimedId) {
//        String[] strings = claimedId.split("/");
//        if (strings.length > 0) {
//            System.out.println(strings[strings.length - 1]);
//            return strings[strings.length - 1];
//        }
//        return null;
//    }
//
//    public static JSONObject getUserMsg(String steamId) {
//        String url = SteamAPI.PLAYER_URL.SUMMARIES_URL.replace("STEAM_ID", steamId);
//        return HttpUtil.getJson(url, HttpUtil.METHOD_GET, null);
//    }
//
//    /**
//     * 获取价格
//     *
//     */
//    public static PriceBean getLowPrice(String appId, String market_hash_name) {
//        String url = SteamAPI.PRICE_URL.replace("APP_ID", appId).replace("MARKET_HASH_NAME",
//                market_hash_name);
//        String content;
//        try {
//            content = HttpUtil.getJsonByProxy3(url, HttpUtil.METHOD_GET, null);
//        } catch (Exception e) {
//            log.error("请求价格信息发生错误:" + url);
//            return new PriceBean(false);
//        }
//        PriceBean priceBean = gson.fromJson(content, PriceBean.class);
//        if (null == priceBean) {
//            log.error("请求价格信息发生错误:" + url);
//            return new PriceBean(false);
//        }
//
////        log.info("请求价格信息成功");
//        return priceBean;
//    }
//
//    /**
//     * 获取库存清单
//     *
//     * @param steamId steamId
//     */
//    public static Inventory getInventory(String appId, String steamId) {
//        String url = CSGOAPI.INVENTORY_URL.replace("APP_ID", appId).replace("STEAM_ID", steamId);
//        String content;
//        try {
//            content = HttpUtil.getJsonByProxy3(url, HttpUtil.METHOD_GET, null);
//        } catch (Exception e) {
//            log.error("steamId:" + steamId + " 请求库存信息发生错误\n" + e.getMessage());
//            return new Inventory(false);
//        }
//        Inventory inventory = gson.fromJson(content, Inventory.class);
//        //判断是否成功，不成功返回null
//        if (null == inventory || !inventory.isSuccess()) {
//            log.error("steamId:" + steamId + " 请求库存信息发生错误");
//            return new Inventory(false);
//        }
//        log.info("steamId:" + steamId + " 请求库存接口完成");
//        return inventory;
//    }
}
