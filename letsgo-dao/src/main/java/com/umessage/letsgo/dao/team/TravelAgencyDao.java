/*
 * TravelAgencyDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by ZhaoYidong
 */
package com.umessage.letsgo.dao.team;

import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import java.util.List;

public interface TravelAgencyDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param travelAgencyEntity
     * @return int
     */
    int insert(TravelAgencyEntity travelAgencyEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.team.TravelAgencyEntity
     */
    TravelAgencyEntity select(Long id);

    //根据用户id获得旅行社信息
    TravelAgencyEntity getByUserId(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.team.TravelAgencyEntity>
     */
    List<TravelAgencyEntity> selectAll();

    /**
     * Description: 
     * @param travelAgencyEntity
     * @return int
     */
    int update(TravelAgencyEntity travelAgencyEntity);

    int setUserId(TravelAgencyEntity travelAgencyEntity);


    TravelAgencyEntity getByTravelerId(Long id);
}