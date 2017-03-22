package com.umessage.letsgo.service.api.security;

import com.umessage.letsgo.domain.po.security.DeviceEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public interface IDeviceService {
    int create(DeviceEntity deviceEntity);

    int update(DeviceEntity deviceEntity);

    DeviceEntity getDevice(Long id);

    DeviceEntity getDeviceByUserId(Long userId);

    DeviceEntity getDeviceByRegId(String regId);

    List<DeviceEntity> getDeviceTypeAndUserName(List<Long> userIdList);
}
