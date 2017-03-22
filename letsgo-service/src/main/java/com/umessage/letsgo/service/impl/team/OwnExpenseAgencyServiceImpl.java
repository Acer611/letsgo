package com.umessage.letsgo.service.impl.team;

import com.elasticsearch.EsClientHelper;
import com.umessage.letsgo.dao.team.OwnExpenseAgencyDao;
import com.umessage.letsgo.domain.po.team.OwnExpenseAgencyEntity;
import com.umessage.letsgo.service.api.team.IAlbumAgencyService;
import com.umessage.letsgo.service.api.team.IOwnExpenseAgencyService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zengguoqing on 2016/9/7.
 */
@Service
public class OwnExpenseAgencyServiceImpl implements IOwnExpenseAgencyService{

    private static Logger logger = Logger.getLogger(OwnExpenseAgencyServiceImpl.class);

    @Resource
    private OwnExpenseAgencyDao ownExpenseAgencyDao;
    @Resource
    private IAlbumAgencyService albumAgencyService;

    @Override
    public int delete(Long id) {
        return ownExpenseAgencyDao.delete(id);
    }

    @Override
    public int insert(OwnExpenseAgencyEntity ownExpenseAgencyEntity) {
        ownExpenseAgencyEntity.setCreateTime(new Date());
        ownExpenseAgencyEntity.setUpdateTime(new Date());
        int insert = ownExpenseAgencyDao.insert(ownExpenseAgencyEntity);
        if (insert > 0){
            albumAgencyService.batchInsertOwnExpenseAgencyAlbum(ownExpenseAgencyEntity);
        }
        return insert;
    }

    @Override
    public OwnExpenseAgencyEntity select(Long id) {
        return ownExpenseAgencyDao.select(id);
    }

    @Override
    public List<OwnExpenseAgencyEntity> selectAll() {
        return ownExpenseAgencyDao.selectAll();
    }

    @Override
    public int update(OwnExpenseAgencyEntity ownExpenseAgencyEntity) {
        ownExpenseAgencyEntity.setUpdateTime(new Date());
        int update = ownExpenseAgencyDao.update(ownExpenseAgencyEntity);
        if (update > 0){
            albumAgencyService.batchUpdateOwnExpenseAgencyAlbum(ownExpenseAgencyEntity);
        }
        return update;
    }

    /**
     * 根据名称查询自费项目
     * @param ownExpenseAgencyEntity
     * @return
     */
    @Override
    public List<OwnExpenseAgencyEntity> selectOwnExpenseByName(OwnExpenseAgencyEntity ownExpenseAgencyEntity) {
        List<OwnExpenseAgencyEntity> ownExpenseAgencyEntities =   new ArrayList<>();
        //获取elasticsearch的client
        TransportClient esTransportClient =  EsClientHelper.getInstance().getClient();
        //获取搜索的结果,调用搜索引擎的Search API
        //从私有库中获取自费项目数据
        List<Map> resultMapList = getExpenses(ownExpenseAgencyEntity, esTransportClient);
        //组装自费项目数据
        ownExpenseAgencyEntities = this.getOwnExpenseAgencys(resultMapList,ownExpenseAgencyEntities,esTransportClient);

        //esTransportClient.close();
        return ownExpenseAgencyEntities;
    }

