package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailCommentEntity;
import com.umessage.letsgo.domain.po.journey.ScoreEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/9.
 */
public class DetailCommentResponse extends CommonResponse{

    @ApiModelProperty(value="用户id")
    ScheduleDetailCommentEntity detailCommentEntity;

    public ScheduleDetailCommentEntity getDetailCommentEntity() {
        return detailCommentEntity;
    }

    public void setDetailCommentEntity(ScheduleDetailCommentEntity detailCommentEntity) {
        this.detailCommentEntity = detailCommentEntity;
    }

    public static DetailCommentResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends DetailCommentResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        DetailCommentResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }
}
