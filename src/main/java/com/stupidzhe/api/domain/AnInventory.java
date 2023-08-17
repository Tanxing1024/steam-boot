package com.stupidzhe.api.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mr.W on 2017/5/17.
 */
public class AnInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appid;
    private String classid;
    private String instanceid;
    private String icon_url;
    private String icon_url_large;
    private String name;
    private String market_hash_name;
    private String market_name;
    private String name_color;
    private String background_color;
    private String type;
    private String tradable;
    private String marketable;
    private String commodity;
    private String market_tradable_destriction;
    private LinkedList<Descriptions> descriptions;
    private String owner_descriptions;
    private LinkedList<Action> actions;
    private LinkedList<marketAction> market_actions;
    private String stickers;
    private String price;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getInstanceid() {
        return instanceid;
    }

    public void setInstanceid(String instanceid) {
        this.instanceid = instanceid;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getIcon_url_large() {
        return icon_url_large;
    }

    public void setIcon_url_large(String icon_url_large) {
        this.icon_url_large = icon_url_large;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarket_hash_name() {
        return market_hash_name;
    }

    public void setMarket_hash_name(String market_hash_name) {
        this.market_hash_name = market_hash_name;
    }

    public String getMarket_name() {
        return market_name;
    }

    public void setMarket_name(String market_name) {
        this.market_name = market_name;
    }

    public String getName_color() {
        return name_color;
    }

    public void setName_color(String name_color) {
        this.name_color = name_color;
    }

    public String getBackground_color() {
        return background_color;
    }

    public void setBackground_color(String background_color) {
        this.background_color = background_color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTradable() {
        return tradable;
    }

    public void setTradable(String tradable) {
        this.tradable = tradable;
    }

    public String getMarketable() {
        return marketable;
    }

    public void setMarketable(String marketable) {
        this.marketable = marketable;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getMarket_tradable_destriction() {
        return market_tradable_destriction;
    }

    public void setMarket_tradable_destriction(String market_tradable_destriction) {
        this.market_tradable_destriction = market_tradable_destriction;
    }

    public List<Descriptions> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(LinkedList<Descriptions> descriptions) {
        this.descriptions = descriptions;
    }

    public String getOwner_descriptions() {
        return owner_descriptions;
    }

    public void setOwner_descriptions(String owner_descriptions) {
        this.owner_descriptions = owner_descriptions;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(LinkedList<Action> actions) {
        this.actions = actions;
    }

    public List<marketAction> getMarket_actions() {
        return market_actions;
    }

    public void setMarket_actions(LinkedList<marketAction> market_actions) {
        this.market_actions = market_actions;
    }

    public String getStickers() {
        return stickers;
    }

    public void setStickers(String stickers) {
        this.stickers = stickers;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public class Descriptions implements Serializable {

        private static final long serialVersionUID = 23243L;

        private String type;
        private String value;
        private String appData;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getAppData() {
            return appData;
        }

        public void setAppData(String appData) {
            this.appData = appData;
        }
    }

    public class Action implements Serializable {

        private static final long serialVersionUID = 2324334L;

        private String name;
        private String link;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public class marketAction implements Serializable {

        private static final long serialVersionUID = 5665L;

        private String name;
        private String link;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public class tags implements Serializable {

        private static final long serialVersionUID = 4L;

        private String internal_name;
        private String name;
        private String category;
        private String category_name;

        public String getInternal_name() {
            return internal_name;
        }

        public void setInternal_name(String internal_name) {
            this.internal_name = internal_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
    }

}
