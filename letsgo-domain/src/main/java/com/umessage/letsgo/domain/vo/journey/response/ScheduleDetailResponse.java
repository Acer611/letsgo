package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

/**
 * Created by zhajl on 16/9/21.
 */
public class ScheduleDetailResponse extends CommonResponse {

    private ScheduleDetailEntity scheduleDetailEntity;

    public ScheduleDetailEntity getScheduleDetailEntity() {
        return scheduleDetailEntity;
    }

    public void setScheduleDetailEntity(ScheduleDetailEntity scheduleDetailEntity) {
        this.scheduleDetailEntity = scheduleDetailEntity;
    }

    public static ScheduleDetailResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends ScheduleDetailResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        ScheduleDetailResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }
}
