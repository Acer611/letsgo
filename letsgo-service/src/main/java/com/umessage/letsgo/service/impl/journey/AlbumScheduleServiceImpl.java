package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.dao.journey.AlbumScheduleDao;
import com.umessage.letsgo.domain.po.journey.AlbumScheduleEntity;
import com.umessage.letsgo.domain.po.journey.HotelScheduleEntity;
import com.umessage.letsgo.domain.po.journey.OwnExpenseScheduleEntity;
import com.umessage.letsgo.service.api.journey.IAlbumScheduleService;
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
public class AlbumScheduleServiceImpl implements IAlbumScheduleService{

    private static Logger logger = Logger.getLogger(AlbumScheduleServiceImpl.class);

    @Resource
    private AlbumScheduleDao albumScheduleDao;

    @Override
    public int delete(Long id) {
        return albumScheduleDao.delete(id);
    }

    @Override
    public int insert(AlbumScheduleEntity albumScheduleEntity) {
        return albumScheduleDao.insert(albumScheduleEntity);
    }

    @Override
    public AlbumScheduleEntity select(Long id) {
        return albumScheduleDao.select(id);
    }

    @Override
    public List<AlbumScheduleEntity> selectAll() {
        return albumScheduleDao.selectAll();
    }

    @Override
    public int update(AlbumScheduleEntity albumScheduleEntity) {
        return albumScheduleDao.update(albumScheduleEntity);
    }

    @Override
    public void batchInsert(List<AlbumScheduleEntity> albumScheduleEntities) {
        albumScheduleDao.batchInsert(albumScheduleEntities);
    }

    @Override
    public void batchInsertHotelScheduleAlbum(HotelScheduleEntity hotelScheduleEntity) {
        List<String> photoUrls = hotelScheduleEntity.getPhotoUrls();
        if (photoUrls != null  && photoUrls.size() > 0){
            List<AlbumScheduleEntity> albumScheduleEntitys = new ArrayList<>();
            for (String photoUrl:photoUrls) {
                if (!StringUtils.isEmpty(photoUrl)){
                    AlbumScheduleEntity albumScheduleEntity = new AlbumScheduleEntity();
                    albumScheduleEntity.setScheduleDetailId(hotelScheduleEntity.getScheduleDetailId());
                    albumScheduleEntity.setObjectId(hotelScheduleEntity.getId());
                    albumScheduleEntity.setType(2);
                    albumScheduleEntity.setPhotoUrl(photoUrl);
                    albumScheduleEntity.setCreateTime(new Date());
                    albumScheduleEntitys.add(albumScheduleEntity);
                }
            }
            this.batchInsert(albumScheduleEntitys);
        }
    }


    /**
     *批量修改每日行程酒店相关的照片
     * @param hotelScheduleEntity
     */
    @Override
    public void batchUpdateHotelScheduleAlbum(HotelScheduleEntity hotelScheduleEntity) {
        //删除之前的照片
        AlbumScheduleEntity albumScheduleEntity = new AlbumScheduleEntity();
        albumScheduleEntity.setScheduleDetailId(hotelScheduleEntity.getScheduleDetailId());
        albumScheduleEntity.setObjectId(hotelScheduleEntity.getId());
        albumScheduleEntity.setType(2);
        this.deleteByContents(albumScheduleEntity);
        this.batchInsertHotelScheduleAlbum(hotelScheduleEntity);
    }


    /**
     * 根据不同类型参数删除照片
     * @param albumScheduleEntity
     * @return
     */
    @Override
    public int deleteByContents(AlbumScheduleEntity albumScheduleEntity) {
        return albumScheduleDao.deleteByContents(albumScheduleEntity);
    }

    /**
     * 根据每日行程id查询照片
     * @param scheduleDetailId
     * @return
     */
    @Override
    public List<AlbumScheduleEntity> selectAlbumsByScheduleDetailId(Long scheduleDetailId) {
        return albumScheduleDao.selectAlbumsByScheduleDetailId(scheduleDetailId);
    }

    /**
     * 批量插入每日行程自费项目照片
     * @param ownExpenseScheduleEntity
     */
    @Override
    public void batchInsertOwnExpenseScheduleAlbum(OwnExpenseScheduleEntity ownExpenseScheduleEntity) {
        List<String> photoUrls = ownExpenseScheduleEntity.getPhotoUrls();
        if (photoUrls != null  && photoUrls.size() > 0){
            List<AlbumScheduleEntity> albumScheduleEntitys = new ArrayList<>();
            for (String photoUrl:photoUrls) {
                if (!StringUtils.isEmpty(photoUrl)){
                    AlbumScheduleEntity albumScheduleEntity = new AlbumScheduleEntity();
                    albumScheduleEntity.setScheduleDetailId(ownExpenseScheduleEntity.getScheduleDetailId());
                    albumScheduleEntity.setObjectId(ownExpenseScheduleEntity.getId());
                    albumScheduleEntity.setType(3);
                    albumScheduleEntity.setPhotoUrl(photoUrl);
                    albumScheduleEntity.setCreateTime(new Date());
                    albumScheduleEntitys.add(albumScheduleEntity);
                }
            }
            this.batchInsert(albumScheduleEntitys);
        }
    }

