package com.umessage.letsgo.task.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.service.api.journey.IQuestionRecordService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.task.utils.JobShardingFilter;
import com.umessage.letsgo.task.utils.PrintContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lizhen on 2016/6/14.
 * 自动统计调查问卷信息
 */
@Component
public class EveryDayQuestionJob extends AbstractIndividualThroughputDataFlowElasticJob<MemberEntity> {
    private PrintContext printContext = new PrintContext(EveryDayQuestionJob.class);

    @Resource
    private IMemberService memberService;

    @Resource
    private IQuestionRecordService questionRecordService;

    Logger logger = Logger.getLogger(EveryDayQuestionJob.class);

    @Override
    public List<MemberEntity> fetchData(JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());
        logger.debug("开始执行EveryDayQuestionJob...");

        //查询前天结束行程的成员
        List<MemberEntity> memberEntityList = memberService.selectMemberByScheduleEnd();

        JobShardingFilter<MemberEntity> filter = new JobShardingFilter<>(context.getShardingItems());
        return filter.filterJob(memberEntityList);
    }

    @Override
    public boolean processData(JobExecutionMultipleShardingContext context, MemberEntity data) {
        printContext.printProcessDataMessage(data);
        logger.debug("EveryDayQuestionJob：正在处理 " + data.getId());
        boolean flag = questionRecordService.insertForMember(data);
        return flag;
    }
}
