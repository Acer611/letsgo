package com.umessage.letsgo.domain.vo.team.respone;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.OnlookersDetailsEntity;
import com.umessage.letsgo.domain.po.team.OnlookersEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zengguoqing on 2016/9/2.
 */
public class OnlookersResponse extends CommonResponse implements Serializable{

     //回复列表和回复信息
    @ApiModelProperty(value = "围观列表和回复信息")
    private Page<WatchMessageResponse> watchMessageResponse;
    //未读消息
    @ApiModelProperty(value = "未读消息数")
    private Integer unRead;

    @ApiModelProperty(value = "腾讯群组ID")
    private String txGroupId;

    @ApiModelProperty(value = "总条数")
    private long totals;
    @ApiModelProperty(value = "总页数")
    private int pages;

    @ApiModelProperty(value = "发布围观用户腾讯ID")
    private String txUserId;

    public String getTxUserId() {
        return txUserId;
    }

    public void setTxUserId(String txUserId) {
        this.txUserId = txUserId;
    }

    public Page<WatchMessageResponse> getWatchMessageResponse() {
        return watchMessageResponse;
    }

    public void setWatchMessageResponse(Page<WatchMessageResponse> watchMessageResponse) {
        this.watchMessageResponse = watchMessageResponse;
    }

    public long getTotals() {
        return totals;
    }

    public void setTotals(long totals) {
        this.totals = totals;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getTxGroupId() {
        return txGroupId;
    }

    public void setTxGroupId(String txGroupId) {
        this.txGroupId = txGroupId;
    }

    public Integer getUnRead() {
        return unRead;
    }

    public void setUnRead(Integer unRead) {
        this.unRead = unRead;
    }

    public static OnlookersResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends OnlookersResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.USER_NOT_LOGIN;
            }

            @Override
            public String getRetMsg() {
                return "用户未登录或登录信息过期";
            }
        }

        return new UserNotLoginResponse();
    }
}
