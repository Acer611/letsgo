package com.umessage.letsgo.domain.vo.journey.response.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ZhaoYidong on 2016/6/7.
 */
public class TravelAgencyInfoVo implements Serializable {

    /**
     * 地接社信息
     */
    @ApiModelProperty(value="地接社信息")
    private String travelAgencyInfo;
    /**
     * 接团社信息
     */
    @ApiModelProperty(value="接团社信息")
    private String groupClubInfo;



    public String getTravelAgencyInfo() {
        return travelAgencyInfo;
    }

    public void setTravelAgencyInfo(String travelAgencyInfo) {
        this.travelAgencyInfo = travelAgencyInfo;
    }

    public String getGroupClubInfo() {
        return groupClubInfo;
    }

    public void setGroupClubInfo(String groupClubInfo) {
        this.groupClubInfo = groupClubInfo;
    }
}
