package com.stupidzhe.api.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Mr.W on 2017/5/23.
 * 机器人相关
 */
@Component
public class Bot implements Serializable {

    private static final long serialVersionUID = -1L;

    private Http http;

    private String apiId;

    private String userName;

    private String playName;

    private String userPsw;

    private String steamId;

    private String sessionId;

    private String sharedSecret;

    private String identitySecret;

    private StringBuilder cookies;

    private Integer botNum;

    private String deviceId;

    private String callBackUrl;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public StringBuilder getCookies() {
        return cookies;
    }

    public void setCookies(StringBuilder cookies) {
        this.cookies = cookies;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public String getSharedSecret() {
        return sharedSecret;
    }

    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentitySecret() {
        return identitySecret;
    }

    public void setIdentitySecret(String identitySecret) {
        this.identitySecret = identitySecret;
    }

    public void setBotNum(Integer botNum) {
        this.botNum = botNum;
    }

    public Integer getBotNum() {
        return botNum;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }
}
