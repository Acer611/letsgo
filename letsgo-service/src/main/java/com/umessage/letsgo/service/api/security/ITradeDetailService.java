package com.umessage.letsgo.service.api.security;
import com.umessage.letsgo.domain.po.security.TradeDetailEntity;
/**
 * Created by fuzhili on 2016/9/28.
 */
public interface ITradeDetailService {
    TradeDetailEntity getTradeDetailEntityByUserId(Long id);
    int add(TradeDetailEntity tradeDetailEntity);

}
