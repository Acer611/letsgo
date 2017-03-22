package com.umessage.letsgo.service.impl.journey;

import com.elasticsearch.EsClientHelper;
import com.umessage.letsgo.dao.journey.AlbumScheduleDao;
import com.umessage.letsgo.dao.journey.ScheduleScenicDao;
import com.umessage.letsgo.dao.team.AlbumAgencyDao;
import com.umessage.letsgo.dao.team.ScenicAgencyDao;
import com.umessage.letsgo.domain.po.journey.AlbumScheduleEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailScenicEntity;
import com.umessage.letsgo.domain.po.system.ScenicEntity;
import com.umessage.letsgo.domain.po.team.AlbumAgencyEntity;
import com.umessage.letsgo.domain.po.team.ScenicAgencyEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScenicResponse;
import com.umessage.letsgo.service.api.journey.IScheduleDetailScenicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 旅行社端每日行程景点信息管理接口
 * Created by gaofei on 2017/2/15.
 */
@Service
public class ScheduleDetailScenicServiceImpl implements IScheduleDetailScenicService {

    private Logger logger = LogManager.getLogger(ScheduleDetailScenicServiceImpl.class.getName());

    @Resource
    private  ScenicAgencyDao scenicAgencyDao;
    @Resource
    private AlbumAgencyDao albumAgencyDao;
    @Resource
    private ScheduleScenicDao scheduleScenicDao;
    @Resource
    private AlbumScheduleDao albumScheduleDao;
    /**
     * 保存每日景点的信息到旅行社私有库
     * @param travelEntity
     * @param scenicEntity
     * @return
     */
    @Override
    public ScenicResponse saveScheduleDetailScenicToTravel(TravelAgencyEntity travelEntity, ScenicAgencyEntity scenicEntity) {
        //根据是否存在id 做插入或者修改的操作
        if( scenicEntity.getId()!=null){
            logger.info("当前操作为修改操作...");
            logger.info("修改景点私有库...");
            //修改景点私有库信息
            scenicEntity.setUpdateTime(new Date());
            scenicEntity.setCreateTime(scenicEntity.getCreateTime());
            scenicEntity.setTravelId(travelEntity.getId());
            scenicAgencyDao.update(scenicEntity);
            AlbumAgencyEntity albumAgencyRequest =  new AlbumAgencyEntity();
            albumAgencyRequest.setTravelId(travelEntity.getId());
            albumAgencyRequest.setObjectId(scenicEntity.getId());
            albumAgencyRequest.setType(1);
            logger.info("删除景点私有库对应的照片信息...");
           // albumAgencyDao.deleteByAlbumAgencyEntity(albumAgencyRequest);
            albumAgencyDao.deleteAlbum(albumAgencyRequest);
        }else{
            //检查景点私有库是否存在此景点（根据景点名称，旅行社id,景点城市查找）
            logger.info("检查景点私有库是否存在此景点...");
            ScenicAgencyEntity scenicAgencyEntity = scenicAgencyDao.selectScheduleDetailScenic(scenicEntity);
            //若不存在，1.此景点存世景点私有库, 2.照片存入旅行社私有照片库
            if(null==scenicAgencyEntity){
                scenicEntity.setTravelId(travelEntity.getId());
                //此景点存入景点私有库
                logger.info("把此景点存入景点私有库...");
                scenicEntity.setCreateTime(new Date());
                scenicEntity.setUpdateTime(new Date());
                scenicAgencyDao.insert(scenicEntity);
            }
        }

        List<String> imageList = scenicEntity.getImageList();
        //照片存入旅行社私有照片库
        logger.info("把照片批量存入旅行社私有照片库...");
        List<AlbumAgencyEntity> albumAgencyEntities = new ArrayList<>();
        if (imageList != null){
            for (String imageUrl:imageList) {
                AlbumAgencyEntity albumAgencyEntity = new AlbumAgencyEntity();
                albumAgencyEntity.setTravelId(travelEntity.getId());
                albumAgencyEntity.setObjectId(scenicEntity.getId());
                albumAgencyEntity.setType(1);
                albumAgencyEntity.setCreateTime(new Date());
                albumAgencyEntity.setUpdateTime(new Date());
                albumAgencyEntity.setPhotoUrl(imageUrl);
                albumAgencyEntities.add(albumAgencyEntity);

            }
        }
        if(!albumAgencyEntities.isEmpty()){
            logger.info("执行插入照片的动作...");
            albumAgencyDao.batchInsert(albumAgencyEntities);
        }

        ScenicResponse response = new ScenicResponse();
        response.setScenicAgencyEntity(scenicEntity);
        return response;
    }

