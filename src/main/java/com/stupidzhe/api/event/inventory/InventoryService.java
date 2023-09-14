package com.stupidzhe.api.event.inventory;

import com.stupidzhe.api.domain.Bot;

/**
 * Created by Mr.W on 2017/7/9.
 * 库存业务
 */
public interface InventoryService {

    /**
     * 获取库存
     */
    String getInventory(Bot bot, String apiId);

    /**
     * 获取对方库存
     * @param bot
     * @param apiId
     * @return
     */
    String getInventoryOthers(Bot bot, String apiId, String referer);
}
