package com.umessage.letsgo.domain.vo.activity.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by ZhaoYidong on 2016/6/20.
 */
public class UploadLocationResponse extends CommonResponse{

    @ApiModelProperty("是否上传位置")
    private boolean enableUpload;
    @ApiModelProperty("上传位置间隔时间，单位：分钟")
    private long timeInterval;


    public static UploadLocationResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends UploadLocationResponse {

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

    public boolean isEnableUpload() {
        return enableUpload;
    }

    public void setEnableUpload(boolean enableUpload) {
        this.enableUpload = enableUpload;
    }

    public long getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(long timeInterval) {
        this.timeInterval = timeInterval;
    }
}
