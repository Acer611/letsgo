/*
 * PromptInfoDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-06-02 Created by ZhaoYidong
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.PromptInfoEntity;
import java.util.List;

public interface PromptInfoDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param promptInfoEntity
     * @return int
     */
    int insert(PromptInfoEntity promptInfoEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.PromptInfoEntity
     */
    PromptInfoEntity select(Long id);

    /**
     * 通过行程id获得提示信息
     * @param scheduleId
     * @return
     */
    PromptInfoEntity selectByScheduleId(Long scheduleId);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.PromptInfoEntity>
     */
    List<PromptInfoEntity> selectAll();

    /**
     * Description: 
     * @param promptInfoEntity
     * @return int
     */
    int update(PromptInfoEntity promptInfoEntity);

}