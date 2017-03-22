package com.umessage.letsgo.task.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;
import com.umessage.letsgo.domain.po.journey.HotelEvaluationsEntity;
import com.umessage.letsgo.domain.po.notice.RemindEntity;
import com.umessage.letsgo.domain.vo.journey.request.HotelValuationsRequest;
import com.umessage.letsgo.service.api.notice.IRemindService;
import com.umessage.letsgo.service.api.team.IHotelValuationsService;
import com.umessage.letsgo.task.utils.JobShardingFilter;
import com.umessage.letsgo.task.utils.PrintContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by wendy on 2016/6/14.
 * 酒店数据统计
 */
@Component
public class HotelStatisticalJob extends AbstractIndividualThroughputDataFlowElasticJob<HotelEvaluationsEntity> {
    private PrintContext printContext = new PrintContext(HotelStatisticalJob.class);

    @Resource
    private IHotelValuationsService hotelValuationsService;

    Logger logger = Logger.getLogger(HotelStatisticalJob.class);

    @Override
    public List<HotelEvaluationsEntity> fetchData(JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());
        logger.debug("开始执行HotelStatisticalJob...");
        HotelValuationsRequest h=new HotelValuationsRequest();
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        date=calendar.getTime();
        h.setStartDate(date);
        List<HotelEvaluationsEntity> remindEntityList = hotelValuationsService.selectStatis(h);
        JobShardingFilter<HotelEvaluationsEntity> filter = new JobShardingFilter<HotelEvaluationsEntity>(context.getShardingItems());
        return filter.filterJob(remindEntityList);

    }

    @Override
    public boolean processData(JobExecutionMultipleShardingContext context, HotelEvaluationsEntity data) {
        printContext.printProcessDataMessage(data);
        data.setCreateTime(new Date());
        logger.debug("HotelStatisticalJob：正在处理 " + data.getHotelId());
        boolean flag=hotelValuationsService.insert(data);
        return flag;
    }
}
