package com.umessage.letsgo.service.impl.team;

import com.elasticsearch.EsClientHelper;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.dao.team.HotelAgencyDao;
import com.umessage.letsgo.domain.po.system.RegionEntity;
import com.umessage.letsgo.domain.po.team.AlbumAgencyEntity;
import com.umessage.letsgo.domain.po.team.HotelAgencyEntity;
import com.umessage.letsgo.domain.po.team.HotelEntity;
import com.umessage.letsgo.service.api.system.IRegionService;
import com.umessage.letsgo.service.api.team.IAlbumAgencyService;
import com.umessage.letsgo.service.api.team.IHotelAgencyService;
import com.umessage.letsgo.service.api.team.IHotelService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zengguoqing on 2016/9/7.
 */
@Service
public class HotelAgencyServiceImpl implements IHotelAgencyService {

    private Logger logger = LogManager.getLogger(HotelAgencyServiceImpl.class.getName());

    @Resource
    private HotelAgencyDao hotelAgencyDao;
    @Resource
    private IHotelService hotelService;
    @Resource
    private IRegionService regionService;
    @Resource
    private IAlbumAgencyService albumAgencyService;


    @Override
    public int delete(Long id) {
        return hotelAgencyDao.delete(id);
    }

    @Override
    public int insert(HotelAgencyEntity hotelAgencyEntity) {
        hotelAgencyEntity.setCreateTime(new Date());
        hotelAgencyEntity.setUpdateTime(new Date());
        return hotelAgencyDao.insert(hotelAgencyEntity);
    }

    @Override
    public HotelAgencyEntity select(Long id) {
        return hotelAgencyDao.select(id);
    }

    @Override
    public List<HotelAgencyEntity> selectAll() {
        return hotelAgencyDao.selectAll();
    }

    @Override
    public int update(HotelAgencyEntity hotelAgencyEntity) {
        hotelAgencyEntity.setUpdateTime(new Date());
        return hotelAgencyDao.update(hotelAgencyEntity);
    }


