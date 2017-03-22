package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.ScheduleDetailScenicEntity;
import com.umessage.letsgo.domain.po.team.ScenicAgencyEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScenicResponse;

/**
 * 旅行社端每日行程景点信息管理接口
 * Created by gaofei on 2017/2/15.
 */

public interface IScheduleDetailScenicService {
    /**
     * 保存每日景点信息 到景点私有库
     * @param travelEntity 旅行社信息
     * @param scenicEntity 私有库景点信息
     * @return
     */
    public ScenicResponse saveScheduleDetailScenicToTravel(TravelAgencyEntity travelEntity,ScenicAgencyEntity scenicEntity) ;

    /**
     * 保存每日景点信息 到景点每日行程关联关系表
     * @param travelEntity 旅行社信息
     * @param scheduleDetailScenic 景点信息
     * @return
     */
    public CommonResponse saveScheduleDetailScenicToTable(TravelAgencyEntity travelEntity,ScheduleDetailScenicEntity scheduleDetailScenic) ;


    /**
     * 根据ID 删除景点信息，每日行程景点信息关联关系表
     * @param id
     * @return
     */
    CommonResponse removeScheduleDetailScenic(Long id);

    /**
     * 修改每日行程景点关联关系表数据
     * @param scheduleDetailScenic
     * @return
     */
    CommonResponse modifyScheduleDetailScenic( TravelAgencyEntity travelEntity,ScheduleDetailScenicEntity scheduleDetailScenic);

    /**
     * 模糊匹配景点名称，查询景点列表
     * @param name
     * @param cities
     */
    ScenicResponse searchScenic(String name, String cities,TravelAgencyEntity travelEntity);

    /**
     * 根据ID查询关联数据
     * @param scenId
     * @return
     */
    ScheduleDetailScenicEntity getScenicByScenicId(Long scenId);

    /**
     * 根据ID 删除景点私有库的数据信息
     * @param id
     * @return
     */
    ScenicResponse deleteScheduleDetailScenic(Long travelId,Long id);

    /**
     * 根据每日行程的ID删除景点信息
     * @param id
     */
    void deleteScenicByScheduldId(Long id);


}
