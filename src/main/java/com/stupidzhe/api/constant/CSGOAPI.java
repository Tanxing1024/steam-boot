package com.stupidzhe.api.constant;

/**
 * Created by Mr.W on 2017/5/16.
 * CS:GO相关
 */
public interface CSGOAPI {

    /**
     * 库存URL
     */
    String INVENTORY_URL = "http://steamcommunity.com/profiles/STEAM_ID/inventory/json/APP_ID/2/true/?l=chinese";
    /**
     * 级别
     */
    interface INSTANCE_ID {

        /**
         * 消费级、普通级
         */
        String CONSUMER_GRADE = "302028390";

        /**
         * 军规级
         */
        String MIL_SPEC_GRADE = "188530139";

        /**
         * 机密级
         */
        String CLASSIFIED_GRADE = "480085569";

        /**
         * 受限级
         */
        String LIMIT_GRADE = "480085569";
    }
}

