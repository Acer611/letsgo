package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.dao.journey.ScheduleDescDao;
import com.umessage.letsgo.domain.po.journey.ScheduleDescEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleDescResponse;
import com.umessage.letsgo.service.api.journey.IScheduleDescService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 行程其他说明service实现类
 * Created by gaofei on 2017/2/20.
 */
@Service
public class ScheduleDescServiceImpl implements IScheduleDescService {

    private Logger logger = Logger.getLogger(ScheduleDescServiceImpl.class);
    @Resource
    private ScheduleDescDao scheduleDescDao;

    /**
     * 添加行程其他说明信息
     * @param scheduleDetailDescs
     * @return
     */
    @Override
    public ScheduleDescResponse createScheduleDesc(List<ScheduleDescEntity> scheduleDetailDescs) {
        if (scheduleDetailDescs != null){
            for (ScheduleDescEntity scheduleDescEntity:scheduleDetailDescs) {
                //验证字段长度
                String title = scheduleDescEntity.getTitle();//其他说明标题
                String content = scheduleDescEntity.getContent();//其他说明内容
                if (!StringUtils.isEmpty(title) && title.length() > 500){
                    return ScheduleDescResponse.createInvalidParameterResponse("其他说明标题内容过长，请重新输入");
                }
                if (!StringUtils.isEmpty(content) && content.length() > 20000){
                    return ScheduleDescResponse.createInvalidParameterResponse("其他说明内容过长，请重新输入");
                }
            }
            for (ScheduleDescEntity scheduleDescEntity:scheduleDetailDescs) {
                scheduleDescEntity.setCreateTime(new Date());
                scheduleDescDao.insert(scheduleDescEntity);
            }
        }
        return new ScheduleDescResponse();
    }

    /**
     *
     * 根据行程的ID获取行程其他说明的信息列表
     * @param scheduleId
     * @return
     */
    @Override
    public List<ScheduleDescEntity> getScheduleDescByScheduleId(Long scheduleId) {
        List<ScheduleDescEntity> scheduleDescEntityList =  scheduleDescDao.getScheduleDescByScheduleId(scheduleId);
        return scheduleDescEntityList;
    }

    /**
     * 根据id 删除行程其他说明
     * @param id
     * @return
     */
    @Override
    public CommonResponse deleteScheduleDescById(Long id) {
        scheduleDescDao.delete(id);
        return new CommonResponse();
    }

    /**
     * 修改行程其他说明
     * @param scheduleDetailDesc
     * @return
     */
    @Override
    public CommonResponse updateScheduleDesc(ScheduleDescEntity scheduleDetailDesc) {
        //验证字段长度
        String title = scheduleDetailDesc.getTitle();//其他说明标题
        String content = scheduleDetailDesc.getContent();//其他说明内容
        if (!StringUtils.isEmpty(title) && title.length() > 500){
            return ScheduleDescResponse.createInvalidParameterResponse("其他说明标题内容过长，请重新输入");
        }
        if (!StringUtils.isEmpty(content) && content.length() > 20000){
            return ScheduleDescResponse.createInvalidParameterResponse("其他说明内容过长，请重新输入");
        }
        scheduleDetailDesc.setUpdateTime(new Date());
        scheduleDescDao.update(scheduleDetailDesc);
        return new CommonResponse();
    }

    @Override
    public int deleteByScheduleId(Long scheduleId) {
        return scheduleDescDao.deleteByScheduleId(scheduleId);
    }

    @Override
    public ScheduleDescEntity select(Long id) {
        return scheduleDescDao.select(id);
    }
}
