package com.stupidzhe.api.controller;

import com.stupidzhe.api.bean.ResultBean;
import com.stupidzhe.api.constant.SteamAPI;
import org.expressme.openid.Association;
import org.expressme.openid.Endpoint;
import org.expressme.openid.OpenIdManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: StupidZhe
 * @Date: Created in 2018/1/3
 * @Description:
 */

@RestController
@RequestMapping(value = "/usr/status")
public class LoginController {
    /**
     * 用户登录
     */
    @PostMapping(value = "/login.do")
    public ResultBean login(@RequestParam(value = "url") String backUrl) {
        OpenIdManager openIdManager = new OpenIdManager();
        openIdManager.setReturnTo(backUrl);
        // 10s
        openIdManager.setTimeOut(100000);
        Endpoint endpoint = openIdManager.lookupEndpoint(SteamAPI.STEAM_OPENID_URL);
        Association association = openIdManager.lookupAssociation(endpoint);
        String url = openIdManager.getAuthenticationUrl(endpoint, association);
        return new ResultBean(true, "redirect", url);
    }
}
