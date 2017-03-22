package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.team.TeamAblumEntity;
import com.umessage.letsgo.domain.vo.team.respone.TeamAblumMaps;
import com.umessage.letsgo.domain.vo.team.respone.TeamAblumRes;

import java.util.Date;
import java.util.List;

public interface ITeamAblumService {
    /**
     * 获取团队相册
     * @param teamId
     * @param pageNum
     * @return
     */
    TeamAblumRes getTeamAblum(String teamId, Integer pageNum);

    /**
     * 按照时间顺序获取团队相册
     * @param teamId
     * @param pageNum
     * @param startDate
     * @param endDate
     * @return
     */
    List<TeamAblumMaps> getTeamAblumByTime(String teamId, Integer pageNum, Date startDate, Date endDate);

    /**
     * 保存团队相册
     * @param teamAblumEntity
     * @return
     */
    int savePhotoUrl(TeamAblumEntity teamAblumEntity);

    public void updatePhotoType(TeamAblumEntity teamAblumEntity);

}
