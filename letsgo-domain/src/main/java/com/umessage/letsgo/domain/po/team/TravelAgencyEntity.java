/*
 * TravelAgencyEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by ZhaoYidong
 */
package com.umessage.letsgo.domain.po.team;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.utils.QueryUtils;
import com.umessage.letsgo.domain.vo.team.respone.LeaderResponse;
import com.umessage.letsgo.domain.vo.team.respone.TravelAgencyResponse;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class TravelAgencyEntity  implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    private Long userId;
    /**
     * 旅行社名称
     */
    @ApiModelProperty(value="旅行社名称")
    private String name;
    /**
     * 联系人姓名
     */
    @ApiModelProperty(value="联系人姓名")
    private String contactPerson;
    /**
     * 联系人手机
     */
    @ApiModelProperty(value="联系人手机")
    private String contactPhone;
    /**
     * email
     */
    @ApiModelProperty(value="email")
    private String email;
    /**
     * 旅行社地址
     */
    @ApiModelProperty(value="旅行社地址")
    private String address;
    /**
     * 旅行社简介
     */
    @ApiModelProperty(value="旅行社简介")
    private String desc;
    /**
     * 营业执照地址
     */
    @ApiModelProperty(value="营业执照地址")
    private String licenseUrl;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;
    /**
     * 版本号
     */
    @ApiModelProperty(value="版本号")
    private Long version;


    /**
     *必填字段数据验证
     */
    public TravelAgencyResponse dataVerify(){
        TravelAgencyResponse verifyResponse = new TravelAgencyResponse();
        verifyResponse.setTravelEntity(this);
        verifyResponse.setRetCode(ErrorConstant.INVALID_PARAMETER);
        if (StringUtils.isEmpty(name)){
            verifyResponse.setRetMsg("旅行社名称不能为空");
            return verifyResponse;
        }
        if (name.length()>60){
            verifyResponse.setRetMsg("旅行社名称("+name+")长度不能超过60个字符");
            return verifyResponse;
        }

//        if (StringUtils.isEmpty(contactPerson)){
//            verifyResponse.setRetMsg("联系人姓名不能为空");
//            return verifyResponse;
//        }
//        if (contactPerson.length()>30){
//            verifyResponse.setRetMsg("联系人姓名("+contactPerson+")长度不能超过30个字符");
//            return verifyResponse;
//        }

        if(StringUtils.isEmpty(contactPhone)){
            verifyResponse.setRetMsg("联系人手机号不能为空");
            return verifyResponse;
        }
        if(!QueryUtils.isChinaPhone(contactPhone)){
            verifyResponse.setRetMsg("联系人手机号("+contactPhone+")格式不正确");
            return verifyResponse;
        }
        verifyResponse.setRetCode(0);
        return verifyResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl == null ? null : licenseUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}