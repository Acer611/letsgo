package com.umessage.letsgo.service.api.activity;

import com.umessage.letsgo.domain.po.activity.CallDetailEntity;
import com.umessage.letsgo.domain.vo.activity.request.CallDetailRequest;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

public interface ICallDetailService {
	// 新增点名明细
	int addCallDetail(CallDetailEntity callDetailEntity);
	
	// 更新点名明细
	int updateCallDetail(CallDetailEntity callDetailEntity);
	
	// 获取点名明细列表
	List<CallDetailEntity> getCallDetailList(CallDetailRequest request);

	// 设置是否签到
	CommonResponse setSignStatus(Long callDetailId, Integer status);
	// 获得已签到的点名详细id.
	List<CallDetailEntity> getSignedCallDetails(CallDetailRequest request);
}
