/*
 * AnnouncementDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.notice;

import java.util.List;

import com.umessage.letsgo.domain.po.notice.AnnouncementEntity;
import com.umessage.letsgo.domain.vo.notice.request.AnnouRequest;
import com.umessage.letsgo.domain.vo.notice.request.AnnouncementRequest;

public interface AnnouncementDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param announcementEntity
     * @return int
     */
    int insert(AnnouncementEntity announcementEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.notice.AnnouncementEntity
     */
    AnnouncementEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.notice.AnnouncementEntity>
     */
    List<AnnouncementEntity> selectAll();

    /**
     * Description: 
     * @param announcementEntity
     * @return int
     */
    int update(AnnouncementEntity announcementEntity);

    List<AnnouncementEntity> selectAnnouncementListWithConditions(AnnouRequest request);

    //获取最新公告导游
    AnnouncementEntity  selectAnnouncementByUserIdAndTeamId(AnnouncementRequest request);
    //获取最新公告游客
    AnnouncementEntity  selectAnnouncementByUserIdAndTeamIdToTourist(AnnouncementRequest request);

    AnnouncementEntity getById(Long id);

    AnnouncementEntity selectWxAnnouncementById(Long id);
}