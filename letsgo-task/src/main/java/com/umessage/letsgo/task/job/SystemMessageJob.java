package com.umessage.letsgo.task.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.service.api.journey.IScheduleDetailsService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.task.utils.JobShardingFilter;
import com.umessage.letsgo.task.utils.PrintContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wendy on 2016/6/13.
 * 次日行程信息及天气推送
 */
@Component
public class SystemMessageJob extends AbstractIndividualThroughputDataFlowElasticJob<TeamEntity> {

    private PrintContext printContext = new PrintContext(SystemMessageJob.class);

    @Resource
    private ITeamService teamService;
    @Resource
    private IScheduleDetailsService scheduleDetailsService;

    Logger logger = Logger.getLogger(SystemMessageJob.class);

    @Override
    public List<TeamEntity> fetchData(JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());
        logger.debug("开始执行SystemMessageJob...");

        List<TeamEntity> datalist = teamService.getSendMessageTeam();

        JobShardingFilter<TeamEntity> filter = new JobShardingFilter<TeamEntity>(context.getShardingItems());
        return filter.filterJob(datalist);
    }

    @Override
    public boolean processData(JobExecutionMultipleShardingContext jobExecutionMultipleShardingContext, TeamEntity data) {
        printContext.printProcessDataMessage(data);
        logger.info("SystemMessageJob：正在处理 " + data.getId() + data.getName());

        // 1、获取明日的行程信息
        ScheduleDetailEntity scheduleDetail = scheduleDetailsService.getTomorrowScheduleDetail(data.getId());
        logger.info("明日的行程明细信息, 行程ID:" + scheduleDetail.getJourId() + ", 目的地1: " + scheduleDetail.getDestination1());

        // 2、根据明日行程出发地计算需要发送天气的时间点
        int hours = teamService.getScheduleStartHours(scheduleDetail);
        logger.info("hours为："+hours+",定时发送时间为："+Integer.parseInt(Constant.WEATHER_REMIND_HOURS));

        // 每晚九点，系统自动推送自定义行程信息
        if (hours == Integer.parseInt(Constant.WEATHER_REMIND_HOURS)) {
            // 3、调用腾讯云IM发送系统自定义的行程消息
            teamService.sendScheduleMessage(data, scheduleDetail);
        }

        return true;
    }

}
