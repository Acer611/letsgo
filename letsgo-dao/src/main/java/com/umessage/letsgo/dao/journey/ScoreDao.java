/*
 * ScoreDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-25 Created by Administrator
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.ScoreEntity;
import com.umessage.letsgo.domain.po.journey.ScoreRecordEntity;

import java.util.List;

public interface ScoreDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param scoreEntity
     * @return int
     */
    int insert(ScoreEntity scoreEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.ScoreEntity
     */
    ScoreEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.ScoreEntity>
     */
    List<ScoreEntity> selectAll();

    /**
     * Description: 
     * @param scoreEntity
     * @return int
     */
    int update(ScoreEntity scoreEntity);

    /**
     * 查询每日点评导出数据
     * @param scoreRecordEntity
     * @return
     */
    List<ScoreRecordEntity> selectAllByScheduleDate(ScoreRecordEntity scoreRecordEntity);
}