    /**
     * 根据酒店名称和旅行社id模糊查询酒店
     * @param hotelName
     * @param travelId
     * @return
     */
    @Override
    public List<HotelAgencyEntity> selectHotelAgencyByName(String hotelName, Long travelId) {
        logger.info("进入酒店的service层...........");
        //去除空格
        if (!StringUtils.isEmpty(hotelName)) {
            hotelName = hotelName.trim();
        }
        logger.info("开始调用酒店的搜索引擎...........");
        //获取私有库酒店
        List<HotelAgencyEntity> hotelAgencyEntities = new ArrayList<>();
        try {
            //获取elasticsearch的client
            TransportClient esTransportClient = EsClientHelper.getInstance().getClient();
            //获取私有库酒店
            List<String> hotelIdList = getHotelAgencys(hotelName, travelId, hotelAgencyEntities, esTransportClient);
            //获取公有库酒店
            getHotels(hotelName, hotelAgencyEntities, esTransportClient, hotelIdList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("最终酒店的对象的长度为 : " + hotelAgencyEntities.size());
        return hotelAgencyEntities;
    }

    /**
     * 获取公有库酒店信息
     * @param hotelName
     * @param hotelAgencyEntities
     * @param esTransportClient
     * @param hotelIdList
     */
    private void getHotels(String hotelName, List<HotelAgencyEntity> hotelAgencyEntities, TransportClient esTransportClient, List<String> hotelIdList) {
        //如果旅行社私有库为空则查询公有库
        logger.info("开始从酒店公有库表查询酒店共有库数据，传入的参数为: " + hotelName);
        //获取公共酒店搜索的结果
        BoolQueryBuilder queryBuilderHotel = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("hotel_chinese_name", hotelName)).should(QueryBuilders.matchQuery("hotel_english_name", hotelName));
        SearchResponse searchResponseHotel = esTransportClient.prepareSearch("letsgo").setTypes("hotel").setQuery(queryBuilderHotel).get();
        //处理搜索的结果集
        SearchHit[] searchHitsHotel = searchResponseHotel.getHits().getHits();
        logger.info("从酒店公有库获取的数据条数为: " + searchHitsHotel.length);
        //获取公共酒店
        for (SearchHit searchHint : searchHitsHotel) {
            Map resultMap = searchHint.getSource();
            HotelEntity hotelEntity = this.getHotel(resultMap);
            String hotelId = hotelEntity.getHotelId();
            if (hotelIdList.contains(hotelId)) {
                continue;
            }
            HotelAgencyEntity hotelAgency = new HotelAgencyEntity();
            hotelAgency.setCityId(hotelEntity.getCityId());
            hotelAgency.setCity(hotelEntity.getCityName());
            hotelAgency.setHotelName(hotelEntity.getHotelChineseName());
            hotelAgency.setHotelEnglishName(hotelEntity.getHotelEnglishName());
            hotelAgency.setBriefintroduction(hotelEntity.getBriefintroduction());
            hotelAgency.setStarLv(hotelEntity.getStarLv());
            hotelAgency.setLon(hotelEntity.getLon());
            hotelAgency.setLat(hotelEntity.getLat());
            hotelAgency.setHotelId(hotelEntity.getHotelId());
            List<String> photos = new ArrayList<>();
            photos.add(hotelEntity.getPicUrl());
            hotelAgency.setPhotoUrls(photos);
            hotelAgencyEntities.add(hotelAgency);
            logger.info("添加: " + hotelEntity.getHotelChineseName() + " 到list 中");
        }
    }

    /**
     * 根据酒店的城市名称获取对应的地域
     * @param hotelAgencyEntities
     * @param esTransportClient
     * @param hotelEntity
     */
    private void getRegions(List<HotelAgencyEntity> hotelAgencyEntities, TransportClient esTransportClient, HotelEntity hotelEntity) {
        logger.info("酒店的搜索条件为: " + hotelEntity.getCityName());
        logger.info("开始调用城市的搜索引擎。。。。。。");
        HotelAgencyEntity hotelAgency = new HotelAgencyEntity();
        if (!StringUtils.isEmpty(hotelEntity.getCityName())) {
            //根据城市名称查询地域表
            SearchResponse searchResponseCity = esTransportClient.prepareSearch("letsgo").setTypes("city")
                    .setQuery(QueryBuilders.matchQuery("alias", hotelEntity.getCityName())).setSize(1).get();
            //处理搜索的结果集
            SearchHit[] searchHitsCitys = searchResponseCity.getHits().getHits();
            logger.info("搜索城市的结果为：" + searchHitsCitys.length);
            List<RegionEntity> regionEntityList = new ArrayList<>();
            for (SearchHit searchHintCity : searchHitsCitys) {
                Map resultMapCity = searchHintCity.getSource();
                RegionEntity regionEntity = this.getRegion(resultMapCity);
                regionEntityList.add(regionEntity);
            }
            if (regionEntityList.size() > 0) {
                RegionEntity regionEntity = regionEntityList.get(0);
                hotelAgency.setCityId(regionEntity.getAreaid());
            }
        }
        hotelAgency.setCity(hotelEntity.getCityName());
        hotelAgency.setHotelName(hotelEntity.getHotelChineseName());
        hotelAgency.setHotelEnglishName(hotelEntity.getHotelEnglishName());
        hotelAgency.setBriefintroduction(hotelEntity.getBriefintroduction());
        hotelAgency.setStarLv(hotelEntity.getStarLv());
        hotelAgency.setLon(hotelEntity.getLon());
        hotelAgency.setLat(hotelEntity.getLat());
        hotelAgency.setHotelId(hotelEntity.getHotelId());
        List<String> photos = new ArrayList<>();
        photos.add(hotelEntity.getPicUrl());
        hotelAgency.setPhotoUrls(photos);
        hotelAgencyEntities.add(hotelAgency);
        logger.info("添加: " + hotelEntity.getHotelChineseName() + " 到list 中");
    }

    /**
     * 获取私有库酒店
     * @param hotelName
     * @param travelId
     * @param hotelAgencyEntities
     * @param esTransportClient
     * @return
     */
    private List<String> getHotelAgencys(String hotelName, Long travelId, List<HotelAgencyEntity> hotelAgencyEntities, TransportClient esTransportClient) {
        //获取私有库酒店搜索的结果,调用搜索引擎的Search API
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("hotel_name", hotelName))
                .must(QueryBuilders.termQuery("travel_id", travelId));
        SearchResponse searchResponse = esTransportClient.prepareSearch("letsgo").setTypes("t_hotel").setQuery(queryBuilder).get();

        //处理搜索的结果集
        SearchHit[] searchHitsThotel = searchResponse.getHits().getHits();
        logger.info("酒店私有库获取的数据有 : " + searchHitsThotel.length + " 条");
        List<String> hotelIdList = new ArrayList<>();
        for (SearchHit searchHint : searchHitsThotel) {
            Map resultMap = searchHint.getSource();
            HotelAgencyEntity hotelAgencyEntityOne = this.getHotelAgency(resultMap);
            if (hotelAgencyEntityOne == null) {
                continue;
            }
            String hotelId = hotelAgencyEntityOne.getHotelId();
            if (null != hotelId) {
                hotelIdList.add(hotelId);
            }
            //获取酒店私有库图片
            getAlbumPicture(esTransportClient, hotelAgencyEntityOne);

            hotelAgencyEntities.add(hotelAgencyEntityOne);
        }
        return hotelIdList;
    }

