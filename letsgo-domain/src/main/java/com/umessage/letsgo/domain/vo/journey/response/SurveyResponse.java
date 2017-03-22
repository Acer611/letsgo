package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.SurveyVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by wendy on 2016/9/12.
 */
public class SurveyResponse extends CommonResponse {
    @ApiModelProperty(value = "已填写列表")
    private List<SurveyVo> isFilled;
    @ApiModelProperty(value = "未填写列表")
    private List<SurveyVo> notFilled;

    public List<SurveyVo> getIsFilled() {
        return isFilled;
    }

    public void setIsFilled(List<SurveyVo> isFilled) {
        this.isFilled = isFilled;
    }

    public List<SurveyVo> getNotFilled() {
        return notFilled;
    }

    public void setNotFilled(List<SurveyVo> notFilled) {
        this.notFilled = notFilled;
    }

    public static SurveyResponse createUserNotLoginResponse(){
        class UserNotLoginQuestionResponse extends SurveyResponse {

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
}