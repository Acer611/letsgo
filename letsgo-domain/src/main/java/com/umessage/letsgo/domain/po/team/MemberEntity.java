/*
 * MemberEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-12 Created by Administrator
 */
package com.umessage.letsgo.domain.po.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.annotation.dataitem.ItemName;
import com.umessage.letsgo.core.annotation.dataitem.ItemValue;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.utils.QueryUtils;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.team.respone.MemberResponse;
import com.umessage.letsgo.domain.vo.team.respone.WebMemberResponse;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Catalog(code = "MemberEntity")
public class MemberEntity  implements Serializable {

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
     * 用户实体
     */
    @ApiModelProperty(value="用户实体")
    @Catalog
    private UserEntity userEntity;

    /**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID")
    /*@JsonIgnore*/
    private Long tId;
    /**
     * 腾讯群组ID
     */
    @ApiModelProperty(value="腾讯群组ID")
    private String teamId;

    /**
     * 小组ID
     */
    @ApiModelProperty(value="小组ID")
    private Long groupId;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String realName;
    /**
     * 性别【1：男；2：女】
     */
    @ApiModelProperty(value="性别【1：男；2：女】")
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
     * 年龄(根据出生日期做计算)
     */
    @ApiModelProperty(value="年龄(根据出生日期做计算)")
    private Integer age;

    /**
     * 手机号码
     */
    @ApiModelProperty(value="手机号码")
    private String phone;
    /**
     * 备用手机号
     */
    @ApiModelProperty(value="备用手机号")
    private String markPhone;
    /**
     * 头像地址
     */
    @ApiModelProperty(value="头像地址")
    private String photoUrl;
    /**
     * 邮箱
     */
    @JsonIgnore
    private String mail;
    /**
     * 住址
     */
    @JsonIgnore
    private String address;
    /**
     * 城市
     */
    @JsonIgnore
    private String city;
    /**
     * 省份
     */
    @JsonIgnore
    private String province;
    /**
     * 国家
     */
    @JsonIgnore
    private String country;
    /**
     * 身份【1：领队；2：导游；3：游客】
     */
    @ApiModelProperty(value="身份【1：领队；2：导游；3：游客】")
    @ItemValue(code = "role")
    private Integer role;
    /**
     * 身份【1：领队；2：导游；3：游客】
     */
    @ApiModelProperty(value="身份字段的翻译，意思是返回身份对应的字符串")
    @ItemName(code = "role")
    private String roleDescn;
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
     * 是否为小组长【1：是；0：否】
     */
    @ApiModelProperty(value="是否为小组长【1：是；0：否】")
    @ItemValue(code = "isLeader")
    private Integer isLeader;
    /**
     * 是否为小组长【1：是；0：否】
     */
    @ApiModelProperty(value="是否为小组长字段的翻译，意思是返回是否为小组长对应的字符串，例如：小组长")
    @ItemName(code = "isLeader")
    private String isLeaderDescn;
    /**
     * 是否为管理员【1：是；0：否】
     */
    @ApiModelProperty(value="是否为管理员【1：是；0：否】")
    private Integer isAdmin;
    /**
     * 加入状态【0：未加入；1：已加入】
     */
    @ApiModelProperty(value="加入状态【0：未加入；1：已加入】")
    @ItemValue(code = "joinStatus")
    private Integer joinStatus;
    /**
     * 加入状态【0：未加入；1：已加入】
     */
    @ApiModelProperty(value="加入状态字段的翻译，意思是返回加入状态对应的字符串，例如：未加入")
    @ItemName(code = "joinStatus")
    private String joinStatusDescn;
    /**
     * 证件类型
     */
    @ApiModelProperty(value="证件类型")
    private String cardType;
    /**
     * 证件号码
     */
    @ApiModelProperty(value="证件号码")
    private String cardNum;
    /**
     * 证件有效日期
     */
    @ApiModelProperty(value="证件有效日期")
    private String cardTime;

    /**
     * 微信
     */
    @ApiModelProperty(value="微信")
    @JsonIgnore
    private String weixin;

    /**
     * QQ
     */
    @ApiModelProperty(value="QQ")
    @JsonIgnore
    private String qq;

    /**
     * 照片库1
     */
    @ApiModelProperty(value="照片库1")
    @JsonIgnore
    private String photoLibrary1Url;
    /**
     * 照片库2
     */
    @ApiModelProperty(value="照片库2")
    @JsonIgnore
    private String photoLibrary2Url;
    /**
     * 照片库3
     */
    @ApiModelProperty(value="照片库3")
    @JsonIgnore
    private String photoLibrary3Url;
    /**
     * 照片库4
     */
    @ApiModelProperty(value="照片库4")
    @JsonIgnore
    private String photoLibrary4Url;

