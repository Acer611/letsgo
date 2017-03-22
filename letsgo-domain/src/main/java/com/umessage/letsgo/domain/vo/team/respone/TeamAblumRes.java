package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.domain.po.team.TeamAblumEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/18.
 */
public class TeamAblumRes implements Serializable{
   private List<TeamAblumEntity> list;

    public List<TeamAblumEntity> getList() {
        return list;
    }

    public void setList(List<TeamAblumEntity> list) {
        this.list = list;
    }


    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    private Long total;
    private Integer totalPage;
}
