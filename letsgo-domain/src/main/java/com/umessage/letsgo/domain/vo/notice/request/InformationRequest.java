package com.umessage.letsgo.domain.vo.notice.request;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2016/5/10.
 */
public class InformationRequest extends CommonRequest {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 上一次的最新通知ID
     */
    private Long lastNoticeId;
    /**
     * 上一次的最新集合ID
     */
    private Long lastGatherId;
    /**
     * 上一次的最新公告ID
     */
    private Long lastAnnouncementId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLastNoticeId() {
        return lastNoticeId;
    }

    public void setLastNoticeId(Long lastNoticeId) {
        this.lastNoticeId = lastNoticeId;
    }

    public Long getLastGatherId() {
        return lastGatherId;
    }

    public void setLastGatherId(Long lastGatherId) {
        this.lastGatherId = lastGatherId;
    }

    public Long getLastAnnouncementId() {
        return lastAnnouncementId;
    }

    public void setLastAnnouncementId(Long lastAnnouncementId) {
        this.lastAnnouncementId = lastAnnouncementId;
    }
}
