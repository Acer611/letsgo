/*
 * ScoreDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-25 Created by Administrator
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.ScoreRecordEntity;

import java.util.List;

public interface ScoreRecordDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param scoreRecordEntity
     * @return int
     */
    int insert(ScoreRecordEntity scoreRecordEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.ScoreRecordEntity
     */
    ScoreRecordEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.ScoreRecordEntity>
     */
    List<ScoreRecordEntity> selectAll();

    /**
     * Description: 
     * @param scoreRecordEntity
     * @return int
     */
    int update(ScoreRecordEntity scoreRecordEntity);
}