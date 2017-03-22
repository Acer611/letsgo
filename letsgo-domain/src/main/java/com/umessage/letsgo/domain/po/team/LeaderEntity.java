/*
 * LeaderEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by ZhaoYidong
 */
package com.umessage.letsgo.domain.po.team;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.utils.QueryUtils;
import com.umessage.letsgo.domain.vo.team.respone.LeaderResponse;

public class LeaderEntity  implements Serializable {

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
     * 旅行社ID
     */
    @ApiModelProperty(value="旅行社ID")
    private Long travelId;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;
    /**
     * 性别【1：男性；2：女性】
     */
    @ApiModelProperty(value="性别【1：男性；2：女性】")
    private Integer sex;
    /**
     * 类别[1:领队；2:导游；3:导游兼领队]
     */
    @ApiModelProperty(value="类别[1:领队；2:导游；3:导游兼领队]")
    private Integer leaderType;
    /**
     * 状态「1:专职；2兼职；3:离职」
     */
    @ApiModelProperty(value="状态「1:专职；2兼职；3:离职」")
    private Integer leaderStatus;
    /**
     * 头像地址
     */
    @ApiModelProperty(value="头像地址")
    private String photoUrl;
    /**
     * 出生日期
     */
    @ApiModelProperty(value="出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 手机号码
     */
    @ApiModelProperty(value="手机号码")
    private String phone;
    /**
     * 微信
     */
    @ApiModelProperty(value="微信")
    private String weixin;
    /**
     * QQ
     */
    @ApiModelProperty(value="QQ")
    private String qq;
    /**
     * 开始带团时间（计算带团时间，有误差（中间有1年没带的情况））
     */
    @ApiModelProperty(value="开始带团时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leadTime;
    /**
     * 擅长路线：港奥台
     */
    @ApiModelProperty(value="擅长路线：港奥台")
    private String destinationGroup1;
    /**
     * 擅长路线：日韩
     */
    @ApiModelProperty(value="擅长路线：日韩")
    private String destinationGroup2;
    /**
     * 擅长路线：东南亚
     */
    @ApiModelProperty(value="擅长路线：东南亚")
    private String destinationGroup3;
    /**
     * 擅长路线：欧洲
     */
    @ApiModelProperty(value="擅长路线：欧洲")
    private String destinationGroup4;
    /**
     * 擅长路线：美洲
     */
    @ApiModelProperty(value="擅长路线：美洲")
    private String destinationGroup5;
    /**
     * 擅长路线：澳洲
     */
    @ApiModelProperty(value="擅长路线：澳洲")
    private String destinationGroup6;
    /**
     * 擅长路线：中东非洲
     */
    @ApiModelProperty(value="擅长路线：中东非洲")
    private String destinationGroup7;
    /**
     * 擅长路线：中东非洲
     */
    @ApiModelProperty(value="擅长路线：其他")
    private String destinationGroup8;
    /**
     * 证件类型[1:领队证；2:导游证] 
     */
    @ApiModelProperty(value="证件类型[1:领队证；2:导游证] ")
    private Integer cardType;
    /**
     * 证件号
     */
    @ApiModelProperty(value="证件号")
    private String cardCode;
    /**
     * 证件有效期
     */
    @ApiModelProperty(value="证件有效期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validityDate;
    /**
     * 语言种类1
     */
    @ApiModelProperty(value="语言种类1")
    private String language1;
    /**
     * 语言1熟练程度：1:非常熟练；2:一般；3略懂
     */
    @ApiModelProperty(value="语言1熟练程度：1:非常熟练；2:一般；3略懂")
    private Integer language1Level;
    /**
     * 语言种类2
     */
    @ApiModelProperty(value="语言种类2")
    private String language2;
    /**
     * 语言2熟练程度：1:非常熟练；2:一般；3略懂
     */
    @ApiModelProperty(value="语言2熟练程度：1:非常熟练；2:一般；3略懂")
    private Integer language2Level;
    /**
     * 语言种类3
     */
    @ApiModelProperty(value="语言种类3")
    private String language3;
    /**
     * 语言3熟练程度：1:非常熟练；2:一般；3略懂
     */
    @ApiModelProperty(value="语言3熟练程度：1:非常熟练；2:一般；3略懂")
    private Integer language3Level;
    /**
     * 照片库2
     */
    @ApiModelProperty(value="照片库2")
    private String photoLibrary2Url;
    /**
     * 照片库3
     */
    @ApiModelProperty(value="照片库3")
    private String photoLibrary3Url;
    /**
     * 照片库1
     */
    @ApiModelProperty(value="照片库1")
    private String photoLibrary1Url;
    /**
     * 照片库4
     */
    @ApiModelProperty(value="照片库4")
    private String photoLibrary4Url;
    /**
     * 照片库5
     */
    @ApiModelProperty(value="照片库5")
    private String photoLibrary5Url;
    /**
     * 照片库6
     */
    @ApiModelProperty(value="照片库6")
    private String photoLibrary6Url;
    /**
     * 照片库7
     */
    @ApiModelProperty(value="照片库7")
    private String photoLibrary7Url;
    /**
     * 照片库8
     */
    @ApiModelProperty(value="照片库8")
    private String photoLibrary8Url;
    /**
     * 照片库9
     */
    @ApiModelProperty(value="照片库9")
    private String photoLibrary9Url;
    /**
     * 照片库10
     */
    @ApiModelProperty(value="照片库10")
    private String photoLibrary10Url;
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
     * 版本号
     */
    @ApiModelProperty(value="带团时间：n年")
    private Integer leadYears;

    /**
     * 自我评价标签，传入JSON字符串，格式：{"evaluationTag":["tag1","tag2","tag3","tag4","tag5"]}
     */
    @ApiModelProperty(value="自我评价标签，传入JSON字符串，格式：{\"evaluationTag\":[\"tag1\",\"tag2\",\"tag3\",\"tag4\",\"tag5\"]} ")
    private String evaluationTag;
    /**
     * 擅长语种
     */
    @ApiModelProperty(value="擅长语种")
    private String languages;
    /**
     * 擅长旅游线路
     */
    @ApiModelProperty(value="擅长旅游线路")
    private String tourRoutes;

    @ApiModelProperty(value="国家代号")
    private  String countryCode;
    /**
     *必填字段数据验证
     */
    public LeaderResponse dataVerify(){
        LeaderResponse verifyResponse = new LeaderResponse();
        verifyResponse.setLeader(this);
        verifyResponse.setRetCode(ErrorConstant.INVALID_PARAMETER);
        if (name == null || StringUtils.isEmpty(name.trim())){
            verifyResponse.setRetMsg("姓名不能为空");
            return verifyResponse;
        }
        if (name.length()>30){
            verifyResponse.setRetMsg("姓名("+name+")长度不能超过30个字符");
            return verifyResponse;
        }
        if (sex == null) {
            verifyResponse.setRetMsg("性别字段不能为空");
            return verifyResponse;
        }
        if((sex !=1 && sex !=2)){
            verifyResponse.setRetMsg("性别("+sex+")只能为\"男\"、\"女\"");
            return verifyResponse;
        }
        if(phone == null || StringUtils.isEmpty(phone.trim())){
            verifyResponse.setRetMsg("手机号不能为空");
            return verifyResponse;
        }
//        if(!QueryUtils.isChinaPhone(phone)){
//            verifyResponse.setRetMsg("手机号("+phone+")格式不正确");
//            return verifyResponse;
//        }
        if(countryCode == null ||StringUtils.isEmpty(countryCode.trim())){
            verifyResponse.setRetMsg("国家代号不能为空");
            return verifyResponse;
        }

        name = QueryUtils.getChinaName(name.trim());
        phone = phone.trim();

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

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getLeaderType() {
        return leaderType;
    }

    public void setLeaderType(Integer leaderType) {
        this.leaderType = leaderType;
    }

    public Integer getLeaderStatus() {
        return leaderStatus;
    }

    public void setLeaderStatus(Integer leaderStatus) {
        this.leaderStatus = leaderStatus;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl == null ? null : photoUrl.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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
        this.qq = qq == null ? null : qq.trim();
    }

    public Date getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Date leadTime) {
        this.leadTime = leadTime;
    }

    public String getDestinationGroup1() {
        return destinationGroup1;
    }

    public void setDestinationGroup1(String destinationGroup1) {
        this.destinationGroup1 = destinationGroup1 == null ? null : destinationGroup1.trim();
    }

    public String getDestinationGroup2() {
        return destinationGroup2;
    }

    public void setDestinationGroup2(String destinationGroup2) {
        this.destinationGroup2 = destinationGroup2 == null ? null : destinationGroup2.trim();
    }

    public String getDestinationGroup3() {
        return destinationGroup3;
    }

    public void setDestinationGroup3(String destinationGroup3) {
        this.destinationGroup3 = destinationGroup3 == null ? null : destinationGroup3.trim();
    }

    public String getDestinationGroup4() {
        return destinationGroup4;
    }

    public void setDestinationGroup4(String destinationGroup4) {
        this.destinationGroup4 = destinationGroup4 == null ? null : destinationGroup4.trim();
    }

    public String getDestinationGroup5() {
        return destinationGroup5;
    }

    public void setDestinationGroup5(String destinationGroup5) {
        this.destinationGroup5 = destinationGroup5 == null ? null : destinationGroup5.trim();
    }

    public String getDestinationGroup6() {
        return destinationGroup6;
    }

    public void setDestinationGroup6(String destinationGroup6) {
        this.destinationGroup6 = destinationGroup6 == null ? null : destinationGroup6.trim();
    }

    public String getDestinationGroup7() {
        return destinationGroup7;
    }

    public void setDestinationGroup7(String destinationGroup7) {
        this.destinationGroup7 = destinationGroup7 == null ? null : destinationGroup7.trim();
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode == null ? null : cardCode.trim();
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    public String getLanguage1() {
        return language1;
    }

    public void setLanguage1(String language1) {
        this.language1 = language1 == null ? null : language1.trim();
    }

    public Integer getLanguage1Level() {
        return language1Level;
    }

    public void setLanguage1Level(Integer language1Level) {
        this.language1Level = language1Level;
    }

    public String getLanguage2() {
        return language2;
    }

    public void setLanguage2(String language2) {
        this.language2 = language2 == null ? null : language2.trim();
    }

    public Integer getLanguage2Level() {
        return language2Level;
    }

    public void setLanguage2Level(Integer language2Level) {
        this.language2Level = language2Level;
    }

    public String getLanguage3() {
        return language3;
    }

    public void setLanguage3(String language3) {
        this.language3 = language3 == null ? null : language3.trim();
    }

    public Integer getLanguage3Level() {
        return language3Level;
    }

    public void setLanguage3Level(Integer language3Level) {
        this.language3Level = language3Level;
    }

    public String getPhotoLibrary2Url() {
        return photoLibrary2Url;
    }

    public void setPhotoLibrary2Url(String photoLibrary2Url) {
        this.photoLibrary2Url = photoLibrary2Url == null ? null : photoLibrary2Url.trim();
    }

    public String getPhotoLibrary3Url() {
        return photoLibrary3Url;
    }

    public void setPhotoLibrary3Url(String photoLibrary3Url) {
        this.photoLibrary3Url = photoLibrary3Url == null ? null : photoLibrary3Url.trim();
    }

    public String getPhotoLibrary1Url() {
        return photoLibrary1Url;
    }

    public void setPhotoLibrary1Url(String photoLibrary1Url) {
        this.photoLibrary1Url = photoLibrary1Url == null ? null : photoLibrary1Url.trim();
    }

    public String getPhotoLibrary4Url() {
        return photoLibrary4Url;
    }

    public void setPhotoLibrary4Url(String photoLibrary4Url) {
        this.photoLibrary4Url = photoLibrary4Url == null ? null : photoLibrary4Url.trim();
    }

    public String getPhotoLibrary5Url() {
        return photoLibrary5Url;
    }

    public void setPhotoLibrary5Url(String photoLibrary5Url) {
        this.photoLibrary5Url = photoLibrary5Url == null ? null : photoLibrary5Url.trim();
    }

    public String getPhotoLibrary6Url() {
        return photoLibrary6Url;
    }

    public void setPhotoLibrary6Url(String photoLibrary6Url) {
        this.photoLibrary6Url = photoLibrary6Url == null ? null : photoLibrary6Url.trim();
    }

    public String getPhotoLibrary7Url() {
        return photoLibrary7Url;
    }

    public void setPhotoLibrary7Url(String photoLibrary7Url) {
        this.photoLibrary7Url = photoLibrary7Url == null ? null : photoLibrary7Url.trim();
    }

    public String getPhotoLibrary8Url() {
        return photoLibrary8Url;
    }

    public void setPhotoLibrary8Url(String photoLibrary8Url) {
        this.photoLibrary8Url = photoLibrary8Url == null ? null : photoLibrary8Url.trim();
    }

    public String getPhotoLibrary9Url() {
        return photoLibrary9Url;
    }

    public void setPhotoLibrary9Url(String photoLibrary9Url) {
        this.photoLibrary9Url = photoLibrary9Url == null ? null : photoLibrary9Url.trim();
    }

    public String getPhotoLibrary10Url() {
        return photoLibrary10Url;
    }

    public void setPhotoLibrary10Url(String photoLibrary10Url) {
        this.photoLibrary10Url = photoLibrary10Url == null ? null : photoLibrary10Url.trim();
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

    public Integer getLeadYears() {
        return leadYears;
    }

    public void setLeadYears(Integer leadYears) {
        this.leadYears = leadYears;
    }

    public String getDestinationGroup8() {
        return destinationGroup8;
    }

    public void setDestinationGroup8(String destinationGroup8) {
        this.destinationGroup8 = destinationGroup8;
    }

	public String getEvaluationTag() {
		return evaluationTag;
	}

	public void setEvaluationTag(String evaluationTag) {
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


    
}