/*
 * YahooWeatherDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-06-23 Created by wendy
 */
package com.umessage.letsgo.dao.system;

import com.umessage.letsgo.domain.po.system.YahooWeather;
import com.umessage.letsgo.domain.vo.system.request.YahooWeatherResquest;

import java.util.List;

public interface YahooWeatherDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param yahooWeather
     * @return int
     */
    int insert(YahooWeather yahooWeather);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.system.YahooWeather
     */
    YahooWeather select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.system.YahooWeather>
     */
    List<YahooWeather> selectAll();

    /**
     * Description: 
     * @param yahooWeather
     * @return int
     */
    int update(YahooWeather yahooWeather);

    List<YahooWeather> selectWithNameAndDate(YahooWeatherResquest resquest);
}