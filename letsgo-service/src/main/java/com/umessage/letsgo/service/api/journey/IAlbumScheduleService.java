package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.AlbumScheduleEntity;
import com.umessage.letsgo.domain.po.journey.HotelScheduleEntity;
import com.umessage.letsgo.domain.po.journey.OwnExpenseScheduleEntity;

import java.util.List;


/**
 * Created by zengguoqing on 2016/9/7.
 */
public interface IAlbumScheduleService {

    /**
     * Description:
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description:
     * @param albumScheduleEntity
     * @return int
     */
    int insert(AlbumScheduleEntity albumScheduleEntity);

    /**
     * Description:
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.AlbumScheduleEntity
     */
    AlbumScheduleEntity select(Long id);

    /**
     * Description:
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.AlbumScheduleEntity>
     */
    List<AlbumScheduleEntity> selectAll();

    /**
     * Description:
     * @param albumScheduleEntity
     * @return int
     */
    int update(AlbumScheduleEntity albumScheduleEntity);

    /**
     * 批量插入每日行程景点关联关系数据
     * @param albumScheduleEntities
     */
    void batchInsert(List<AlbumScheduleEntity> albumScheduleEntities);

    /**
     * 批量插入每日行程酒店相关的照片
     * @param hotelScheduleEntity
     */
    void batchInsertHotelScheduleAlbum(HotelScheduleEntity hotelScheduleEntity);

    /**
     * 批量修改每日行程酒店相关的照片
     * @param hotelScheduleEntity
     */
    void batchUpdateHotelScheduleAlbum(HotelScheduleEntity hotelScheduleEntity);

    /**
     * 根据不同类型参数删除照片
     * @param albumScheduleEntity
     * @return
     */
    int deleteByContents(AlbumScheduleEntity albumScheduleEntity);

    /**
     * 根据每日行程id查询照片
     * @param scheduleDetailId
     * @return
     */
    List<AlbumScheduleEntity> selectAlbumsByScheduleDetailId(Long scheduleDetailId);

    /**
     * 批量插入自费项目照片
     * @param ownExpenseScheduleEntity
     */
    void batchInsertOwnExpenseScheduleAlbum(OwnExpenseScheduleEntity ownExpenseScheduleEntity);

    /**
     * 批量修改每日行程自费项目相关的照片
     * @param ownExpenseScheduleEntity
     */
    void batchUpdateOwnExpenseScheduleAlbum(OwnExpenseScheduleEntity ownExpenseScheduleEntity);

    /**
     * 根据行程明细ID object id 和type 查找图片信息
     * @param scheduleDetailId,objectId,type
     * @return
     */
    List<AlbumScheduleEntity> getAlbumSchedule(Long scheduleDetailId, Long objectId, Integer type);

    /**
     * 根据行程id查询图片
     * @param scheduleId
     * @return
     */
    List<AlbumScheduleEntity> getAlbumScheduleByScheduleId(Long scheduleId, Long objectId, Integer type);

    /**
     * 批量插入行程自费项目照片
     * @param ownExpenseScheduleEntity
     */
    void batchInsertOwnExpenseScheduleAlbumBySchedule(OwnExpenseScheduleEntity ownExpenseScheduleEntity);


    /**
     * 批量修改每日行程自费项目相关的照片
     * @param ownExpenseScheduleEntity
     */
    void batchUpdateOwnExpenseScheduleAlbumBySchedule(OwnExpenseScheduleEntity ownExpenseScheduleEntity);

}
