package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

/**
 * Created by ZhaoYidong on 2016/7/5.
 */
public class TravelAgencyResponse extends CommonResponse{

    private TravelAgencyEntity travelEntity;


    public TravelAgencyResponse() {
    }

    public TravelAgencyResponse(TravelAgencyEntity travelEntity) {
        this.travelEntity = travelEntity;
    }

    public TravelAgencyEntity getTravelEntity() {
        return travelEntity;
    }

    public void setTravelEntity(TravelAgencyEntity travelEntity) {
        this.travelEntity = travelEntity;
    }
}
