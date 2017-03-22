package com.umessage.letsgo.domain.vo.message.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.message.MessageTextEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by gaofei on 2017/1/12.
 */
public class MessageListResponse extends CommonResponse {

    @ApiModelProperty(value = "是否有回复列表  0 没有 1 有")
    private Integer hasList;

    @ApiModelProperty(value = "旅行社ID")
    private String travelId;

    @ApiModelProperty(value = "问题反馈主题Id")
    private String mId;

    @ApiModelProperty(value = "旅行社头像")
    private String travelHeadUrl;

    @ApiModelProperty(value = "用户头像")
    private String headUrl;

    //反馈问题列表
    @ApiModelProperty(value = "反馈问题列表")
    private  List<MessageTextEntity> messageTextEntityList;

    public Integer getHasList() {
        return hasList;
    }

    public void setHasList(Integer hasList) {
        this.hasList = hasList;
    }

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public List<MessageTextEntity> getMessageTextEntityList() {
        return messageTextEntityList;
    }

    public void setMessageTextEntityList(List<MessageTextEntity> messageTextEntityList) {
        this.messageTextEntityList = messageTextEntityList;
    }

    public String getTravelHeadUrl() {
        return travelHeadUrl;
    }

    public void setTravelHeadUrl(String travelHeadUrl) {
        this.travelHeadUrl = travelHeadUrl;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public static MessageListResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends MessageListResponse {

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
