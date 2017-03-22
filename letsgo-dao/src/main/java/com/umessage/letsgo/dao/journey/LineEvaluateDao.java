/*
 * ScheduleDao.java
 * Copyright(C) 2015-2016 ????
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by ZhaoYidong
 */
package com.umessage.letsgo.dao.journey;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.journey.LineEvaluateEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.vo.journey.request.LineEvaluateRequest;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleReq;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleRequest;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleLineVo;
import com.umessage.letsgo.domain.vo.journey.response.TeamScheduleVo;
import com.umessage.letsgo.domain.vo.team.requset.WebMemberRequest;

import java.util.List;

public interface LineEvaluateDao {
    /**
     * Description: 
     * @pasram id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param lineEvaluateEntity
     * @return int
     */
    int insert(LineEvaluateEntity lineEvaluateEntity);

    //根据条件查询线路评价统计数据
    Page<LineEvaluateEntity>  getLineList(LineEvaluateRequest request);

}
