package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.team.HotelAgencyEntity;

import java.util.List;


/**
 * Created by zengguoqing on 2016/9/7.
 */
public interface IHotelAgencyService {

    /**
     * 删除
     * Description:
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * 插入
     * Description:
     * @param hotelAgencyEntity
     * @return int
     */
    int insert(HotelAgencyEntity hotelAgencyEntity);

    /**
     * 根据id查询
     * Description:
     * @param id
     * @return com.umessage.letsgo.domain.po.team.HotelAgencyEntity
     */
    HotelAgencyEntity select(Long id);

    /**
     * 查询所有
     * Description:
     * @return java.util.List<com.umessage.letsgo.domain.po.team.HotelAgencyEntity>
     */
    List<HotelAgencyEntity> selectAll();

    /**
     * 更新
     * Description:
     * @param hotelAgencyEntity
     * @return int
     */
    int update(HotelAgencyEntity hotelAgencyEntity);

    /**
     * 根据私有酒店名称查询
     * @param hotelName
     * @return
     */
    List<HotelAgencyEntity> selectHotelAgencyByName(String hotelName, Long travelId);

    /**
     * 查询名字一样的酒店
     * @param hotelAgencyEntity
     * @return
     */
    Boolean selectHotelAgencyBySameName(HotelAgencyEntity hotelAgencyEntity);

    /**
     * 查询酒店及相册
     * @param id
     * @return
     */
    HotelAgencyEntity selectHotelAndAlbum(Long id);

}
