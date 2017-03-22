package com.umessage.letsgo.domain.vo.team.respone;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

/**
 * Created by admin on 2016/10/31.
 */
public class TeamPageRespone extends CommonResponse {

    private TeamEntity scheduleEntity;
    private Page<TeamEntity> scheduleList;
    private long totals;
    private int pages;

    public static TeamPageRespone createNotFoundResponse(String retMsg){
        class NotFoundResponse extends TeamPageRespone {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        TeamPageRespone response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public TeamEntity getScheduleEntity() {
        return scheduleEntity;
    }

    public void setScheduleEntity(TeamEntity scheduleEntity) {
        this.scheduleEntity = scheduleEntity;
    }

    public Page<TeamEntity> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(Page<TeamEntity> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public long getTotals() {
        return totals;
    }

    public void setTotals(long totals) {
        this.totals = totals;
    }
}
