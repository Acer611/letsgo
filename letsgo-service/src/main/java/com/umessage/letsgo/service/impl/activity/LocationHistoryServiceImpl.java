package com.umessage.letsgo.service.impl.activity;

import com.umessage.letsgo.dao.activity.LocationHistoryDao;
import com.umessage.letsgo.domain.po.activity.LocationHistoryEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.activity.request.LocationHistoryRequest;
import com.umessage.letsgo.domain.vo.activity.response.LocationTrackResponse;
import com.umessage.letsgo.service.api.activity.ILocationHistoryService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.team.ITeamService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ZhaoYidong on 2016/6/16.
 */
@Service
public class LocationHistoryServiceImpl implements ILocationHistoryService {

    @Resource
    private LocationHistoryDao locationHistoryDao;

    @Resource
    private IUserService userService;

    @Resource
    private ITeamService teamService;

    @Override
    public int addLocationHistory(LocationHistoryEntity entity) {
        return locationHistoryDao.insert(entity);
    }

    @Override
    public List<LocationHistoryEntity> getLocationHistoryEntity(List<Long> userId, String date) {
        LocationHistoryRequest request = new LocationHistoryRequest();
        request.setUserIds(userId);
        request.setDate(date);

        List<LocationHistoryEntity> locationHistoryEntityList = locationHistoryDao.selectLocationWithUserIdAndDate(request);

        if (CollectionUtils.isEmpty(locationHistoryEntityList)) {
            return new ArrayList<>();
        }
        return locationHistoryEntityList;
    }

    /**
     * 根据用户ID和创建时间和坐标距离查询用户位置数量
     * @param userId
     * @param startDate
     * @param endDate
     * @param slongitude
     * @param slatitude
     * @return
     */
    @Override
    public int selectCountWithUserIdAndDate(List<Long> userId, Date startDate, Date endDate, Double slongitude, Double slatitude) {
        LocationHistoryRequest request = new LocationHistoryRequest();
        request.setUserIds(userId);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setSlongitude(slongitude);
        request.setSlatitude(slatitude);
        return locationHistoryDao.selectCountWithUserIdAndDate(request);
    }

    @Override
    public LocationTrackResponse getMemberTrack(Long tId, Long userId) {
        UserEntity userEntity = userService.getUserById(userId);
        if (userEntity == null || userEntity.getId() == null){
            return LocationTrackResponse.createUserNotFoundResponse();
        }

        TeamEntity teamEntity = teamService.selectById(tId);
        if (teamEntity == null || teamEntity.getId() == null){
            return LocationTrackResponse.createTeamNotFoundResponse();
        }

        LocationTrackResponse response = new LocationTrackResponse();

        List<LocationHistoryEntity> locationHistoryEntityList = locationHistoryDao.getMemberTrack(tId, userId);

        if (CollectionUtils.isEmpty(locationHistoryEntityList)) {
            return response;
        }

        response.settId(tId);
        response.setUserId(userId);
        response.setUserEntity(userEntity);
        response.setLocationHistoryEntityList(filterTrack(locationHistoryEntityList));

        return response;
    }

    /**
     * 过滤历史位置,每小时只保留一个位置
     * @param locationHistoryEntityList
     * @return
     */
    private List<LocationHistoryEntity> filterTrack(List<LocationHistoryEntity> locationHistoryEntityList){
        List<LocationHistoryEntity> locationHistoryList = new ArrayList<>();

        // 使用Map过滤
        Map<String, Date> locationMap = new HashMap<>();
        for (LocationHistoryEntity location : locationHistoryEntityList
             ) {
            Date createTime = location.getCreateTime();
            // 每小时作为一个key
            String hourKey = dateToHourString(createTime);

            if (!locationMap.containsKey(hourKey)){
                locationMap.put(hourKey, createTime);

                locationHistoryList.add(location);
            }
        }

        return locationHistoryList;
    }

    private String dateToHourString(Date createTime) {
        if (createTime == null) createTime = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        String hour = sdf.format(createTime);
        return hour;
    }


}
