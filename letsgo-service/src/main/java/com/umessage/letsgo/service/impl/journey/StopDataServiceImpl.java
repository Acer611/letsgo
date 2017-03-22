package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.domain.po.activity.LocationHistoryEntity;
import com.umessage.letsgo.domain.po.journey.ScenicEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.system.SpotEntity;
import com.umessage.letsgo.domain.vo.journey.response.StopDataResponse;
import com.umessage.letsgo.service.api.activity.ILocationHistoryService;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.journey.IStopDataService;
import com.umessage.letsgo.service.api.system.ISpotService;
import com.umessage.letsgo.service.api.team.IMemberService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wendy on 2016/8/25.
 */
@Service
public class StopDataServiceImpl implements IStopDataService {

    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IMemberService memberService;
    @Resource
    private ISpotService spotService;
    @Resource
    private ILocationHistoryService locationHistoryService;

    @Override
    public StopDataResponse getStopData(Long id) {
        ScheduleEntity scheduleEntity = scheduleService.getSchedule(id);

        // 获取行程景点
        List<ScenicEntity> scenicEntityList = new ArrayList<>();
        List<ScheduleDetailEntity> scheduleDetailEntityList = scheduleEntity.getScheduleDetailList();

        for (ScheduleDetailEntity detailEntity : scheduleDetailEntityList) {
            // 如果行程日期大于今天，则不作停留数据计算
            if (detailEntity.getScheduleDate().after(new Date())) {
                continue;   // 跳出本次循环
            }

            String scheduleDate = DateUtils.date2String("yyyy-MM-dd", detailEntity.getScheduleDate());
            scenicEntityList = detailEntity.getScenicList();

            // 获取团队中所有用户的对应每日行程的历史位置信息
            List<Long> userIds = memberService.getUserIdInTId(scheduleEntity.getTeamId());
            if (!CollectionUtils.isEmpty(userIds)) {
                List<LocationHistoryEntity> locationHistoryEntityList = locationHistoryService.getLocationHistoryEntity(userIds, scheduleDate);
                // 计算停留数据
                this.calculateStopData(scenicEntityList, locationHistoryEntityList);
            }


        }


        StopDataResponse response = new StopDataResponse();
        response.setScheduleEntity(scheduleEntity);
        return response;
    }

    /**
     * 计算景点的停留数据
     * 计算规则：当团队中第一个人进入景点，最后一个人离开景点，为景点的停留时间范围
     * @param scenicEntityList
     * @param locationHistoryEntityList
     */
    private void calculateStopData(List<ScenicEntity> scenicEntityList, List<LocationHistoryEntity> locationHistoryEntityList) {
        for (ScenicEntity scenicEntity : scenicEntityList) {
            Long spotId = scenicEntity.getSpotId();
            if (spotId != null) {
                SpotEntity spotEntity = spotService.getSpot(spotId);
                if (spotEntity != null) {

                    double lat = Double.valueOf(spotEntity.getLat());
                    double lng = Double.valueOf(spotEntity.getLng());

                    // 设置景点经纬度
                    scenicEntity.setLat(lat);
                    scenicEntity.setLng(lng);

                    List<LocationHistoryEntity> list = new ArrayList<>();
                    for (LocationHistoryEntity locationHistoryEntity : locationHistoryEntityList) {
                        double dist = getDistance(lat, lng, locationHistoryEntity.getLatitude(), locationHistoryEntity.getLongitude());
                        if (dist > 2) {
                            list.add(locationHistoryEntity);
                        }
                    }

                    if (!CollectionUtils.isEmpty(list)) {
                        scenicEntity.setStopStartTime(DateUtils.date2String("mm:ss", list.get(0).getCreateTime()));
                        scenicEntity.setStopEndTime(DateUtils.date2String("mm:ss", list.get(list.size() - 1).getCreateTime()));
                    }
                }
            }
        }
    }

    /**
     * 谷歌地图算法：根据经纬度计算两点之间的距离
     * @param lat1 纬度1
     * @param lng1 经度1
     * @param lat2 纬度2
     * @param lng2 经度2
     * @return 距离 Km
     */
    private double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double a = radLat1 - radLat2;
        double b = Math.toRadians(lng1) - Math.toRadians(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * 6378.137;//地球赤道半径：6378.137 Km
        s = Math.round(s * 100) / 100d;
        return s;
    }

}