    /**
     * 照片库5
     */
    @ApiModelProperty(value="照片库5")
    @JsonIgnore
    private String photoLibrary5Url;

    /**
     * 照片库6
     */
    @ApiModelProperty(value="照片库6")
    @JsonIgnore
    private String photoLibrary6Url;
    /**
     * 照片库7
     */
    @ApiModelProperty(value="照片库7")
    @JsonIgnore
    private String photoLibrary7Url;
    /**
     * 照片库8
     */
    @ApiModelProperty(value="照片库8")
    @JsonIgnore
    private String photoLibrary8Url;
    /**
     * 照片库9
     */
    @ApiModelProperty(value="照片库9")
    @JsonIgnore
    private String photoLibrary9Url;
    /**
     * 照片库10
     */
    @ApiModelProperty(value="照片库10")
    @JsonIgnore
    private String photoLibrary10Url;

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
     * 小组名称
     */
    @ApiModelProperty(value="小组名称")
    private String groupName;
    /**
     * 小组组长电话
     */
    @ApiModelProperty(value="小组组长电话【新】")
    private String groupLeaderPhone;
    /**
     * 标识成员是否是当前登录的用户
     */
    @ApiModelProperty(value = "标识成员是否是当前登录的用户，如果是，值为1，如果不是，值为0【新】")
    private Integer isCurrentUser;
    /**
     * 标识成员是否和当前登录的用户在同一个小组
     */
    @ApiModelProperty(value = "标识成员是否和当前登录的用户在同一个小组，如果是，值为1，如果不是，值为0【新】")
    private Integer isPartner;

    @ApiModelProperty(value = "国家代号")
    private String countryCode;

    /**
     * 是否为新加入（即为自己通过扫描填写资料加入）
     */
    @ApiModelProperty(value="是否为新加入（即为自己通过扫描填写资料加入）【0：否；1：是】")
    private Integer isNewJoin;

    @ApiModelProperty(value="微信唯一标识")
    private String unionid;

    //以下为外部字段
    //团队名称
    private String tName;
    /**
     *必填字段数据验证
     */
    public WebMemberResponse dataVerify(){
        WebMemberResponse verifyResponse = new WebMemberResponse();
        verifyResponse.setMember(this);
        verifyResponse.setRetCode(ErrorConstant.INVALID_PARAMETER);
        if(realName == null || StringUtils.isEmpty(realName.trim())){
            verifyResponse.setRetMsg("姓名字段不能为空");
            return verifyResponse;
        }
        if(realName.length()>30){
            verifyResponse.setRetMsg("姓名("+realName+")长度不能超过30个字符");
            return verifyResponse;
        }
        if(sex == null){
            verifyResponse.setRetMsg("性别字段不能为空");
            return verifyResponse;
        }
        if((sex !=1 && sex !=2)){
            verifyResponse.setRetMsg("性别只能为\"男\"、\"女\"");
            return verifyResponse;
        }
        /*if(phone == null || StringUtils.isEmpty(phone.trim())){
            verifyResponse.setRetMsg("手机号不能为空");
            return verifyResponse;
        }
        if(!QueryUtils.isChinaPhone(phone)){
            verifyResponse.setRetMsg("手机号("+phone+")格式不正确");
            return verifyResponse;
        }*/
        if(!StringUtils.isEmpty(cardType)){
            if(!cardType.equals("身份证") && !cardType.equals("护照") && !cardType.equals("军人证")
                    && !cardType.equals("港澳通行证") && !cardType.equals("导游证")){
                verifyResponse.setRetMsg("不支持该证件类型("+cardType+")");
                return verifyResponse;
            }
            if(cardNum == null || StringUtils.isEmpty(cardNum.trim())){
                verifyResponse.setRetMsg("证件号不能为空");
                return verifyResponse;
            }


            if(cardTime == null || StringUtils.isEmpty(cardTime.trim())){
                verifyResponse.setRetMsg("证件有效期不能为空");
                return verifyResponse;
            }


            if(!QueryUtils.verifyDateType(cardTime)){
                verifyResponse.setRetMsg("证件有效期("+cardTime+")格式为\"yyyy-MM-dd\"");
                return verifyResponse;
            }

            cardNum = cardNum.trim();
            cardTime = cardTime.trim();
        }
        realName = QueryUtils.getChinaName(realName.trim());
        if (!StringUtils.isEmpty(phone)) {   // 如果手机号不为空时
            phone = phone.trim();
        }

        if(countryCode == null ||StringUtils.isEmpty(countryCode.trim())){
            verifyResponse.setRetMsg("国家代号不能为空");
            return verifyResponse;
        }
        if (countryCode.trim().equals("+86") && !StringUtils.isEmpty(phone) && !QueryUtils.isChinaPhone(countryCode.trim()+phone.trim())){
            verifyResponse.setRetMsg("手机号格式不正确");
            return verifyResponse;
        }
        if(countryCode.trim().equals("+86") && !StringUtils.isEmpty(phone) && phone.length() !=11 ){
            verifyResponse.setRetMsg("手机号格式不正确");
            return verifyResponse;
        }

        verifyResponse.setRetCode(0);
        return verifyResponse;
    }

