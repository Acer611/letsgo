package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.dao.journey.ShoppingScheduleDao;
import com.umessage.letsgo.domain.po.journey.ShoppingScheduleEntity;
import com.umessage.letsgo.service.api.journey.IScheduleDetailsService;
import com.umessage.letsgo.service.api.journey.IShoppingScheduleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/7.
 */
@Service
public class ShoppingScheduleServiceImpl implements IShoppingScheduleService{

    private static Logger logger = Logger.getLogger(ShoppingScheduleServiceImpl.class);

    @Resource
    private ShoppingScheduleDao shoppingScheduleDao;
    @Resource
    private IScheduleDetailsService scheduleDetailsService;

    @Override
    public int delete(Long id) {
        return shoppingScheduleDao.delete(id);
    }

    @Override
    public int insert(ShoppingScheduleEntity shoppingScheduleEntity) {
        shoppingScheduleEntity.setCreateTime(new Date());
        return shoppingScheduleDao.insert(shoppingScheduleEntity);
    }

    @Override
    public ShoppingScheduleEntity select(Long id) {
        return shoppingScheduleDao.select(id);
    }

    @Override
    public List<ShoppingScheduleEntity> selectAll() {
        return shoppingScheduleDao.selectAll();
    }

    @Override
    public int update(ShoppingScheduleEntity shoppingScheduleEntity) {
        shoppingScheduleEntity.setUpdateTime(new Date());
        return shoppingScheduleDao.update(shoppingScheduleEntity);
    }

    @Override
    public List<ShoppingScheduleEntity> selectShoppingByScheduleDetailId(Long scheduleDetailId) {
        return shoppingScheduleDao.selectShoppingByScheduleDetailId(scheduleDetailId);
    }

    @Override
    public List<ShoppingScheduleEntity> selectShoppingByScheduleId(Long scheduleId) {
        List<ShoppingScheduleEntity> shoppingScheduleEntities = shoppingScheduleDao.selectShoppingByScheduleId(scheduleId);
        /*
        List<ScheduleDetailEntity> scheduleDetailEntities = scheduleDetailsService.selectScheduleDetailByScheduleId(scheduleId);
        for (ScheduleDetailEntity scheduleDetailEntity:scheduleDetailEntities) {
            List<ShoppingScheduleEntity> shoppingScheduleEntityList = this.selectShoppingByScheduleDetailId(scheduleDetailEntity.getId());
            if (shoppingScheduleEntityList != null){
                shoppingScheduleEntities.addAll(shoppingScheduleEntityList);
            }
        }
        */
        return shoppingScheduleEntities;
    }

    /**
     * 根据每日行程删除
     * @param scheduleDetailId
     * @return
     */
    @Override
    public int deleteShoppingByScheduleDetailId(Long scheduleDetailId) {
        return shoppingScheduleDao.deleteShoppingByScheduleDetailId(scheduleDetailId);
    }

    /**
     * 根据行程删除
     * @param scheduleId
     * @return
     */
    @Override
    public int deleteShoppingByScheduleId(Long scheduleId) {
        return shoppingScheduleDao.deleteShoppingByScheduleId(scheduleId);
    }
}
