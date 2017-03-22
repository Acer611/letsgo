package com.umessage.letsgo.domain.vo.system.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by wendy on 2016/6/29.
 */
public class UnreadAndUnconfirmedResponse extends CommonResponse {
    @ApiModelProperty(value = "消息分类【1：集合；2：通知：3：公告】")
    private List<Integer> type;
    @ApiModelProperty(value = "消息实体")
    private List<Information> info;
    @ApiModelProperty(value = "总数量")
    private int total;

    public List<Integer> getType() {
        return type;
    }

    public void setType(List<Integer> type) {
        this.type = type;
    }

    public List<Information> getInfo() {
        return info;
    }

    public void setInfo(List<Information> info) {
        this.info = info;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static UnreadAndUnconfirmedResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends UnreadAndUnconfirmedResponse {

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
