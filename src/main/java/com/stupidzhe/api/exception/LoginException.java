package com.stupidzhe.api.exception;

/**
 * Created by Mr.W on 2017/5/23.
 * 登录错误报错
 */
public class LoginException extends SteamException {

    public LoginException(String message) {
        super(message);
    }

    /**
     * Error logging in
     *
     * @param message Message why login failed
     * @param cause Exception causing login error
     */
    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }


}
