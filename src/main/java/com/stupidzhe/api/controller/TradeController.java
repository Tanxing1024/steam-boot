package com.stupidzhe.api.controller;

import com.stupidzhe.api.bean.AssertBean;
import com.stupidzhe.api.bean.ResultBean;
import com.stupidzhe.api.constant.PathConst;
import com.stupidzhe.api.domain.TradeResult;
import com.stupidzhe.api.event.trade.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Mr.W on 2017/5/28.
 * 交易相关
 */
@RestController
@RequestMapping(value = PathConst.BASE1 + "/{api_id}/trade")
public class TradeController extends BaseController {

    private final TradeService tradeService;

    private static final Logger log = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     *  发送交易报价
     * @param apiId
     * @param yourAssertIdJson
     * @param hisAssertIdJson
     * @param steamId
     * @param url
     * @param botId
     * @return
     */
    @PostMapping(value = "")
    public TradeResult trade(@PathVariable(value = "api_id") Integer apiId,
                            @RequestParam(value = "you_send_inventory") String yourAssertIdJson,
                            @RequestParam(value = "he_send_inventory") String hisAssertIdJson,
                            @RequestParam(value = "steam_id") String steamId,
                            @RequestParam(value = "url") String url,
                            @RequestParam(value = "bot_id")String botId) {
        AssertBean youSendBeans = gson.fromJson(yourAssertIdJson, AssertBean.class);
        AssertBean heSendBeans = gson.fromJson(hisAssertIdJson, AssertBean.class);
        if (null == yourAssertIdJson || null == heSendBeans || "".equals(url.trim())) {
            return new TradeResult(false);
        }
        return tradeService.tradeOffer(url, steamId, youSendBeans, heSendBeans,apiId + ":" + botId, apiId);
    }

    /**
     * 手动取消交易报价
     * @param apiId
     * @param offerId
     * @param botId
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResultBean cancelTrade(@PathVariable(value = "api_id") String apiId,
                            @RequestParam(value = "offer_id") String offerId,
                            @RequestParam(value = "bot_id")String botId) {
        boolean res;
        try {
            res = tradeService.cancelTradeOffer(offerId, apiId + ":" + botId);
        } catch (Exception e) {
            return new ResultBean(false);
        }
        if (res) return new ResultBean(true);
        return new ResultBean(false);
    }
}
