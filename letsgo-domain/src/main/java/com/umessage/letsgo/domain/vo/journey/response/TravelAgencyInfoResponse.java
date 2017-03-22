package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.notice.NoticeReplyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.TravelAgencyInfoVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by lizhen on 2016/9/27.
 */
public class TravelAgencyInfoResponse extends CommonResponse {
    @ApiModelProperty(value = "接团社信息和地接社信息")
    private TravelAgencyInfoVo travelAgencyInfoVo;

    public TravelAgencyInfoVo getTravelAgencyInfoVo() {
        return travelAgencyInfoVo;
    }

    public void setTravelAgencyInfoVo(TravelAgencyInfoVo travelAgencyInfoVo) {
        this.travelAgencyInfoVo = travelAgencyInfoVo;
    }

    public static TravelAgencyInfoResponse createNotTravelAgencyInfoResponse(){
        class NotTravelAgencyInfoResponse extends TravelAgencyInfoResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }

            @Override
            public String getRetMsg() {
                return "没有该腾迅云id对应的接团社信息和地接社信息";
            }
        }

        return new NotTravelAgencyInfoResponse();
    }
}
