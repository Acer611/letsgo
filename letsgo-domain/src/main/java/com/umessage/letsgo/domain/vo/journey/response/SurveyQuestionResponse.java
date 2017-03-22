package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.QuestionEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by pw on 2016/9/12.
 *
 */
public class SurveyQuestionResponse extends CommonResponse {

    @ApiModelProperty(value="问卷标题")
    private String title;
    @ApiModelProperty(value="问题表实体")
    private List<QuestionEntity> questionList;
    @ApiModelProperty(value = "签名")
    private String signUrl;
    @ApiModelProperty(value = "状态：0未填写，1已填写领队未确认可编辑操作，2领队已确认查看操作")
    private Integer state;

    @ApiModelProperty(value = "问卷确认状态：【1：已确认；0：未确认】")
    private Integer confirmStatus;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionEntity> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionEntity> questionList) {
        this.questionList = questionList;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public static SurveyQuestionResponse createNotFoundResponse(String retMsg){
        class NotFoundQuestionResponse extends SurveyQuestionResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        SurveyQuestionResponse response = new NotFoundQuestionResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static SurveyQuestionResponse createUserNotLoginResponse(){
        class UserNotLoginQuestionResponse extends SurveyQuestionResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.USER_NOT_LOGIN;
            }

            @Override
            public String getRetMsg() {
                return "用户未登录或登录信息过期";
            }
        }

        return new UserNotLoginQuestionResponse();
    }

    public static SurveyQuestionResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterQuestionResponse extends SurveyQuestionResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        SurveyQuestionResponse response = new EmptyParameterQuestionResponse();
        response.setRetMsg(retMsg);
        return response;
    }

}
