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
}
