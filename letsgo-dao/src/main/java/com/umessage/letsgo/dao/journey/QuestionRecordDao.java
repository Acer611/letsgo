/*
 * ScoreDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-25 Created by Administrator
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.QuestionRecordEntity;

import java.util.List;

public interface QuestionRecordDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param questionRecordEntity
     * @return int
     */
    int insert(QuestionRecordEntity questionRecordEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.QuestionRecordEntity
     */
    QuestionRecordEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.QuestionRecordEntity>
     */
    List<QuestionRecordEntity> selectAll();

    /**
     * Description: 
     * @param questionRecordEntity
     * @return int
     */
    int update(QuestionRecordEntity questionRecordEntity);
}