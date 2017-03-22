package com.umessage.letsgo.service.impl.team;

import com.elasticsearch.ESTransportClient;
import com.elasticsearch.EsClientHelper;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.dao.team.ShoppingAgencyDao;
import com.umessage.letsgo.domain.po.team.ShoppingAgencyEntity;
import com.umessage.letsgo.service.api.team.IShoppingAgencyService;
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
public class ShoppingAgencyServiceImpl implements IShoppingAgencyService{

    private static Logger logger = Logger.getLogger(ShoppingAgencyServiceImpl.class);

    @Resource
    private ShoppingAgencyDao shoppingAgencyDao;

    @Override
    public int delete(Long id) {
        return shoppingAgencyDao.delete(id);
    }

    @Override
    public int insert(ShoppingAgencyEntity shoppingAgencyEntity) {
        shoppingAgencyEntity.setCreateTime(new Date());
        shoppingAgencyEntity.setUpdateTime(new Date());
        return shoppingAgencyDao.insert(shoppingAgencyEntity);
    }

    @Override
    public ShoppingAgencyEntity select(Long id) {
        return shoppingAgencyDao.select(id);
    }

    @Override
    public List<ShoppingAgencyEntity> selectAll() {
        return shoppingAgencyDao.selectAll();
    }

    @Override
    public int update(ShoppingAgencyEntity shoppingAgencyEntity) {
        shoppingAgencyEntity.setUpdateTime(new Date());
        return shoppingAgencyDao.update(shoppingAgencyEntity);
    }

    /**
     * 根据名称查询私有库购物场所
     * @param shoppingAgencyEntity
     * @return
     */
    @Override
    public List<ShoppingAgencyEntity> selectShoppingAgencyByName(ShoppingAgencyEntity shoppingAgencyEntity) {
        String shoppingName = shoppingAgencyEntity.getShoppingName();
        if (!StringUtils.isEmpty(shoppingName)){
            shoppingName = shoppingName.trim();
        }
        shoppingAgencyEntity.setShoppingName(shoppingName);
        //List<ShoppingAgencyEntity> shoppingAgencyEntities = shoppingAgencyDao.selectShoppingAgencyByName(shoppingAgencyEntity);

        List<ShoppingAgencyEntity> shoppingAgencyEntityList = new ArrayList<>();
        //获取elasticsearch的client
        //TransportClient esTransportClient = ESTransportClient.getClient();
        TransportClient esTransportClient =  EsClientHelper.getInstance().getClient();
        BoolQueryBuilder queryBuilderHotel = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("shopping_name", shoppingName))
        .must(QueryBuilders.termQuery("travel_id",shoppingAgencyEntity.getTravelId()));
        SearchResponse searchResponseShopping = esTransportClient.prepareSearch("letsgo").setTypes("shopping").setQuery(queryBuilderHotel).get();
        //处理搜索的结果集
        SearchHit[] searchHitsShopping = searchResponseShopping.getHits().getHits();
        for (SearchHit searchHint:searchHitsShopping) {
            Map resultMap =   searchHint.getSource();
            ShoppingAgencyEntity shopping = this.getShopping(resultMap);
            shoppingAgencyEntityList.add(shopping);
        }

        //esTransportClient.close();
        return shoppingAgencyEntityList;
    }


    public ShoppingAgencyEntity getShopping(Map resultMap){
        ShoppingAgencyEntity shoppingAgencyEntity = new ShoppingAgencyEntity();
        //获取父对象，以便组装country信息
        Object oid = resultMap.get("id");
        Object otravelId = resultMap.get("travel_id");
        Object oshopping_name = resultMap.get("shopping_name");
        Object oshopping_city = resultMap.get("shopping_city");
        Object ocity_id = resultMap.get("city_id");
        Object osell_products = resultMap.get("sell_products");
        Object oresidence_time = resultMap.get("residence_time");
        Object olon = resultMap.get("lon");
        Object olat = resultMap.get("lat");
        Object ocreateTime = resultMap.get("create_time");
        Object oupdateTime = resultMap.get("update_time");
        if(null!=oid){
            Long aLong = Long.valueOf(oid.toString());
            shoppingAgencyEntity.setId(aLong);
        }
        if(null!=otravelId){
            Long ltravelId = Long.valueOf(otravelId.toString());
            shoppingAgencyEntity.setTravelId(ltravelId);
        }
        if(null!=oshopping_name){
            shoppingAgencyEntity.setShoppingName(oshopping_name.toString());
        }
        if(null!=oshopping_city){
            shoppingAgencyEntity.setShoppingCity(oshopping_city.toString());
        }
        if(null!=ocity_id){
            shoppingAgencyEntity.setCityId(ocity_id.toString());
        }
        if(null!=osell_products){
            shoppingAgencyEntity.setSellProducts(osell_products.toString());
        }
        if(null!=oresidence_time){
            shoppingAgencyEntity.setResidenceTime(oresidence_time.toString());
        }
        if(null!=olon){
            shoppingAgencyEntity.setLon(olon.toString());
        }
        if(null!=olat){
            shoppingAgencyEntity.setLat(olat.toString());
        }
        if(null!=ocreateTime){
            Date createTime = DateUtils.convertDateUTC(ocreateTime.toString());
            shoppingAgencyEntity.setCreateTime(createTime);
        }
        if(null!=oupdateTime){
            Date updateTime = DateUtils.convertDateUTC(oupdateTime.toString());
            shoppingAgencyEntity.setUpdateTime(updateTime);
        }
        return shoppingAgencyEntity;
    }


    /**
     * 检查是否有同名的购物场所
     * @param shoppingAgencyEntity
     * @return
     */
    @Override
    public Boolean selectHotelAgencyBySameName(ShoppingAgencyEntity shoppingAgencyEntity) {
        Boolean flag = true;
        String shoppingName = shoppingAgencyEntity.getShoppingName();
        if (!StringUtils.isEmpty(shoppingName)){
            shoppingName = shoppingName.trim();
        }
        shoppingAgencyEntity.setShoppingName(shoppingName);
        String shoppingCity = shoppingAgencyEntity.getShoppingCity();
        if (!StringUtils.isEmpty(shoppingCity)){
            shoppingCity = shoppingCity.trim();
        }
        shoppingAgencyEntity.setShoppingCity(shoppingCity);
        ShoppingAgencyEntity sameShoppingAgencyEntity = shoppingAgencyDao.selectShoppingAgencyBySameName(shoppingAgencyEntity);
        if (sameShoppingAgencyEntity != null){
            flag = false;
        }
        return flag;
    }
}
