package com.stupidzhe.api.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.W on 2017/5/17.
 * 库存
 */
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    public Inventory() {
    }

    public Inventory(boolean success) {
        this.success = success;
    }

    private boolean success;

    private Map<String, RgInventory> rgInventory;

    private Map<String, Inventory.rgDescriptions> rgDescriptions;

    public Map<String, Inventory.rgDescriptions> getRgDescriptions() {
        return rgDescriptions;
    }

    public void setRgDescriptions(Map<String, Inventory.rgDescriptions> rgDescriptions) {
        this.rgDescriptions = rgDescriptions;
    }


    public class rgDescriptions {

        private String assetId;
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
        private LinkedList<AnInventory.Descriptions> descriptions;
        private LinkedList<AnInventory.Action> actions;
        private LinkedList<AnInventory.marketAction> market_actions;
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

        public List<AnInventory.Descriptions> getDescriptions() {
            return descriptions;
        }

        public void setDescriptions(LinkedList<AnInventory.Descriptions> descriptions) {
            this.descriptions = descriptions;
        }

        public List<AnInventory.Action> getActions() {
            return actions;
        }

        public void setActions(LinkedList<AnInventory.Action> actions) {
            this.actions = actions;
        }

        public List<AnInventory.marketAction> getMarket_actions() {
            return market_actions;
        }

        public void setMarket_actions(LinkedList<AnInventory.marketAction> market_actions) {
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

        public String getAssetId() {
            return assetId;
        }

        public void setAssetId(String assetId) {
            this.assetId = assetId;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

//    public List<AnInventory> getRgDescriptions() {
//        return rgDescriptions;
//    }

//    public void setRgDescriptions(List<AnInventory> rgDescriptions) {
//        this.rgDescriptions = rgDescriptions;
//    }

    public class RgInventory {

        private String id;
        private String classid;
        private String instanceid;
        private String amount;
        private String pos;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }
    }

    public Map<String, RgInventory> getRgInventory() {
        return rgInventory;
    }

    public void setRgInventory(Map<String, RgInventory> rgInventory) {
        this.rgInventory = rgInventory;
    }

//    public static void main(String[] args) {
//        String json = "{\n" +
//                "    \"success\": true,\n" +
//                "    \"rgInventory\": {\n" +
//                "        \"9222031420\": {\n" +
//                "            \"id\": \"9222031420\",\n" +
//                "            \"classid\": \"1989275515\",\n" +
//                "            \"instanceid\": \"302028390\",\n" +
//                "            \"amount\": \"1\",\n" +
//                "            \"pos\": 35\n" +
//                "        }\n" +
//                "    },\n" +
//                "    \"rgCurrency\": [],\n" +
//                "    \"rgDescriptions\": {\n" +
//                "        \"1989275515_302028390\": {\n" +
//                "            \"appid\": \"730\",\n" +
//                "            \"classid\": \"1989275515\",\n" +
//                "            \"instanceid\": \"302028390\",\n" +
//                "            \"icon_url\": \"IzMF03bi9WpSBq-S-ekoE33L-iLqGFHVaU25ZzQNQcXdB2ozio1RrlIWFK3UfvMYB8UsvjiMXojflsZalyxSh31CIyHz2GZ-KuFpPsrTzBG0se2dGHvwJjWdfCXYSQ8wGOEPPWrf-jWh4LydQD7LQ74kEQ8Ne6ZQ-mccOs6AOEMjlNlc7Wa3m0tvEwMkZsxWfBbmnHFHN-Qj9Qe6PAA\",\n" +
//                "            \"icon_url_large\": \"IzMF03bi9WpSBq-S-ekoE33L-iLqGFHVaU25ZzQNQcXdB2ozio1RrlIWFK3UfvMYB8UsvjiMXojflsZalyxSh31CIyHz2GZ-KuFpPsrTzBG0se2dGHvwVzvFPSbcUls6SbsPPGjerGKn5LzHRTCcQuh4FQ8CeKFRpzYdNZ-JPxs9gYRa8zb2h0p6WBUnfspUfRq33n0DPaR4niRGI5IEQMRrM98\",\n" +
//                "            \"icon_drag_url\": \"\",\n" +
//                "            \"name\": \"封装的涂鸦 | 碰碰狗 (暗紫)\",\n" +
//                "            \"market_hash_name\": \"Sealed Graffiti | Popdog (Monster Purple)\",\n" +
//                "            \"market_name\": \"封装的涂鸦 | 碰碰狗 (暗紫)\",\n" +
//                "            \"name_color\": \"D2D2D2\",\n" +
//                "            \"background_color\": \"\",\n" +
//                "            \"type\": \"普通级 涂鸦\",\n" +
//                "            \"tradable\": 1,\n" +
//                "            \"marketable\": 1,\n" +
//                "            \"commodity\": 1,\n" +
//                "            \"market_tradable_restriction\": \"7\",\n" +
//                "            \"descriptions\": [\n" +
//                "                {\n" +
//                "                    \"type\": \"html\",\n" +
//                "                    \"value\": \"这个罐子里封装着—个涂鸦图案。涂鸦一经开封，可以在游戏内喷涂 <b>50</b> 次。\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"type\": \"html\",\n" +
//                "                    \"value\": \" \"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"type\": \"html\",\n" +
//                "                    \"value\": \"　\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"type\": \"html\",\n" +
//                "                    \"value\": \" \"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"type\": \"html\",\n" +
//                "                    \"value\": \"\",\n" +
//                "                    \"color\": \"00a000\",\n" +
//                "                    \"app_data\": {\n" +
//                "                        \"limited\": 1\n" +
//                "                    }\n" +
//                "                }\n" +
//                "            ],\n" +
//                "            \"actions\": [\n" +
//                "                {\n" +
//                "                    \"name\": \"在游戏中检视...\",\n" +
//                "                    \"link\": \"steam://rungame/730/76561202255233023/+csgo_econ_action_preview%20S%owner_steamid%A%assetid%D7693922421707647238\"\n" +
//                "                }\n" +
//                "            ],\n" +
//                "            \"market_actions\": [\n" +
//                "                {\n" +
//                "                    \"name\": \"在游戏中检视...\",\n" +
//                "                    \"link\": \"steam://rungame/730/76561202255233023/+csgo_econ_action_preview%20M%listingid%A%assetid%D7693922421707647238\"\n" +
//                "                }\n" +
//                "            ],\n" +
//                "            \"tags\": [\n" +
//                "                {\n" +
//                "                    \"internal_name\": \"CSGO_Type_Spray\",\n" +
//                "                    \"name\": \"涂鸦\",\n" +
//                "                    \"category\": \"Type\",\n" +
//                "                    \"category_name\": \"类型\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"internal_name\": \"normal\",\n" +
//                "                    \"name\": \"普通\",\n" +
//                "                    \"category\": \"Quality\",\n" +
//                "                    \"category_name\": \"类别\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"internal_name\": \"Rarity_Common\",\n" +
//                "                    \"name\": \"普通级\",\n" +
//                "                    \"category\": \"Rarity\",\n" +
//                "                    \"color\": \"b0c3d9\",\n" +
//                "                    \"category_name\": \"品质\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"internal_name\": \"Tint15\",\n" +
//                "                    \"name\": \"暗紫\",\n" +
//                "                    \"category\": \"SprayColorCategory\",\n" +
//                "                    \"category_name\": \"涂鸦颜色\"\n" +
//                "                }\n" +
//                "            ]\n" +
//                "        }\n" +
//                "    },\n" +
//                "    \"more\": false,\n" +
//                "    \"more_start\": false\n" +
//                "}";
//        Gson gson = new Gson();
////        Type listType = new TypeToken<Map<String, String>>(){}.getType();
//        Inventory inventory = gson.fromJson(json, Inventory.class);
//        System.out.println(inventory);
//    }

}
