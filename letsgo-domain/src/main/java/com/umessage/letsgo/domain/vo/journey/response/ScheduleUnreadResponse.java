package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

/**
 * Created by wendy on 2016/9/2.
 */
public class ScheduleUnreadResponse extends CommonResponse {
    private Integer travelingUnread;
    private Integer paperTravelUnread;
    private Integer allUnread;

    public Integer getTravelingUnread() {
        return travelingUnread;
    }

    public void setTravelingUnread(Integer travelingUnread) {
        this.travelingUnread = travelingUnread;
    }

    public Integer getPaperTravelUnread() {
        return paperTravelUnread;
    }

    public void setPaperTravelUnread(Integer paperTravelUnread) {
        this.paperTravelUnread = paperTravelUnread;
    }

    public Integer getAllUnread() {
        return allUnread;
    }

    public void setAllUnread(Integer allUnread) {
        this.allUnread = allUnread;
    }
}
