package com.umessage.letsgo.task.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.task.utils.JobShardingFilter;
import com.umessage.letsgo.task.utils.PrintContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhajl on 2016/5/29.
 * 奖励自动发放
 */
@Component
public class RewardJob extends AbstractIndividualThroughputDataFlowElasticJob<TeamEntity> {

    private PrintContext printContext = new PrintContext(RewardJob.class);

    @Resource
    private ITeamService teamService;

    Logger logger = Logger.getLogger(RewardJob.class);

    @Override
    public List<TeamEntity> fetchData(final JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());
        logger.debug("开始执行TeamStatusJob...");
        //获取有效的并且行程在昨天已经结束的团队
        List<TeamEntity> datalist = teamService.getFinishedTeam();
        JobShardingFilter<TeamEntity> filter = new JobShardingFilter<TeamEntity>(context.getShardingItems());
        return  filter.filterJob(datalist);
    }

    @Override
    public boolean processData(final JobExecutionMultipleShardingContext context, final TeamEntity data) {
        printContext.printProcessDataMessage(data);
        logger.debug("TeamStatusJob：开始处理：" + data.getId() + data.getName());
        //发放奖励
        boolean flag = teamService.issueReward(data);
        return flag;
    }

}
