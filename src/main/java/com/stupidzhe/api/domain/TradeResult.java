package com.stupidzhe.api.domain;

/**
 * Created by Mr.W on 2017/7/17.
 * 交易报价发送返回结果
 */
//{"tradeofferid":"2308194200","needs_mobile_confirmation":true,"needs_email_confirmation":false,"email_domain"
//:"gmail.com"}
public class TradeResult {
    private String tradeofferid;
    private boolean needs_mobile_confirmation;
    private boolean needs_email_confirmation;
    private String email_domain;
    private boolean success;
    private String strError;

    public TradeResult(boolean success) {
        this.success = success;
    }

    public String getTradeofferid() {
        return tradeofferid;
    }

    public void setTradeofferid(String tradeofferid) {
        this.tradeofferid = tradeofferid;
    }

    public boolean isNeeds_mobile_confirmation() {
        return needs_mobile_confirmation;
    }

    public void setNeeds_mobile_confirmation(boolean needs_mobile_confirmation) {
        this.needs_mobile_confirmation = needs_mobile_confirmation;
    }

    public String getEmail_domain() {
        return email_domain;
    }

    public void setEmail_domain(String email_domain) {
        this.email_domain = email_domain;
    }

    public boolean isNeeds_email_confirmation() {
        return needs_email_confirmation;
    }

    public void setNeeds_email_confirmation(boolean needs_email_confirmation) {
        this.needs_email_confirmation = needs_email_confirmation;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStrError() {
        return strError;
    }

    public void setStrError(String strError) {
        this.strError = strError;
    }
}
