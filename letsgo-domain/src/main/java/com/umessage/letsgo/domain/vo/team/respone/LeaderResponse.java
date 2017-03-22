package com.umessage.letsgo.domain.vo.team.respone;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.LeaderEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

/**
 * Created by ZhaoYidong on 2016/6/29.
 */
public class LeaderResponse extends CommonResponse{
    private Page<LeaderEntity> list;
    private LeaderEntity leader;
    private long totals;
    private int pages;



    public static LeaderResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends LeaderResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        LeaderResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public Page<LeaderEntity> getList() {
        return list;
    }

    public void setList(Page<LeaderEntity> list) {
        this.list = list;
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

    public LeaderEntity getLeader() {
        return leader;
    }

    public void setLeader(LeaderEntity leader) {
        this.leader = leader;
    }
}
