package com.umessage.letsgo.service.api.activity;

import com.umessage.letsgo.domain.po.activity.LocationHistoryEntity;
import com.umessage.letsgo.domain.vo.activity.response.LocationTrackResponse;

import java.util.Date;
import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/16.
 */
public interface ILocationHistoryService {

    int addLocationHistory(LocationHistoryEntity entity);

    List<LocationHistoryEntity> getLocationHistoryEntity(List<Long> userId, String date);

    int selectCountWithUserIdAndDate(List<Long> userId, Date startDate, Date endDate, Double slongitude, Double slatitude);

    // 获取团队成员的位置轨迹
    LocationTrackResponse getMemberTrack(Long tId, Long userId);
}
