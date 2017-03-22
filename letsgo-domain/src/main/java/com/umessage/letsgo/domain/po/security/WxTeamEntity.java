package com.umessage.letsgo.domain.po.security;

import java.io.Serializable;

/**
 * Created by admin on 2016/11/23.
 */
public class WxTeamEntity implements Serializable {


    private  Long id;

    private Long teamId ;

    private  String unionid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        return "WxTeamEntity{" +
                "id=" + id +
                ", teamId=" + teamId +
                ", unionid='" + unionid + '\'' +
                '}';
    }
}
