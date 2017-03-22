package com.umessage.letsgo.task.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;
import com.umessage.letsgo.domain.po.notice.RemindEntity;
import com.umessage.letsgo.service.api.notice.IRemindService;
import com.umessage.letsgo.task.utils.JobShardingFilter;
import com.umessage.letsgo.task.utils.PrintContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wendy on 2016/6/14.
 * 集合提前提醒
 */
@Component
public class RemindNoticeJob extends AbstractIndividualThroughputDataFlowElasticJob<RemindEntity> {
    private PrintContext printContext = new PrintContext(RemindNoticeJob.class);

    @Resource
    private IRemindService remindService;

    Logger logger = Logger.getLogger(RemindNoticeJob.class);

    @Override
    public List<RemindEntity> fetchData(JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());
        logger.debug("开始执行RemindNoticeJob...");

        List<RemindEntity> remindEntityList = remindService.getRemindList();

        JobShardingFilter<RemindEntity> filter = new JobShardingFilter<RemindEntity>(context.getShardingItems());
        return filter.filterJob(remindEntityList);
    }

    @Override
    public boolean processData(JobExecutionMultipleShardingContext context, RemindEntity data) {
        printContext.printProcessDataMessage(data);
        logger.debug("RemindNoticeJob：正在处理 " + data.getId());

        boolean flag = remindService.remindNoticeToMember(data);
        return flag;
    }
}
