package com.umessage.letsgo.domain.vo.security.request;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by wendy on 2016/8/30.
 */
public class RewardDetailsRequest extends CommonRequest {
    private int pageNum;

    private Long userId;
    /**
     * 奖励金额
     */
    private Double amount;
    /**
     * 奖励描述
     */
    private String descn;
    /**
     * 是否入账【0：未入账；1：已入账】
     */
    private Integer isInto;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 奖励来源（用户ID）
     */
    private Long fromId;
    /**
     * 奖励类型【1：邀请奖励；2：带团奖励；3：分享奖励】
     */
    private Integer rewardType;
    /**
     * 团队ID
     */
    private Long teamId;
    /**
     * 带团奖励对象 1：邀请人，2：被邀请人
     */
    private Integer rewardFrom;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

    public Integer getIsInto() {
        return isInto;
    }

    public void setIsInto(Integer isInto) {
        this.isInto = isInto;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getRewardFrom() {
        return rewardFrom;
    }

    public void setRewardFrom(Integer rewardFrom) {
        this.rewardFrom = rewardFrom;
    }
}
