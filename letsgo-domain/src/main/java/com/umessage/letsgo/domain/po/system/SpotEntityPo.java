/*
 * SpotEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-15 Created by wendy
 */
package com.umessage.letsgo.domain.po.system;

import java.util.Date;

public class SpotEntityPo extends  SpotEntity{
    /**
     * 开始日期
     */
    private Date js_start_date;
    /**
     * 结束日期
     */
    private Date js_end_date;


    public Date getJs_start_date() {
        return js_start_date;
    }

    public void setJs_start_date(Date js_start_date) {
        this.js_start_date = js_start_date;
    }

    public Date getJs_end_date() {
        return js_end_date;
    }

    public void setJs_end_date(Date js_end_date) {
        this.js_end_date = js_end_date;
    }

    @Override
    public String toString() {
        return "SpotEntityPo{" +
                "js_start_date=" + js_start_date +
                ", js_end_date=" + js_end_date +
                '}';
    }
}