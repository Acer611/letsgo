package com.umessage.letsgo.domain.po.team;

import java.util.Date;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public class OnlookersUserEntity {
    private Long  id;
    private Long  scheduleId;
    private Long  userId;
    private Long onlookerUserId;
    private Integer onlookersStatus;
    private Date updateTime;
    private Date createTime;
    private Long version;

    /**
     * 性别：1男；2女
     */
    private Integer sex;
    /**
     *姓名
     */
    private String name;
    /**
     *手机号
     */
    private String phone;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOnlookerUserId() {
        return onlookerUserId;
    }

    public void setOnlookerUserId(Long onlookerUserId) {
        this.onlookerUserId = onlookerUserId;
    }

    public Integer getOnlookersStatus() {
        return onlookersStatus;
    }

    public void setOnlookersStatus(Integer onlookersStatus) {
        this.onlookersStatus = onlookersStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
