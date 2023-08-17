package com.stupidzhe.api.bean;

/**
 * Created by Mr.W on 2017/5/28.
 * 返回结果
 */
public class ResultBean {

    //返回是否成功
    private boolean success;

    //返回所请求内容
    private String content;

    //时间戳
    private String timeStamp;

    //返回
    private String referer;

    //api
    private String apiId;

    public ResultBean() {

    };

    public ResultBean(boolean success, String content) {
        this.success = success;
        this.content = content;
    }

    public ResultBean(boolean success, String timeStamp, String content) {
        this.success = success;
        this.content = content;
        this.timeStamp = timeStamp;
    }

    public ResultBean(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }
}
