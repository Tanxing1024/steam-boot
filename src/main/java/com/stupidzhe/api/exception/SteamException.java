package com.stupidzhe.api.exception;

/**
 * Created by Mr.W on 2017/5/23.
 * steam相关错误
 */
public class SteamException extends RuntimeException {

    /**
     * Constructor with message
     *
     * @param message The error message
     */
    public SteamException(String message) {
        super(message);
    }

    /**
     * Constructor with message and exception
     *
     * @param message The error message
     * @param throwable Underlying exception
     */
    public SteamException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
