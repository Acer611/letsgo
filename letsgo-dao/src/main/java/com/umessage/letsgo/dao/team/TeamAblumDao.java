package com.umessage.letsgo.dao.team;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.team.TeamAblumEntity;
import com.umessage.letsgo.domain.vo.team.requset.TeamAblumRequest;

import java.util.List;

/**
 * Created by zengguoqing on 2016/8/18.
 */
public interface TeamAblumDao {
    public Page<TeamAblumEntity> getTeamAblum(TeamAblumRequest teamAblumRequest);
    public List<TeamAblumEntity> getTeamAblumByTime(TeamAblumRequest teamAblumRequest);
    public int savePhotoUrl(TeamAblumEntity teamAblumEntity);
    public void updatePhotoType(TeamAblumEntity teamAblumEntity);
    public List<TeamAblumEntity> getTeamAblumCreateTimeByTime(TeamAblumRequest teamAblumRequest);
}
