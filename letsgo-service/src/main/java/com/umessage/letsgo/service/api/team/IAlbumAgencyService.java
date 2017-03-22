package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.team.AlbumAgencyEntity;
import com.umessage.letsgo.domain.po.team.HotelAgencyEntity;
import com.umessage.letsgo.domain.po.team.OwnExpenseAgencyEntity;

import java.util.List;


/**
 * Created by zengguoqing on 2016/9/7.
 */
public interface IAlbumAgencyService {

    /**
     * Description:
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description:
     * @param albumAgencyEntity
     * @return int
     */
    int insert(AlbumAgencyEntity albumAgencyEntity);

    /**
     * Description:
     * @param id
     * @return com.umessage.letsgo.domain.po.team.AlbumAgencyEntity
     */
    AlbumAgencyEntity select(Long id);

    /**
     * Description:
     * @return java.util.List<com.umessage.letsgo.domain.po.team.AlbumAgencyEntity>
     */
    List<AlbumAgencyEntity> selectAll();

    /**
     * Description:
     * @param albumAgencyEntity
     * @return int
     */
    int update(AlbumAgencyEntity albumAgencyEntity);


    /**
     * 旅行社私有照片库照片批量插入
     * @param albumAgencyEntities
     */
    void batchInsert(List<AlbumAgencyEntity> albumAgencyEntities);

    /**
     * 批量插入旅行社私有酒店的照片
     * @param hotelAgencyEntity
     */
    void batchInsertHotelAgencyAlbum(HotelAgencyEntity hotelAgencyEntity);

    /**
     *批量修改旅行社私有酒店相关的照片
     * @param hotelAgencyEntity
     */
    void batchUpdateHotelAgencyAlbum(HotelAgencyEntity hotelAgencyEntity);

    /**
     * 根据旅行社ID object id 和type 删除照片私有库数据
     * @param albumAgencyRequest
     */
    void deleteByAlbumAgencyEntity(AlbumAgencyEntity albumAgencyRequest);

    /**
     * 批量插入旅行社私有自费项目的照片
     * @param ownExpenseAgencyEntity
     */
    void batchInsertOwnExpenseAgencyAlbum(OwnExpenseAgencyEntity ownExpenseAgencyEntity);

    /**
     * 批量修改旅行社私有自费项目相关的照片
     * @param ownExpenseAgencyEntity
     */
    void batchUpdateOwnExpenseAgencyAlbum(OwnExpenseAgencyEntity ownExpenseAgencyEntity);

    /**
     * 根据旅行社ID object id 和type 删查询照片私有库数据
     * @param travelId,objectId,type
     * @return
     */
    List<AlbumAgencyEntity> selectByAlbumAgencyEntity(Long travelId, Long objectId, Integer type);
}
