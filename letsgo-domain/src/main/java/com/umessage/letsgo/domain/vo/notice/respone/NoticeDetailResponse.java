package com.umessage.letsgo.domain.vo.notice.respone;

import com.umessage.letsgo.domain.po.notice.NoticeDetailEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Administrator on 2016/5/27.
 */
public class NoticeDetailResponse extends CommonResponse {
    /**
     * 已确认人数
     */
    @ApiModelProperty(value="已确认人数")
    private Integer sureCount;
    /**
     * 未确认人数
     */
    @ApiModelProperty(value="未确认人数")
    private Integer notsureCount;
    /**
     * 已确认列表
     */
    @ApiModelProperty(value="已确认列表")
    private List<NoticeDetailEntity> sureList;
    /**
     * 未确认列表
     */
    @ApiModelProperty(value="未确认列表")
    private List<NoticeDetailEntity> notsureList;

    public Integer getSureCount() {
        return sureCount;
    }

    public void setSureCount(Integer sureCount) {
        this.sureCount = sureCount;
    }

    public Integer getNotsureCount() {
        return notsureCount;
    }

    public void setNotsureCount(Integer notsureCount) {
        this.notsureCount = notsureCount;
    }

    public List<NoticeDetailEntity> getSureList() {
        return sureList;
    }

    public void setSureList(List<NoticeDetailEntity> sureList) {
        this.sureList = sureList;
    }

    public List<NoticeDetailEntity> getNotsureList() {
        return notsureList;
    }

    public void setNotsureList(List<NoticeDetailEntity> notsureList) {
        this.notsureList = notsureList;
    }

    @Override
    public String toString() {
        return "NoticeDetailResponse{" +
                "sureCount=" + sureCount +
                ", notsureCount=" + notsureCount +
                ", sureList=" + sureList +
                ", notsureList=" + notsureList +
                '}';
    }
}
