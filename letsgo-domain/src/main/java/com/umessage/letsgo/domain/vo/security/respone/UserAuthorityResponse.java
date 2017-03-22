package com.umessage.letsgo.domain.vo.security.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wendy on 2016/7/14.
 */
public class UserAuthorityResponse extends CommonResponse {
    @ApiModelProperty(value = "用户在团队中的身份【1：领队，2：导游；3：游客】")
    private Integer memberRole;

    @ApiModelProperty(value = "用户在团队中的管理权限【1：有管理权限；0：管理权限】")
    private Integer isAdmin;

    @ApiModelProperty(value = "团队的出行状态【1：出行中，2：即将出行；3：已出行】")
    private Integer teamStatus;

    public Integer getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(Integer memberRole) {
        this.memberRole = memberRole;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(Integer teamStatus) {
        this.teamStatus = teamStatus;
    }

    public static UserAuthorityResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends UserAuthorityResponse {

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

    public static UserAuthorityResponse createNotFoundResponse(){
        class NotFoundResponse extends UserAuthorityResponse {

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
