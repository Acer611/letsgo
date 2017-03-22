package com.umessage.letsgo.task.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.service.api.journey.IScheduleDetailCommentService;
import com.umessage.letsgo.service.api.journey.IScheduleDetailsService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.task.utils.JobShardingFilter;
import com.umessage.letsgo.task.utils.PrintContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lizhen on 2016/6/14.
 * 每日行程点评信息推送
 */
@Component
public class EveryDayCommentJob extends AbstractIndividualThroughputDataFlowElasticJob<TeamEntity> {
    private PrintContext printContext = new PrintContext(EveryDayCommentJob.class);

    @Resource
    private ITeamService teamService;
    @Resource
    private IScheduleDetailsService scheduleDetailsService;

    Logger logger = Logger.getLogger(EveryDayCommentJob.class);

    @Override
    public List<TeamEntity> fetchData(JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());
        logger.debug("开始执行EveryDayCommentJob...");

        //根据出行时间查询行程详细和对应的用户id
        //List<MemberEntity> memberEntityList = memberService.selectMemberByScheduleDate();

        // 获取需要发送点评的团队
        List<TeamEntity> datalist = teamService.getSendCommnetTeam();

        JobShardingFilter<TeamEntity> filter = new JobShardingFilter<>(context.getShardingItems());
        return filter.filterJob(datalist);
    }

    @Override
    public boolean processData(JobExecutionMultipleShardingContext context, TeamEntity data) {
        printContext.printProcessDataMessage(data);
        logger.debug("EveryDayCommentJob：正在处理 " + data.getId() + data.getName());

        // 1、获取今天的行程信息
        ScheduleDetailEntity scheduleDetail = scheduleDetailsService.getTodayScheduleDetail(data.getId());
        logger.info("当天的行程明细信息, 行程ID:" + scheduleDetail.getJourId() + ", 目的地1: " + scheduleDetail.getDestination1());

        // 2、根据当前行程的出行日期和时区计算需要发送的时间点
        int hours = teamService.getScheduleDestinationHours(scheduleDetail);
        logger.info("hours为："+hours+",定时发送时间为：" + Integer.parseInt(Constant.COMMENT_REMIND_HOURS));

        // 每晚九点，系统自动推送自定义行程信息
        if (hours == Integer.parseInt(Constant.COMMENT_REMIND_HOURS)) {
            teamService.sendCommnetMessage(data);
        }

        return true;
    }
}
