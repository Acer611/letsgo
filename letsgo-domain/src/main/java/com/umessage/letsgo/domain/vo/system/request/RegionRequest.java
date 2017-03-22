package com.umessage.letsgo.domain.vo.system.request;

import java.io.Serializable;

/**
 * Created by admin on 2016/10/31.
 */
public class RegionRequest implements Serializable {

    private String areaid;
    private String alias;
    private String areaengname;
    private String areapinyinname;
    //10：亚洲，11：欧洲，12：北美洲，13	南美洲，14：非洲，15：大洋洲，16：	南极洲
    private String delta;
//    private String countryid;
//    private String cityid;
    private Integer lv;
    private String parentid;

    private Double lat;
    private Double lng;

    //时区Id
    private String timezone;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAreaengname() {
        return areaengname;
    }

    public void setAreaengname(String areaengname) {
        this.areaengname = areaengname;
    }

    public String getAreapinyinname() {
        return areapinyinname;
    }

    public void setAreapinyinname(String areapinyinname) {
        this.areapinyinname = areapinyinname;
    }

    public String getDelta() {
        return delta;
    }

    public void setDelta(String delta) {
        this.delta = delta;
    }



    public Integer getLv() {
        return lv;
    }

    public void setLv(Integer lv) {
        this.lv = lv;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "RegionRequest{" +
                "areaid='" + areaid + '\'' +
                ", alias='" + alias + '\'' +
                ", areaengname='" + areaengname + '\'' +
                ", areapinyinname='" + areapinyinname + '\'' +
                ", delta='" + delta + '\'' +
                ", lv=" + lv +
                ", parentid='" + parentid + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", timezone='" + timezone + '\'' +
                '}';
    }
}
