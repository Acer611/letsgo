package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.ScheduleDescEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleDescResponse;

import java.util.List;

/**
 * 行程其他说明接口
 * Created by gaofei on 2017/2/20.
 */
public interface IScheduleDescService {
    /**
     * 添加行程其他说明信息
     * @param scheduleDetailDescs
     * @return
     */
    ScheduleDescResponse createScheduleDesc(List<ScheduleDescEntity> scheduleDetailDescs);

    /**
     * 根据行程的ID获取每日行程其他说明的信息
     * @param scheduleId
     * @return
     */
    List<ScheduleDescEntity> getScheduleDescByScheduleId(Long scheduleId);

    /**
     * 根据ID删除行程其他说明信息
     * @param id
     * @return
     */
    CommonResponse deleteScheduleDescById(Long id);

    /**
     * 修改行程其他说明
     * @param scheduleDetailDesc
     * @return
     */
    CommonResponse updateScheduleDesc(ScheduleDescEntity scheduleDetailDesc);

    /**
     * 根据行程id删除其他说明
     * @param scheduleId
     * @return
     */
    int deleteByScheduleId(Long scheduleId);

    ScheduleDescEntity select(Long id);
}
