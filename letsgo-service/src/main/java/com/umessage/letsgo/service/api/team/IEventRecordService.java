package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.team.EventRecordEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.team.requset.EventRecordRequest;
import com.umessage.letsgo.domain.vo.team.respone.EventRecordPageResponse;
import com.umessage.letsgo.domain.vo.team.respone.EventRecordResponse;

import java.util.List;

public interface IEventRecordService {
	// 新增
	int add(EventRecordEntity eventRecordEntity);
	// 删除
	int delete(long id);
	// 更新
	int update(EventRecordEntity eventRecordEntity);
	// 获取单个
	EventRecordEntity getEventRecordEntity(long id);
	// 获取列表
	EventRecordResponse getEventRecordEntitys(EventRecordRequest request);

	List<EventRecordEntity>  getEventRecordList(EventRecordRequest request);

	EventRecordPageResponse getEventList(EventRecordRequest request);
	//新增事件记录
	CommonResponse addEventRecord(EventRecordRequest request);
}
