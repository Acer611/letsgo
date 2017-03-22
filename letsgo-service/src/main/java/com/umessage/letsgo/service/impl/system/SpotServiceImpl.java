package com.umessage.letsgo.service.impl.system;

import com.umessage.letsgo.dao.system.SpotDao;
import com.umessage.letsgo.domain.po.system.SpotEntity;
import com.umessage.letsgo.domain.po.system.SpotEntityPo;
import com.umessage.letsgo.domain.vo.system.request.SpotRequest;
import com.umessage.letsgo.service.api.system.ISpotService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 2016/8/15.
 */
@Service
public class SpotServiceImpl implements ISpotService {
    @Resource
    private SpotDao spotDao;

    /**
     * 模糊匹配景点名称，查询景点列表
     * @param query
     * @return
     */
    @Override
    public List<SpotEntity> getSpotList(String query, String cities) {

        SpotRequest request = new SpotRequest();
        if(query.matches("[a-zA-Z]+")){
            request.setSpotpinyinname(query.toLowerCase()+"%");
        }else{
            request.setQuery(query);
        }
//      request.setQuery(query);
        request.setCities(str2list(cities));

        List<SpotEntity> spotEntityList = spotDao.selectLike(request);
        if (CollectionUtils.isEmpty(spotEntityList)) {
            return new ArrayList<>();
        }
        return spotEntityList;
    }

    @Override
    public SpotEntity getSpot(Long id) {
        SpotEntity spotEntity = spotDao.select(id);
        return spotEntity;
    }

    @Override
    public SpotEntity selectOneSpot(Long mafengId) {
        return spotDao.select(mafengId);
    }

    /**
     * 根据团队ID查询景点
     * @param teamId
     * @return
     */
    @Override
    public List<SpotEntityPo> getSpotListByTeam(Long teamId){
        SpotRequest request = new SpotRequest();
        request.setTeamId(teamId);
        List<SpotEntityPo> spotEntityList = spotDao.selectByTeam(request);
        if (CollectionUtils.isEmpty(spotEntityList)) {
            return new ArrayList<>();
        }
        return spotEntityList;
    }

    private List<String> str2list(String str) {
        List<String> list = new ArrayList<>();
        String[] array = str.split(",");
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }

        return list;
    }
}
