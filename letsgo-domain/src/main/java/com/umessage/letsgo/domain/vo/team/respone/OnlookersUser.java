package com.umessage.letsgo.domain.vo.team.respone;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by pw on 2016/9/7.
 */
public class OnlookersUser {

    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String name;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value="真实姓名")
    private String realName;

    /**
     * 围观好友ID
     */
    @ApiModelProperty(value="腾讯组ID")
    private String userId;

    /**
     * 围观好友ID
     */
    @ApiModelProperty(value="头像")
    private String photoUrl;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
