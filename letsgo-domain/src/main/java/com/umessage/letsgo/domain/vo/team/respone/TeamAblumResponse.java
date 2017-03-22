package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.domain.po.team.TeamAblumEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import java.util.Map;
import java.util.List;

/**
 * Created by zengguoqing on 2016/8/22.
 */
public class TeamAblumResponse extends CommonResponse{
   private Map<String,List<TeamAblumEntity>> map;

    /**
     * 封装团队相册实体list
     */
    public List<TeamAblumMaps> list;

    public Map<String, List<TeamAblumEntity>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<TeamAblumEntity>> map) {
        this.map = map;
    }

    public List<TeamAblumMaps> getList() {
        return list;
    }

    public void setList(List<TeamAblumMaps> list) {
        this.list = list;
    }
}
