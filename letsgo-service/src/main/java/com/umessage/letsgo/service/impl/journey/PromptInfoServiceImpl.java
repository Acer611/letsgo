package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.dao.journey.PromptInfoDao;
import com.umessage.letsgo.domain.po.journey.OwnExpenseScheduleEntity;
import com.umessage.letsgo.domain.po.journey.PromptInfoEntity;
import com.umessage.letsgo.domain.po.journey.ShoppingScheduleEntity;
import com.umessage.letsgo.service.api.journey.IOwnExpenseScheduleService;
import com.umessage.letsgo.service.api.journey.IPromptInfoService;
import com.umessage.letsgo.service.api.journey.IShoppingScheduleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/2.
 */
@Service
public class PromptInfoServiceImpl implements IPromptInfoService{

    @Resource
    private PromptInfoDao dao;
    @Resource
    private IShoppingScheduleService shoppingScheduleService;
    @Resource
    private IOwnExpenseScheduleService ownExpenseScheduleService;

    @Override
    public int addPromptInfo(PromptInfoEntity promptInfoEntity) {
        //增加行程的购物场所
       List<ShoppingScheduleEntity> shoppingScheduleList = promptInfoEntity.getShoppingScheduleList();
        if(null!=shoppingScheduleList){
            for (ShoppingScheduleEntity shoppingScheduleEntity:shoppingScheduleList) {
                shoppingScheduleEntity.setScheduleId(promptInfoEntity.getScheduleId());
                shoppingScheduleService.insert(shoppingScheduleEntity);
            }
        }

        //增加行程的自费项目
        List<OwnExpenseScheduleEntity> ownExpenseScheduleList = promptInfoEntity.getOwnExpenseScheduleList();
        if(null!=ownExpenseScheduleList){
            for (OwnExpenseScheduleEntity ownExpenseScheduleEntity:ownExpenseScheduleList) {
                ownExpenseScheduleEntity.setScheduleId(promptInfoEntity.getScheduleId());
                ownExpenseScheduleService.insertBySchedule(ownExpenseScheduleEntity);
            }
        }

        Date date = new Date();
        promptInfoEntity.setCreateTime(date);
        promptInfoEntity.setUpdateTime(date);
        promptInfoEntity.setVersion(0L);
        return dao.insert(promptInfoEntity);
    }

    @Override
    public int deletePromptInfo(long id) {
        return dao.delete(id);
    }

    @Override
    public int updatePromptInfo(PromptInfoEntity promptInfoEntity) {

        //更新行程的购物场所
        List<ShoppingScheduleEntity> shoppingScheduleList = promptInfoEntity.getShoppingScheduleList();
        if (shoppingScheduleList != null){
            shoppingScheduleService.deleteShoppingByScheduleId(promptInfoEntity.getScheduleId());
            for (ShoppingScheduleEntity shoppingScheduleEntity:shoppingScheduleList) {
                shoppingScheduleEntity.setScheduleId(promptInfoEntity.getScheduleId());
                shoppingScheduleService.insert(shoppingScheduleEntity);
            }
        }


        //更新行程的自费项目
        List<OwnExpenseScheduleEntity> ownExpenseScheduleList = promptInfoEntity.getOwnExpenseScheduleList();
        if (ownExpenseScheduleList != null){
            ownExpenseScheduleService.deleteOwnExpenseScheduleByScheduleId(promptInfoEntity.getScheduleId());
            for (OwnExpenseScheduleEntity ownExpenseScheduleEntity:ownExpenseScheduleList) {
                ownExpenseScheduleEntity.setScheduleId(promptInfoEntity.getScheduleId());
                ownExpenseScheduleService.insertBySchedule(ownExpenseScheduleEntity);
            }
        }

        promptInfoEntity.setUpdateTime(new Date());
        return dao.update(promptInfoEntity);
    }

    @Override
    public List<PromptInfoEntity> getPromptInfos() {
        List<PromptInfoEntity> list = dao.selectAll();
        if(CollectionUtils.isEmpty(list)){
            list = Collections.emptyList();
        }
        return list;
    }

    @Override
    public PromptInfoEntity getPromptInfo(long id) {
        PromptInfoEntity entity = dao.select(id);
        if(entity == null){
            entity = new PromptInfoEntity();
        }
        return entity;
    }

    @Override
    public PromptInfoEntity getByScheduleId(long scheduleId) {
        PromptInfoEntity entity = dao.selectByScheduleId(scheduleId);
        if(entity == null){
            entity = new PromptInfoEntity();
        }
        return entity;
    }
}