    /**
     * 保存景点信息到每日行程关联关系表
     * @param travelEntity 旅行社信息
     * @param scheduleDetailScenic 景点信息
     * @return
     */
    @Override
    public CommonResponse saveScheduleDetailScenicToTable(TravelAgencyEntity travelEntity, ScheduleDetailScenicEntity scheduleDetailScenic) {
        if(null!=scheduleDetailScenic){
            //把景点录入 1.景点每日行程关联关系表 2.照片存入每日行程照片关联关系表
            logger.info("把此景点存入每日行程景点关联关系表...");
            scheduleDetailScenic.setCreateTime(new Date());
            scheduleDetailScenic.setUpdateTime(new Date());
            scheduleScenicDao.insert(scheduleDetailScenic);

            logger.info("把照片批量存入景点 图片关联关系表...");
            List<AlbumScheduleEntity> albumScheduleEntities = new ArrayList<>();
            List<String> imageList = scheduleDetailScenic.getImageList();
            if(null!=imageList){
                for (String imageUrl:imageList) {
                    AlbumScheduleEntity albumScheduleEntity =  new AlbumScheduleEntity();
                    albumScheduleEntity.setScheduleDetailId(scheduleDetailScenic.getScheduleDetailId());
                    albumScheduleEntity.setObjectId(scheduleDetailScenic.getId());
                    albumScheduleEntity.setType(1);
                    albumScheduleEntity.setCreateTime(new Date());
                    albumScheduleEntity.setUpdateTime(new Date());
                    albumScheduleEntity.setPhotoUrl(imageUrl);
                    albumScheduleEntities.add(albumScheduleEntity);
                }

            }
            if(albumScheduleEntities != null && !albumScheduleEntities.isEmpty()){
                logger.info("执行插入照片的动作...");
                albumScheduleDao.batchInsert(albumScheduleEntities);
            }
        }
        CommonResponse response = new CommonResponse();
        return response;
    }

    /**
     * 根据每日行程景点关联关系表的ID 删除关联数据
     * @param id
     * @return
     */
    @Override
    public CommonResponse removeScheduleDetailScenic(Long id) {
        scheduleScenicDao.deleteScheduleDetailScenicById(id);
        CommonResponse response = new CommonResponse();
        return response;
    }

