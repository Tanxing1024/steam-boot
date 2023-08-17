package com.stupidzhe.api.bean;

import java.util.List;

/**
 * Created by Mr.W on 2017/5/29.
 * 交易报价bean
 */
public class TradeSendBean {

    private boolean newversion;

    private Integer version;

    private Me me = new Me();

    private Me them = new Me();

    public boolean isNewversion() {
        return newversion;
    }

    public void setNewversion(boolean newversion) {
        this.newversion = newversion;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Me getMe() {
        return me;
    }

    public void setMe(Me me) {
        this.me = me;
    }

    public Me getThem() {
        return them;
    }

    public void setThem(Me them) {
        this.them = them;
    }

    public static class Me {
       private List<Asset> assets;
       private List<String> currency;
       private boolean ready;

        public List<Asset> getAssets() {
            return assets;
        }

        public void setAssets(List<Asset> assets) {
            this.assets = assets;
        }

        public List<String> getCurrency() {
            return currency;
        }

        public void setCurrency(List<String> currency) {
            this.currency = currency;
        }

        public boolean isReady() {
            return ready;
        }

        public void setReady(boolean ready) {
            this.ready = ready;
        }

        public static class Asset {
            private Integer appid;
            private String contextid;
            private Integer amount;
            private String assetid;

            public Integer getAppid() {
                return appid;
            }

            public void setAppid(Integer appid) {
                this.appid = appid;
            }

            public String getContextid() {
                return contextid;
            }

            public void setContextid(String contextid) {
                this.contextid = contextid;
            }

            public Integer getAmount() {
                return amount;
            }

            public void setAmount(Integer amount) {
                this.amount = amount;
            }

            public String getAssetid() {
                return assetid;
            }

            public void setAssetid(String assetid) {
                this.assetid = assetid;
            }
        }
    }

    /*
{
    "newversion": true,
    "version": 5,
    "me": {
        "assets": [
            {
                "appid": 730,
                "contextid": "2",
                "amount": 1,
                "assetid": "10277681181"
            }
        ],
        "currency": [],
        "ready": false
    },
    "them": {
        "assets": [
            {
                "appid": 730,
                "contextid": "2",
                "amount": 1,
                "assetid": "10250743392"
            },
            {
                "appid": 730,
                "contextid": "2",
                "amount": 1,
                "assetid": "10384868449"
            },
            {
                "appid": 730,
                "contextid": "2",
                "amount": 1,
                "assetid": "10379373609"
            }
        ],
        "currency": [],
        "ready": false
    }
}
     */
}
