/*
 * MessageDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-04 Created by Administrator
 */
package com.umessage.letsgo.dao.system;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.system.LogManage;
import com.umessage.letsgo.domain.po.system.MessageEntity;
import com.umessage.letsgo.domain.vo.system.request.LogManageRequest;
import com.umessage.letsgo.domain.vo.system.request.MessageRequest;

import java.util.List;

public interface LogManageDao {
    /**
     * Description: 
     * @param logManage 新增
     * @return int
     */
    int insert(LogManage logManage);
    //获取日志列表
    Page<LogManage> getLogList(LogManageRequest request);
}