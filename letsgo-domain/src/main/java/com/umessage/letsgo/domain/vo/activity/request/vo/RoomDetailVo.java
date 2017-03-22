package com.umessage.letsgo.domain.vo.activity.request.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ZhaoYidong on 2016/5/26.
 */
public class RoomDetailVo implements Serializable {
    /**
     * 小组id
     */
    @ApiModelProperty(value = "小组id")
    private Long groupId;

    /**
     * 成员id
     */
    @ApiModelProperty(value = "成员id")
    private Long memberId;


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "RoomDetailVo{" +
                "groupId=" + groupId +
                ", memberId=" + memberId +
                '}';
    }
}
