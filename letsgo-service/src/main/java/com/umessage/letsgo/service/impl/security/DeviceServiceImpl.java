package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.security.DeviceDao;
import com.umessage.letsgo.domain.po.security.DeviceEntity;
import com.umessage.letsgo.service.api.security.IDeviceService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
@Service
public class DeviceServiceImpl implements IDeviceService {
    @Resource
    private DeviceDao deviceDao;

    @Override
    public int create(DeviceEntity deviceEntity) {
        if(StringUtils.isEmpty(deviceEntity.getIsHuaWei())){
            deviceEntity.setIsHuaWei("0");
        }
        deviceEntity.setCreateTime(new Date());
        deviceEntity.setVersion(0l);
        return deviceDao.insert(deviceEntity);
    }

    @Override
    public int update(DeviceEntity deviceEntity) {
        deviceEntity.setUpdateTime(new Date());
        return deviceDao.updateWithUserId(deviceEntity);
    }

    @Override
    public DeviceEntity getDevice(Long id) {
        DeviceEntity deviceEntity = deviceDao.select(id);
        if (deviceEntity == null)
            return new DeviceEntity();
        return deviceEntity;
    }

    @Override
    public DeviceEntity getDeviceByUserId(Long userId) {
        DeviceEntity deviceEntity = deviceDao.selectWithUserId(userId);
        if (deviceEntity == null) {
            return new DeviceEntity();
        }
        return deviceEntity;
    }

    @Override
    public DeviceEntity getDeviceByRegId(String regId) {
        DeviceEntity deviceEntity = deviceDao.selectWithRegId(regId);
        if (deviceEntity == null) {
            return new DeviceEntity();
        }
        return deviceEntity;
    }

    @Override
    public List<DeviceEntity> getDeviceTypeAndUserName(List<Long> userIdList){
        List<DeviceEntity> list = deviceDao.selectDeviceWithUserIds(userIdList);
        if(list == null){
            list = Collections.emptyList();
        }
        return list;
    }

}
