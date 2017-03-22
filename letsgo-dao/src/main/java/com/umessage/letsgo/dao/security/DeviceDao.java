/*
 * DeviceDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-17 Created by Administrator
 */
package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.DeviceEntity;
import java.util.List;

public interface DeviceDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param deviceEntity
     * @return int
     */
    int insert(DeviceEntity deviceEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.security.DeviceEntity
     */
    DeviceEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.security.DeviceEntity>
     */
    List<DeviceEntity> selectAll();

    /**
     * Description: 
     * @param deviceEntity
     * @return int
     */
    int update(DeviceEntity deviceEntity);

    int updateWithUserId(DeviceEntity deviceEntity);

    DeviceEntity selectWithUserId(Long userId);

    DeviceEntity selectWithRegId(String regId);

    /**
     * 查询设备
     * @param userIds
     * @return
     */
    List<DeviceEntity> selectDeviceWithUserIds(List<Long> userIds);
}