package com.umessage.letsgo.domain.vo.message.request;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by gaofei on 2017/1/16.
 */
public class MessageTextRequest extends CommonRequest {
    @ApiModelProperty(value = "回复主题的id", required = true)
    private String mid;

    @ApiModelProperty(value = "问题反馈信息", required = true)
    private String message;

    @ApiModelProperty(value = "旅行社的ID", required = true)
    private String travelId;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }
}
