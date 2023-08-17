package com.stupidzhe.api.constant;

/**
 * Created by Mr.W on 2017/5/15.
 * steam web API相关常数
 */
public interface SteamAPI {

    /**
     * this key(licence) is given by Steam, you can get it by http://steamcommunity.com/dev
     */
    String KEY = "ECE625D661B681DB81B2FCB521BF41EC";

    String STEAM_URL = "http://api.steampowered.com";

    String STEAM_OPENID_URL = "https://steamcommunity.com/openid/";

    String GET_SUPPORTED_API_LIST = "http://api.steampowered.com/ISteamWebAPIUtil/GetSupportedAPIList/v0001/";

    String GET_SUPPORTED_API_LIST_BY_KEY = "http://api.steampowered.com/ISteamWebAPIUtil/GetSupportedAPIList/v0001/?key=" + KEY;

    String IMG_URL = "http://steamcommunity-a.akamaihd.net/economy/image/";

    String TRADE_URL = "https://steamcommunity.com/id/me/tradeoffers/privacy#trade_offer_access_url";

    String PRICE_URL = "http://steamcommunity.com/market/priceoverview/?country=US&currency=23&appid=APP_ID&market_hash_name=MARKET_HASH_NAME";
    /**
     * interface
     */
    interface STEAM_INTERFACE {

        String IPLAYERSERVICE = "/IPlayerService";

        String ISTEAMUSERSTATS = "/ISteamUserStats";

        String ISTEAMUSER = "/ISteamUser";

        String ISTEAMAPPS = "/ISteamApps";

        String IACCOUNTRECOVERYSERVICE = "/IAccountRecoveryService";

        String IBROADCASTSERVICE = "/IBroadcastService";

        String ICSGOPLAYERS_730 = "/ICSGOPlayers_730";

        String ICSGOSERVERS_730 = "/ICSGOServers_730";

        String ICSGOTOURNAMENTS_730 = "/ICSGOTournaments_730";
    }

    /**
     * method
     */
    interface STEAM_METHOD {
        String GETPLAYERSUMMARIES = "/GetPlayerSummaries";
    }

    /**
     * version
     */
    interface VERSION {
        String V1 = "/V0001";
        String V2 = "/V0002";
    }

    /**
     * format
     */
    interface FORMAT {
        String XML = "xml";
        String JSON = "json";
    }

    interface PLAYER_URL {
        String SUMMARIES_URL = STEAM_URL +
                STEAM_INTERFACE.ISTEAMUSER +
                STEAM_METHOD.GETPLAYERSUMMARIES +
                VERSION.V2 +
                "/?key=" + KEY +
                "&steamids=STEAM_ID&format=" + FORMAT.JSON;
    }
}
