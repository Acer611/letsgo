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
 * 团队状态自动变更
 */
@Component
public class TeamStatusJob extends AbstractIndividualThroughputDataFlowElasticJob<TeamEntity> {

    private PrintContext printContext = new PrintContext(TeamStatusJob.class);

    @Resource
    private ITeamService teamService;

    Logger logger = Logger.getLogger(TeamStatusJob.class);

    @Override
    public List<TeamEntity> fetchData(final JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());
        logger.debug("开始执行TeamStatusJob...");

        List<TeamEntity> datalist = teamService.getTripStageTeam();
        JobShardingFilter<TeamEntity> filter = new JobShardingFilter<TeamEntity>(context.getShardingItems());
        return  filter.filterJob(datalist);
    }

    @Override
    public boolean processData(final JobExecutionMultipleShardingContext context, final TeamEntity data) {
        printContext.printProcessDataMessage(data);
        logger.debug("TeamStatusJob：开始处理：" + data.getId() + data.getName());

        boolean flag = teamService.processTripStage(data);
        return flag;
    }

}
