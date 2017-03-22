package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.ScheduleDescEntity;

import java.util.List;

/**
 *
 * 行程其他说明dao层接口
 * Created by gaofei on 2017/2/20.
 */
public interface ScheduleDescDao {

    /**
     * 创建行程其他说明信息
     * @param scheduleDetailDesc
     * @return
     */
    int insert(ScheduleDescEntity scheduleDetailDesc);

    /**
     * 根据行程的ID获取行程和其他说明的信息列表
     * @param scheduleId
     * @return
     */
    List<ScheduleDescEntity> getScheduleDescByScheduleId(Long scheduleId);

    /**
     * 删除每日行程的说明
     * @param id
     */
    void delete(Long id);

    /**
     * 修改行程其他说明
     * @param scheduleDetailDesc
     * @return
     */
    int update(ScheduleDescEntity scheduleDetailDesc);

    /**
     * 根据行程id删除其他说明
     * @param scheduleId
     * @return
     */
    int deleteByScheduleId(Long scheduleId);

    ScheduleDescEntity select(Long id);
}
