package com.umessage.letsgo.domain.po.security;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by zengguoqing on 2016/11/22.
 */
@Catalog(code = "UserEntity")
public class WxInfoEntity implements Serializable {


    
    @ApiModelProperty(value="ID")
    private Long id;

    @ApiModelProperty(value="微信唯一标识")
    private String unionID;

    @ApiModelProperty(value="微信用户openID")
    private String openid;

    @ApiModelProperty(value="微信应用的ID")
    private String appId;

    @ApiModelProperty(value="昵称")
    private String nickname;

    @ApiModelProperty(value="性别")
    private String sex;

    @ApiModelProperty(value="省份")
    private String province;

    @ApiModelProperty(value="国家")
    private String country;

    @ApiModelProperty(value="城市")
    private String city;

    @ApiModelProperty(value="头像URL")
    private String headImgUrl;

    @ApiModelProperty(value="是否是绑定用户  0 为绑定用户 1为没有绑定用户 默认为1  方便为已关注为绑定这类用户推送特殊消息使用")
    private int binding;

    @ApiModelProperty(value="微信用户是否取消公众号 0 为没有取消 1 为取消用户")
    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnionID() {
        return unionID;
    }

    public void setUnionID(String unionID) {
        this.unionID = unionID;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public int getBinding() {
        return binding;
    }

    public void setBinding(int binding) {
        this.binding = binding;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "WxInfoEntity{" +
                "id=" + id +
                ", unionID='" + unionID + '\'' +
                ", openid='" + openid + '\'' +
                ", appId='" + appId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", binding=" + binding +
                ", status=" + status +
                '}';
    }
}
