/*
 * UserTagEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-09-08 Created by wendy
 */
package com.umessage.letsgo.domain.po.security;

import java.util.Date;
import java.util.List;

public class UserTagEntity {

    private Long id;
    //登陆用户ID
    private Long labellingUserId;
    private Long tagId;
    //好友ID
    private Long labeledUserId;
    private Date createTime;
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLabellingUserId() {
        return labellingUserId;
    }

    public void setLabellingUserId(Long labellingUserId) {
        this.labellingUserId = labellingUserId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

	public Long getLabeledUserId() {
        return labeledUserId;
    }

    public void setLabeledUserId(Long labeledUserId) {
        this.labeledUserId = labeledUserId;
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