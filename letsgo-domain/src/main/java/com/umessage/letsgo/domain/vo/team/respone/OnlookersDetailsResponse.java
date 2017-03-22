package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/5.
 */
public class OnlookersDetailsResponse extends CommonResponse implements Serializable{
    public List<OnlookersDetailsInfo> getList() {
        return list;
    }

    public void setList(List<OnlookersDetailsInfo> list) {
        this.list = list;
    }

    private List<OnlookersDetailsInfo> list;

    public static OnlookersDetailsResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends OnlookersDetailsResponse {

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
