package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.ScheduleDetailScenicEntity;
import com.umessage.letsgo.domain.po.team.ScenicAgencyEntity;

import java.util.List;

/**
 * 每日行程景点关联关系dao层处理类
 * Created by gaofei on 2017/2/15.
 */
public interface ScheduleScenicDao {

    /**
     * 保存每日行程景点关联关系
     * @param scheduleDetailScenic
     * @return
     */
    int insert(ScheduleDetailScenicEntity scheduleDetailScenic);

    /**
     * g根据id 删除景点每日行程的关联关系
     * @param id
     */
    void deleteScheduleDetailScenicById(Long id);

    /**
     * 修改每日行程景点关联数据表数据
     * @param scheduleDetailScenic
     */
    void updateScheduleDetailScenic(ScheduleDetailScenicEntity scheduleDetailScenic);


    /**
     * 根据ID 查询关联景点数据信息
     * @param scenicId
     * @return
     */
    ScheduleDetailScenicEntity selectScenicInfoById(Long scenicId);

    /**
     * 删除景点信息
     * @param scheduleDetailScenicEntity
     */
    void deleteSchedule(ScheduleDetailScenicEntity scheduleDetailScenicEntity);
}
