/*
 * NoticeDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-12 Created by Administrator
 */
package com.umessage.letsgo.dao.notice;

import java.util.List;

import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.vo.notice.request.NoticeParamRequest;
import com.umessage.letsgo.domain.vo.notice.request.NoticeRequest;

public interface NoticeDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param noticeEntity
     * @return int
     */
    int insert(NoticeEntity noticeEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.notice.NoticeEntity
     */
    NoticeEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.notice.NoticeEntity>
     */
    List<NoticeEntity> selectAll();

    /**
     * Description: 
     * @param noticeEntity
     * @return int
     */
    int update(NoticeEntity noticeEntity);

    List<NoticeEntity> selectNoticeListWithConditions(NoticeParamRequest request);

//    List<NoticeEntity> selectNoticesSortCreateTiem(NoticeParamRequest request);
    //获取最新的集合和通知
      NoticeEntity  getLastMessage(NoticeRequest request);
    //获取最新的集合和通知游客
    NoticeEntity  getLastMessageToTourist(NoticeRequest request);

    NoticeEntity getWxGatherById(NoticeEntity noticeEntity);


    NoticeEntity getWxGatherByIdOne(Long id);

    /**
     * 获取通知的列表
     * @param request
     * @return
     */
    List<NoticeEntity> getNoticeMessages(NoticeParamRequest request);

    /**
     * 更新签名图片数量
     * @param notice
     */
    void updateNoticeSignCount(NoticeEntity notice);
}