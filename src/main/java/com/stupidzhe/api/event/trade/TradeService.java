package com.stupidzhe.api.event.trade;

import com.stupidzhe.api.bean.AssertBean;
import com.stupidzhe.api.bean.ResultBean;
import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.domain.TradeResult;

import java.util.Map;

/**
 * Created by Mr.W on 2017/5/23.
 * 交易相关
 */
public interface TradeService {

    /**
     * 与第三方玩家交易
     *
     * @param youSend 机器人发送的物品
     * @param himSend 对方发送的物品
     * @param url     对方交易URL
     * @param partner 对方steamId
     * @param botNum  机器人编号
     * @param apiId   游戏appId
     * @return 是否成功
     */
    TradeResult tradeOffer(String url, String partner, AssertBean youSend, AssertBean himSend, String botNum, Integer apiId);

    /**
     * 接收对方报价
     *
     * @param tradeOfferId 报价id
     * @param partner      对方steamId
     * @param botNum       机器人编号
     * @return
     */
    boolean acceptTradeOffer(String tradeOfferId, String partner, String botNum, boolean accept);

    /**
     * 结束自己的报价（比如过期）
     *
     * @param tradeOfferId 报价id
     * @param botNum       机器人编号
     * @return
     */
    boolean cancelTradeOffer(String tradeOfferId, String botNum) throws Exception;

    /**
     * 获取对方发送给机器人的交易报价（自己饰品为空时
     *
     * @param bot 机器人
     * @return map<offerId.partner>
     */
    Map<String, String> getTradeOffer(Bot bot);


    /**
     * 获取机器人报价信息
     *
     * @param bot 机器人
     * @return
     */
    ResultBean getBotTradeOffer(Bot bot);


    /**
     * 获取机器人报价详情
     *
     * @param bot
     * @param offerId
     * @return
     */
    ResultBean getBotTradeOfferDetails(Bot bot, String offerId);

    /**
     * 获取用户确认情况
     */
    boolean sendConfirmMessage(String offerId, String status, Bot bot) throws Exception;

}
