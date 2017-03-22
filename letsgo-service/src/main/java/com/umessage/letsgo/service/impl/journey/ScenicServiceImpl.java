package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.dao.journey.ScenicDao;
import com.umessage.letsgo.domain.po.journey.ScenicEntity;
import com.umessage.letsgo.service.api.journey.IScenicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/2.
 */
@Service
public class ScenicServiceImpl implements IScenicService{

    @Resource
    private ScenicDao dao;

    public int addScenic(ScenicEntity scenicEntity) {
        scenicEntity.setCreateTime(new Date());
        scenicEntity.setVersion(0L);
        return dao.insert(scenicEntity);
    }

    public int deleteScenic(long id) {
        return dao.delete(id);
    }


    public int deleteByScheduleDetailId(long scheduleDetailId){
        return dao.deleteByScheduleDetailId(scheduleDetailId);
    }

    public int updateScenic(ScenicEntity scenicEntity) {
        scenicEntity.setCreateTime(new Date());
        return dao.update(scenicEntity);
    }


    public ScenicEntity getScenic(long id) {
        ScenicEntity entity = dao.select(id);
        if(entity == null){
            entity = new ScenicEntity();
        }
        return entity;
    }

    public List<ScenicEntity> getScenicListByScheduleDetailId(long scheduleDetailId) {
        List<ScenicEntity> list = dao.getByScheduleDetailId(scheduleDetailId);
        return list;
    }


}
