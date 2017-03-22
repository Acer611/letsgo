package com.umessage.letsgo.domain.po.system;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by pw on 2016/9/8.
 */
public class LogManage {

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
     * 账号类型:0旅行社管理员，1门店销售（门店），2计调，3领队，
     */
    @ApiModelProperty(value="账号类型:0旅行社管理员，1门店销售（门店），2计调，3领队，")
    private Integer accountType;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;
    /**
     * 操作类型
     */
    @ApiModelProperty(value="操作类型")
    private String operateType;
    /**
     * 操作模块：0团队行程管理；1统计报表；2领队导游管理；3日志管理；4游客管理；5账户管理；6我的设置
     */
    @ApiModelProperty(value="操作模块：0团队行程管理；1统计报表；2领队导游管理；3日志管理；4游客管理；5账户管理；6我的设置")
    private String operateModel;
    /**
     * 操作内容
     */
    @ApiModelProperty(value="操作内容")
    private String operateContent;
    /**
     * 操作时间
     */
    @ApiModelProperty(value="操作时间")
    private Date operateTime;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    /**
     * 版本号
     */
    @ApiModelProperty(value="版本号")
    private Long version;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateModel() {
        return operateModel;
    }

    public void setOperateModel(String operateModel) {
        this.operateModel = operateModel;
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
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
