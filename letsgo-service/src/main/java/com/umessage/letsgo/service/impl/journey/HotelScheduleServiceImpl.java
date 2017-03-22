package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.dao.journey.HotelScheduleDao;
import com.umessage.letsgo.domain.po.journey.AlbumScheduleEntity;
import com.umessage.letsgo.domain.po.journey.HotelScheduleEntity;
import com.umessage.letsgo.service.api.journey.IAlbumScheduleService;
import com.umessage.letsgo.service.api.journey.IHotelScheduleService;
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
public class HotelScheduleServiceImpl implements IHotelScheduleService{

    private static Logger logger = Logger.getLogger(HotelScheduleServiceImpl.class);

    @Resource
    private HotelScheduleDao hotelScheduleDao;
    @Resource
    private IAlbumScheduleService albumScheduleService;


    @Override
    public int delete(Long id) {
        return hotelScheduleDao.delete(id);
    }

    @Override
    public int insert(HotelScheduleEntity hotelScheduleEntity) {
        hotelScheduleEntity.setCreateTime(new Date());
        int insert = hotelScheduleDao.insert(hotelScheduleEntity);
        if (insert > 0){
            albumScheduleService.batchInsertHotelScheduleAlbum(hotelScheduleEntity);
        }
        return insert;
    }

    @Override
    public HotelScheduleEntity select(Long id) {
        return hotelScheduleDao.select(id);
    }

    @Override
    public List<HotelScheduleEntity> selectAll() {
        return hotelScheduleDao.selectAll();
    }

    @Override
    public int update(HotelScheduleEntity hotelScheduleEntity) {
        hotelScheduleEntity.setUpdateTime(new Date());
        int update = hotelScheduleDao.update(hotelScheduleEntity);
        if (update > 0){
            albumScheduleService.batchUpdateHotelScheduleAlbum(hotelScheduleEntity);
        }
        return update;
    }


    /**
     * 保存每日行程酒店及照片
     * @param hotelScheduleEntity
     */
    @Override
    public void saveHotelSchedule(HotelScheduleEntity hotelScheduleEntity) {
        if (hotelScheduleEntity.getId() != null){
            this.update(hotelScheduleEntity);
        }else {
            this.insert(hotelScheduleEntity);
        }
    }

    /**
     * 根据每日行程id查询酒店
     * @param scheduleDetailId
     * @return
     */
    @Override
    public HotelScheduleEntity selectByScheduleDetailId(Long scheduleDetailId) {
        return hotelScheduleDao.selectByScheduleDetailId(scheduleDetailId);
    }

    /**
     * 根据id查询每日行程酒店及相册
     * @param id
     * @return
     */
    @Override
    public HotelScheduleEntity selectHotelScheduleAndAlbum(Long id) {
        HotelScheduleEntity hotelScheduleEntity = hotelScheduleDao.select(id);
        if (hotelScheduleEntity != null){
            List<AlbumScheduleEntity> albumSchedules = albumScheduleService.getAlbumSchedule(hotelScheduleEntity.getScheduleDetailId(), hotelScheduleEntity.getId(), 2);
            if (albumSchedules != null){
                List<String> photoUrls = new ArrayList<>();
                for (AlbumScheduleEntity albumScheduleEntity:albumSchedules) {
                    photoUrls.add(albumScheduleEntity.getPhotoUrl());
                }
                hotelScheduleEntity.setPhotoUrls(photoUrls);
                hotelScheduleEntity.setAlbumScheduleList(albumSchedules);
            }
        }
        return hotelScheduleEntity;
    }

    @Override
    public int deleteByScheduleDetailId(Long scheduleDetailId) {
        return hotelScheduleDao.deleteByScheduleDetailId(scheduleDetailId);
    }
}
