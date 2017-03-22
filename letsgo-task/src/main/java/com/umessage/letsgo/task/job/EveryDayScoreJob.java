package com.umessage.letsgo.task.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;
import com.umessage.letsgo.domain.po.journey.ScoreRecordEntity;
import com.umessage.letsgo.service.api.journey.IScheduleDetailCommentService;
import com.umessage.letsgo.service.api.journey.IScoreRecordService;
import com.umessage.letsgo.task.utils.JobShardingFilter;
import com.umessage.letsgo.task.utils.PrintContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lizhen on 2016/6/14.
 * 自动统计点评信息
 */
@Component
public class EveryDayScoreJob extends AbstractIndividualThroughputDataFlowElasticJob<ScoreRecordEntity> {
    private PrintContext printContext = new PrintContext(EveryDayScoreJob.class);

    @Resource
    private IScheduleDetailCommentService scheduleDetailCommentService;

    @Resource
    private IScoreRecordService scoreRecordService;

    Logger logger = Logger.getLogger(EveryDayScoreJob.class);

    @Override
    public List<ScoreRecordEntity> fetchData(JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());
        logger.debug("开始执行EveryDayScoreJob...");

        //查询昨天所有的每日点评
        List<ScoreRecordEntity> scoreRecordEntities = scheduleDetailCommentService.selectAllByScheduleDate();

        JobShardingFilter<ScoreRecordEntity> filter = new JobShardingFilter<>(context.getShardingItems());
        return filter.filterJob(scoreRecordEntities);
    }

    @Override
    public boolean processData(JobExecutionMultipleShardingContext context, ScoreRecordEntity data) {
        printContext.printProcessDataMessage(data);
        logger.debug("EveryDayScoreJob：正在处理 " + data.getId());
        boolean flag = scoreRecordService.insertForJob(data);
        return flag;
    }
}
