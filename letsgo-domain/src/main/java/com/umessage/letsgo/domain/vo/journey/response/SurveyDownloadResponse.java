package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.SurveyVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by pw on 2016/10/12.
 */
public class SurveyDownloadResponse extends CommonResponse {
    @ApiModelProperty(value = "下载链接")
    private String downloadUrl;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public static SurveyDownloadResponse createUserNotLoginResponse(){
        class UserNotLoginQuestionResponse extends SurveyDownloadResponse {

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