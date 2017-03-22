/*
 * GroupDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.team;

import com.umessage.letsgo.domain.po.team.OwnExpenseAgencyEntity;

import java.util.List;

public interface OwnExpenseAgencyDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param ownExpenseAgencyEntity
     * @return int
     */
    int insert(OwnExpenseAgencyEntity ownExpenseAgencyEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.team.OwnExpenseAgencyEntity
     */
    OwnExpenseAgencyEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.team.OwnExpenseAgencyEntity>
     */
    List<OwnExpenseAgencyEntity> selectAll();

    /**
     * Description: 
     * @param ownExpenseAgencyEntity
     * @return int
     */
    int update(OwnExpenseAgencyEntity ownExpenseAgencyEntity);

    /**
     *根据名称联想查询自费项目
     * @param ownExpenseAgencyEntity
     * @return
     */
    List<OwnExpenseAgencyEntity> selectOwnExpenseByName(OwnExpenseAgencyEntity ownExpenseAgencyEntity);

    /**
     *查询名称相同的自费项目
     * @param ownExpenseAgencyEntity
     * @return
     */
    OwnExpenseAgencyEntity selectOwnExpenseBySameName(OwnExpenseAgencyEntity ownExpenseAgencyEntity);

    /**
     * 查询自费项目及相册
     * @param id
     * @return
     */
    OwnExpenseAgencyEntity selectOwnExpenseAndAlbum(Long id);

}