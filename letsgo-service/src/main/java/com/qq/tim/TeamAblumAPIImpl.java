package com.qq.tim;

import com.umessage.letsgo.domain.po.team.TeamAblumEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
@Service
public class TeamAblumAPIImpl implements ITeamAblumAPI {
    @Resource
    private  RestTemplate restTemplate;


    @Override
    public void uploadPhotl(List<TeamAblumEntity> list) {

    }
}
