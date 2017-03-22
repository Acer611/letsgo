package com.umessage.letsgo.task.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.notice.RemindEntity;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleLineVo;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.notice.IRemindService;
import com.umessage.letsgo.task.utils.JobShardingFilter;
import com.umessage.letsgo.task.utils.PrintContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pw on 2016/8/29.
 * 线路评论统计定时任务
 */
@Component
public class ScheduleReportsJob extends AbstractIndividualThroughputDataFlowElasticJob<ScheduleLineVo> {
    private PrintContext printContext = new PrintContext(ScheduleReportsJob.class);

    @Resource
    private IScheduleService scheduleService;

    Logger logger = Logger.getLogger(ScheduleReportsJob.class);

    @Override
    public List<ScheduleLineVo> fetchData(JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());
        logger.debug("开始执行ScheduleReportsJob...");
        //获取要处理的数据
        List<ScheduleLineVo> scheduleEntityList = scheduleService.getScheduleList();

        JobShardingFilter<ScheduleLineVo> filter = new JobShardingFilter<ScheduleLineVo>(context.getShardingItems());
        //如果有多台服务器，相当于一个分布式，根据list的大小分成多个任务执行，每个任务执行一次processData方法
        return filter.filterJob(scheduleEntityList);
    }

    @Override
    public boolean processData(JobExecutionMultipleShardingContext context, ScheduleLineVo data) {
        printContext.printProcessDataMessage(data);
        logger.debug("ScheduleReportsJob：正在处理 " + data.getId());
        //对数据进行处理
        boolean flag = scheduleService.setLineEvaluate(data);
        return flag;
    }
}
