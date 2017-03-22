package com.umessage.letsgo.domain.vo.team.respone;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.team.EventRecordEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

/**
 * Created by peng on 2016/8/23.
 */
public class EventRecordPageResponse extends CommonResponse {

    private EventRecordEntity eventRecordEntity;
    private Page<EventRecordEntity> eventList;
    private long totals;
    private int pages;

    public EventRecordEntity getEventRecordEntity() {
        return eventRecordEntity;
    }

    public void setEventRecordEntity(EventRecordEntity eventRecordEntity) {
        this.eventRecordEntity = eventRecordEntity;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getTotals() {
        return totals;
    }

    public void setTotals(long totals) {
        this.totals = totals;
    }

    public Page<EventRecordEntity> getEventList() {
        return eventList;
    }

    public void setEventList(Page<EventRecordEntity> eventList) {
        this.eventList = eventList;
    }

    public static EventRecordPageResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends EventRecordPageResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        EventRecordPageResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

}
