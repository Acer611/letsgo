package com.umessage.letsgo.service.impl.system;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.maps.model.LatLng;
import com.umessage.letsgo.core.timezone.TimeZoneUtil;
import com.umessage.letsgo.dao.system.RegionDao;
import com.umessage.letsgo.domain.po.system.RegionEntity;
import com.umessage.letsgo.domain.po.system.RegionEntityPo;
import com.umessage.letsgo.domain.vo.journey.response.TimeZoneInfo;
import com.umessage.letsgo.domain.vo.system.request.RegionRequest;
import com.umessage.letsgo.service.api.system.IRegionService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wendy on 2016/8/15.
 */
@Service
public class RegionServiceImpl implements IRegionService {

    private Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);

    @Resource
    private RegionDao regionDao;

    @Override
    public RegionEntity getById(String id) {
        return regionDao.select(id);
    }

    @Override
    public List<RegionEntity> getRegionList(String alias) {
        return regionDao.getRegionByName(alias);
    }
    //通过国家的名称获取所属大洲的id
    //11：亚洲02：欧洲03：非洲04：大洋洲05：北美洲06：南美洲
    public String getDeltaByCountryName(String alias){
        String delta = "";
        RegionEntity regionEntity=regionDao.getCountryByName(alias);
        if(regionEntity!=null){
            delta=regionEntity.getDelta();
        }
        return delta;
    }
    @Override
    public List<RegionEntity> getCountryByDelta(String delta) {
        return regionDao.getCountryByDelta(delta);
    }


    @Override
    public List<RegionEntityPo> getCountryByCity(String alias) {
        RegionRequest request  = new RegionRequest();
        if(alias.matches("[a-zA-Z]+")){
            request.setAreapinyinname(alias.toLowerCase()+"%");
        }else{
            request.setAlias(alias);
        }
        List<RegionEntityPo> cityNameList = regionDao.getCityAndCountryByName(request);
        return cityNameList;
    }


    private boolean locationIsEmpty(Double lat, Double lng){
        if (lat == null || lat <= 0) return true;
        if (lng == null || lng <= 0) return true;
        return false;
    }

    private int updateCityLocation(RegionEntityPo regionEntityPo){
        int ret = 0;
        try {
            LatLng latLng = TimeZoneUtil.getLocationWithCity(regionEntityPo.getAreaengname() + "," +regionEntityPo.getCountryEngName());
            TimeZone timeZone =TimeZoneUtil.getTimeZoneWithCoordinate(latLng.lat, latLng.lng);

            regionEntityPo.setLat(latLng.lat);
            regionEntityPo.setLng(latLng.lng);
            regionEntityPo.setTimezone(timeZone.getID());
            ret = regionDao.update(regionEntityPo);
            logger.error("更新城市{}地标成功,lat: {}, lng{}", regionEntityPo.getAlias(), latLng.lat, latLng.lng);
        } catch (Exception e) {
            logger.error("未找到城市{}对应的地标", regionEntityPo.getAlias());
            //e.printStackTrace();
        }
        return ret;
    }

    private int updateCountryLocation(RegionEntity regionEntity){
        int ret = 0;
        try {
            LatLng latLng = TimeZoneUtil.getLocationWithCity(regionEntity.getAlias());
            TimeZone timeZone =TimeZoneUtil.getTimeZoneWithCoordinate(latLng.lat, latLng.lng);

            regionEntity.setLat(latLng.lat);
            regionEntity.setLng(latLng.lng);
            regionEntity.setTimezone(timeZone.getID());
            ret = regionDao.update(regionEntity);
            logger.error("更新国家{}地标成功,lat: {}, lng{}", regionEntity.getAlias(), latLng.lat, latLng.lng);
        } catch (Exception e) {
            logger.error("未找到国家{}对应的地标", regionEntity.getAlias());
            //e.printStackTrace();
        }
        return ret;
    }

    public void batchUpdateCityLocation(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        Page<RegionEntityPo> page = regionDao.selectCityAndCountry();
        List<RegionEntityPo> pageResult = page.getResult();

        for (Iterator<RegionEntityPo> iterator = pageResult.iterator(); iterator.hasNext(); ) {
            RegionEntityPo next =  iterator.next();
            if (this.locationIsEmpty(next.getLat(), next.getLng())){
                updateCityLocation(next);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void batchUpdateCountryLocation(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);

        Page<RegionEntity> page = regionDao.selectCountry();
        List<RegionEntity> pageResult = page.getResult();

        for (Iterator<RegionEntity> iterator = pageResult.iterator(); iterator.hasNext(); ) {
            RegionEntity next =  iterator.next();
            if (this.locationIsEmpty(next.getLat(), next.getLng())){
                updateCountryLocation(next);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public RegionEntity getDestinationByDest(String destination) {
        RegionEntity regionEntity = regionDao.selectWithDest(destination);
        //RegionEntity regionEntity = regionDao.select(destination);
        if (regionEntity == null) {
            return new RegionEntity();
        }
        return regionEntity;
    }

    @Override
    public List<RegionEntity> getDestinationByDestIds(List<String> destIds) {

        List<RegionEntity> destinationEntityList = regionDao.selectWithMultiDestIds(destIds);
        if (CollectionUtils.isEmpty(destinationEntityList)) {
            return new ArrayList<>();
        }
        return destinationEntityList;
    }

    /**
     * 根据城市Id获取时区
     * @param regionId
     * @return
     */
    public TimeZone getTimeZoneByRegionId(String regionId) {
        RegionEntity regionEntity = this.getById(regionId);

        // 默认当前时区
        TimeZone timeZone = getTimeZone(regionEntity);
        return timeZone;
    }

    /**
     * 根据城市Id获取时区信息
     * @param regionId
     * @return
     */
    public TimeZoneInfo getTimeZoneInfoByRegionId(String regionId) {
        RegionEntity regionEntity = this.getById(regionId);
        TimeZone timeZone = getTimeZone(regionEntity);

        TimeZoneInfo timeZoneInfo = new TimeZoneInfo();
        timeZoneInfo.setDestination(regionEntity.getAlias() + "时间");
        timeZoneInfo.setTimeZoneId(timeZone.getID());

        return timeZoneInfo;
    }

    @Override
    public RegionEntity select(String areaid) {
        return regionDao.select(areaid);
    }

    @Override
    public RegionEntity getRegionByNameOne(String alias) {
        if (!StringUtils.isEmpty(alias)){
            alias = alias.trim();
        }
        return regionDao.getRegionByNameOne(alias);
    }

    private TimeZone getTimeZone(RegionEntity regionEntity) {
        // 默认当前时区
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();

        if (regionEntity == null){
            return timeZone;
        }

        // 取目的地时区
        if (!StringUtils.isEmpty(regionEntity.getTimezone())) {
            timeZone = TimeZone.getTimeZone(regionEntity.getTimezone());
        } else if (regionEntity.getAreaid() != null &&
                regionEntity.getLat() != null &&
                regionEntity.getLng() != null) {
            timeZone = TimeZoneUtil.getTimeZoneWithCoordinate(regionEntity.getLat(), regionEntity.getLng());
        }
        return timeZone;
    }

}
