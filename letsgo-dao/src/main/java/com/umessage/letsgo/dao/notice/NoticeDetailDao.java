/*
 * NoticeDetailDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-06-29 Created by wendy
 */
package com.umessage.letsgo.dao.notice;

import com.umessage.letsgo.domain.po.notice.NoticeDetailEntity;
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.po.notice.NoticeSignEntity;
import com.umessage.letsgo.domain.vo.notice.request.DetailParamRequest;

import java.util.List;

public interface NoticeDetailDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param noticeDetailEntity
     * @return int
     */
    int insert(NoticeDetailEntity noticeDetailEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.notice.NoticeDetailEntity
     */
    NoticeDetailEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.notice.NoticeDetailEntity>
     */
    List<NoticeDetailEntity> selectAll();

    /**
     * Description: 
     * @param noticeDetailEntity
     * @return int
     */
    int update(NoticeDetailEntity noticeDetailEntity);

    NoticeDetailEntity selectNoticeDetailWithConditions(DetailParamRequest request);

    List<NoticeDetailEntity> selectNoticeDetailListWithConditions(DetailParamRequest request);

    /**
     * 根据条件查询团队或全局的未读信息（集合、通知、公告）
     */
    int updateUnReads(DetailParamRequest request);

    int selectCountWithConditions(DetailParamRequest request);

    /**
     * 根据条件查询领队未读信息（集合、通知、公告）
     */
    int getNumsByTypeAndUserId(DetailParamRequest request);

    int getNumsByTypeAndUserIdOne(DetailParamRequest request);

    int getNotOptionCountByUserIdAndStatus(DetailParamRequest request);

    int getTotalCountByUserId(DetailParamRequest request);

    int updateNotClick(DetailParamRequest request);

    //根据类型获取最新的提醒ID
    Long getNewNoticeId(DetailParamRequest request);

    /**
     * 获取通知的签名信息
     * @param noticeId
     * @return
     */
    NoticeEntity getNoticeSignInfo(Long noticeId);

    /**
     * 根据通知明细的id获取签名信息
     * @param id
     * @return
     */
    List<NoticeSignEntity> getNoticeSignByDetailId(Long id);

    /**
     * 保存照片到notic sign 表
     * @param noticeSignEntityList
     */
    int insterBatch(List<NoticeSignEntity> noticeSignEntityList);
}