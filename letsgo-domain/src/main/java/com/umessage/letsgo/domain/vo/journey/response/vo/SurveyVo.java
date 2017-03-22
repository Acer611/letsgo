package com.umessage.letsgo.domain.vo.journey.response.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wendy on 2016/9/12.
 */
public class SurveyVo implements Serializable {
    @ApiModelProperty(value = "问卷详细id")
    private Long id;
    @ApiModelProperty(value = "游客名称")
    private String name;
    @ApiModelProperty(value = "头像地址")
    private String photoUrl;
    @ApiModelProperty(value = "填写时间")
    private Date fillTime;
    @ApiModelProperty(value = "确认状态，1:确认，0:未确认 未填写时字段为空")
    private Integer confirmStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Date getFillTime() {
        return fillTime;
    }

    public void setFillTime(Date fillTime) {
        this.fillTime = fillTime;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }
}
