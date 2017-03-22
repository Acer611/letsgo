package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.team.HotelEntity;
import com.umessage.letsgo.domain.vo.system.request.HotelRequest;

import java.util.List;

public interface IHotelService {
	// 新增小组
	List<HotelEntity> getHotel(HotelRequest hotel);

	HotelEntity selectById(String hotelId);

	List<HotelEntity> getHotelByName(String hotelName);

	List<HotelEntity> getHotelByNameAndCityName(String hotelName, String cities);
}
