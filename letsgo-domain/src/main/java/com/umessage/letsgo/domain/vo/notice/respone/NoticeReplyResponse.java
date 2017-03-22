package com.umessage.letsgo.domain.vo.notice.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.notice.NoticeReplyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Administrator on 2016/5/27.
 */
public class NoticeReplyResponse extends CommonResponse {
    @ApiModelProperty(value = "回复列表")
    private List<NoticeReplyEntity> noticeReplyList;

    @ApiModelProperty(value = "权限标识【值为1，代表有权限；值为0，代表没有权限】")
    private int isOperable;

    public List<NoticeReplyEntity> getNoticeReplyList() {
        return noticeReplyList;
    }

    public void setNoticeReplyList(List<NoticeReplyEntity> noticeReplyList) {
        this.noticeReplyList = noticeReplyList;
    }

    public int getIsOperable() {
        return isOperable;
    }

    public void setIsOperable(int isOperable) {
        this.isOperable = isOperable;
    }

    @Override
    public String toString() {
        return "NoticeReplyResponse{" +
                "noticeReplyList=" + noticeReplyList +
                ", isOperable=" + isOperable +
                '}';
    }

    public static NoticeReplyResponse createNotFoundResponse(){
        class NotFoundResponse extends NoticeReplyResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }

            @Override
            public String getRetMsg() {
                return "暂未回复";
            }
        }

        return new NotFoundResponse();
    }

    public static NoticeReplyResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends NoticeReplyResponse {

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