    //获取酒店私有库图片
    private void getAlbumPicture(TransportClient esTransportClient, HotelAgencyEntity hotelAgencyEntityOne) {
        List<AlbumAgencyEntity> albumAgencyEntitieList = new ArrayList<>();
        //关联图片
        BoolQueryBuilder queryBuilderAlbum = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("travel_id", hotelAgencyEntityOne.getTravelId()))
                .must(QueryBuilders.termQuery("object_id", hotelAgencyEntityOne.getId()))
                .must(QueryBuilders.termQuery("type", 2))
                .mustNot(QueryBuilders.termQuery("status", "deleted"));
        ;
        SearchResponse searchResponseAlbum = esTransportClient.prepareSearch("letsgo").setTypes("album").setQuery(queryBuilderAlbum).get();
        //处理搜索的结果集
        SearchHit[] searchHitsAlbum = searchResponseAlbum.getHits().getHits();
        for (SearchHit searchHintAlbum : searchHitsAlbum) {
            Map resultMapAlbum = searchHintAlbum.getSource();
            AlbumAgencyEntity albumAgencyEntity = this.getAlbum(resultMapAlbum);
            albumAgencyEntitieList.add(albumAgencyEntity);
        }
        //List<AlbumAgencyEntity> albumAgencyEntities = albumAgencyService.selectByAlbumAgencyEntity(hotelAgencyEntityOne.getTravelId(), hotelAgencyEntityOne.getId(), 2);
        if (albumAgencyEntitieList != null && !albumAgencyEntitieList.isEmpty()) {
            List<String> photoUrls = new ArrayList<>();
            for (AlbumAgencyEntity albumAgencyEntity : albumAgencyEntitieList) {
                photoUrls.add(albumAgencyEntity.getPhotoUrl());
            }
            hotelAgencyEntityOne.setPhotoUrls(photoUrls);
            hotelAgencyEntityOne.setAlbumAgencyList(albumAgencyEntitieList);
        }
    }

    /**
     * 根据搜索引擎返回值封装酒店私有库实体类
     * @param resultMap
     * @return
     */
    public HotelAgencyEntity getHotelAgency(Map resultMap) {
        HotelAgencyEntity hotelAgencyEntityOne = new HotelAgencyEntity();
        //获取父对象，以便组装country信息
        Object oid = resultMap.get("id");
        Object otravelId = resultMap.get("travel_id");
        Object ohotelName = resultMap.get("hotel_name");
        Object odelta = resultMap.get("delta");
        Object ocountry = resultMap.get("country");
        Object ocity = resultMap.get("city");
        Object ocityId = resultMap.get("city_id");
        Object ostarLv = resultMap.get("star_lv");
        Object olon = resultMap.get("lon");
        Object olat = resultMap.get("lat");
        Object ocreateTime = resultMap.get("create_time");
        Object oupdateTime = resultMap.get("update_time");
        Object obriefintroduction = resultMap.get("briefintroduction");
        Object hoteId = resultMap.get("hotel_id");
        if (null != oid) {
            Long aLong = Long.valueOf(oid.toString());
            hotelAgencyEntityOne.setId(aLong);
        }
        if (null != otravelId) {
            Long ltravelId = Long.valueOf(otravelId.toString());
            hotelAgencyEntityOne.setTravelId(ltravelId);
        }
        if (null != ohotelName) {
            hotelAgencyEntityOne.setHotelName(ohotelName.toString());
        }
        if (null != odelta) {
            hotelAgencyEntityOne.setDelta(odelta.toString());
        }
        if (null != ocountry) {
            hotelAgencyEntityOne.setCountry(ocountry.toString());
        }
        if (null != ocity) {
            hotelAgencyEntityOne.setCity(ocity.toString());
        }
        if (null != ocityId) {
            hotelAgencyEntityOne.setCityId(ocityId.toString());
        }
        if (null != ostarLv) {
            hotelAgencyEntityOne.setStarLv(ostarLv.toString());
        }
        if (null != olon) {
            hotelAgencyEntityOne.setLon(olon.toString());
        }
        if (null != olat) {
            hotelAgencyEntityOne.setLat(olat.toString());
        }
        if (null != ocreateTime) {
            Date createTime = DateUtils.convertDateUTC(ocreateTime.toString());
            hotelAgencyEntityOne.setCreateTime(createTime);
        }
        if (null != oupdateTime) {
            Date updateTime = DateUtils.convertDateUTC(oupdateTime.toString());
            hotelAgencyEntityOne.setUpdateTime(updateTime);
        }
        if (null != obriefintroduction) {
            hotelAgencyEntityOne.setBriefintroduction(obriefintroduction.toString());
        }
        if (null != hoteId) {
            hotelAgencyEntityOne.setHotelId(hoteId.toString());
        }
        return hotelAgencyEntityOne;
    }


    /**
     * 根据搜索引擎返回值封装酒店公有库实体类
     * @param resultMap
     * @return
     */
    public HotelEntity getHotel(Map resultMap) {
        HotelEntity hotelEntity = new HotelEntity();
        //获取父对象，以便组装country信息
        Object ochinese_name = resultMap.get("hotel_chinese_name");
        logger.info("酒店中文名字为: " + ochinese_name);
        Object oenglish_name = resultMap.get("hotel_english_name");
        Object opic_url = resultMap.get("pic_url");
        Object ocity_name = resultMap.get("city_name");
        Object ocity_id = resultMap.get("city_id");
        Object ocountry_id = resultMap.get("country_id");
        Object ostar_lv = resultMap.get("star_lv");
        Object olon = resultMap.get("lon");
        Object olat = resultMap.get("lat");
        Object obriefintroduction = resultMap.get("briefintroduction");
        Object hotelId = resultMap.get("hotel_id");
        if (null != ochinese_name) {
            hotelEntity.setHotelChineseName(ochinese_name.toString());
        }
        if (null != oenglish_name) {
            hotelEntity.setHotelEnglishName(oenglish_name.toString());
        }
        //因为抓取的公用库酒店的照片信息有问题 暂时注释掉图片信息
       /* if (null != opic_url) {
            hotelEntity.setPicUrl(opic_url.toString());
        }*/
        if (null != ocity_name) {
            hotelEntity.setCityName(ocity_name.toString());
        }
        if (null != ocity_id) {
            hotelEntity.setCityId(ocity_id.toString());
        }
        if (null != ostar_lv) {
            hotelEntity.setStarLv(ostar_lv.toString());
        }
        //因酒店数据问题，根据国家id设置经纬度，如果为中国则经度为纬度，纬度为经度
        if (null != ocountry_id) {
            String scountry = ocountry_id.toString();
            if (scountry.equals("10100")){
                if (null != olat) {
                    hotelEntity.setLon(olat.toString());
                }
                if (null != olon) {
                    hotelEntity.setLat(olon.toString());
                }
            }else {
                if (null != olon) {
                    hotelEntity.setLon(olon.toString());
                }
                if (null != olat) {
                    hotelEntity.setLat(olat.toString());
                }
            }
        }
        if (null != obriefintroduction) {
            hotelEntity.setBriefintroduction(obriefintroduction.toString());
        }
        if (null != hotelId) {
            hotelEntity.setHotelId(hotelId.toString());
        }
        return hotelEntity;
    }


    public RegionEntity getRegion(Map resultMap) {
        RegionEntity regionEntity = new RegionEntity();
        //获取父对象，以便组装country信息
        Object oareaid = resultMap.get("areaid");
        Object oalias = resultMap.get("alias");
        if (null != oareaid) {
            regionEntity.setAreaid(oareaid.toString());
        }
        if (null != oalias) {
            regionEntity.setAlias(oalias.toString());
        }
        return regionEntity;
    }


    /**
     * 根据搜索引擎返回值封装相册私有库实体类
     * @param resultMap
     * @return
     */
    public AlbumAgencyEntity getAlbum(Map resultMap) {
        AlbumAgencyEntity albumAgencyEntity = new AlbumAgencyEntity();
        //获取父对象，以便组装country信息
        Object oid = resultMap.get("id");
        Object otravel_id = resultMap.get("travel_id");
        Object oobject_id = resultMap.get("object_id");
        Object otype = resultMap.get("type");
        Object ophoto_url = resultMap.get("photo_url");
        Object ocreate_time = resultMap.get("create_time");
        Object oupdate_time = resultMap.get("update_time");
        if (null != oid) {
            Long longid = Long.valueOf(oid.toString());
            albumAgencyEntity.setId(longid);
        }
        if (null != otravel_id) {
            Long longtravel_id = Long.valueOf(otravel_id.toString());
            albumAgencyEntity.setTravelId(longtravel_id);
        }
        if (null != oobject_id) {
            Long longobject_id = Long.valueOf(oobject_id.toString());
            albumAgencyEntity.setObjectId(longobject_id);
        }
        if (null != otype) {
            int itype = Integer.parseInt(otype.toString());
            albumAgencyEntity.setType(itype);
        }
        if (null != ophoto_url) {
            albumAgencyEntity.setPhotoUrl(ophoto_url.toString());
        }
        if (null != ocreate_time) {
            Date createTime = DateUtils.convertDateUTC(ocreate_time.toString());
            albumAgencyEntity.setCreateTime(createTime);
        }
        if (null != oupdate_time) {
            Date updateTime = DateUtils.convertDateUTC(oupdate_time.toString());
            albumAgencyEntity.setUpdateTime(updateTime);
        }
        return albumAgencyEntity;
    }


    /**
     * 检查是否有同名的酒店
     *
     * @param hotelAgencyEntity
     * @return
     */
    @Override
    public Boolean selectHotelAgencyBySameName(HotelAgencyEntity hotelAgencyEntity) {
        Boolean flag = true;
        String hotelName = hotelAgencyEntity.getHotelName();
        if (!StringUtils.isEmpty(hotelName)) {
            hotelName = hotelName.trim();
        }
        hotelAgencyEntity.setHotelName(hotelName);
        String city = hotelAgencyEntity.getCity();
        if (!StringUtils.isEmpty(city)) {
            city = city.trim();
        }
        hotelAgencyEntity.setCity(city);
        HotelAgencyEntity sameHotelAgency = hotelAgencyDao.selectHotelAgencyBySameName(hotelAgencyEntity);
        if (sameHotelAgency != null) {
            flag = false;
        }
        return flag;
    }

    /**
     * 查询酒店及相册
     *
     * @param id
     * @return
     */
    @Override
    public HotelAgencyEntity selectHotelAndAlbum(Long id) {
        return hotelAgencyDao.selectHotelAndAlbum(id);
    }
}
