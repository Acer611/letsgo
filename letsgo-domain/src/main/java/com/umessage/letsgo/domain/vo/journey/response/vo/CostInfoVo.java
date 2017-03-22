package com.umessage.letsgo.domain.vo.journey.response.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ZhaoYidong on 2016/6/7.
 */
public class CostInfoVo implements Serializable {

    /**
     * 旅游费用包含
     */
    @ApiModelProperty(value="旅游费用包含")
    private String costInclude;
    /**
     * 旅游费用不包含
     */
    @ApiModelProperty(value="旅游费用不包含")
    private String costNotInclude;
    /**
     * 自费项目介绍
     */
    @ApiModelProperty(value="自费项目介绍")
    private String ownExpenceInfo;


    public String getCostInclude() {
        return costInclude;
    }

    public void setCostInclude(String costInclude) {
        this.costInclude = costInclude;
    }

    public String getCostNotInclude() {
        return costNotInclude;
    }

    public void setCostNotInclude(String costNotInclude) {
        this.costNotInclude = costNotInclude;
    }

    public String getOwnExpenceInfo() {
        return ownExpenceInfo;
    }

    public void setOwnExpenceInfo(String ownExpenceInfo) {
        this.ownExpenceInfo = ownExpenceInfo;
    }
}
