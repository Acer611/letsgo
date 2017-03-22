package com.umessage.letsgo.service.api.activity;

import com.umessage.letsgo.domain.po.activity.LocationEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.activity.response.LocationRespone;

import java.util.List;

public interface ILocationService {

	LocationEntity select(Long userId);

	int addLocation(LocationEntity locationEntity);
	
	int updateLocation(LocationEntity locationEntity);
	
	// 获取单个用户的位置信息
	LocationEntity getLocationByUserId(Long userId);
	
	List<LocationEntity> getLocationList(String teamId, Long userId,boolean isPushed) throws Exception;
   //获取围观位置
	List<LocationEntity> getOnlookLocation(String teamId, Long userId, boolean isPushed) throws Exception;

	LocationRespone createLocation(Long userId, Double lng, Double lat);
	List<UserEntity> getUploadLocationUsers(List<UserEntity> userEntityList);
}
