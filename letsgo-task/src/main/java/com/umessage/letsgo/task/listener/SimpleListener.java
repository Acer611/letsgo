package com.umessage.letsgo.task.listener;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.api.listener.ElasticJobListener;
import org.apache.log4j.Logger;

/**
 * Created by zhajl on 2016/6/23.
 */
public class SimpleListener implements ElasticJobListener {

    Logger logger = Logger.getLogger(SimpleListener.class);

    @Override
    public void beforeJobExecuted(final JobExecutionMultipleShardingContext shardingContext) {
        //System.out.println("beforeJobExecuted:" + shardingContext.getJobName());
        logger.info("beforeJobExecuted:" + shardingContext.getJobName());
    }

    @Override
    public void afterJobExecuted(final JobExecutionMultipleShardingContext shardingContext) {
        //System.out.println("afterJobExecuted:" + shardingContext.getJobName());
        logger.info("afterJobExecuted:" + shardingContext.getJobName());
    }
}
