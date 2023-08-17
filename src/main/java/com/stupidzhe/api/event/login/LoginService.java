package com.stupidzhe.api.event.login;

import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.exception.LoginException;

/**
 * Created by Mr.W on 2017/5/23.
 * 登录相关
 */
public interface LoginService {

    /**
     * 登录
     *
     * @return 机器人实例
     */
    boolean login(Bot bot) throws LoginException;

}
