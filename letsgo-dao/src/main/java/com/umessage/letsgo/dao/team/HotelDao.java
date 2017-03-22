/*
 * GroupDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.team;

import com.umessage.letsgo.domain.po.team.HotelEntity;
import com.umessage.letsgo.domain.vo.system.request.HotelRequest;

import java.util.List;

public interface HotelDao {
    List<HotelEntity> getHotel(HotelRequest hotel);

    HotelEntity selectById(String hotelId);

    List<HotelEntity> getHotelByName(HotelRequest hotel);

    List<HotelEntity> getHotelByNameAndCityName(HotelRequest hotel);
}