    /**
     * 修改每日行程景点关联数据表数据
     * @param scheduleDetailScenic
     * @return
     */
    @Override
    public CommonResponse modifyScheduleDetailScenic( TravelAgencyEntity travelEntity,ScheduleDetailScenicEntity scheduleDetailScenic) {

        logger.info("修改景点信息(关联关系表)...");
        //修改景点信息(关联关系表)
        scheduleDetailScenic.setUpdateTime(new Date());
        scheduleScenicDao.updateScheduleDetailScenic(scheduleDetailScenic);

        //修改照片信息（关联关系表）
        List<AlbumScheduleEntity> albumScheduleEntities = new ArrayList<>();
        List<String> imageList = scheduleDetailScenic.getImageList();
        if(null!=imageList){
            for (String imageUrl:imageList) {
                AlbumScheduleEntity albumScheduleEntity =  new AlbumScheduleEntity();
                albumScheduleEntity.setScheduleDetailId(scheduleDetailScenic.getScheduleDetailId());
                albumScheduleEntity.setObjectId(scheduleDetailScenic.getId());
                albumScheduleEntity.setType(1);
                albumScheduleEntity.setCreateTime(new Date());
                albumScheduleEntity.setUpdateTime(new Date());
                albumScheduleEntity.setPhotoUrl(imageUrl);
                albumScheduleEntities.add(albumScheduleEntity);
            }
            //插入前先删除数据
            AlbumScheduleEntity albumScheduleRequest = new AlbumScheduleEntity();
            albumScheduleRequest.setScheduleDetailId(scheduleDetailScenic.getScheduleDetailId());
            albumScheduleRequest.setObjectId(scheduleDetailScenic.getId());
            albumScheduleRequest.setType(1);
            logger.info("照片信息先删除...");
            albumScheduleDao.deleteByContents(albumScheduleRequest);

            logger.info("执行批量插入照片的动作...");
            //执行批量插入
            if(albumScheduleEntities != null && !albumScheduleEntities.isEmpty()){
                albumScheduleDao.batchInsert(albumScheduleEntities);
            }

        }

       CommonResponse response = new CommonResponse();
        return response;
    }

    /**
     * 模糊匹配景点名称，查询景点列表
     * @param name
     * @param cities cities 城市的id 多个以 “，”分隔
     */
    @Override
    public ScenicResponse searchScenic(String name, String cities,TravelAgencyEntity travelEntity) {

        logger.info("进入景点搜索引擎service层");
        ScenicResponse scenicResponse =  new ScenicResponse();
        List<ScenicEntity> scenicEntitys = new ArrayList<>();
        //以下为使用搜索引擎Elasticsearch 获取城市的代码逻辑
        List<Map> scenicResultMapList = new ArrayList<>();
        try {
            //获取elasticsearch的client
            TransportClient esTransportClient =  EsClientHelper.getInstance().getClient();
            //获取搜索的结果,调用搜索引擎的Search API
            //从私有库中获取数据  scenic_en_name
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("scenic_name", name))
                    .must(QueryBuilders.termQuery("travel_id",travelEntity.getId())).should(QueryBuilders.matchQuery("scenic_en_name", name));
            SearchResponse searchResponse = esTransportClient.prepareSearch("letsgo").setTypes("scenic").setQuery(queryBuilder).get();
            SearchHit[] searchHitsList = searchResponse.getHits().getHits();

            for (SearchHit searchHint:searchHitsList) {
                Map tempMap =   searchHint.getSource();
                scenicResultMapList.add(tempMap);
            }
            logger.info("景点私有库搜索完成......");
            //从共有索引库获取数据
            BoolQueryBuilder queryBuilderPub = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", name))
                    .should(QueryBuilders.matchQuery("en_name", name))
                    .should(QueryBuilders.matchQuery("spotpinyinname", name));
            SearchResponse searchResponsePub = esTransportClient.prepareSearch("letsgo").setTypes("spot").setQuery(queryBuilderPub).get();
            SearchHit[] searchHits = searchResponsePub.getHits().getHits();

            List<Map> spotResultMapList = new ArrayList<>();
            for (SearchHit searchHint:searchHits) {
                Map tempMap =   searchHint.getSource();
                spotResultMapList.add(tempMap);
            }
            logger.info("景点公有库搜索完成......");
            //组装数据
            scenicEntitys = this.getScenicitys(scenicEntitys,scenicResultMapList,spotResultMapList,esTransportClient);

           // esTransportClient.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        scenicResponse.setScenicInfoList(scenicEntitys);

        return scenicResponse;
    }

