package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wendy on 2016/6/18.
 */
public class TeamInfoResponse extends CommonResponse {
    @ApiModelProperty(value = "团队的管理员信息实体")
    private AdministratorInfo administratorInfo;

    public AdministratorInfo getAdministratorInfo() {
        return administratorInfo;
    }

    public void setAdministratorInfo(AdministratorInfo administratorInfo) {
        this.administratorInfo = administratorInfo;
    }

    public static TeamInfoResponse createNotFoundResponse(){
        class NotFoundResponse extends TeamInfoResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }

            @Override
            public String getRetMsg() {
                return "未找到领队及导游";
            }
        }

        return new NotFoundResponse();
    }

    public static TeamInfoResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends TeamInfoResponse {

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
