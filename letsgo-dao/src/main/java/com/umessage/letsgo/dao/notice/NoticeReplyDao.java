/*
 * NoticeReplyDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.notice;

import java.util.List;

import com.umessage.letsgo.domain.po.notice.NoticeReplyEntity;

public interface NoticeReplyDao {
    /**
     * Description:
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description:
     * @param noticeReplyEntity
     * @return int
     */
    int insert(NoticeReplyEntity noticeReplyEntity);

    /**
     * Description:
     * @param id
     * @return com.umessage.letsgo.domain.po.notice.NoticeReplyEntity
     */
    NoticeReplyEntity select(Long id);

    /**
     * Description:
     * @return java.util.List<com.umessage.letsgo.domain.po.notice.NoticeReplyEntity>
     */
    List<NoticeReplyEntity> selectAll();

    /**
     * Description:
     * @param noticeReplyEntity
     * @return int
     */
    int update(NoticeReplyEntity noticeReplyEntity);
}