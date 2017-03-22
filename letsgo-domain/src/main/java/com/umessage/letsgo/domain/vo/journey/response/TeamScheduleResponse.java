package com.umessage.letsgo.domain.vo.journey.response;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengguoqing on 2016/8/26.
 */
public class TeamScheduleResponse implements Serializable {
    private Page<TeamScheduleVo> page;

    public Page<TeamScheduleVo> getPage() {
        return page;
    }

    public void setPage(Page<TeamScheduleVo> page) {
        this.page = page;
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

    private long totals;
    private int pages;
}
