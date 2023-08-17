package com.stupidzhe.api.bean;

/**
 * Created by Mr.W on 2017/5/28.
 *{"success":true,"lowest_price":"\u00a5 143","volume":"16","median_price":"\u00a5 131.95"}
 */
public class PriceBean {

    public PriceBean(boolean success) {
        this.success = success;
    }

    private boolean success;

    private String lowest_price;

    private String volume;

    private String median_price;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getLowest_price() {
        return lowest_price;
    }

    public void setLowest_price(String lowest_price) {
        this.lowest_price = lowest_price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getMedian_price() {
        return median_price;
    }

    public void setMedian_price(String median_price) {
        this.median_price = median_price;
    }
}
