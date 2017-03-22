package com.umessage.letsgo.domain.vo.security.respone;

import com.umessage.letsgo.domain.po.security.RewardDetailsEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by wendy on 2016/8/30.
 */
public class RewardDetailsResponse extends CommonResponse {
    private String inviteCode;

    private Integer inviteCount;

    private Double totalRewardAmount;

    private Map<String, List<RewardDetailsEntity>> rewardDetailsMap;

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

    public Double getTotalRewardAmount() {
        return totalRewardAmount;
    }

    public void setTotalRewardAmount(Double totalRewardAmount) {
        this.totalRewardAmount = totalRewardAmount;
    }

    public Map<String, List<RewardDetailsEntity>> getRewardDetailsMap() {
        return rewardDetailsMap;
    }

    public void setRewardDetailsMap(Map<String, List<RewardDetailsEntity>> rewardDetailsMap) {
        this.rewardDetailsMap = rewardDetailsMap;
    }

}
