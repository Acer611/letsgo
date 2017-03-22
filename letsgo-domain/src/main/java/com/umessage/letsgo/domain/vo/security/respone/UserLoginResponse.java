package com.umessage.letsgo.domain.vo.security.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UserLoginResponse extends CommonResponse {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 绑定手机
     */
    @ApiModelProperty(value = "绑定手机")
    private String phoneNumber;

    /**
     * 微信Token
     */
    @ApiModelProperty(value="微信Token")
    private String weixinToken;

    /**
     * 用户角色
     */
    @ApiModelProperty(value = "用户角色")
    private List<String> userRole;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWeixinToken() {
		return weixinToken;
	}

    public void setWeixinToken(String weixinToken) {
        this.weixinToken = weixinToken;
    }

    public List<String> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<String> userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "UserLoginResponse{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", weixinToken='" + weixinToken + '\'' +
                ", userRole=" + userRole +
                '}';
    }

    public static UserLoginResponse createBadCredentialsResponse() {
        class BadCredentialsResponse extends UserLoginResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.BAD_CREDENTIALS;
            }

            @Override
            public String getRetMsg() {
                return "用户名或密码不正确";
            }
        }

        return new BadCredentialsResponse();
    }

    public static UserLoginResponse createBadVerificationCodeResponse() {
        class BadVerificationCodeResponse extends UserLoginResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.BAD_SMS_VERIFICATION_CODE;
            }

            @Override
            public String getRetMsg() {
                return "手机验证码不正确或已过期";
            }
        }

        return new BadVerificationCodeResponse();
    }

    public static UserLoginResponse createNotJOINResponse() {
        class NotJOINResponse extends UserLoginResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_JOIN;
            }

            @Override
            public String getRetMsg() {
                return "当前用户还没有注册，请注册后登陆。";
            }
        }

        UserLoginResponse response = new NotJOINResponse();
        return response;
    }

    public static UserLoginResponse createNotFoundResponse() {
        class NotFoundResponse extends UserLoginResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }

            @Override
            public String getRetMsg() {
                return "找不到对应的用户";
            }
        }

        UserLoginResponse response = new NotFoundResponse();
        return response;
    }

    public static UserLoginResponse createNotLoginResponse() {
        class NotLoginResponse extends UserLoginResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.USER_NOT_LOGIN;
            }

            @Override
            public String getRetMsg() {
                return "用户未登录或登录信息过期，请重新登录";
            }
        }

        UserLoginResponse response = new NotLoginResponse();
        return response;
    }
}
