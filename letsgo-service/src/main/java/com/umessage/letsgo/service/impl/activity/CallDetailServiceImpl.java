package com.umessage.letsgo.service.impl.activity;

import com.umessage.letsgo.dao.activity.CallDetailDao;
import com.umessage.letsgo.domain.po.activity.CallDetailEntity;
import com.umessage.letsgo.domain.po.activity.CallEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.activity.request.CallDetailRequest;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.service.api.activity.ICallDetailService;
import com.umessage.letsgo.service.api.activity.ICallService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class CallDetailServiceImpl implements ICallDetailService {
	@Resource
	private CallDetailDao callDetailMapper;
	@Resource	private ICallService callService;

	@Override
	public int addCallDetail(CallDetailEntity callDetailEntity) {
		callDetailEntity.setCreateTime(new Date());
		callDetailEntity.setVersion(0l);
		return callDetailMapper.insert(callDetailEntity);
	}

	@Override
	public int updateCallDetail(CallDetailEntity callDetailEntity) {
		callDetailEntity.setUpdateTime(new Date());
		return callDetailMapper.update(callDetailEntity);
	}

	@Override
	public List<CallDetailEntity> getCallDetailList(CallDetailRequest request) {
		List<CallDetailEntity> list = callDetailMapper.selectCallDetailListWithConditions(request);
		if(list == null){
			list = Collections.emptyList();
		}
		return list;
	}

	@Override
	public List<CallDetailEntity> getSignedCallDetails(CallDetailRequest request){
		List<CallDetailEntity> list = callDetailMapper.selectSignCallDetails(request);
		if(list == null){
			list = Collections.emptyList();
		}
		for (CallDetailEntity callDetailEntity:list) {
			MemberEntity memberEntity = callDetailEntity.getMemberEntity();
			memberEntity = this.changePhotoUrl(memberEntity);
		}
		return list;
	}


	/**
	 * 设置团队头像地址为用户头像地址 lizhen
	 * @param memberEntity
	 * @return
	 */
	private MemberEntity changePhotoUrl(MemberEntity memberEntity){
		if(null == memberEntity){
			return memberEntity;
		}
		UserEntity userEntity = memberEntity.getUserEntity();
		if (userEntity != null){
			String photoUrl = userEntity.getPhotoUrl();
			//判断关联用户的头像地址是否为空，不为空设置团队头像地址为用户头像地址
			if (!StringUtils.isEmpty(photoUrl)){
				memberEntity.setPhotoUrl(photoUrl);
			}
		}

		return memberEntity;
	}


	@Override
	public CommonResponse setSignStatus(Long id, Integer status) {
		// 1、根据点名明细ID，获取点名明细
		CallDetailEntity callDetail = callDetailMapper.select(id);
		if (null == callDetail) {
			return CommonResponse.createNotFoundResponse("找不到对应的点名明细");
		}

		int originalStatus = callDetail.getStatus();
		if(originalStatus == status){
			return new CommonResponse();
		}
		// 2、设置手动签到，签到状态及签到时间，更新点名明细
		callDetail.setIsManual(1);
		callDetail.setStatus(status);
		callDetail.setCallTime(new Date());
		this.updateCallDetail(callDetail);

		// 3、找到对应的点名，更新点名中的已到人数和未到人数
		CallEntity call = callService.getCall(callDetail.getCallId());

		// 签到情况
		CommonResponse response = new CommonResponse();
		if (status == 1) { 
			this.signIn(callDetail, call);
			response.setRetMsg("签到成功！");
			return response;
		}
		
		this.signOut(callDetail, call);
		response.setRetMsg("取消成功！");
		return response;
	}

	/**
	 * 签到，更改点名中的已到人数和未到人数
	 * @param callDetail
	 * @param call
     */
	private void signIn(CallDetailEntity callDetail, CallEntity call) {
		// 判断是小组还是个人
		if (callDetail.getGroupId() == -1) {	// 个人，已到人数加1，未到人数减1
			call.setArrivedCount(call.getArrivedCount() + 1);
			call.setNotarrivedCount(call.getNotarrivedCount() - 1);
		} else {	// 小组，已到人数加小组总数，未到人数减小组人数
			call.setArrivedCount(call.getArrivedCount() + callDetail.getGroupCount());
			call.setNotarrivedCount(call.getNotarrivedCount() - callDetail.getGroupCount());
		}
		callService.updateCall(call);
	}

	/**
	 * 未到，更改点名中的已到人数和未到人数
	 * @param callDetail
	 * @param call
     */
	private void signOut(CallDetailEntity callDetail, CallEntity call) {
		// 判断是小组还是个人
		if (callDetail.getGroupId() == -1) {	// 个人，已到人数减1，未到人数加1
			call.setArrivedCount(call.getArrivedCount() - 1);
			call.setNotarrivedCount(call.getNotarrivedCount() + 1);
		} else {	// 小组，已到人数减小组总数，未到人数加小组人数
			call.setArrivedCount(call.getArrivedCount() - callDetail.getGroupCount());
			call.setNotarrivedCount(call.getNotarrivedCount() + callDetail.getGroupCount());
		}
		callService.updateCall(call);
	}

}
