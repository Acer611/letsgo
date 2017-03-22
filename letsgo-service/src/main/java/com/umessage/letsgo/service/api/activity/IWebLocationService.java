package com.umessage.letsgo.service.api.activity;

import com.umessage.letsgo.domain.po.activity.LocationEntity;
import com.umessage.letsgo.domain.vo.activity.response.LocationListResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.SignVo;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/12.
 */
public interface IWebLocationService {

    /**
     * 获取成员的位置信息
     */
    List<LocationEntity> getLocationList(String teamId, Long userId, boolean isPushed) throws Exception;

    /**
     * 获取刷新位置的请求签名信息
     */
    LocationListResponse getLocationSign(String teamId, LocationListResponse respone, SignVo signVo);

    //获取围观位置
    List<LocationEntity> getOnlookLocation(String teamId, Long userId, boolean isPushed) throws Exception;
}