    /**
     *批量修改每日行程自费项目相关的照片
     * @param ownExpenseScheduleEntity
     */
    @Override
    public void batchUpdateOwnExpenseScheduleAlbum(OwnExpenseScheduleEntity ownExpenseScheduleEntity) {
        //删除之前所有的照片
        AlbumScheduleEntity albumScheduleEntity = new AlbumScheduleEntity();
        albumScheduleEntity.setScheduleDetailId(ownExpenseScheduleEntity.getScheduleDetailId());
        albumScheduleEntity.setObjectId(ownExpenseScheduleEntity.getId());
        albumScheduleEntity.setType(3);
        this.deleteByContents(albumScheduleEntity);
        this.batchInsertOwnExpenseScheduleAlbum(ownExpenseScheduleEntity);
    }

    /**
     * 根据行程明细ID object id 和type 查找图片信息
     * @param scheduleDetailId,objectId,type
     * @return
     */
    @Override
    public List<AlbumScheduleEntity> getAlbumSchedule(Long scheduleDetailId, Long objectId, Integer type) {
        AlbumScheduleEntity albumScheduleEntity = new AlbumScheduleEntity();
        albumScheduleEntity.setScheduleDetailId(scheduleDetailId);
        albumScheduleEntity.setObjectId(objectId);
        albumScheduleEntity.setType(type);
        return albumScheduleDao.getAlbumSchedule(albumScheduleEntity);
    }

    @Override
    public List<AlbumScheduleEntity> getAlbumScheduleByScheduleId(Long scheduleId, Long objectId, Integer type) {
        AlbumScheduleEntity albumScheduleEntity = new AlbumScheduleEntity();
        albumScheduleEntity.setScheduleId(scheduleId);
        albumScheduleEntity.setObjectId(objectId);
        albumScheduleEntity.setType(type);
        return albumScheduleDao.getAlbumScheduleByScheduleId(albumScheduleEntity);
    }



    /**
     * 批量插入行程自费项目照片
     * @param ownExpenseScheduleEntity
     */
    @Override
    public void batchInsertOwnExpenseScheduleAlbumBySchedule(OwnExpenseScheduleEntity ownExpenseScheduleEntity) {
        List<String> photoUrls = ownExpenseScheduleEntity.getPhotoUrls();
        if (photoUrls != null  && photoUrls.size() > 0){
            List<AlbumScheduleEntity> albumScheduleEntitys = new ArrayList<>();
            for (String photoUrl:photoUrls) {
                if (!StringUtils.isEmpty(photoUrl)){
                    AlbumScheduleEntity albumScheduleEntity = new AlbumScheduleEntity();
                    albumScheduleEntity.setScheduleId(ownExpenseScheduleEntity.getScheduleId());
                    albumScheduleEntity.setObjectId(ownExpenseScheduleEntity.getId());
                    albumScheduleEntity.setType(3);
                    albumScheduleEntity.setPhotoUrl(photoUrl);
                    albumScheduleEntity.setCreateTime(new Date());
                    albumScheduleEntity.setUpdateTime(new Date());
                    albumScheduleEntitys.add(albumScheduleEntity);
                }
            }
            this.batchInsert(albumScheduleEntitys);
        }
    }

    /**
     *批量修改每日行程自费项目相关的照片
     * @param ownExpenseScheduleEntity
     */
    @Override
    public void batchUpdateOwnExpenseScheduleAlbumBySchedule(OwnExpenseScheduleEntity ownExpenseScheduleEntity) {
        //删除之前所有的照片
        AlbumScheduleEntity albumScheduleEntity = new AlbumScheduleEntity();
        albumScheduleEntity.setScheduleId(ownExpenseScheduleEntity.getScheduleId());
        albumScheduleEntity.setObjectId(ownExpenseScheduleEntity.getId());
        albumScheduleEntity.setType(3);
        this.deleteByContents(albumScheduleEntity);
        this.batchInsertOwnExpenseScheduleAlbumBySchedule(ownExpenseScheduleEntity);
    }

}
