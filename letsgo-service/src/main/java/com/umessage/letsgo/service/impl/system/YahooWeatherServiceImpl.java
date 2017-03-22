package com.umessage.letsgo.service.impl.system;

import com.umessage.letsgo.dao.system.YahooWeatherDao;
import com.umessage.letsgo.domain.po.system.YahooWeather;
import com.umessage.letsgo.domain.vo.system.request.YahooWeatherResquest;
import com.umessage.letsgo.service.api.system.IYahooWeatherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wendy on 2016/6/23.
 */
@Service
public class YahooWeatherServiceImpl implements IYahooWeatherService {
    @Resource
    private YahooWeatherDao yahooWeatherDao;

    @Override
    @Transactional
    public int addWeather(YahooWeather yahooWeather) {
        yahooWeather.setUpdateTime(new Date());
        yahooWeather.setVersion(0l);
        return yahooWeatherDao.insert(yahooWeather);
    }

    @Override
    @Transactional
    public int updateWeather(YahooWeather yahooWeather) {
        yahooWeather.setUpdateTime(new Date());
        return yahooWeatherDao.update(yahooWeather);
    }

    @Override
    public YahooWeather getWeather(String name, String date) {
        YahooWeatherResquest resquest = new YahooWeatherResquest();
        resquest.setName(name);
        resquest.setDate(date);

        List<YahooWeather> weather = yahooWeatherDao.selectWithNameAndDate(resquest);
        if (CollectionUtils.isEmpty(weather)) {
            return new YahooWeather();
        }
        return weather.get(0);
    }

    @Override
    @Transactional
    public int saveYahooWeather(YahooWeather yahooWeather) {
        YahooWeather weather = this.getWeather(yahooWeather.getCityName(), yahooWeather.getDate());

        // 判断数据库是否有该城市对应日期的数据记录，如果没有，则创建；如果有，则更新
        if (weather.getId() == null) {
            this.addWeather(yahooWeather);
        } else {
            weather.setHigh(yahooWeather.getHigh());
            weather.setLow(yahooWeather.getLow());
            weather.setText(yahooWeather.getText());
            weather.setDescn(yahooWeather.getDescn());
            weather.setCityName(yahooWeather.getCityName());
            this.updateWeather(weather);
        }

        return 1;
    }
}
