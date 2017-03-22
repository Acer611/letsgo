/*
 * UserEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-20 Created by Administrator
 */
package com.umessage.letsgo.domain.po.security;

import com.umessage.letsgo.core.utils.JsonUtils;
import com.umessage.letsgo.domain.po.team.LeaderEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.annotation.dataitem.ItemName;
import com.umessage.letsgo.core.annotation.dataitem.ItemValue;
import com.umessage.letsgo.domain.vo.security.respone.CardResponse;
import org.apache.commons.lang.StringUtils;

@Catalog(code = "UserEntity")
public class UserEntity implements Serializable{

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;

    @ApiModelProperty(value="领队标识0为游客1为领队，默认为游客，当领队或者导游登陆时变为1")
    private Integer guideStatus;
    /**
     * 用户名，用于腾讯IM私聊，相当于腾讯SDK里的peer，也可用于小米推送中的别名
     */
    @ApiModelProperty(value="用户名，用于腾讯IM私聊，相当于腾讯SDK里的peer，也可用于小米推送中的别名")
    private String userName;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String realName;
    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String nickName;
    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String newPasswords;

    //用户状态：[1:可用，2:审核中，3:停用]
    @JsonIgnore
    @ItemValue(code = "userStatus")
    private Integer userStatus;

    //用户状态翻译字段：返回用户状态对应的字符串
    @JsonIgnore
    @ItemName(code = "userStatus")
    private String userStatusDesc;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别【1：男；2：女】")
    @ItemValue(code = "sex")
    private Integer sex;
    /**
     * 性别【1：男；2：女】
     */
    @ApiModelProperty(value="性别字段的翻译，意思是返回性别对应的字符串")
    @ItemName(code = "sex")
    private String sexDescn;
    /**
     * 出生日期
     */
    @ApiModelProperty(value="出生日期")
    private String birthday;
    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String phone;
    /**
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    private String mail;
    /**
     * 头像地址
     */
    @ApiModelProperty(value="头像地址")
    private String photoUrl;
    /**
     * 类型【1：成人；2：儿童；3：老人】
     */
    @ApiModelProperty(value="类型【1：成人；2：儿童；3：老人】")
    @ItemValue(code = "type")
    private Integer type;
    /**
     * 类型【1：成人；2：儿童；3：老人】
     */
    @ApiModelProperty(value="类型字段的翻译，意思是返回类型对应的字符串")
    @ItemName(code = "type")
    private String typeDescn;
    /**
     * 腾讯云签名
     */
    @ApiModelProperty(value="腾讯云签名")
    private String usersig;
    /**
     * 登录限制次数
     */
    @JsonIgnore
    private Integer loginTotalCount;
    /**
     * 登录累计次数
     */
    @JsonIgnore
    private Integer loginCount;
    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonIgnore
    private Date updateTime;
    /**
     * 版本号
     */
    @JsonIgnore
    private Long version;
    /**
     * 是否有密码
     */
    @ApiModelProperty(value = "是否有密码，0代表无密码，1代表有密码")
    private Integer isPass;
    /**
     * 证件信息
     */
    @ApiModelProperty(value = "证件信息，样例：[{\"身份证\",\"452501XXXX0225\",\"2005/05/08-2015/05/09\"]},{\"港澳通行证\",\"4208611XXXX0225\",\"2005/05/08-2015/05/09\"}]")
    private List<CardResponse> cardInfoList = new ArrayList<>();
    /**
     * 身份信息
     */
    @JsonIgnore
    private List<RoleEntity> roleList;

    private Integer role;

    /**
     * 邀请码
     */
    @JsonIgnore
    private String inviteCode;
    /**
     * 邀请总人数
     */
    @JsonIgnore
    private Integer inviteCount;
    /**
     * 奖励总金额
     */
    @JsonIgnore
    private Double totalReward;

    @ApiModelProperty(value = "是否为好友【1：是好友；0：不是好友】")
    private Integer isFriend;
    @ApiModelProperty(value = "好友类型【1：游客；2：同行】")
    private Integer friendType;

    @ApiModelProperty(value = "自我评价，数组格式：{[\"tag1\",\"tag2\",\"tag3\",\"tag4\",\"tag5\"]")
    private List<String> evaluationTag;
    /**
     * 自我评价标签，传入JSON字符串，格式：{"evaluationTag":["tag1","tag2","tag3","tag4","tag5"]}
     */
    @ApiModelProperty(value="自我评价标签，传入JSON字符串，格式：{\"evaluationTags\":[\"tag1\",\"tag2\",\"tag3\",\"tag4\",\"tag5\"]} ")
    private String evaluationTags;
    @ApiModelProperty(value = "擅长语种")
    private String languages;
    @ApiModelProperty(value = "擅长路线")
    private String tourRoutes;

    @ApiModelProperty(value = "标签列表")
    private List<String> tagList;
    @ApiModelProperty(value = "同团记录列表")
    private List<String> sameTeamList;

    @ApiModelProperty(value = "旅行社ID")
    private Long travelerId;
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**
     * 二维码URL
     */
    @ApiModelProperty(value="二维码URL")
    private String qrURL;
    /**
     * 二维码类型：u：个人中心二维码用于通讯录加好友
     */
    @ApiModelProperty(value="二维码类型：u：个人中心二维码用于通讯录加好友")
    private String qrType;

