/*
 * MessageEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-04 Created by Administrator
 */
package com.umessage.letsgo.domain.po.system;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class MessageEntity  implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 短信内容
     */
    @ApiModelProperty(value="短信内容")
    private String content;
    /**
     * 参数
     */
    @ApiModelProperty(value="参数")
    private String param;
    /**
     * 发送时间
     */
    @ApiModelProperty(value="发送时间",dataType = "java.lang.Long")
    private Date sendTime;
    /**
     * 发送类型
     */
    @ApiModelProperty(value="发送类型")
    private Integer smsType;
    /**
     * 发送状态【1：成功；0：失败】
     */
    @ApiModelProperty(value="发送状态，为短信接口返回的参数")
    private String smsStatus;
    /**
     * 接收人手机
     */
    @ApiModelProperty(value="接收人手机")
    private String phone;
    /**
     * 短信有效性【1：有效；0：无效】
     */
    @ApiModelProperty(value="短信有效性【1：有效；0：无效】")
    private Integer valid;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String mark;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",dataType = "java.lang.Long")
    private Date createTime;
    /**
     * 版本号
     */
    @ApiModelProperty(value="版本号")
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public String getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}