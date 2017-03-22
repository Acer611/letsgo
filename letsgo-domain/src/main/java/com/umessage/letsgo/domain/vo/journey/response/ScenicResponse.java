package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.system.ScenicEntity;
import com.umessage.letsgo.domain.po.team.ScenicAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 *
 * Created by gaofei on 2017/2/16.
 */
public class ScenicResponse  extends CommonResponse {

    @ApiModelProperty(value="景点列表")
    private List<ScenicEntity> scenicInfoList;

    @ApiModelProperty(value="景点私有库实体信息")
    private ScenicAgencyEntity scenicAgencyEntity;

    public static ScenicInfoResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends ScenicInfoResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        ScenicInfoResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public List<ScenicEntity> getScenicInfoList() {
        return scenicInfoList;
    }

    public void setScenicInfoList(List<ScenicEntity> scenicInfoList) {
        this.scenicInfoList = scenicInfoList;
    }

    public ScenicAgencyEntity getScenicAgencyEntity() {
        return scenicAgencyEntity;
    }

    public void setScenicAgencyEntity(ScenicAgencyEntity scenicAgencyEntity) {
        this.scenicAgencyEntity = scenicAgencyEntity;
    }
}
