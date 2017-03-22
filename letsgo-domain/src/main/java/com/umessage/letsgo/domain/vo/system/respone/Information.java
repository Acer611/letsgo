package com.umessage.letsgo.domain.vo.system.respone;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wendy on 2016/6/29.
 */
public class Information implements Serializable {
    @ApiModelProperty(value = "未读数量")
    private int unreadCount;
    @ApiModelProperty(value = "未确认数量")
    private int unconfirmedCount;
    @ApiModelProperty(value = "对应分类的消息实体")
    private Message message;

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public int getUnconfirmedCount() {
        return unconfirmedCount;
    }

    public void setUnconfirmedCount(int unconfirmedCount) {
        this.unconfirmedCount = unconfirmedCount;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
