package com.umessage.letsgo.task.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;
import com.umessage.letsgo.domain.po.system.PushEntity;
import com.umessage.letsgo.service.api.system.IPushService;
import com.umessage.letsgo.task.utils.JobShardingFilter;
import com.umessage.letsgo.task.utils.PrintContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wendy on 2016/7/19.
 * 推送任务
 */
@Component
public class TracerPushMsgJob extends AbstractIndividualThroughputDataFlowElasticJob<PushEntity> {
    private PrintContext printContext = new PrintContext(RemindNoticeJob.class);

    @Resource
    private IPushService pushService;

    Logger logger = Logger.getLogger(RemindNoticeJob.class);

    @Override
    public List<PushEntity> fetchData(JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());
        logger.debug("开始执行TracerPushMsgJob...");

        List<PushEntity> pushEntityList = pushService.getPushList();

        JobShardingFilter<PushEntity> filter = new JobShardingFilter<PushEntity>(context.getShardingItems());
        return filter.filterJob(pushEntityList);
    }

    @Override
    public boolean processData(JobExecutionMultipleShardingContext context, PushEntity data) {
        printContext.printProcessDataMessage(data);
        logger.debug("TracerPushMsgJob：正在处理 " + data.getMsgid());

        boolean flag = pushService.updatePushWithTracker(data);

        return flag;
    }
}
