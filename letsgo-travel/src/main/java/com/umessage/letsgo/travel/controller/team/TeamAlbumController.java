package com.umessage.letsgo.travel.controller.team;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.team.TeamAblumEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.team.respone.TeamAblumRes;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.team.ITeamAblumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengguoqing on 2016/8/25.
 */
@Controller
@RequestMapping(value = "/teamAlbum")
public class TeamAlbumController {

    private Logger logger = LoggerFactory.getLogger(TeamAlbumController.class);

    @Resource
    private ITeamAblumService teamAblumService;
    @Resource
    private IScheduleService scheduleService;
    @RequestMapping(value = "/getTeamPhotoList", method = RequestMethod.GET)
    public String getTeamAlbum(@RequestParam String teamId, @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,Model model) {
        TeamAblumRes teamAblumRes=teamAblumService.getTeamAblum(teamId,pageNum);
        Map<String,List<TeamAblumEntity>> map=new HashMap<String,List<TeamAblumEntity>>();
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
        if(teamAblumRes.getList().size()>0){

            for (TeamAblumEntity tt:teamAblumRes.getList()){
                String date= sim.format(tt.getCreateTime());
                if(map.containsKey(date)){
                    List<TeamAblumEntity> list1=map.get(date);
                    list1.add(tt);
                }
                else{
                    List<TeamAblumEntity> list2 = new ArrayList<TeamAblumEntity>();
                    list2.add(tt);
                    map.put(date,list2);
                }
            }
        }
        ScheduleEntity sch=scheduleService.getTeamName(teamId);
        model.addAttribute("map",map);
        model.addAttribute("sch",sch);
        return "teamphoto/teamPhoto";
    }
    @RequestMapping(value = "/getTeamPhotoLists", method = RequestMethod.GET)
    public String getTeamAlbums(@RequestParam String teamId, @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,Model model) {
        TeamAblumRes list=teamAblumService.getTeamAblum(teamId,pageNum);
        Map<String,List<TeamAblumEntity>> map=new HashMap<String,List<TeamAblumEntity>>();
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
        if(list.getList().size()>0){

            for (TeamAblumEntity tt:list.getList()){
                String date= sim.format(tt.getCreateTime());
                if(map.containsKey(date)){
                    List<TeamAblumEntity> list1=map.get(date);
                    list1.add(tt);
                }
                else{
                    List<TeamAblumEntity> list2=new ArrayList<TeamAblumEntity>();
                    list2.add(tt);
                    map.put(date,list2);
                }
            }
        }
        ScheduleEntity sch=scheduleService.getTeamName(teamId);
        model.addAttribute("map",map);
        model.addAttribute("sch",sch);
        return "teamphoto/playPhoto";
    }

    @RequestMapping(value = "/updateTeamPhotoType")
    @ResponseBody
    public CommonResponse updatePhotoType(@RequestParam Integer type,@RequestParam String teamIdString){
        String[] teamIdS = teamIdString.split(",");
        if (null == type) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：图片属性【type】不能为空！");
        }
        if (StringUtils.isEmpty(teamIdString)){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：图片id【id】不能为空！");
        }

        for (int i= 0;i<teamIdS.length;i++){
            TeamAblumEntity teamAblumEntity = new TeamAblumEntity();
            teamAblumEntity.setType(type);
//          teamAblumEntity.setTeamId(teamIdS[i]);
            teamAblumEntity.setId(Long.valueOf(teamIdS[i]).longValue());
            logger.info("图片id为："+Long.valueOf(teamIdS[i]).longValue());
            teamAblumService.updatePhotoType(teamAblumEntity);
            logger.info("执行完毕");
        }
        return new CommonResponse();
    }
}
