package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

/**
 * Created by wendy on 2016/9/2.
 */
public class WaitingResponse extends CommonResponse {
    List<String> busyList;
    List<ScheduleEntity> travelList;
    List<String> freeList;

    public List<String> getBusyList() {
        return busyList;
    }

    public void setBusyList(List<String> busyList) {
        this.busyList = busyList;
    }

    public List<ScheduleEntity> getTravelList() {
        return travelList;
    }

    public void setTravelList(List<ScheduleEntity> travelList) {
        this.travelList = travelList;
    }

    public List<String> getFreeList() {
        return freeList;
    }

    public void setFreeList(List<String> freeList) {
        this.freeList = freeList;
    }
}
