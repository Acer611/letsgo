package com.umessage.letsgo.domain.vo.journey.response.vo;

/**
 * Created by wendy on 2016/8/31.
 */
public class DataVo {
    // 数据名称
    private String dataName;
    // 数据占有数量
    private int dataCount;
    // 数据占有率
    private double dataRate;

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public double getDataRate() {
        return dataRate;
    }

    public void setDataRate(double dataRate) {
        this.dataRate = dataRate;
    }
}
