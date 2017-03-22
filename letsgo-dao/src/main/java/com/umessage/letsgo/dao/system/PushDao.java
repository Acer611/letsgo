/*
 * PushDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-07-19 Created by wendy
 */
package com.umessage.letsgo.dao.system;

import com.umessage.letsgo.domain.po.system.PushEntity;
import java.util.List;

public interface PushDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param pushEntity
     * @return int
     */
    int insert(PushEntity pushEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.system.PushEntity
     */
    PushEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.system.PushEntity>
     */
    List<PushEntity> selectAll();

    /**
     * Description: 
     * @param pushEntity
     * @return int
     */
    int update(PushEntity pushEntity);

    List<PushEntity> selectPushList();
}