    /**
     *导入excel必填字段数据验证
     */
    public WebMemberResponse dataVerifyImportExcel(){
        WebMemberResponse verifyResponse = new WebMemberResponse();
        verifyResponse.setMember(this);
        verifyResponse.setRetCode(ErrorConstant.INVALID_PARAMETER);
        if(realName == null || StringUtils.isEmpty(realName.trim())){
            verifyResponse.setRetMsg("姓名字段不能为空");
            return verifyResponse;
        }
        if(realName.length()>30){
            verifyResponse.setRetMsg("姓名("+realName+")长度不能超过30个字符");
            return verifyResponse;
        }
        if(sex == null){
            verifyResponse.setRetMsg("性别字段不能为空");
            return verifyResponse;
        }
        if((sex !=1 && sex !=2)){
            verifyResponse.setRetMsg("性别只能为\"男\"、\"女\"");
            return verifyResponse;
        }
        /*if(phone == null || StringUtils.isEmpty(phone.trim())){
            verifyResponse.setRetMsg("手机号不能为空");
            return verifyResponse;
        }
        if(!QueryUtils.isChinaPhone(phone)){
            verifyResponse.setRetMsg("手机号("+phone+")格式不正确");
            return verifyResponse;
        }*/
        if(!StringUtils.isEmpty(cardType)){
            if(!cardType.equals("身份证") && !cardType.equals("护照") && !cardType.equals("军人证")
                    && !cardType.equals("港澳通行证") && !cardType.equals("导游证")){
                verifyResponse.setRetMsg("不支持该证件类型("+cardType+")");
                return verifyResponse;
            }
            if(cardNum == null || StringUtils.isEmpty(cardNum.trim())){
                verifyResponse.setRetMsg("证件号不能为空");
                return verifyResponse;
            }


            if(cardTime == null || StringUtils.isEmpty(cardTime.trim())){
                verifyResponse.setRetMsg("证件有效期不能为空");
                return verifyResponse;
            }


            if(!QueryUtils.verifyDateType(cardTime)){
                verifyResponse.setRetMsg("证件有效期("+cardTime+")格式为\"yyyy-MM-dd\"");
                return verifyResponse;
            }

            cardNum = cardNum.trim();
            cardTime = cardTime.trim();
        }
        realName = QueryUtils.getChinaName(realName.trim());
        if (!StringUtils.isEmpty(phone)) {   // 如果手机号不为空时
            phone = phone.trim();
        }

        verifyResponse.setRetCode(0);
        return verifyResponse;
    }

    public Integer getIsNewJoin() {
        return isNewJoin;
    }

