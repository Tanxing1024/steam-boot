package com.stupidzhe.api.controller;

import com.stupidzhe.api.bean.ResultBean;
import com.stupidzhe.api.constant.PathConst;
import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.event.inventory.InventoryService;
import com.stupidzhe.api.redis.BotCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mr.W on 2017/6/24.
 * 库存控制器
 */
@RestController
@RequestMapping(value = PathConst.BASE1 + "/{api_id}/inventory")
public class InventoryController {

    private final BotCache botCache;
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(BotCache botCache, InventoryService inventoryService) {
        this.botCache = botCache;
        this.inventoryService = inventoryService;
    }

    /**
     * 获取库存json
     */
    @RequestMapping(value = "/{bot_id}", method = RequestMethod.GET)
    public ResultBean getInventory(@PathVariable("bot_id") String botId,
                                   @PathVariable("api_id") String apiId) {
        Bot bot = botCache.getBot(apiId + ":" + botId);
        if (null == bot) {
            return new ResultBean(false, "获取失败");
        }
        String content = inventoryService.getInventory(bot, apiId);
        if (null != content) {
            return new ResultBean(true, content);
        }
        return new ResultBean(false, "获取失败");
    }
}
