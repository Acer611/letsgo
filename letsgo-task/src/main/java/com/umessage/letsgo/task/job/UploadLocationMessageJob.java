package com.umessage.letsgo.task.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;
import com.umessage.letsgo.core.timezone.TimeZoneUtil;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.domain.po.activity.LocationEntity;
import com.umessage.letsgo.domain.po.security.DeviceEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.service.api.activity.ILocationService;
import com.umessage.letsgo.service.api.security.IDeviceService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.IPushService;
import com.umessage.letsgo.task.utils.JobShardingFilter;
import com.umessage.letsgo.task.utils.PrintContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wendy on 2016/6/13.
 * 位置上传自动推送
 */
@Component
public class UploadLocationMessageJob extends AbstractIndividualThroughputDataFlowElasticJob<UserEntity> {

    private PrintContext printContext = new PrintContext(UploadLocationMessageJob.class);

    @Resource
    private IUserService userService;
    @Resource
    private IDeviceService deviceService;
    @Resource
    private IPushService pushService;
    @Resource
    private ILocationService locationService;

    Logger logger = Logger.getLogger(UploadLocationMessageJob.class);

    @Override
    public List<UserEntity> fetchData(JobExecutionMultipleShardingContext context) {
        printContext.printFetchDataMessage(context.getShardingItems());
        logger.debug("开始执行UploadLocationMessageJob...");
        logger.debug(DateUtils.date2String("yyyy-MM-dd hh:mm:ss", new Date()) + "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        List<UserEntity> userList = userService.getUsersIsTravel(1);
        userList = locationService.getUploadLocationUsers(userList);

        logger.info("发送用户：" + userList.toString());

        JobShardingFilter<UserEntity> filter = new JobShardingFilter<UserEntity>(context.getShardingItems());
        return filter.filterJob(userList);
    }

    @Override
    public boolean processData(JobExecutionMultipleShardingContext jobExecutionMultipleShardingContext, UserEntity data) {
        printContext.printProcessDataMessage(data);
        logger.debug("UploadLocationMessageJob：正在处理：" + data.getId() + data.getRealName());

        try {
            // 睡眠100ms
            Thread.sleep(100);

            List<Long> userIdList = new ArrayList<Long>();
            userIdList.add(data.getId());
            List<DeviceEntity> deviceList = deviceService.getDeviceTypeAndUserName(userIdList);

            // 每隔10分钟，系统自动向正在出行的用户推送上传位置信息
            Map<String, String> param = new HashMap<String,String>();
            param.put("type", "4");
            pushService.pushMIMessageToGuide(deviceList, "位置上传", param);  // 推送至领队端
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
