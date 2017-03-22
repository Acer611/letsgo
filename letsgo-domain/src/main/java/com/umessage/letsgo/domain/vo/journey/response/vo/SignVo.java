package com.umessage.letsgo.domain.vo.journey.response.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ZhaoYidong on 2016/6/8.
 */
public class SignVo implements Serializable {

    @ApiModelProperty(value = "client_id")
    private String client_id;
    @ApiModelProperty(value = "timestamp")
    private long timestamp;
    @ApiModelProperty(value = "sign")
    private String sign;
    @ApiModelProperty(value = "ver")
    private String ver;
    @ApiModelProperty(value = "access_token")
    private String access_token;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "SignVo{" +
                "client_id='" + client_id + '\'' +
                ", timestamp=" + timestamp +
                ", sign='" + sign + '\'' +
                ", ver='" + ver + '\'' +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}
