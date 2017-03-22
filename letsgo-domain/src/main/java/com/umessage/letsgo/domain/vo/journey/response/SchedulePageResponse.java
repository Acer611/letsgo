package com.umessage.letsgo.domain.vo.journey.response;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/29.
 */
public class SchedulePageResponse extends CommonResponse {

    private ScheduleEntity scheduleEntity;
    private Page<ScheduleEntity> scheduleList;
    private long totals;
    private int pages;

    public static SchedulePageResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends SchedulePageResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        SchedulePageResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public ScheduleEntity getScheduleEntity() {
        return scheduleEntity;
    }

    public void setScheduleEntity(ScheduleEntity scheduleEntity) {
        this.scheduleEntity = scheduleEntity;
    }

    public Page<ScheduleEntity> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(Page<ScheduleEntity> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public long getTotals() {
        return totals;
    }

    public void setTotals(long totals) {
        this.totals = totals;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
