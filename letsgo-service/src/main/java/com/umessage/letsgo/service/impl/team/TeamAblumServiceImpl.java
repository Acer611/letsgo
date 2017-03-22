package com.umessage.letsgo.service.impl.team;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.umessage.letsgo.dao.team.TeamAblumDao;
import com.umessage.letsgo.domain.po.team.TeamAblumEntity;
import com.umessage.letsgo.domain.vo.team.requset.TeamAblumRequest;
import com.umessage.letsgo.domain.vo.team.respone.TeamAblumMaps;
import com.umessage.letsgo.domain.vo.team.respone.TeamAblumRes;
import com.umessage.letsgo.service.api.team.ITeamAblumService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zengguoqing on 2016/8/19.
 */

@Service
public class TeamAblumServiceImpl implements ITeamAblumService {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(TeamAblumServiceImpl.class);

    @Resource
    private TeamAblumDao teamAblumDao;

    @Override
    public TeamAblumRes getTeamAblum(String teamId, Integer pageNum) {
        TeamAblumRequest teamAblumRequest=new TeamAblumRequest();
        teamAblumRequest.setPageNum(pageNum);
        teamAblumRequest.setTeamId(teamId);
        PageHelper.startPage(pageNum,10);
        Page<TeamAblumEntity> page=teamAblumDao.getTeamAblum(teamAblumRequest);
        TeamAblumRes teamAblumRes=new TeamAblumRes();
        //将获取的云相册list放入到实体中
        teamAblumRes.setList(page.getResult());
        teamAblumRes.setTotal(page.getTotal());
        teamAblumRes.setTotalPage(page.getPages());
        return teamAblumRes;
    }

    /**
     * 获取团队相册
     * @param teamId
     * @param pageNum
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<TeamAblumMaps> getTeamAblumByTime(String teamId, Integer pageNum, Date startDate, Date endDate) {
        List<TeamAblumEntity> teamAblumCreateTimeByTime = this.getTeamAblumCreateTimeByTimeFunction(teamId, pageNum);

        List<TeamAblumEntity> page = this.getTeamAblumByTimeFunction(teamId, teamAblumCreateTimeByTime);

        return this.teamAblumsByTimeSort(page);
    }

    @Override
    public int savePhotoUrl(TeamAblumEntity teamAblumEntity) {
        return teamAblumDao.savePhotoUrl(teamAblumEntity);
    }

    public void updatePhotoType(TeamAblumEntity teamAblumEntity){
        logger.info("图片id"+teamAblumEntity.getId());
        teamAblumDao.updatePhotoType(teamAblumEntity);
    }

    //根据团队id和分页分组查询团队相册
    public List<TeamAblumEntity> getTeamAblumCreateTimeByTimeFunction(String teamId, Integer pageNum){
        TeamAblumRequest teamAblumRequestTime = new TeamAblumRequest();
        teamAblumRequestTime.setTeamId(teamId);
        Integer index = (pageNum - 1) * 3;
        teamAblumRequestTime.setStartIndex(index);
        //PageHelper.startPage(pageNum,3);
        return teamAblumDao.getTeamAblumCreateTimeByTime(teamAblumRequestTime);
    }

    //根据团队id查询出分组时间内的所有团队相册
    public List<TeamAblumEntity> getTeamAblumByTimeFunction(String teamId, List<TeamAblumEntity> teamAblumCreateTimeByTime){
        TeamAblumRequest teamAblumRequest=new TeamAblumRequest();
        if (teamAblumCreateTimeByTime.size()<=0){
            return new ArrayList<TeamAblumEntity>();
        }
        TeamAblumEntity teamAblumEntity1 = teamAblumCreateTimeByTime.get(0);
        Date createTime1 = teamAblumEntity1.getCreateTime();
        TeamAblumEntity teamAblumEntity2 = teamAblumCreateTimeByTime.get(teamAblumCreateTimeByTime.size() - 1);
        Date createTime2 = teamAblumEntity2.getCreateTime();
        teamAblumRequest.setStartDate(createTime1);
        teamAblumRequest.setEndDate(createTime2);
        teamAblumRequest.setTeamId(teamId);
        //PageHelper.startPage(pageNum,3);
        return teamAblumDao.getTeamAblumByTime(teamAblumRequest);
    }

    public List<TeamAblumMaps> teamAblumsByTimeSort(List<TeamAblumEntity> page){
        TeamAblumRes teamAblumRes=new TeamAblumRes();
        //将获取的云相册list放入到实体中
        teamAblumRes.setList(page);
        /*teamAblumRes.setList(page.getResult());
        teamAblumRes.setTotal(page.getTotal());
        teamAblumRes.setTotalPage(page.getPages());*/


        //创建封装实体类的lists
        List<TeamAblumMaps> list = new LinkedList<>();

        if(teamAblumRes.getList().size()>0){
            Map<String, TeamAblumMaps> map = this.getteamAblumRes(teamAblumRes);
            Object[] objects = map.keySet().toArray();
            Arrays.sort(objects);
            for (int i = 0; i < objects.length; i++) {
                TeamAblumMaps teamAblumMaps = map.get(objects[i]);
                list.add(teamAblumMaps);
            }
        }
        return list;
    }



    public Map<String,TeamAblumMaps> getteamAblumRes(TeamAblumRes teamAblumRes){
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
        Map<String,TeamAblumMaps> map=new HashMap<String,TeamAblumMaps>();
        for (TeamAblumEntity tt:teamAblumRes.getList()){
            //相册创建时间
            Date createTime = tt.getCreateTime();
            //获取团队相册的创建时间
            String date= sim.format(createTime);
            long time = createTime.getTime();
            String stime = Long.toString(time);

            if (map.containsKey(date)){
                TeamAblumMaps teamAblumMaps = map.get(date);
                List<TeamAblumEntity> list1 = teamAblumMaps.getList();
                list1.add(tt);
            }else{
                List<TeamAblumEntity> tbelist2 = new ArrayList<TeamAblumEntity>();
                //将该云相册放入到该list中
                tbelist2.add(tt);
                //新建一个封装实体类
                TeamAblumMaps teamAblumMaps2 = new TeamAblumMaps();
                teamAblumMaps2.setDate(stime);
                teamAblumMaps2.setList(tbelist2);
                map.put(date,teamAblumMaps2);
            }
        }
        return map;
    }
}
