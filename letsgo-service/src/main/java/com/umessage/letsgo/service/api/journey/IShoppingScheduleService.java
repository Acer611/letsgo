package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.ShoppingScheduleEntity;

import java.util.List;


/**
 * Created by zengguoqing on 2016/9/7.
 */
public interface IShoppingScheduleService {

    /**
     * Description:
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description:
     * @param shoppingScheduleEntity
     * @return int
     */
    int insert(ShoppingScheduleEntity shoppingScheduleEntity);

    /**
     * Description:
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.ShoppingScheduleEntity
     */
    ShoppingScheduleEntity select(Long id);

    /**
     * Description:
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.ShoppingScheduleEntity>
     */
    List<ShoppingScheduleEntity> selectAll();

    /**
     * Description:
     * @param shoppingScheduleEntity
     * @return int
     */
    int update(ShoppingScheduleEntity shoppingScheduleEntity);

    /**
     * 根据每日行程id查询购物场所
     * @param scheduleDetailId
     * @return
     */
    List<ShoppingScheduleEntity> selectShoppingByScheduleDetailId(Long scheduleDetailId);

    /**
     * 根据行程id查询购物场所
     * @param scheduleId
     * @return
     */
    List<ShoppingScheduleEntity> selectShoppingByScheduleId(Long scheduleId);


    /**
     * 根据每日行程删除
     * @param scheduleDetailId
     * @return
     */
    int deleteShoppingByScheduleDetailId(Long scheduleDetailId);

    /**
     * 根据行程删除
     * @param scheduleId
     * @return
     */
    int deleteShoppingByScheduleId(Long scheduleId);

}
