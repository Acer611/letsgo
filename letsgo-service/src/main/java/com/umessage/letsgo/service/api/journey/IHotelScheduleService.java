package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.HotelScheduleEntity;

import java.util.List;


/**
 * Created by zengguoqing on 2016/9/7.
 */
public interface IHotelScheduleService {

    /**
     * 删除
     */
    int delete(Long id);

    /**
     * 插入
     */
    int insert(HotelScheduleEntity hotelScheduleEntity);

    /**
     * Description:
     * @param id
     * 根据id查询
     */
    HotelScheduleEntity select(Long id);

    /**
     * 查询所有
     */
    List<HotelScheduleEntity> selectAll();

    /**
     * Description:
     * @param hotelScheduleEntity
     * @return int
     * 更新
     */
    int update(HotelScheduleEntity hotelScheduleEntity);

    /**
     * 保存酒店及图片
     * @param hotelScheduleEntity
     */
    void saveHotelSchedule(HotelScheduleEntity hotelScheduleEntity);

    /**
     * 根据每日行程id查询酒店
     * @param scheduleDetailId
     * @return
     */
    HotelScheduleEntity selectByScheduleDetailId(Long scheduleDetailId);

    /**
     * 根据id查询每日行程酒店及相册
     * @param id
     * @return
     */
    HotelScheduleEntity selectHotelScheduleAndAlbum(Long id);

    /**
     * 根据每日行程id删除酒店
     * @param scheduleDetailId
     * @return
     */
    int deleteByScheduleDetailId(Long scheduleDetailId);

}
