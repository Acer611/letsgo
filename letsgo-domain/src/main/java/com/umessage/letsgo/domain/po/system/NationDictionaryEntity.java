/*
 * NationDictionaryEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-06-06 Created by wendy
 */
package com.umessage.letsgo.domain.po.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class NationDictionaryEntity  implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    @JsonIgnore
    private Long id;
    /**
     * 币种三字码
     */
    @ApiModelProperty(value="币种三字码")
    private String code;
    /**
     * 币种名称
     */
    @ApiModelProperty(value="币种名称")
    private String name;
    /**
     * 国家名称
     */
    @ApiModelProperty(value="国家名称")
    @JsonIgnore
    private String nationname;
    /**
     * 币种符号
     */
    @ApiModelProperty(value="币种符号")
    @JsonIgnore
    private String coinsign;
    /**
     * 国旗地址
     */
    @ApiModelProperty(value="国旗地址")
    private String nationflagurl;
    /**
     * 国家描述
     */
    @ApiModelProperty(value="国家描述")
    @JsonIgnore
    private String nationdesc;
    /**
     * 记录时间
     */
    @ApiModelProperty(value="记录时间")
    @JsonIgnore
    private Date createtime;
    /**
     * 是否可用
     */
    @ApiModelProperty(value="是否可用")
    @JsonIgnore
    private Integer useful;
    /**
     * 洲编码
     */
    @ApiModelProperty(value="洲编码")
    @JsonIgnore
    private String continentscode;
    /**
     * 洲名称
     */
    @ApiModelProperty(value="洲名称")
    @JsonIgnore
    private String continentsname;
    /**
     * 是否热门
     */
    @ApiModelProperty(value="是否热门")
    @JsonIgnore
    private Integer ishot;
    /**
     * 汇率，以美元为准基数
     */
    @ApiModelProperty(value="汇率，以美元为准基数")
    private String rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNationname() {
        return nationname;
    }

    public void setNationname(String nationname) {
        this.nationname = nationname == null ? null : nationname.trim();
    }

    public String getCoinsign() {
        return coinsign;
    }

    public void setCoinsign(String coinsign) {
        this.coinsign = coinsign == null ? null : coinsign.trim();
    }

    public String getNationflagurl() {
        return nationflagurl;
    }

    public void setNationflagurl(String nationflagurl) {
        this.nationflagurl = nationflagurl == null ? null : nationflagurl.trim();
    }

    public String getNationdesc() {
        return nationdesc;
    }

    public void setNationdesc(String nationdesc) {
        this.nationdesc = nationdesc == null ? null : nationdesc.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getUseful() {
        return useful;
    }

    public void setUseful(Integer useful) {
        this.useful = useful;
    }

    public String getContinentscode() {
        return continentscode;
    }

    public void setContinentscode(String continentscode) {
        this.continentscode = continentscode == null ? null : continentscode.trim();
    }

    public String getContinentsname() {
        return continentsname;
    }

    public void setContinentsname(String continentsname) {
        this.continentsname = continentsname == null ? null : continentsname.trim();
    }

    public Integer getIshot() {
        return ishot;
    }

    public void setIshot(Integer ishot) {
        this.ishot = ishot;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}