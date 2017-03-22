package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.dao.journey.OwnExpenseScheduleDao;
import com.umessage.letsgo.domain.po.journey.AlbumScheduleEntity;
import com.umessage.letsgo.domain.po.journey.OwnExpenseScheduleEntity;
import com.umessage.letsgo.service.api.journey.IAlbumScheduleService;
import com.umessage.letsgo.service.api.journey.IOwnExpenseScheduleService;
import com.umessage.letsgo.service.api.journey.IScheduleDetailsService;
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
public class OwnExpenseScheduleServiceImpl implements IOwnExpenseScheduleService{

    private static Logger logger = Logger.getLogger(OwnExpenseScheduleServiceImpl.class);

    @Resource
    private OwnExpenseScheduleDao ownExpenseScheduleDao;
    @Resource
    private IAlbumScheduleService albumScheduleService;
    @Resource
    private IScheduleDetailsService scheduleDetailsService;


    @Override
    public int delete(Long id) {
        return ownExpenseScheduleDao.delete(id);
    }

    @Override
    public int insert(OwnExpenseScheduleEntity ownExpenseScheduleEntity) {
        ownExpenseScheduleEntity.setCreateTime(new Date());
        int insert = ownExpenseScheduleDao.insert(ownExpenseScheduleEntity);
        if (insert > 0){
            albumScheduleService.batchInsertOwnExpenseScheduleAlbum(ownExpenseScheduleEntity);
        }
        return insert;
    }

    @Override
    public int insertBySchedule(OwnExpenseScheduleEntity ownExpenseScheduleEntity) {
        ownExpenseScheduleEntity.setCreateTime(new Date());
        int insert = ownExpenseScheduleDao.insert(ownExpenseScheduleEntity);
        if (insert > 0){
            albumScheduleService.batchInsertOwnExpenseScheduleAlbumBySchedule(ownExpenseScheduleEntity);
        }
        return insert;
    }

    @Override
    public OwnExpenseScheduleEntity select(Long id) {
        return ownExpenseScheduleDao.select(id);
    }

    @Override
    public List<OwnExpenseScheduleEntity> selectAll() {
        return ownExpenseScheduleDao.selectAll();
    }

    @Override
    public int update(OwnExpenseScheduleEntity ownExpenseScheduleEntity) {
        ownExpenseScheduleEntity.setUpdateTime(new Date());
        int update = ownExpenseScheduleDao.update(ownExpenseScheduleEntity);
        if (update > 0){
            albumScheduleService.batchUpdateOwnExpenseScheduleAlbum(ownExpenseScheduleEntity);
        }
        return update;
    }

    @Override
    public int updateBySchedule(OwnExpenseScheduleEntity ownExpenseScheduleEntity) {
        ownExpenseScheduleEntity.setUpdateTime(new Date());
        int update = ownExpenseScheduleDao.update(ownExpenseScheduleEntity);
        if (update > 0){
            albumScheduleService.batchUpdateOwnExpenseScheduleAlbumBySchedule(ownExpenseScheduleEntity);
        }
        return update;
    }

    @Override
    public int deleteOwnExpenseScheduleByScheduleId(Long scheduleId) {
        return ownExpenseScheduleDao.deleteOwnExpenseScheduleByScheduleId(scheduleId);
    }

    @Override
    public int deleteOwnExpenseScheduleByScheduleDetailId(Long scheduleDetailId) {
        return ownExpenseScheduleDao.deleteOwnExpenseScheduleByScheduleDetailId(scheduleDetailId);
    }

    /**
     * 查询自费项目及相册
     * @param id
     * @return
     */
    @Override
    public OwnExpenseScheduleEntity selectOwnExpenseScheduleAndAlbum(Long id) {
        return ownExpenseScheduleDao.selectOwnExpenseScheduleAndAlbum(id);
    }

    /**
     * 根据行程id查询自费项目
     * @param scheduleId
     * @return
     */
    @Override
    public List<OwnExpenseScheduleEntity> selectOwnExpenseScheduleByScheduleId(Long scheduleId) {
        List<OwnExpenseScheduleEntity> ownExpenseScheduleEntities = ownExpenseScheduleDao.selectOwnExpenseScheduleByScheduleId(scheduleId);
        for (OwnExpenseScheduleEntity ownExpenseSchedule:ownExpenseScheduleEntities) {
            List<AlbumScheduleEntity> albumScheduleEntities = albumScheduleService.getAlbumScheduleByScheduleId(scheduleId, ownExpenseSchedule.getId(), 3);
            List<String> images = new ArrayList<>();
            if (albumScheduleEntities != null){
                for (AlbumScheduleEntity albumSchedule:albumScheduleEntities) {
                    images.add("\""+albumSchedule.getPhotoUrl()+"\"");
                }
                ownExpenseSchedule.setAlbumScheduleList(albumScheduleEntities);
            }
            ownExpenseSchedule.setPhotoUrls(images);
        }
        /*
        List<ScheduleDetailEntity> scheduleDetailEntities = scheduleDetailsService.selectScheduleDetailByScheduleId(scheduleId);
        for (ScheduleDetailEntity scheduleDetailEntity:scheduleDetailEntities) {
            List<OwnExpenseScheduleEntity> ownExpenseScheduleEntityList = this.selectOwnExpenseScheduleByScheduleDetailId(scheduleDetailEntity.getId());
            if (ownExpenseScheduleEntityList != null){
                ownExpenseScheduleEntities.addAll(ownExpenseScheduleEntityList);
            }
        }
        */
        return ownExpenseScheduleEntities;
    }

    /**
     * 根据每日行程id查询自费项目
     * @param scheduleDetailId
     * @return
     */
    @Override
    public List<OwnExpenseScheduleEntity> selectOwnExpenseScheduleByScheduleDetailId(Long scheduleDetailId) {
        List<OwnExpenseScheduleEntity> ownExpenseScheduleEntities = ownExpenseScheduleDao.selectOwnExpenseScheduleByScheduleDetailId(scheduleDetailId);
        for (OwnExpenseScheduleEntity ownExpenseSchedule:ownExpenseScheduleEntities) {
            List<AlbumScheduleEntity> albumScheduleEntities = albumScheduleService.getAlbumSchedule(ownExpenseSchedule.getScheduleDetailId(), ownExpenseSchedule.getId(), 3);
            List<String> images = new ArrayList<>();
            if (albumScheduleEntities != null){
                for (AlbumScheduleEntity albumSchedule:albumScheduleEntities) {
                    images.add("\""+albumSchedule.getPhotoUrl()+"\"");
                }
                ownExpenseSchedule.setAlbumScheduleList(albumScheduleEntities);
            }
            ownExpenseSchedule.setPhotoUrls(images);
        }
        return ownExpenseScheduleEntities;
    }
}
