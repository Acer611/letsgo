package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.security.TradeDetailDao;
import com.umessage.letsgo.domain.po.security.TradeDetailEntity;
import com.umessage.letsgo.service.api.security.ITradeDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by fuzhili on 2016/9/28.
 */
@Service
public class TradeDetailServiceImpl implements ITradeDetailService {

    @Resource
    private TradeDetailDao tradeDetailDao;

    @Override
    public TradeDetailEntity getTradeDetailEntityByUserId(Long id) {
        TradeDetailEntity tradeDetailEntity =new TradeDetailEntity();
        tradeDetailEntity.setArriveDate(new Date());
        tradeDetailEntity.setCreateTime(new Date());
        tradeDetailEntity.setTradeDate(new Date());
        return tradeDetailDao.select(id);
    }

    @Override
    public int add(TradeDetailEntity tradeDetailEntity) {
        return tradeDetailDao.insert(tradeDetailEntity);
    }

}
