/*
 * GroupDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.team;

import com.umessage.letsgo.domain.po.team.ShoppingAgencyEntity;

import java.util.List;

public interface ShoppingAgencyDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param shoppingAgencyEntity
     * @return int
     */
    int insert(ShoppingAgencyEntity shoppingAgencyEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.team.ShoppingAgencyEntity
     */
    ShoppingAgencyEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.team.ShoppingAgencyEntity>
     */
    List<ShoppingAgencyEntity> selectAll();

    /**
     * Description: 
     * @param shoppingAgencyEntity
     * @return int
     */
    int update(ShoppingAgencyEntity shoppingAgencyEntity);

    /**
     * 根据私有购物场所名称模糊查询
     * @param shoppingAgencyEntity
     * @return
     */
    List<ShoppingAgencyEntity> selectShoppingAgencyByName(ShoppingAgencyEntity shoppingAgencyEntity);

    /**
     * 查询名字一样的购物场所
     * @param shoppingAgencyEntity
     * @return
     */
    ShoppingAgencyEntity selectShoppingAgencyBySameName(ShoppingAgencyEntity shoppingAgencyEntity);

}