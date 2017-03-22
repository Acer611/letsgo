package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2016/4/28.
 */
public class AccountImportRequest {
    @ApiModelProperty(value = "用户名")
    @JsonProperty(value = "Identifier")
    private String identifier;
    @ApiModelProperty(value = "用户昵称")
    @JsonProperty(value = "Nick")
    private String nick;
    @ApiModelProperty(value = "用户头像URL")
    @JsonProperty(value = "FaceUrl")
    private String faceUrl;

    public AccountImportRequest() {
    }

    public AccountImportRequest(String identifier, String nick, String faceUrl) {
        this.identifier = identifier;
        this.nick = nick;
        this.faceUrl = faceUrl;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"identifier\":\"" + identifier + "\""
                + ",                         \"nick\":\"" + nick + "\""
                + ",                         \"faceUrl\":\"" + faceUrl + "\""
                + "}";
    }
}
