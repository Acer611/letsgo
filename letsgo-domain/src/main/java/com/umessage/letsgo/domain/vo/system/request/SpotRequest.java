package com.umessage.letsgo.domain.vo.system.request;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

import java.util.List;

/**
 * Created by wendy on 2016/8/16.
 */
public class SpotRequest extends CommonRequest {
    /**
     * 查询字符串
     */
    private String query;
    /**
     * 目的地
     */
    private List<String> cities;
    /**
     * 团队ID
     */
    private Long teamId;

    /*
      景点拼音
     */
    private String spotpinyinname;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getSpotpinyinname() {
        return spotpinyinname;
    }

    public void setSpotpinyinname(String spotpinyinname) {
        this.spotpinyinname = spotpinyinname;
    }

    @Override
    public String toString() {
        return "SpotRequest{" +
                "query='" + query + '\'' +
                ", cities=" + cities +
                ", spotpinyinname='" + spotpinyinname + '\'' +
                '}';
    }
}
