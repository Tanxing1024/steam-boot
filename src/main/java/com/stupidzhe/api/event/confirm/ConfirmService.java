package com.stupidzhe.api.event.confirm;

import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.domain.Confirmation;

import java.util.List;

/**
 * Created by Mr.W on 2017/6/17.
 * 自动确认相关
 */
public interface ConfirmService {

    /**
     * 自动确认(过180s后过期)
     * 
     * @param accept
     * @param bot
     */
    String autoConfirm(Boolean accept, Bot bot, Confirmation confirmation);

    /**
     * 获取需确认信息
     *
     * @param bot  机器人
     */
    List<Confirmation> getConfirmations(Bot bot);
}
