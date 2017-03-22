package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.TradeDetailEntity;

/**
 * Created by fuzhili on 2016/9/28.
 */
public interface TradeDetailDao {
    TradeDetailEntity select(Long id);
    int insert(TradeDetailEntity TradeDetailEntity);
}
