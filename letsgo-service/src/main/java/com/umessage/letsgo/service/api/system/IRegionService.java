package com.umessage.letsgo.service.api.system;

import com.umessage.letsgo.domain.po.system.RegionEntity;
import com.umessage.letsgo.domain.po.system.RegionEntityPo;
import com.umessage.letsgo.domain.vo.journey.response.TimeZoneInfo;

import java.util.List;
import java.util.TimeZone;

/**
 * Created by wendy on 2016/8/15.
 */
public interface IRegionService {

    RegionEntity getById(String id);

    List<RegionEntity> getRegionList(String alias);

    //通过国家的名称获取所属大洲的id
    String getDeltaByCountryName(String alias);

    List<RegionEntity> getCountryByDelta(String delta);

    List<RegionEntityPo> getCountryByCity(String alias);

    void batchUpdateCityLocation(Integer pageNum, Integer pageSize);

    void batchUpdateCountryLocation(Integer pageNum, Integer pageSize);

    //根据城市获取时区
//    RegionEntity selectWithDest(String dest);

    RegionEntity getDestinationByDest(String destination);

    List<RegionEntity> getDestinationByDestIds(List<String> destIds);

    // 根据城市Id获取时区
    TimeZone getTimeZoneByRegionId(String regionId);

    // 根据城市Id获取时区信息
    TimeZoneInfo getTimeZoneInfoByRegionId(String regionId);

    RegionEntity select(String areaid);
    
    /**
     * 根据昵称查询地域
     * @param alias
     * @return
     */
    RegionEntity getRegionByNameOne(String alias);
}
