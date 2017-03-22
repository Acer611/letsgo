package com.umessage.letsgo.domain.vo.common.request;

import java.io.Serializable;

/**
 * Created by wendy on 2016/7/16.
 */
public class ParameMsg implements Serializable {
    /**
     * 标题
     */
    private String parameKey;
    /**
     * 内容
     */
    private String parameValue;

    public String getParameKey() {
        return parameKey;
    }

    public void setParameKey(String parameKey) {
        this.parameKey = parameKey;
    }

    public String getParameValue() {
        return parameValue;
    }

    public void setParameValue(String parameValue) {
        this.parameValue = parameValue;
    }

    @Override
    public String toString() {
        return "ParameMsg{" +
                "parameKey='" + parameKey + '\'' +
                ", parameValue='" + parameValue + '\'' +
                '}';
    }
}
