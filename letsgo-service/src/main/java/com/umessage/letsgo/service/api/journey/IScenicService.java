package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.ScenicEntity;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/2.
 */
public interface IScenicService {
    // 新增
    int addScenic(ScenicEntity scenicEntity);
    // 删除
    int deleteScenic(long id);
    //删除每日行程中的景点
    int deleteByScheduleDetailId(long ScheduleDetailId);
    // 更新
    int updateScenic(ScenicEntity scenicEntity);
    // 获取
    ScenicEntity getScenic(long id);

    //根据scheduleDetailId获取景点列表
    List<ScenicEntity> getScenicListByScheduleDetailId(long scheduleDetailId);

}
