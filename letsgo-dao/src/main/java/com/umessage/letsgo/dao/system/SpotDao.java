/*
 * SpotDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-15 Created by wendy
 */
package com.umessage.letsgo.dao.system;

import com.umessage.letsgo.domain.po.system.SpotEntity;
import com.umessage.letsgo.domain.po.system.SpotEntityPo;
import com.umessage.letsgo.domain.vo.system.request.SpotRequest;

import java.util.List;

public interface SpotDao {
    /**
     * Description: 
     * @param mafengId
     * @return int
     */
    int delete(Long mafengId);

    /**
     * Description: 
     * @param spotEntity
     * @return int
     */
    int insert(SpotEntity spotEntity);

    /**
     * Description: 
     * @param mafengId
     * @return com.umessage.letsgo.domain.po.system.SpotEntity
     */
    SpotEntity select(Long mafengId);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.system.SpotEntity>
     */
    List<SpotEntity> selectAll();

    /**
     * Description: 
     * @param spotEntity
     * @return int
     */
    int update(SpotEntity spotEntity);

    List<SpotEntity> selectLike(SpotRequest request);

    /**
     * 根据团队ID查询景点
     * @param request
     * @return
     */
    List<SpotEntityPo> selectByTeam(SpotRequest request);

}