package com.umessage.letsgo.task.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailCommentEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailIdUserIdEntity;
import com.umessage.letsgo.service.api.journey.IScheduleDetailCommentService;
import com.umessage.letsgo.service.api.journey.IScheduleDetailsService;
import com.umessage.letsgo.task.utils.JobShardingFilter;
import com.umessage.letsgo.task.utils.PrintContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wendy on 2016/6/14.
 * 游客未点评，自动默认点评前一天行程
 */
@Component
public class UserCommentJob extends AbstractIndividualThroughputDataFlowElasticJob<ScheduleDetailIdUserIdEntity> {
    private PrintContext printContext = new PrintContext(UserCommentJob.class);

    @Resource
    private IScheduleDetailsService scheduleDetailsService;

    @Resource
    private IScheduleDetailCommentService scheduleDetailCommentService;

    Logger logger = Logger.getLogger(UserCommentJob.class);

    @Override
    public List<ScheduleDetailIdUserIdEntity> fetchData(JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());
        logger.debug("开始执行UserCommentJob...");

        //根据出行时间查询行程详细和对应的用户id
        List<ScheduleDetailIdUserIdEntity> scheduleDetailIdUserIdEntities = scheduleDetailsService.selectScheduleDetailByScheduleDate();

        JobShardingFilter<ScheduleDetailIdUserIdEntity> filter = new JobShardingFilter<>(context.getShardingItems());
        return filter.filterJob(scheduleDetailIdUserIdEntities);
    }

    @Override
    public boolean processData(JobExecutionMultipleShardingContext context, ScheduleDetailIdUserIdEntity data) {
        printContext.printProcessDataMessage(data);
        logger.debug("UserCommentJob：正在处理 " + data.getUserId());
        boolean flag = scheduleDetailCommentService.setSatisfiedStatusForUserId(data);
        return flag;
    }
}
