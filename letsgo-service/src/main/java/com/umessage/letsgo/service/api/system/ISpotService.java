package com.umessage.letsgo.service.api.system;

import com.umessage.letsgo.domain.po.system.SpotEntity;
import com.umessage.letsgo.domain.po.system.SpotEntityPo;

import java.util.List;

/**
 * Created by wendy on 2016/8/15.
 */
public interface ISpotService {
    List<SpotEntity> getSpotList(String query, String cities);

    SpotEntity getSpot(Long id);

    SpotEntity selectOneSpot(Long mafengId);

    List<SpotEntityPo> getSpotListByTeam(Long teamId);

}
