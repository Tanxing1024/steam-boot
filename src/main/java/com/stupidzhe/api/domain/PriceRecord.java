package com.stupidzhe.api.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class PriceRecord implements Serializable {
    private Integer priceId;

    private String hashName;

    private String price;

    private String updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public String getHashName() {
        return hashName;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}