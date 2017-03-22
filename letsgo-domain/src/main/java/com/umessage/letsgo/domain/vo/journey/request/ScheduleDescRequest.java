package com.umessage.letsgo.domain.vo.journey.request;

import com.umessage.letsgo.domain.po.journey.ScheduleDescEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhp on 2016/9/12.
 */
public class ScheduleDescRequest implements Serializable {
    private List<ScheduleDescEntity> scheduleDescs;

    public List<ScheduleDescEntity> getScheduleDescs() {
        return scheduleDescs;
    }

    public void setScheduleDescs(List<ScheduleDescEntity> scheduleDescs) {
        this.scheduleDescs = scheduleDescs;
    }
}