    //组装数据
    private List<ScenicEntity> getScenicitys(List<ScenicEntity> scenicEntitys, List<Map> scenicResultMapList, List<Map> spotResultMapList,TransportClient esTransportClient) {
        logger.info("进入组装景点数据的方法......");
        List<Long> mefengIds = new ArrayList<>();
        //组装私有库的数据
        logger.info("进入组装景点私有库的数据的方法......");
        scenicEntitys = getAlbumSceniceMap(scenicEntitys, scenicResultMapList, esTransportClient, mefengIds);

        logger.info("进入组装景点公有库数据的方法......");
        scenicEntitys =  getPublicScenicMap(scenicEntitys, spotResultMapList, esTransportClient, mefengIds);

        return scenicEntitys;
    }

    /**
     * 组装公有库数据
     * @param scenicEntitys
     * @param spotResultMapList
     * @param esTransportClient
     * @param mefengIds
     * @return
     */
    private List<ScenicEntity>   getPublicScenicMap(List<ScenicEntity> scenicEntitys, List<Map> spotResultMapList, TransportClient esTransportClient, List<Long> mefengIds) {
        for (Map spotMap:spotResultMapList) {
            ScenicEntity scenicEntity = new ScenicEntity();
            Long mafengId = null != spotMap.get("mafeng_id")? Long.parseLong(spotMap.get("mafeng_id").toString()):null;
            if(mefengIds.contains(mafengId)){
                continue;
            }
            scenicEntity.setMafengId(mafengId);
            scenicEntity.setDestionationId(null != spotMap.get("destionation_id")? spotMap.get("destionation_id").toString():null);
            scenicEntity.setBrief(null != spotMap.get("brief")? spotMap.get("brief").toString():null);
            scenicEntity.setEnName(null != spotMap.get("en_name")? spotMap.get("en_name").toString():null);
            scenicEntity.setName(null != spotMap.get("name")? spotMap.get("name").toString():null);
            scenicEntity.setLat(null != spotMap.get("lat")? spotMap.get("lat").toString():null);
            scenicEntity.setLng(null != spotMap.get("lng")? spotMap.get("lng").toString():null);

            if(null==spotMap.get("destionation_id")) continue;

            GetResponse response = esTransportClient.prepareGet("letsgo","city",spotMap.get("destionation_id").toString()).get();
            Map pidMap =  response.getSource();
            scenicEntity.setCity(null != pidMap.get("alias")? pidMap.get("alias").toString():null);
            scenicEntitys.add(scenicEntity);
        }
        return scenicEntitys;
    }

    /**
     * 组装私有库的数据
     * @param scenicEntitys
     * @param scenicResultMapList
     * @param esTransportClient
     * @param mefengIds
     * @return
     */
    private List<ScenicEntity> getAlbumSceniceMap(List<ScenicEntity> scenicEntitys, List<Map> scenicResultMapList, TransportClient esTransportClient, List<Long> mefengIds) {
        for (Map secnicMap:scenicResultMapList) {
            ScenicEntity scenicEntity = new ScenicEntity();
            scenicEntity.setDestionationId(null != secnicMap.get("city_id")? secnicMap.get("city_id").toString():null);
            scenicEntity.setBrief(null != secnicMap.get("briefintroduction")? secnicMap.get("briefintroduction").toString():null);
            scenicEntity.setEnName(null != secnicMap.get("scenic_en_name")? secnicMap.get("scenic_en_name").toString():null);
            scenicEntity.setName(null != secnicMap.get("scenic_name")? secnicMap.get("scenic_name").toString():null);
            scenicEntity.setLat(null != secnicMap.get("lat")? secnicMap.get("lat").toString():null);
            scenicEntity.setLng(null != secnicMap.get("lon")? secnicMap.get("lon").toString():null);
            scenicEntity.setId(null != secnicMap.get("id")? Long.parseLong(secnicMap.get("id").toString()):null);
            scenicEntity.setCity(null != secnicMap.get("scenic_city")? secnicMap.get("scenic_city").toString():null);
            Long mafengId = null != secnicMap.get("mafeng_id")? Long.parseLong(secnicMap.get("mafeng_id").toString()):null;
            if(null != mafengId){
                scenicEntity.setMafengId(mafengId);
                mefengIds.add(mafengId);
            }
            //关联景点照片
            if(null == secnicMap.get("id")){
                continue;
            }
            //获取景点的图片信息
            scenicEntity = getImages(esTransportClient, secnicMap, scenicEntity);
            scenicEntitys.add(scenicEntity);
        }
        return scenicEntitys;
    }

