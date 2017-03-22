package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.team.LeaderEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleShowRequest;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.SignVo;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/8.
 */
public interface IWebScheduleService {
    public ScheduleResponse getScheduleShowByTeamId(String teamId, Long scheduleDetaildId,Long scheduleId, Long descId);

    //获取页面所有链接的签名map
    public ScheduleResponse getScheduleSigns(ScheduleShowRequest showRequest, ScheduleResponse response, SignVo signVo);

    //获取行程中所有导游
    public List<LeaderEntity> getScheduleGuides(String teamId);

    // 获取游客列表
    public List<MemberEntity> getTouristList(Long scheduleId);

}
