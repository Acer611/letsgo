package com.umessage.letsgo.domain.vo.system.request;

import java.io.Serializable;

/**
 * Created by wendy on 2016/6/23.
 */
public class YahooWeatherResquest implements Serializable {
    private String name;    // 城市名
    private String date;    // 日期

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