    //获取私有库照片信息
    private ScenicEntity getImages(TransportClient esTransportClient, Map secnicMap, ScenicEntity scenicEntity) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("object_id", secnicMap.get("id").toString()))
                .must(QueryBuilders.termQuery("type","1"))
                .mustNot(QueryBuilders.termQuery("status","deleted"));
        SearchResponse searchResponse = esTransportClient.prepareSearch("letsgo").setTypes("album").setQuery(queryBuilder).get();
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        List<String> imagesList = new ArrayList<>();
        for (SearchHit searchHint:searchHits) {
            Map tempMap =   searchHint.getSource();
            imagesList.add(null!=tempMap.get("photo_url")?tempMap.get("photo_url").toString():null);
        }
        scenicEntity.setImageList(imagesList);
        return  scenicEntity;
    }

    /**
     * 根据景点的id获取景点数据
     * @param scenicId
     * @return
     */
    @Override
    public ScheduleDetailScenicEntity getScenicByScenicId(Long scenicId) {
        ScheduleDetailScenicEntity scheduleDetailScenicEntity = scheduleScenicDao.selectScenicInfoById(scenicId);
        if(scheduleDetailScenicEntity.getAlbumScheduleEntities()==null){
            List<AlbumScheduleEntity> albumScheduleEntityList =  new ArrayList<>();
            scheduleDetailScenicEntity.setAlbumScheduleEntities(albumScheduleEntityList);
        }
        return scheduleDetailScenicEntity;
    }

    @Override
    public ScenicResponse deleteScheduleDetailScenic(Long travelId,Long id) {
        logger.info("删除私有库照片的信息...");
        //删除照片信息
        AlbumAgencyEntity albumAgencyRequest =  new AlbumAgencyEntity();
        albumAgencyRequest.setTravelId(travelId);
        albumAgencyRequest.setObjectId(id);
        albumAgencyRequest.setType(1);
        //albumAgencyDao.deleteByAlbumAgencyEntity(albumAgencyRequest);
        albumAgencyDao.deleteAlbum(albumAgencyRequest);
        logger.info("删除私有景点信息...");
        scenicAgencyDao.delete(id);

        return new ScenicResponse();
    }

    @Override
    public void deleteScenicByScheduldId(Long id) {
        ScheduleDetailScenicEntity scheduleDetailScenicEntity =  new ScheduleDetailScenicEntity();
        scheduleDetailScenicEntity.setScheduleDetailId(id);
        scheduleScenicDao.deleteSchedule(scheduleDetailScenicEntity);
    }

    //私有库获取景点数据
    private List<ScenicAgencyEntity> getScenicFormTravel(String name, String cities,TravelAgencyEntity travelEntity) {
        ScenicAgencyEntity scenicAgencyEntity = new ScenicAgencyEntity();
        scenicAgencyEntity.setTravelId(travelEntity.getId());
        scenicAgencyEntity.setScenicName(name);
        scenicAgencyEntity.setCities(strToList(cities));

        List<ScenicAgencyEntity> scenicAgencyEntityList = scenicAgencyDao.selectLike(scenicAgencyEntity);
        if (CollectionUtils.isEmpty(scenicAgencyEntityList)) {
            return new ArrayList<>();
        }
        return scenicAgencyEntityList;
    }

    private List<String> strToList(String str) {
        List<String> list = new ArrayList<>();
        String[] array = str.split(",");
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }
}
