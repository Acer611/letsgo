package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;

public interface ITravelAgencyService {
	// 新增旅行社
	int addTravelAgency(TravelAgencyEntity travelAgencyEntity);
	
	// 修改旅行社
	TravelAgencyEntity updateTravelAgency(TravelAgencyEntity travelAgencyEntity);
	
	// 获取单个旅行社
	TravelAgencyEntity getTravelAgency(Long id);

	//根据用户id获得旅行社
	TravelAgencyEntity getByUserId(Long id);

	//获得当前登录的旅行社
	TravelAgencyEntity getCurrentTravel();

	//保存用户ID
	int setUserId(TravelAgencyEntity travelAgencyEntity);

	//根据trvelid获得旅行社
	TravelAgencyEntity getByTravelerId(Long id);


}
