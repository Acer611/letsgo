package com.umessage.letsgo.service.api.activity;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.activity.CallEntity;
import com.umessage.letsgo.domain.po.activity.LocationEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.activity.response.CallResponse;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

/**
 * 点名Service接口
 * @author Administrator
 *
 */
public interface ICallService {
	// 新增点名
	int addCall(CallEntity callEntity);
	
	// 更新点名
	int updateCall(CallEntity callEntity);

	// 获取单个点名
	CallEntity getCall(Long id);
	
	// 创建点名，在有领队位置时
	CallResponse createCall(String teamId, UserEntity user, LocationEntity locationEntity) throws Exception;
	// 创建手动点名，在无领队位置时
	CallResponse createManualCall(String teamId, UserEntity user) throws Exception;
	// 获取点名详情列表
	CallResponse getCallDetailList(Long id);
	
	// 完成点名
	CommonResponse finishCall(Long id);

	CommonResponse updateCallDetailList(UserEntity userEntity, LocationEntity locationEntity);

	Page<CallEntity> selectAll();
}
