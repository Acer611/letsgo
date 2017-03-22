/*
 * NationDictionaryDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-06-06 Created by wendy
 */
package com.umessage.letsgo.dao.system;

import com.umessage.letsgo.domain.po.system.NationDictionaryEntity;
import java.util.List;

public interface NationDictionaryDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param nationDictionaryEntity
     * @return int
     */
    int insert(NationDictionaryEntity nationDictionaryEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.system.NationDictionaryEntity
     */
    NationDictionaryEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.system.NationDictionaryEntity>
     */
    List<NationDictionaryEntity> selectAll();

    /**
     * Description: 
     * @param nationDictionaryEntity
     * @return int
     */
    int update(NationDictionaryEntity nationDictionaryEntity);

    List<NationDictionaryEntity> selectHot();

    NationDictionaryEntity selectWithCode(String code);
}