    /**
     * 二维码参数：用户腾讯ID
     */
    @ApiModelProperty(value="二维码参数：用户腾讯ID")
    private String qrParam;

    public String getQrType() {
        return qrType;
    }

    public void setQrType(String qrType) {
        this.qrType = qrType;
    }

    public String getQrParam() {
        return qrParam;
    }

    public void setQrParam(String qrParam) {
        this.qrParam = qrParam;
    }

    public String getQrURL() {
        return qrURL;
    }

    public void setQrURL(String qrURL) {
        this.qrURL = qrURL;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSexDescn() {
        return sexDescn;
    }

    public void setSexDescn(String sexDescn) {
        this.sexDescn = sexDescn;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDescn() {
        return typeDescn;
    }

    public void setTypeDescn(String typeDescn) {
        this.typeDescn = typeDescn;
    }

    public String getUsersig() {
        return usersig;
    }

    public void setUsersig(String usersig) {
        this.usersig = usersig;
    }

    public Integer getLoginTotalCount() {
        return loginTotalCount;
    }

    public void setLoginTotalCount(Integer loginTotalCount) {
        this.loginTotalCount = loginTotalCount;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
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

    public Integer getIsPass() {
        return isPass;
    }

    public void setIsPass(Integer isPass) {
        this.isPass = isPass;
    }

    public List<CardResponse> getCardInfoList() {
        return cardInfoList;
    }

    public void setCardInfoList(List<CardResponse> cardInfoList) {
        this.cardInfoList = cardInfoList;
    }

    public List<RoleEntity> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleEntity> roleList) {
        this.roleList = roleList;
    }

    public String getNewPasswords() {
        return newPasswords;
    }

    public void setNewPasswords(String newPasswords) {
        this.newPasswords = newPasswords;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserStatusDesc() {
        return userStatusDesc;
    }

    public void setUserStatusDesc(String userStatusDesc) {
        this.userStatusDesc = userStatusDesc;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Integer getInviteCount() {
        return inviteCount;
    }

    public void setInviteCount(Integer inviteCount) {
        this.inviteCount = inviteCount;
    }

    public Double getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(Double totalReward) {
        this.totalReward = totalReward;
    }

    public Integer getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(Integer isFriend) {
        this.isFriend = isFriend;
    }

    public Integer getFriendType() {
        return friendType;
    }

    public void setFriendType(Integer friendType) {
        this.friendType = friendType;
    }

    public List<String> getEvaluationTag() {
        return evaluationTag;
    }

    public void setEvaluationTag(List<String> evaluationTag) {
        this.evaluationTag = evaluationTag;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getTourRoutes() {
        return tourRoutes;
    }

    public void setTourRoutes(String tourRoutes) {
        this.tourRoutes = tourRoutes;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public List<String> getSameTeamList() {
        return sameTeamList;
    }

    public void setSameTeamList(List<String> sameTeamList) {
        this.sameTeamList = sameTeamList;
    }

    public Long getTravelerId() {
		return travelerId;
	}

	public void setTravelerId(Long travelerId) {
		this.travelerId = travelerId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    public Integer getGuideStatus() {
        return guideStatus;
    }

    public void setGuideStatus(Integer guideStatus) {
        this.guideStatus = guideStatus;
    }

    public String getEvaluationTags() {
        return evaluationTags;
    }

    public void setEvaluationTags(String evaluationTags) {
        this.evaluationTags = evaluationTags;
    }

    public void setUserInfo(UserEntity userEntity) {
        if (StringUtils.isEmpty(userEntity.getEvaluationTags())) {
            this.setEvaluationTag(new ArrayList<String>());
        } else {
            // 解析JSON字符串
            Map<String, List<String>> map = JsonUtils.json2map(userEntity.getEvaluationTags());
            this.setEvaluationTag(map.get("evaluationTag"));
        }

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (id != null && obj instanceof UserEntity) {
            UserEntity otherUser = (UserEntity) obj;
            return id.equals(otherUser.getId());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", guideStatus=" + guideStatus +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", newPasswords='" + newPasswords + '\'' +
                ", userStatus=" + userStatus +
                ", userStatusDesc='" + userStatusDesc + '\'' +
                ", sex=" + sex +
                ", sexDescn='" + sexDescn + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", type=" + type +
                ", typeDescn='" + typeDescn + '\'' +
                ", usersig='" + usersig + '\'' +
                ", loginTotalCount=" + loginTotalCount +
                ", loginCount=" + loginCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version=" + version +
                ", isPass=" + isPass +
                ", cardInfoList=" + cardInfoList +
                ", roleList=" + roleList +
                ", role=" + role +
                ", inviteCode='" + inviteCode + '\'' +
                ", inviteCount=" + inviteCount +
                ", totalReward=" + totalReward +
                ", isFriend=" + isFriend +
                ", friendType=" + friendType +
                ", evaluationTags=" + evaluationTags +
                ", languages='" + languages + '\'' +
                ", tourRoutes='" + tourRoutes + '\'' +
                ", tagList=" + tagList +
                ", sameTeamList=" + sameTeamList +
                ", travelerId=" + travelerId +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}