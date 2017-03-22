package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wendy on 2016/6/17.
 */
public class AdministratorInfo implements Serializable {
    @ApiModelProperty(value = "是否有管理权限的导游，值为1，则有，值为0，则没有；如果领队还处于未加入状态，值为2")
    private Integer existGuide;
    @ApiModelProperty(value = "领队在腾讯IM中的唯一标识，用于与领队私聊")
    private String leaderPeer;
    @ApiModelProperty(value = "导游在腾讯IM中的唯一标识，用于与导游私聊")
    private String tourGuidePeer;


    public Integer getExistGuide() {
        return existGuide;
    }

    public void setExistGuide(Integer existGuide) {
        this.existGuide = existGuide;
    }

    public String getLeaderPeer() {
        return leaderPeer;
    }

    public void setLeaderPeer(String leaderPeer) {
        this.leaderPeer = leaderPeer;
    }

    public String getTourGuidePeer() {
        return tourGuidePeer;
    }

    public void setTourGuidePeer(String tourGuidePeer) {
        this.tourGuidePeer = tourGuidePeer;
    }

}
