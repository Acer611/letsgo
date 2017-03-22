package com.jrmf360.vo.request;

/**
 * Created by zhajl on 16/9/23.
 */
public class WalletCommonRequest {
    /**
     * 渠道标识
     */
    private String partnerId;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 密钥
     */
    private String seckey;
    /**
     * 签名
     */
    private String sign;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSeckey() {
        return seckey;
    }

    public void setSeckey(String seckey) {
        this.seckey = seckey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
