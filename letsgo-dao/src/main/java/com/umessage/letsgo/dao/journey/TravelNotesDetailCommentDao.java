/*
 * TravelNotesDetailCommentDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-10-09 Created by Administrator
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.TravelNotesDetailCommentEntity;
import java.util.List;

public interface TravelNotesDetailCommentDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param travelNotesDetailCommentEntity
     * @return int
     */
    int insert(TravelNotesDetailCommentEntity travelNotesDetailCommentEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.TravelNotesDetailCommentEntity
     */
    TravelNotesDetailCommentEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.TravelNotesDetailCommentEntity>
     */
    List<TravelNotesDetailCommentEntity> selectAll();

    /**
     * Description: 
     * @param travelNotesDetailCommentEntity
     * @return int
     */
    int update(TravelNotesDetailCommentEntity travelNotesDetailCommentEntity);
}