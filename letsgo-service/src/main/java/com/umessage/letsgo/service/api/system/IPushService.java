package com.umessage.letsgo.service.api.system;

import com.umessage.letsgo.domain.po.security.DeviceEntity;
import com.umessage.letsgo.domain.po.system.PushEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/4.
 */
public interface IPushService {
    int create(PushEntity pushEntity);

    int update(PushEntity pushEntity);

    void pushMessage(String desc, Map<String, String> param, List<MemberEntity> memberEntityList) throws Exception;

    void pushMessageByMyRingtone(String desc, Map<String, String> param, List<MemberEntity> memberEntityList) throws Exception;

    //void pushMIMessageToTourist(List<DeviceEntity> deviceList, String desc, Map<String, String> param) throws Exception;

    void pushMIMessageToGuide(List<DeviceEntity> deviceList, String desc, Map<String, String> param) throws Exception;

    List<PushEntity> getPushList();

    boolean updatePushWithTracker(PushEntity pushEntity);

}
