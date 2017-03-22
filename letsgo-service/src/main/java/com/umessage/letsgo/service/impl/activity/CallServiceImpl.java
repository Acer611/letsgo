package com.umessage.letsgo.service.impl.activity;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.annotation.dataitem.DataItem;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.dao.activity.CallDao;
import com.umessage.letsgo.dao.security.DeviceDao;
import com.umessage.letsgo.domain.po.activity.*;
import com.umessage.letsgo.domain.po.security.DeviceEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.GroupEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.activity.request.CallDetailRequest;
import com.umessage.letsgo.domain.vo.activity.request.CallRequest;
import com.umessage.letsgo.domain.vo.activity.response.CallResponse;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.service.api.activity.*;
import com.umessage.letsgo.service.api.system.IPushService;
import com.umessage.letsgo.service.api.team.IGroupService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.constant.Constant;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CallServiceImpl implements ICallService {
	@Resource
	private CallDao callMapper;
	@Resource
	private ICallDetailService callDetailService;
	@Resource
	private IMemberService memberService;
	@Resource
	private ITeamService teamService;
	@Resource
	private IGroupService groupService;
	@Resource
	private ICallHistoryService callHistoryService;
	@Resource
	private ICallDetailHistoryService callDetailHistoryService;
	@Resource
	private ILocationService locationService;
	@Resource
	private Mapper dozerBeanMapper;
	@Resource
	private IPushService pushService;
	@Resource
	private DeviceDao deviceDao;

	@Override
	public int addCall(CallEntity callEntity) {
		callEntity.setStatus(0);
		callEntity.setArrivedCount(0);
		callEntity.setCreateTime(new Date());
		callEntity.setVersion(0l);
		return callMapper.insert(callEntity);
	}

	@Override
	public int updateCall(CallEntity callEntity) {
		callEntity.setUpdateTime(new Date());
		return callMapper.update(callEntity);
	}

	@Override
	public CallEntity getCall(Long id) {
		CallEntity entity = callMapper.select(id);
		if(entity == null){
			entity = new CallEntity();
		}
		return entity;
	}

	@Override
	@DataItem
	@Transactional
	public CallResponse createCall(String teamId, UserEntity user,LocationEntity leaderLocation) throws Exception {
		// 判断权限
		List<Integer> roles = teamService.roleStatus(teamId, user.getId());
		Integer teamStatus = teamService.teamStatus(teamId);
		if (teamStatus == null || roles.get(0) ==null || roles.get(1) == null){
			throw new BusinessException(ErrorConstant.NOT_FOUND, "没有发现团队或成员！");
		}
		if (teamStatus == 3 || roles.get(1) == 0) {
			return geCallResponse(roles,teamStatus);
		}
		List<MemberEntity> members = memberService.getTouristList(teamId);
		if (CollectionUtils.isEmpty(members)) {
			return CallResponse.createNotFoundResponse("未找到团队成员信息");
		}
		//结束该领队之前未结束的点名
		finishCalls(user);
		//创建一个点名对象
		CallEntity call = saveCall(teamId,user,leaderLocation);
		//分别为团队的游客成员和组新建一条点名明细
		if(leaderLocation == null){
			createCallDetailList(members,call);
		}else {
			createCallDetailList(members, call,leaderLocation);
		}
		//获取点名已到未到列表
		CallResponse callResponse = getCallDetailList(call.getId());
		callResponse.setRetMsg("已经开始点名啦！");
		callResponse.setTeamStatus(teamStatus);
		callResponse.setRoleStatus(roles.get(0));
		callResponse.setAdminStatus(roles.get(1));
		//自动点名时,给未到的成员推送信息
		pushCallMessage(callResponse,leaderLocation == null);
		return callResponse;
	}
	@Override
	@DataItem
	public CallResponse createManualCall(String teamId, UserEntity user) throws Exception {
		return createCall(teamId,user,null);
	}
	/**
	 *结束用户之前未结束的点名
     */
	private void finishCalls(UserEntity user){
		List<CallEntity> list = selectCallsByleadUserId(user);
		for (CallEntity call:list){
			finishCall(call.getId());
		}
	}

	/**
	 * 保存一个点名实体
     */
	private CallEntity saveCall(String teamId,UserEntity user,LocationEntity leaderLocation){
		TeamEntity team = teamService.getTeamByTXGroupId(teamId);
		CallEntity call = new CallEntity();
		call.setTeamId(teamId);
		call.setNotarrivedCount(team.getTotalCount());
		call.setUserId(user.getId());
		if(leaderLocation != null){
			call.setLocationId(leaderLocation.getId());
		}
		addCall(call);
		return call;
	}

	/**
	 * 给自动点名未到的游客发送点名推送
     */
	private void pushCallMessage(CallResponse callResponse,boolean isManualCall) throws Exception {
		if(callResponse.getCallEntity() == null || isManualCall) return;
		List<CallDetailEntity> notArrivedList = callResponse.getCallEntity().getNotarrivedList();
		//将已到达的成员添加到推送中 用于调试功能
		//notArrivedList.addAll(callResponse.getCallEntity().getArrivedList());

		if (!CollectionUtils.isEmpty(notArrivedList)){
			List<Long> userIdList = new ArrayList<Long>();
			for (CallDetailEntity detail:notArrivedList){
				Long userId = detail.getUserId();
				if (userId != null) {
					userIdList.add(userId);
				}
			}
			if(!userIdList.isEmpty()){
				try {
					List<DeviceEntity> deviceList = deviceDao.selectDeviceWithUserIds(userIdList);
					Map<String, String> param = new HashMap<>();
					param.put("type", "5");
					pushService.pushMIMessageToGuide(deviceList, "开始点名啦，小主快快打开跟上APP告诉我们您在哪儿吧", param);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}
	private CallResponse geCallResponse(List<Integer> roles,Integer teamStatus){
		CallResponse callResponse = new CallResponse();
		callResponse.setTeamStatus(teamStatus);
		callResponse.setRoleStatus(roles.get(0));
		callResponse.setAdminStatus(roles.get(1));
		return callResponse;
	}
	/**
	 * 生成手动点名明细
	 */
	private void createCallDetailList(List<MemberEntity> members, CallEntity call) {
		for (MemberEntity member : members) {
			// 如果个人，则保存成员点名详细
			if (member.getGroupId() == -1) {
				CallDetailEntity callDetail = getMemberCallDetail(member,call.getId());
				callDetailService.addCallDetail(callDetail);
			}

			// 如果是组长，则保存小组点名详细
			if (member.getGroupId() > -1 && member.getIsLeader() == 1) {
				CallDetailEntity callDetail = getGroupCallDetail(member,call.getId());
				callDetailService.addCallDetail(callDetail);
			}
		}
	}

	/**
	 * 生成点名明细
	 * 
	 * @param members 团队所有成员
	 * @param call 点名实体
	 * @param leaderLocation 领队位置
	 *
	 */
	private void createCallDetailList(List<MemberEntity> members, CallEntity call,LocationEntity leaderLocation) {
		int signCount = 0;
		for (MemberEntity member : members) {
			// 如果个人，则保存成员点名详细
			if (member.getGroupId() == -1) {
				CallDetailEntity callDetail = getMemberCallDetail(member,call.getId(),leaderLocation);
				//已到人数 +1
				if(callDetail.getStatus() == Constant.SING_STATUS_TRUE){
					signCount ++;
				}
				callDetailService.addCallDetail(callDetail);
			}

			// 如果是组长，则保存小组点名详细
			if (member.getGroupId() > -1 && member.getIsLeader() == 1) {
				CallDetailEntity callDetail = getGroupCallDetail(member,call.getId(),leaderLocation);
				//已到人数 + 组员数量
				if(callDetail.getStatus() == Constant.SING_STATUS_TRUE){
					signCount += callDetail.getGroupCount();
				}
				callDetailService.addCallDetail(callDetail);
			}
		}

		call.setArrivedCount(signCount);
		call.setNotarrivedCount(members.size() - signCount);
		updateCall(call);
	}

	/**
	 * 获得成员组点名详细
	 * @param member 成员实体(组长)
	 * @param callId 点名id
	 * @param leaderLocation 领队位置
     * @return 成员组点名详细
     */
	public CallDetailEntity getGroupCallDetail(MemberEntity member,Long callId,LocationEntity leaderLocation){
		CallDetailEntity callDetail = createCallDetail(member,callId,leaderLocation);

		GroupEntity group = groupService.getGroup(member.getGroupId());
		callDetail.setName(group.getName());
		callDetail.setGroupId(group.getId());
		callDetail.setGroupCount(group.getTotalCount());

		return callDetail;
	}
	/**
	 * 获得成员组点名详细
     */
	public CallDetailEntity getGroupCallDetail(MemberEntity member,Long callId){
		return getGroupCallDetail(member,callId,null);
	}

	/**
	 * 获得成员个人点名详细。
	 * @param member 成员实体
	 * @param callId 点名id
	 * @param leaderLocation 领队位置
	 * @return 成员个人点名详细
	 */
	public CallDetailEntity getMemberCallDetail(MemberEntity member,Long callId,LocationEntity leaderLocation){
		CallDetailEntity callDetail = createCallDetail(member,callId,leaderLocation);
		callDetail.setName(member.getRealName());
		callDetail.setGroupId(member.getGroupId());

		return callDetail;
	}
	/**
	 * 获得成员个人点名详细。
	 */
	public CallDetailEntity getMemberCallDetail(MemberEntity member,Long callId){
		return getMemberCallDetail(member,callId,null);
	}

	/**
	 * 创建点名详细
	 * @param member 成员实体
	 * @param callId 点名id
	 * @param leaderLocation 领队位置
     * @return 点名详细
     */
	public CallDetailEntity createCallDetail(MemberEntity member,Long callId,LocationEntity leaderLocation){
		CallDetailEntity callDetail = new CallDetailEntity();
		callDetail.setCallId(callId);
		callDetail.setUserId(member.getUserId());
		callDetail.setMemberId(member.getId());

		if(leaderLocation == null){
			callDetail.setIsManual(1);
			callDetail.setDistance(-1D);
			callDetail.setStatus(0);
		}else{
			callDetail.setIsManual(0);
			callDetail = setSignDetail(callDetail,member,leaderLocation);
		}
		return callDetail;
	}

	/**
	 * 设置签到方面属性
	 * @param callDetail 点名详细
	 * @param member 成员实体
	 * @param leaderLocation 领队位置
     * @return 点名详细
     */
	public CallDetailEntity setSignDetail(CallDetailEntity callDetail,MemberEntity member,LocationEntity leaderLocation){
		LocationEntity memberLocation = locationService.getLocationByUserId(member.getUserId());
		double distance = getSignDistance(memberLocation,leaderLocation);
		int signStatus = getSignStatus(distance);

		callDetail.setCallTime(new Date());
		callDetail.setDistance(distance);
		callDetail.setStatus(signStatus);

		return callDetail;
	}
	/**
	 *根据成员位置和领队位置计算距离
	 * @param memberLocation 成员位置
	 * @param leaderLocation 领队位置
	 * @return 距离 米
	 */
	public double getSignDistance(LocationEntity memberLocation,LocationEntity leaderLocation){
		if(memberLocation.getFinishTime() == null){
			return Constant.SING_DISTANCE_NONE;
		}
		//最后一次上传位置的时间必须是10（假定）分钟内，位置才可用作签到。
		Date date =addMinute(memberLocation.getFinishTime(),Constant.SING_TIME);
		if(date.before(new Date())){
			return Constant.SING_DISTANCE_NONE;
		}
		return getDistance(memberLocation.getLatitude(),memberLocation.getLongitude(),leaderLocation.getLatitude(),leaderLocation.getLongitude());
	}
	/**
	 *根据距离判断是否签到。
	 * @param distance 距离
     * @return 成员签到状态：1到，0未到。
     */
	public int getSignStatus(double distance){
		if(distance != Constant.SING_DISTANCE_NONE && distance < Constant.SING_DISTANCE){
			return Constant.SING_STATUS_TRUE;
		}
		return Constant.SING_STATUS_FALSE;
	}

	/**
	 * 返回N分钟后的时间。
	 * @param date	时间
	 * @param minute 增加分钟数
     * @return	时间
     */
	public Date addMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	/**
	 * 谷歌地图算法：根据经纬度计算两点之间的距离
	 * @param lat1 纬度1
	 * @param lng1 经度1
	 * @param lat2 纬度2
	 * @param lng2 经度2
	 * @return 距离 Km
     */
	private double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = Math.toRadians(lat1);
		double radLat2 = Math.toRadians(lat2);
		double a = radLat1 - radLat2;
		double b = Math.toRadians(lng1) - Math.toRadians(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
				Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * 6378.137;//地球赤道半径：6378.137 Km
		s = Math.round(s * 100) / 100d;
		return s;
	}

	@Override
	public CommonResponse finishCall(Long id) {
		// 1、根据点名ID，获取正在进行的点名
		CallEntity call = selectCallWithIdAndStatus(id,0);
		if (null == call) {
			return CommonResponse.createNotFoundResponse("该点名已经结束或没有找到对应的点名");
		}
		// 2、设置点名状态，更新点名
		call.setStatus(1);
		this.updateCall(call);
		// 3、把点名复制到点名历史中
		CallHistoryEntity callHistoryEntity = dozerBeanMapper.map(call, CallHistoryEntity.class);
		callHistoryService.add(callHistoryEntity);
		for (CallDetailEntity callDetail : call.getCallDetailList()) {
			CallDetailHistoryEntity history = dozerBeanMapper.map(callDetail, CallDetailHistoryEntity.class);
			callDetailHistoryService.add(history);
		}

		CommonResponse response = new CommonResponse();
		response.setRetMsg("结束点名成功！");
		return response;
	}

	@Override
	@DataItem
	public CallResponse getCallDetailList(Long callId) {
		// 1、获取点名信息
		CallEntity call = selectCallWithIdAndStatus(callId,0);
		if (null == call) {
			return CallResponse.createNotFoundResponse();
		}
		// 2、获取已到的点名明细列表
		CallDetailRequest request = new CallDetailRequest();
		request.setCallId(callId);

		request.setStatus(Constant.SING_STATUS_TRUE);
		List<CallDetailEntity> arrivedList = callDetailService.getSignedCallDetails(request);
		call.setArrivedList(arrivedList);

		// 3、获取未到的点名明细列表
		request.setStatus(Constant.SING_STATUS_FALSE);
		List<CallDetailEntity> notArrivedList = callDetailService.getSignedCallDetails(request);
		call.setNotarrivedList(notArrivedList);

		CallResponse response = new CallResponse();
		response.setCallEntity(call);
		return response;
	}

	public CallEntity selectCallWithIdAndStatus(Long callId,Integer status){
		CallRequest request = new CallRequest();
		request.setStatus(status);
		request.setId(callId);
		return callMapper.selectCallWithIdAndStatus(request);
	}

//	/**
//	 * 设置点名详细中组的头像
//	 * @param list 点名详细列表
//	 * @return 点名详细列表
//	 */
//	private List<CallDetailEntity> setGroupPhotoUrl(List<CallDetailEntity> list) {
//		for (CallDetailEntity callDetailEntity : list) {
//			if (callDetailEntity.getGroupId() > -1) {
//				GroupEntity groupEntity = groupService.getGroup(callDetailEntity.getGroupId());
//				callDetailEntity.setPhotoUrl(groupEntity.getPhotoUrl());
//			}
//		}
//		return list;
//	}

	@Override
	public CommonResponse updateCallDetailList(UserEntity userEntity, LocationEntity memberLocation){
		List<CallEntity> callList = selectCallsByMemberUserId(userEntity);
		if (callList.isEmpty()){
			return CommonResponse.createNotFoundResponse("当前没有正在进行的点名");
		}

		for (CallEntity call : callList) {
			CallDetailEntity callDetail = call.getCallDetailList().get(0);
			if(callDetail == null || callDetail.getIsManual() == 1) break;

			LocationEntity leadLocation = locationService.getLocationByUserId(call.getUserId());
			if(leadLocation.getLongitude() == null) break;

			double distance = getDistance(leadLocation.getLatitude(),leadLocation.getLongitude(),memberLocation.getLatitude(),memberLocation.getLongitude());
			int signStatus = getSignStatus(distance);
			int originalStatus = callDetail.getStatus();

			callDetail.setCallTime(new Date());
			callDetail.setDistance(distance);
			callDetail.setStatus(signStatus);
			callDetailService.updateCallDetail(callDetail);

			//签到状态变时需要更新已到未到人数
			if(originalStatus != signStatus){
				call = setSignArrivedNumber(callDetail,call);
				updateCall(call);
			}
		}
		CommonResponse callResponse = new CommonResponse();
		callResponse.setRetMsg("上传位置签到成功");
		return callResponse;
	}

	@Override
	public Page<CallEntity> selectAll() {
		return callMapper.selectAll();
	}

	private CallEntity setSignArrivedNumber(CallDetailEntity callDetail,CallEntity call){
		if(callDetail.getGroupId() > -1){
			if(callDetail.getStatus() == Constant.SING_STATUS_TRUE){
				call.setArrivedCount(call.getArrivedCount()+callDetail.getGroupCount());
				call.setNotarrivedCount(call.getNotarrivedCount()-callDetail.getGroupCount());
			}
			if(callDetail.getStatus() == Constant.SING_STATUS_FALSE){
				call.setArrivedCount(call.getArrivedCount()-callDetail.getGroupCount());
				call.setNotarrivedCount(call.getNotarrivedCount()+callDetail.getGroupCount());
			}

		}else{
			if(callDetail.getStatus() == Constant.SING_STATUS_TRUE){
				call.setArrivedCount(call.getArrivedCount()+1);
				call.setNotarrivedCount(call.getNotarrivedCount()-1);
			}
			if(callDetail.getStatus() == Constant.SING_STATUS_FALSE){
				call.setArrivedCount(call.getArrivedCount()-1);
				call.setNotarrivedCount(call.getNotarrivedCount()+1);
			}
		}
		return call;
	}
	/**
	 * 获得游客正在参与当前点名的点名信息
	 * @param userEntity 游客用户
     * @return 游客点名信息
     */
	private List<CallEntity> selectCallsByMemberUserId(UserEntity userEntity){
		CallRequest callRequest = new CallRequest();
		callRequest.setUserId(userEntity.getId());
		callRequest.setStatus(0);
		List<CallEntity> callList = callMapper.selectCallListWithConditons(callRequest);
		if(callList == null){
			callList = Collections.emptyList();
		}
		return callList;
	}

	/**
	 * 根据领队id获得之前自己未完成的点名
	 * @param userEntity 领队id
	 * @return 点名信息
	 */
	private List<CallEntity> selectCallsByleadUserId(UserEntity userEntity){
		CallRequest callRequest = new CallRequest();
		callRequest.setLeadUserId(userEntity.getId());
		callRequest.setStatus(0);
		List<CallEntity> callList = callMapper.selectCallListWithConditons(callRequest);
		if(callList == null){
			callList = Collections.emptyList();
		}
		return callList;
	}
}