    /**
     * 从私有库中获取自费项目数据
     * @param ownExpenseAgencyEntity
     * @param esTransportClient
     * @return
     */
    private List<Map> getExpenses(OwnExpenseAgencyEntity ownExpenseAgencyEntity, TransportClient esTransportClient) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("item_name", ownExpenseAgencyEntity.getItemName()))
                .must(QueryBuilders.termQuery("travel_id",ownExpenseAgencyEntity.getTravelId()));
        SearchResponse searchResponse = esTransportClient.prepareSearch("letsgo").setTypes("expense").setQuery(queryBuilder).get();
        SearchHit[] searchHitsList = searchResponse.getHits().getHits();
        List<Map> resultMapList = new ArrayList<>();
        for (SearchHit searchHint:searchHitsList) {
            Map tempMap =   searchHint.getSource();
            resultMapList.add(tempMap);
        }
        return resultMapList;
    }

    /**
     * 组装自费项目数据
     * @param resultMapList
     * @param ownExpenseAgencyEntities
     * @param esTransportClient
     * @return
     */
    private List<OwnExpenseAgencyEntity> getOwnExpenseAgencys(List<Map> resultMapList, List<OwnExpenseAgencyEntity> ownExpenseAgencyEntities,TransportClient esTransportClient) {

        if(null!=resultMapList){
            for (Map map :resultMapList) {
                OwnExpenseAgencyEntity ownExpenseAgency = new OwnExpenseAgencyEntity();
                ownExpenseAgency.setTravelId(null != map.get("travel_id") ? Long.parseLong(map.get("travel_id").toString()): null);
                ownExpenseAgency.setCityId(null != map.get("city_id") ? map.get("city_id").toString(): null);
                ownExpenseAgency.setId(null != map.get("id") ? Long.parseLong(map.get("id").toString()): null );
                ownExpenseAgency.setBriefintroduction(null != map.get("briefintroduction")? map.get("briefintroduction").toString():null);
                ownExpenseAgency.setLon(null != map.get("briefintroduction")? map.get("briefintroduction").toString():null);
                ownExpenseAgency.setLat(null != map.get("lat")? map.get("lat").toString():null );
                ownExpenseAgency.setLon(null != map.get("lon")? map.get("lon").toString():null);
                ownExpenseAgency.setItemCity(null != map.get("item_city")? map.get("item_city").toString():null);
                ownExpenseAgency.setItemName(null != map.get("item_name")? map.get("item_name").toString():null);
                ownExpenseAgency.setLimitNumber(null != map.get("limit_number")? map.get("limit_number").toString():null);
                ownExpenseAgency.setReferencePrice(null != map.get("reference_price")? map.get("reference_price").toString():null);
                if(null!=map.get("id")){
                    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("object_id", map.get("id").toString()))
                            .must(QueryBuilders.termQuery("type","3"))
                            .mustNot(QueryBuilders.termQuery("status","deleted"));
                    SearchResponse searchResponse = esTransportClient.prepareSearch("letsgo").setTypes("album").setQuery(queryBuilder).get();
                    SearchHit[] searchHits = searchResponse.getHits().getHits();
                    List<String> imagesList = new ArrayList<>();
                    for (SearchHit searchHint:searchHits) {
                        Map tempMap =   searchHint.getSource();
                        imagesList.add(null!=tempMap.get("photo_url")?tempMap.get("photo_url").toString():null);
                    }
                    ownExpenseAgency.setPhotoUrls(imagesList);
                }
                ownExpenseAgencyEntities.add(ownExpenseAgency);
            }
        }

        return ownExpenseAgencyEntities;
    }

    /**
     * 检查是否有同名的自费项目
     * @param ownExpenseAgencyEntity
     * @return
     */
    @Override
    public Boolean selectOwnExpenseBySameName(OwnExpenseAgencyEntity ownExpenseAgencyEntity) {
        Boolean flag = true;
        String itemName = ownExpenseAgencyEntity.getItemName();
        if (!StringUtils.isEmpty(itemName)){
            itemName = itemName.trim();
        }
        ownExpenseAgencyEntity.setItemName(itemName);
        String itemCity = ownExpenseAgencyEntity.getItemCity();
        if (!StringUtils.isEmpty(itemCity)){
            itemCity = itemCity.trim();
        }
        ownExpenseAgencyEntity.setItemCity(itemCity);
        OwnExpenseAgencyEntity sameOwnExpenseAgencyEntity = ownExpenseAgencyDao.selectOwnExpenseBySameName(ownExpenseAgencyEntity);
        if (sameOwnExpenseAgencyEntity != null){
            flag = false;
        }
        return flag;
    }

    @Override
    public OwnExpenseAgencyEntity selectOwnExpenseAndAlbum(Long id) {
        return ownExpenseAgencyDao.selectOwnExpenseAndAlbum(id);
    }

}
