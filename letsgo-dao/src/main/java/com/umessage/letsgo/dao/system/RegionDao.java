/*
 * RegionDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-15 Created by wendy
 */
package com.umessage.letsgo.dao.system;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.system.RegionEntity;
import com.umessage.letsgo.domain.po.system.RegionEntityPo;
import com.umessage.letsgo.domain.vo.system.request.RegionRequest;

import java.util.List;

public interface RegionDao {
    /**
     * Description: 
     * @param areaid
     * @return int
     */
    int delete(String areaid);

    /**
     * Description: 
     * @param regionEntity
     * @return int
     */
    int insert(RegionEntity regionEntity);

    /**
     * Description: 
     * @param areaid
     * @return com.umessage.letsgo.domain.po.system.RegionEntity
     */
    RegionEntity select(String areaid);

    Page<RegionEntityPo> selectCityAndCountry();

    Page<RegionEntity> selectCountry();

    /**
     * Description: 
     * @param regionEntity
     * @return int
     */
    int update(RegionEntity regionEntity);


    List<RegionEntity> getCountryByDelta(String delta);

    //通过国家的名称获取所属大洲的id
    RegionEntity getCountryByName(String alias);

    /**
     * 根据昵称查询地域
     * @param alias
     * @return
     */
    RegionEntity getRegionByNameOne(String alias);

    List<RegionEntity> getRegionByName(String alias);

    List<RegionEntityPo> getCityAndCountryByName(RegionRequest request);

    //根据城市获取时区
    RegionEntity selectWithDest(String dest);

    List<RegionEntity> selectWithMultiDestIds(List<String> destIds);

}