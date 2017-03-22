package com.umessage.letsgo.domain.vo.notice.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.notice.LastMessageVo;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by pw on 2016/8/24.
 */
public class LastMessageResponse extends CommonResponse {
    @ApiModelProperty(value = "通知未读数")
    private Integer noticeUnread;

    @ApiModelProperty(value = "集合未读数")
    private Integer gatherUnread;

    @ApiModelProperty(value = "公告未读数")
    private Integer announcementUnread;

    @ApiModelProperty(value = "最新消息响应体列表")
    private List<LastMessageVo> lastMessageVo;

    @ApiModelProperty(value = "是否有调查问卷（游客端）1有；0无")
    private Integer hasQuestion;

    @ApiModelProperty(value = "调查问卷对象")
    private QuestionVo questionVo;

    public Integer getHasQuestion() {
        return hasQuestion;
    }

    public void setHasQuestion(Integer hasQuestion) {
        this.hasQuestion = hasQuestion;
    }

    public QuestionVo getQuestionVo() {
        return questionVo;
    }

    public void setQuestionVo(QuestionVo questionVo) {
        this.questionVo = questionVo;
    }

    public Integer getNoticeUnread() {
        return noticeUnread;
    }

    public void setNoticeUnread(Integer noticeUnread) {
        this.noticeUnread = noticeUnread;
    }

    public Integer getGatherUnread() {
        return gatherUnread;
    }

    public void setGatherUnread(Integer gatherUnread) {
        this.gatherUnread = gatherUnread;
    }

    public Integer getAnnouncementUnread() {
        return announcementUnread;
    }

    public void setAnnouncementUnread(Integer announcementUnread) {
        this.announcementUnread = announcementUnread;
    }

    public List<LastMessageVo> getLastMessageVo() {
        return lastMessageVo;
    }

    public void setLastMessageVo(List<LastMessageVo> lastMessageVo) {
        this.lastMessageVo = lastMessageVo;
    }

    public static LastMessageResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends LastMessageResponse {

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

    public static LastMessageResponse createNotFoundResponse(){
        class NotFoundResponse extends LastMessageResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }

            @Override
            public String getRetMsg() {
                return "未找到对应的团队成员";
            }
        }

        return new NotFoundResponse();
    }
}
