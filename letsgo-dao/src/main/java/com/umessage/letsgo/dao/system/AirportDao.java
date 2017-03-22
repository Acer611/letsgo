/*
 * AirportDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-15 Created by wendy
 */
package com.umessage.letsgo.dao.system;

import com.umessage.letsgo.domain.po.system.AirportEntity;
import java.util.List;

public interface AirportDao {
    /**
     * Description: 
     * @param airportEntity
     * @return int
     */
    int insert(AirportEntity airportEntity);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.system.AirportEntity>
     */
    List<AirportEntity> selectAll();

    List<AirportEntity> selectLike(String query);
}