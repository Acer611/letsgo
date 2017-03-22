/*
 * RemindDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.notice;

import com.umessage.letsgo.domain.po.notice.RemindEntity;
import java.util.List;

public interface RemindDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param remindEntity
     * @return int
     */
    int insert(RemindEntity remindEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.notice.RemindEntity
     */
    RemindEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.notice.RemindEntity>
     */
    List<RemindEntity> selectAll();

    /**
     * Description: 
     * @param remindEntity
     * @return int
     */
    int update(RemindEntity remindEntity);
}