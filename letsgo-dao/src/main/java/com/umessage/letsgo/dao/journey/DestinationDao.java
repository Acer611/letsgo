/*
 * DestinationDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-07-11 Created by wendy
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.DestinationEntity;
import java.util.List;

public interface DestinationDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param destinationEntity
     * @return int
     */
    int insert(DestinationEntity destinationEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.DestinationEntity
     */
    DestinationEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.DestinationEntity>
     */
    List<DestinationEntity> selectAll();

    /**
     * Description: 
     * @param destinationEntity
     * @return int
     */
    int update(DestinationEntity destinationEntity);

    DestinationEntity selectWithDest(String dest);

    List<DestinationEntity> selectWithMultiDest(List<String> destList);
}