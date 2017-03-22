package com.umessage.letsgo.domain.vo.team.respone.qq;

import java.io.Serializable;

/**
 * Created by zengguoqing on 2016/8/25.
 */
public class QQTeamAlbumCallBackResponse implements Serializable {
    private static final long serialVersionUID = 2029838219398373649L;
    private String ActionStatus;

    public String getActionStatus() {
        return ActionStatus;
    }

    public void setActionStatus(String actionStatus) {
        ActionStatus = actionStatus;
    }

    public String getErrorInfo() {
        return ErrorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        ErrorInfo = errorInfo;
    }

    public Integer getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(Integer errorCode) {
        ErrorCode = errorCode;
    }

    private String ErrorInfo;
    private Integer ErrorCode;  //0 忽略回调结果
}
