package com.umessage.letsgo.domain.vo.journey.response;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by ZhaoYidong on 2016/6/29.
 */
public class ScheduleAddResponse extends CommonResponse {

    /**
     *行程ID
     */
    @ApiModelProperty(value="行程ID")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static ScheduleAddResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends ScheduleAddResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        ScheduleAddResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }
    public static ScheduleAddResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends ScheduleAddResponse {

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
