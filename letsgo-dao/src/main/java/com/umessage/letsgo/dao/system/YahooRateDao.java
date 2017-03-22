/*
 * YahooRateDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-06-06 Created by wendy
 */
package com.umessage.letsgo.dao.system;

import com.umessage.letsgo.domain.po.system.YahooRate;
import java.util.List;

public interface YahooRateDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(String id);

    /**
     * Description: 
     * @param yahooRate
     * @return int
     */
    int insert(YahooRate yahooRate);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.system.YahooRate
     */
    YahooRate select(String id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.system.YahooRate>
     */
    List<YahooRate> selectAll();

    /**
     * Description: 
     * @param yahooRate
     * @return int
     */
    int update(YahooRate yahooRate);

    List<YahooRate> selectWithIDs(List<String> ids);
}