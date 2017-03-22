package com.umessage.letsgo.service.api.system;

import com.umessage.letsgo.domain.po.system.YahooWeather;

/**
 * Created by wendy on 2016/6/23.
 */
public interface IYahooWeatherService {
    int addWeather(YahooWeather yahooWeather);

    int updateWeather(YahooWeather yahooWeather);

    YahooWeather getWeather(String name, String date);

    int saveYahooWeather(YahooWeather yahooWeather);
}
