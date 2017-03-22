package com.umessage.letsgo.domain.po.team;

import com.umessage.letsgo.core.annotation.dataitem.ItemValue;

import io.swagger.annotations.ApiModelProperty;

public class AnalyticalData {

	/**
     * 性别【1：男；2：女】
     */
    @ApiModelProperty(value="性别【1：男；2：女】")
    @ItemValue(code = "sex")
	private Integer sex;
    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
	private Long userId;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String realName;
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
     * 省份
     */
    @ApiModelProperty(value="省份")
    private String province;
    /**
     * 职业代号
     */
    @ApiModelProperty(value="职业代码")
    private Integer proCode;
    /**
     * 职业名称
     */
    @ApiModelProperty(value="职业名称")
    private String proName;
    /**
     * 下次是否需要提交职业
     * 1：需要；0：不需要
     */
    @ApiModelProperty(value="下次是否需要提交职业")
    private Integer isSubmitNext;
    
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Integer getProCode() {
		return proCode;
	}
	public void setProCode(Integer proCode) {
		this.proCode = proCode;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public Integer getIsSubmitNext() {
		return isSubmitNext;
	}
	public void setIsSubmitNext(Integer isSubmitNext) {
		this.isSubmitNext = isSubmitNext;
	}
}
