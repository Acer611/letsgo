package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.domain.po.journey.ScheduleEntity;

/**
 * Created by wendy on 2016/8/26.
 */
public class StopDataResponse {
    private ScheduleEntity scheduleEntity;

    public ScheduleEntity getScheduleEntity() {
        return scheduleEntity;
    }

    public void setScheduleEntity(ScheduleEntity scheduleEntity) {
        this.scheduleEntity = scheduleEntity;
    }
}
