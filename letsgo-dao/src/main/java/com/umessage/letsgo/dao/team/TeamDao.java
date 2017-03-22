/*
 * TeamDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-11 Created by Administrator
 */
package com.umessage.letsgo.dao.team;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.team.AnalyticalData;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleRequest;
import com.umessage.letsgo.domain.vo.team.requset.TeamRequest;

public interface TeamDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param teamEntity
     * @return int
     */
    int insert(TeamEntity teamEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.team.TeamEntity
     */
    TeamEntity select(Long id);
    TeamEntity selectById(Long id);

    /**
     * 通过行程id查询团队
     */
    TeamEntity selectByScheduleId(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.team.TeamEntity>
     */
    List<TeamEntity> selectAll();

    /**
     * Description: 
     * @param teamEntity
     * @return int
     */
    int update(TeamEntity teamEntity);

	List<TeamEntity> selectTeamListWithIDs(List<String> teamIds);

    List<TeamEntity> selectUnfinishedTeamListWithIDs(List<String> teamIds);

    TeamEntity selectTeamWithTXGroupId(String TXGroupId);

    /**
     * 获取即将出行和出行中的团队
     * @return
     */
    List<TeamEntity> selectUnfinishedTeam();

    /**
     * 获取出行结束的团队
     * @return
     */
    List<TeamEntity> selectFinishedTeam(ScheduleRequest scheduleRequest);

    /**
     * 获取需要发送系统信息的团队
     * @return
     */
    List<TeamEntity> selectSendMsgTeam();

    /**
     * 获取需要发送点评信息的团队
     * @return
     */
    List<TeamEntity> selectSendCommnetTeam(@Param(value = "startDate") Date startDate);

    List<TeamEntity> selectByTeamNumAndTravelId(ScheduleRequest scheduleRequest);

	Page<AnalyticalData> queryAnalyticalDataList(String teamId);

	int saveProfession(@Param(value="teamId")String teamId,@Param(value="userId") Long userId,
			@Param(value="proCode") String proCode , @Param(value="proName")String proName);

    List<String> getTeamListBetweenTwoUserId(TeamRequest request);
    //判断团号是否存在
    int hasTeamNum(String teamNum);

    Page<TeamEntity> selectTeamListByCountryN(TeamRequest request);
}