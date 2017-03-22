package com.umessage.letsgo.domain.vo.notice.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wendy on 2016/6/4.
 */
public class RemindRequest implements Serializable {
    /**
     * 通知ID
     */
    @ApiModelProperty(value="通知ID", required=true)
    private Long noticeId;
    /**
     * 接受人ID，如果是小组，接收人ID设置组长ID
     */
    @ApiModelProperty(value = "接受人ID，如果是小组，接收人ID设置组长ID", required = true)
    private List<Long> memberIds;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }
}
