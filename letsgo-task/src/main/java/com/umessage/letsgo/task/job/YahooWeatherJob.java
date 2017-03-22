package com.umessage.letsgo.task.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;
import com.umessage.letsgo.domain.po.system.RegionEntity;
import com.umessage.letsgo.domain.po.system.YahooWeather;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.system.IYahooWeatherService;
import com.umessage.letsgo.service.common.helper.YahooHelper;
import com.umessage.letsgo.task.utils.JobShardingFilter;
import com.umessage.letsgo.task.utils.PrintContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wendy on 2016/6/23.
 * 自动抓取雅虎天气
 */
@Component
public class YahooWeatherJob extends AbstractIndividualThroughputDataFlowElasticJob<RegionEntity> {
    private PrintContext printContext = new PrintContext(YahooWeatherJob.class);

    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IYahooWeatherService yahooWeatherService;
    @Resource
    private RestTemplate restTemplate;

    Logger logger = Logger.getLogger(YahooWeatherJob.class);

    @Override
    public List<RegionEntity> fetchData(final JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());

        logger.debug("开始执行YahooWeatherJob...");

//      List<DestinationEntity> cities = scheduleService.getScheduleCitiesInLatestTenDay();
        List<RegionEntity> cities = scheduleService.getScheduleCitiesInLatestTenDay();

        JobShardingFilter<RegionEntity> filter = new JobShardingFilter<RegionEntity>(context.getShardingItems());
        return filter.filterJob(cities);
    }

    @Override
    public boolean processData(final JobExecutionMultipleShardingContext context, RegionEntity data) {
        printContext.printFetchDataMessage(context.getShardingItems());
        logger.debug("YahooWeatherJob：正在处理: " + data.getAlias());

        try {
            // 睡眠100ms
            Thread.sleep(100);

            List<YahooWeather> weathers = YahooHelper.queryYahooWeather(restTemplate, data);
            logger.info("采集到的天气：" + weathers.toString());

            for (YahooWeather weather : weathers
                 ) {
                yahooWeatherService.saveYahooWeather(weather);
                Thread.sleep(100);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }
}