    public void setIsNewJoin(Integer isNewJoin) {
        this.isNewJoin = isNewJoin;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getMarkPhone() {
        return markPhone;
    }

    public void setMarkPhone(String markPhone) {
        this.markPhone = markPhone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getRoleDescn() {
        return roleDescn;
    }

    public void setRoleDescn(String roleDescn) {
        this.roleDescn = roleDescn;
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

    public Integer getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(Integer isLeader) {
        this.isLeader = isLeader;
    }

    public String getIsLeaderDescn() {
        return isLeaderDescn;
    }

    public void setIsLeaderDescn(String isLeaderDescn) {
        this.isLeaderDescn = isLeaderDescn;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(Integer joinStatus) {
        this.joinStatus = joinStatus;
    }

    public String getJoinStatusDescn() {
        return joinStatusDescn;
    }

    public void setJoinStatusDescn(String joinStatusDescn) {
        this.joinStatusDescn = joinStatusDescn;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardTime() {
        return cardTime;
    }

    public void setCardTime(String cardTime) {
        this.cardTime = cardTime;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupLeaderPhone() {
        return groupLeaderPhone;
    }

    public void setGroupLeaderPhone(String groupLeaderPhone) {
        this.groupLeaderPhone = groupLeaderPhone;
    }

    public Integer getIsCurrentUser() {
        return isCurrentUser;
    }

    public void setIsCurrentUser(Integer isCurrentUser) {
        this.isCurrentUser = isCurrentUser;
    }

    public Integer getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(Integer isPartner) {
        this.isPartner = isPartner;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhotoLibrary1Url() {
        return photoLibrary1Url;
    }

    public void setPhotoLibrary1Url(String photoLibrary1Url) {
        this.photoLibrary1Url = photoLibrary1Url;
    }

    public String getPhotoLibrary2Url() {
        return photoLibrary2Url;
    }

    public void setPhotoLibrary2Url(String photoLibrary2Url) {
        this.photoLibrary2Url = photoLibrary2Url;
    }

    public String getPhotoLibrary3Url() {
        return photoLibrary3Url;
    }

    public void setPhotoLibrary3Url(String photoLibrary3Url) {
        this.photoLibrary3Url = photoLibrary3Url;
    }

    public String getPhotoLibrary4Url() {
        return photoLibrary4Url;
    }

    public void setPhotoLibrary4Url(String photoLibrary4Url) {
        this.photoLibrary4Url = photoLibrary4Url;
    }

    public String getPhotoLibrary5Url() {
        return photoLibrary5Url;
    }

    public void setPhotoLibrary5Url(String photoLibrary5Url) {
        this.photoLibrary5Url = photoLibrary5Url;
    }

    public String getPhotoLibrary6Url() {
        return photoLibrary6Url;
    }

    public void setPhotoLibrary6Url(String photoLibrary6Url) {
        this.photoLibrary6Url = photoLibrary6Url;
    }

    public String getPhotoLibrary7Url() {
        return photoLibrary7Url;
    }

    public void setPhotoLibrary7Url(String photoLibrary7Url) {
        this.photoLibrary7Url = photoLibrary7Url;
    }

    public String getPhotoLibrary8Url() {
        return photoLibrary8Url;
    }

    public void setPhotoLibrary8Url(String photoLibrary8Url) {
        this.photoLibrary8Url = photoLibrary8Url;
    }

    public String getPhotoLibrary9Url() {
        return photoLibrary9Url;
    }

    public void setPhotoLibrary9Url(String photoLibrary9Url) {
        this.photoLibrary9Url = photoLibrary9Url;
    }

    public String getPhotoLibrary10Url() {
        return photoLibrary10Url;
    }

    public void setPhotoLibrary10Url(String photoLibrary10Url) {
        this.photoLibrary10Url = photoLibrary10Url;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    @Override
    public String toString() {
        return "MemberEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", userEntity=" + userEntity +
                ", tId=" + tId +
                ", teamId='" + teamId + '\'' +
                ", groupId=" + groupId +
                ", realName='" + realName + '\'' +
                ", sex=" + sex +
                ", sexDescn='" + sexDescn + '\'' +
                ", birthday='" + birthday + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", markPhone='" + markPhone + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", mail='" + mail + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", role=" + role +
                ", roleDescn='" + roleDescn + '\'' +
                ", type=" + type +
                ", typeDescn='" + typeDescn + '\'' +
                ", isLeader=" + isLeader +
                ", isLeaderDescn='" + isLeaderDescn + '\'' +
                ", isAdmin=" + isAdmin +
                ", joinStatus=" + joinStatus +
                ", joinStatusDescn='" + joinStatusDescn + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardNum='" + cardNum + '\'' +
                ", cardTime='" + cardTime + '\'' +
                ", weixin='" + weixin + '\'' +
                ", qq='" + qq + '\'' +
                ", photoLibrary1Url='" + photoLibrary1Url + '\'' +
                ", photoLibrary2Url='" + photoLibrary2Url + '\'' +
                ", photoLibrary3Url='" + photoLibrary3Url + '\'' +
                ", photoLibrary4Url='" + photoLibrary4Url + '\'' +
                ", photoLibrary5Url='" + photoLibrary5Url + '\'' +
                ", photoLibrary6Url='" + photoLibrary6Url + '\'' +
                ", photoLibrary7Url='" + photoLibrary7Url + '\'' +
                ", photoLibrary8Url='" + photoLibrary8Url + '\'' +
                ", photoLibrary9Url='" + photoLibrary9Url + '\'' +
                ", photoLibrary10Url='" + photoLibrary10Url + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version=" + version +
                ", groupName='" + groupName + '\'' +
                ", groupLeaderPhone='" + groupLeaderPhone + '\'' +
                ", isCurrentUser=" + isCurrentUser +
                ", isPartner=" + isPartner +
                ", countryCode='" + countryCode + '\'' +
                ", isNewJoin=" + isNewJoin +
                ", unionid='" + unionid + '\'' +
                '}';
    }
}