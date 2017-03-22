package com.umessage.letsgo.service.impl.team;

import com.umessage.letsgo.dao.team.AlbumAgencyDao;
import com.umessage.letsgo.domain.po.team.AlbumAgencyEntity;
import com.umessage.letsgo.domain.po.team.HotelAgencyEntity;
import com.umessage.letsgo.domain.po.team.OwnExpenseAgencyEntity;
import com.umessage.letsgo.service.api.team.IAlbumAgencyService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/7.
 */
@Service
public class AlbumAgencyServiceImpl implements IAlbumAgencyService{

    private static Logger logger = Logger.getLogger(AlbumAgencyServiceImpl.class);

    @Resource
    private AlbumAgencyDao albumAgencyDao;

    @Override
    public int delete(Long id) {
        return albumAgencyDao.delete(id);
    }

    @Override
    public int insert(AlbumAgencyEntity albumAgencyEntity) {
        albumAgencyEntity.setCreateTime(new Date());
        return albumAgencyDao.insert(albumAgencyEntity);
    }

    @Override
    public AlbumAgencyEntity select(Long id) {
        return albumAgencyDao.select(id);
    }

    @Override
    public List<AlbumAgencyEntity> selectAll() {
        return albumAgencyDao.selectAll();
    }

    @Override
    public int update(AlbumAgencyEntity albumAgencyEntity) {
        albumAgencyEntity.setUpdateTime(new Date());
        return albumAgencyDao.update(albumAgencyEntity);
    }

    /**
     * 批量插入
     * @param albumAgencyEntities
     */
    @Override
    public void batchInsert(List<AlbumAgencyEntity> albumAgencyEntities) {
        albumAgencyDao.batchInsert(albumAgencyEntities);
    }

    /**
     * 批量插入旅行社私有酒店的照片
     * @param hotelAgencyEntity
     */
    @Override
    public void batchInsertHotelAgencyAlbum(HotelAgencyEntity hotelAgencyEntity) {
        List<String> photoUrls = hotelAgencyEntity.getPhotoUrls();
        if (photoUrls != null && photoUrls.size() > 0){
            List<AlbumAgencyEntity> albumAgencyEntitys = new ArrayList<>();
            for (String photoUrl:photoUrls) {
                if (!StringUtils.isEmpty(photoUrl)){
                    AlbumAgencyEntity albumAgency = new AlbumAgencyEntity();
                    albumAgency.setTravelId(hotelAgencyEntity.getTravelId());
                    albumAgency.setObjectId(hotelAgencyEntity.getId());
                    albumAgency.setType(2);
                    albumAgency.setPhotoUrl(photoUrl);
                    albumAgency.setCreateTime(new Date());
                    albumAgency.setUpdateTime(new Date());
                    albumAgencyEntitys.add(albumAgency);
                }
            }
            this.batchInsert(albumAgencyEntitys);
        }
    }

    /**
     *根据旅行社ID object id 和type 删除照片私有库数据
     * @param albumAgencyRequest
     */
    @Override
    public void deleteByAlbumAgencyEntity(AlbumAgencyEntity albumAgencyRequest) {
        //albumAgencyDao.deleteByAlbumAgencyEntity(albumAgencyRequest);
        albumAgencyDao.deleteAlbum(albumAgencyRequest);
    }

    /**
     *批量修改旅行社私有酒店相关的照片
     * @param hotelAgencyEntity
     */
    @Override
    public void batchUpdateHotelAgencyAlbum(HotelAgencyEntity hotelAgencyEntity) {
        //删除之前的照片
        AlbumAgencyEntity albumAgencyEntity = new AlbumAgencyEntity();
        albumAgencyEntity.setTravelId(hotelAgencyEntity.getTravelId());
        albumAgencyEntity.setObjectId(hotelAgencyEntity.getId());
        albumAgencyEntity.setType(2);
        this.deleteByAlbumAgencyEntity(albumAgencyEntity);
        this.batchInsertHotelAgencyAlbum(hotelAgencyEntity);
    }

    /**
     * 批量插入旅行社私有自费项目的照片
     * @param ownExpenseAgencyEntity
     */
    @Override
    public void batchInsertOwnExpenseAgencyAlbum(OwnExpenseAgencyEntity ownExpenseAgencyEntity) {
        List<String> photoUrls = ownExpenseAgencyEntity.getPhotoUrls();
        if (photoUrls != null  && photoUrls.size() > 0){
            List<AlbumAgencyEntity> albumAgencyEntitys = new ArrayList<>();
            for (String photoUrl:photoUrls) {
                if (!StringUtils.isEmpty(photoUrl)){
                    AlbumAgencyEntity albumAgency = new AlbumAgencyEntity();
                    albumAgency.setTravelId(ownExpenseAgencyEntity.getTravelId());
                    albumAgency.setObjectId(ownExpenseAgencyEntity.getId());
                    albumAgency.setType(3);
                    albumAgency.setPhotoUrl(photoUrl);
                    albumAgency.setCreateTime(new Date());
                    albumAgency.setCreateTime(new Date());
                    albumAgencyEntitys.add(albumAgency);
                }
            }
            this.batchInsert(albumAgencyEntitys);
        }
    }


    /**
     *批量修改旅行社私有自费项目相关的照片
     * @param ownExpenseAgencyEntity
     */
    @Override
    public void batchUpdateOwnExpenseAgencyAlbum(OwnExpenseAgencyEntity ownExpenseAgencyEntity) {
        //删除之前的照片
        AlbumAgencyEntity albumAgencyEntity = new AlbumAgencyEntity();
        albumAgencyEntity.setTravelId(ownExpenseAgencyEntity.getTravelId());
        albumAgencyEntity.setObjectId(ownExpenseAgencyEntity.getId());
        albumAgencyEntity.setType(3);
        this.deleteByAlbumAgencyEntity(albumAgencyEntity);
        this.batchInsertOwnExpenseAgencyAlbum(ownExpenseAgencyEntity);
    }

    @Override
    public List<AlbumAgencyEntity> selectByAlbumAgencyEntity(Long travelId, Long objectId, Integer type) {
        AlbumAgencyEntity albumAgencyEntity = new AlbumAgencyEntity();
        albumAgencyEntity.setTravelId(travelId);
        albumAgencyEntity.setObjectId(objectId);
        albumAgencyEntity.setType(type);
        return albumAgencyDao.selectByAlbumAgencyEntity(albumAgencyEntity);
    }
}
