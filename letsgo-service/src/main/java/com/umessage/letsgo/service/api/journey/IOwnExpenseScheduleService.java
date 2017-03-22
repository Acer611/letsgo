package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.OwnExpenseScheduleEntity;

import java.util.List;


/**
 * Created by zengguoqing on 2016/9/7.
 */
public interface IOwnExpenseScheduleService {

    /**
     * Description:
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description:
     * @param ownExpenseScheduleEntity
     * @return int
     */
    int insert(OwnExpenseScheduleEntity ownExpenseScheduleEntity);

    /**
     * Description:
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.OwnExpenseScheduleEntity
     */
    OwnExpenseScheduleEntity select(Long id);

    /**
     * Description:
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.OwnExpenseScheduleEntity>
     */
    List<OwnExpenseScheduleEntity> selectAll();

    /**
     * Description:
     * @param ownExpenseScheduleEntity
     * @return int
     */
    int update(OwnExpenseScheduleEntity ownExpenseScheduleEntity);

    /**
     * 查询自费项目及相册
     * @param id
     * @return
     */
    OwnExpenseScheduleEntity selectOwnExpenseScheduleAndAlbum(Long id);

    /**
     * 根据行程id查询自费项目
     * @param scheduleId
     * @return
     */
    List<OwnExpenseScheduleEntity> selectOwnExpenseScheduleByScheduleId(Long scheduleId);

    /**
     * 根据每日行程id查询自费项目
     * @param scheduleDetailId
     * @return
     */
    List<OwnExpenseScheduleEntity> selectOwnExpenseScheduleByScheduleDetailId(Long scheduleDetailId);


    /**
     * 根据行程id插入
     * @param ownExpenseScheduleEntity
     * @return
     */
    int insertBySchedule(OwnExpenseScheduleEntity ownExpenseScheduleEntity);


    /**
     * 根据行程id更新
     * @param ownExpenseScheduleEntity
     * @return
     */
    int updateBySchedule(OwnExpenseScheduleEntity ownExpenseScheduleEntity);

    /**
     * 根据行程id删除自费项目
     * @param scheduleId
     * @return
     */
    int deleteOwnExpenseScheduleByScheduleId(Long scheduleId);

    /**
     * 根据每日行程id删除自费项目
     * @param scheduleDetailId
     * @return
     */
    int deleteOwnExpenseScheduleByScheduleDetailId(Long scheduleDetailId);

}
