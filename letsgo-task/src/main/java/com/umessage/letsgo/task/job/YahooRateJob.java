package com.umessage.letsgo.task.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;
import com.umessage.letsgo.domain.po.system.NationDictionaryEntity;
import com.umessage.letsgo.domain.po.system.YahooRate;
import com.umessage.letsgo.service.api.system.INationDictionaryService;
import com.umessage.letsgo.service.api.system.IYahooRateService;
import com.umessage.letsgo.service.common.helper.YahooHelper;
import com.umessage.letsgo.task.utils.JobShardingFilter;
import com.umessage.letsgo.task.utils.PrintContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 2016/6/23.
 * 自动抓取雅虎汇率
 */
@Component
public class YahooRateJob extends AbstractIndividualThroughputDataFlowElasticJob<YahooRate> {

    private PrintContext printContext = new PrintContext(YahooRateJob.class);

    @Resource
    private IYahooRateService yahooRateService;
    @Resource
    private INationDictionaryService nationDictionaryService;
    @Resource
    private RestTemplate restTemplate;

    Logger logger = Logger.getLogger(YahooRate.class);

    @Override
    public List<YahooRate> fetchData(final JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());

        logger.debug("开始执行YahooRateJob...");

        // 获取所有的货币类型
        List<NationDictionaryEntity> nations = nationDictionaryService.getAllNationDictionaryEntity();
        List<String> rateIds = new ArrayList<>();
        for (NationDictionaryEntity nation : nations) {
            String rateId = "\"USD" + nation.getCode() + "\"";
            rateIds.add(rateId);
        }

        // 调用雅虎API获取最新的汇率（以美元为准基数）
        List<YahooRate> yahooRates = YahooHelper.queryYahooRate(restTemplate, rateIds);

        JobShardingFilter<YahooRate> filter = new JobShardingFilter<YahooRate>(context.getShardingItems());
        return filter.filterJob(yahooRates);
    }

    @Override
    public boolean processData(final JobExecutionMultipleShardingContext context, YahooRate data) {
        printContext.printFetchDataMessage(context.getShardingItems());

        logger.debug("YahooRateJob：正在处理: " + data.getId() + " : " + data.getName());

        yahooRateService.saveAndUpateRate(data);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }
}
