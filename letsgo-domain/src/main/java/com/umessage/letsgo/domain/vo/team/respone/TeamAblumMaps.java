package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.domain.po.team.TeamAblumEntity;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/9/22.
 */
public class TeamAblumMaps {
    private String date;
    private List<TeamAblumEntity> list;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<TeamAblumEntity> getList() {
        return list;
    }

    public void setList(List<TeamAblumEntity> list) {
        this.list = list;
    }
}
