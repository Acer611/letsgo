package com.umessage.letsgo.service.impl.journey;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qq.tim.ITimRestAPI;
import com.qq.tim.vo.request.*;
import com.qq.tim.vo.response.AddGroupMemberResponse;
import com.qq.tim.vo.response.CreateGroupResponse;
import com.umessage.letsgo.core.config.constant.ConfigConstant;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.dao.journey.AlbumScheduleDao;
import com.umessage.letsgo.dao.journey.ScheduleDao;
import com.umessage.letsgo.domain.po.journey.*;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.system.LogManage;
import com.umessage.letsgo.domain.po.system.RegionEntity;
import com.umessage.letsgo.domain.po.team.*;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.*;
import com.umessage.letsgo.domain.vo.journey.response.*;
import com.umessage.letsgo.domain.vo.journey.response.vo.TravelAgencyInfoVo;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.MemberRequest;
import com.umessage.letsgo.domain.vo.team.requset.WebMemberRequest;
import com.umessage.letsgo.service.api.journey.*;
import com.umessage.letsgo.service.api.notice.INoticeDetailService;
import com.umessage.letsgo.service.api.security.IDeviceService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.ILogManageService;
import com.umessage.letsgo.service.api.system.IMessageService;
import com.umessage.letsgo.service.api.system.IRegionService;
import com.umessage.letsgo.service.api.team.*;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.constant.HelpConstant;
import com.umessage.letsgo.service.common.helper.PhotoHelper;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ScheduleServiceImpl implements IScheduleService {

	private Logger logger = LogManager.getLogger(ScheduleServiceImpl.class.getName());

	@Resource
	private ScheduleDao scheduleDao;
	@Resource
	private IPromptInfoService promptService;
	@Resource
	private ITeamService teamService;
	@Resource
	private IMemberService memberService;
	@Resource
	private ITimRestAPI timRestAPI;
	@Resource
	private IUserService userService;
	@Resource
	private IDeviceService deviceService;
	@Resource
	private IDestinationService destinationService;
	@Resource
	private IMessageService messageService;
	@Resource
	private ITravelAgencyService travelService;
	@Resource
	private UserLoginHelper userLoginHelper;
	@Resource
	private INoticeDetailService noticeDetailService;
	@Resource
	private ILineEvaluateService lineEvaluateService;
	@Resource
	private IRegionService regionService;
	@Resource
	private ILeaderService leaderService;
	@Resource
	private IOnlookersUserService onlookersUserService;
	@Resource
	private IPromptInfoService iPromptInfoService;
	@Resource
	private IScheduleDetailsService scheduleDetailsServiceImpl;
	@Resource
	private IOnlookersDetailsService onlookersDetailsService;
	@Resource
	private ILogManageService logManageService;
	@Resource
	protected WxMpService wxMpService;

	@Resource
	private AlbumScheduleDao albumScheduleDao;
	@Resource
	private IAlbumScheduleService albumScheduleService;

	@Override
	public int addSchedule(ScheduleEntity scheduleEntity) {
		Date date = new Date();
		scheduleEntity.setCreateTime(date);
		scheduleEntity.setUpdateTime(date);
		scheduleEntity.setVersion(0L);
		//逻辑删除字段：0正常  1删除
		scheduleEntity.setDel(0);
		int i=scheduleDao.insert(scheduleEntity);
		return i;
	}

	@Override
	public ScheduleResponse scheduleSave(ScheduleEntity schedule) {
		if(schedule == null) return ScheduleResponse.createNotFoundResponse("没有行程");

		//验证字段长度
		String feature = schedule.getFeature();//行程特色
		if (!StringUtils.isEmpty(feature) && feature.length() > 20000){
			return ScheduleResponse.createInvalidParameterResponse("行程特色内容过长，请重新输入");
		}

		String emergencyContact = schedule.getEmergencyContact();//紧急联系人
		if (!StringUtils.isEmpty(emergencyContact) && emergencyContact.length() > 5000){
			return ScheduleResponse.createInvalidParameterResponse("紧急联系人内容过长，请重新输入");
		}

		ScheduleResponse verifyResponse = schedule.dataVerify();
		if (verifyResponse.getRetCode() != 0) {
			return verifyResponse;
		}
		TravelAgencyEntity travel = travelService.getCurrentTravel();
		UserResponse userResponse = userLoginHelper.getLoginUser();
		UserEntity user = userResponse.getUserEntity();
		if(user == null || user.getId() == null){
			throw new BusinessException(ErrorConstant.USER_NOT_LOGIN,"用户未登录或登录信息过期");
		}
		schedule.setUserId(user.getId());
		TeamEntity teamEntity = teamSave(schedule);
		schedule.setTeamId(teamEntity.getId());
		//1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
		schedule.setType(1);
		//创建行程未发布状态-发布状态：1全部-2已发布-3未发布-4待确认processStatus
		schedule.setProcessStatus(3);
		addSchedule(schedule);
		//如果有领队，将领队添加到成员表中。
		MemberEntity memberEntity=null;
		if(schedule.getLeaderId() != null && !"".equals(schedule.getLeaderId())){
			 memberEntity=addMemberForLeader(schedule,teamEntity);
			teamEntity.setTotalCount(teamEntity.getTotalCount()+1);
			teamService.updateTeam(teamEntity);
		}
		ScheduleResponse scheduleResponse = new ScheduleResponse();
		scheduleResponse.setScheduleEntity(schedule);
		//添加日志
		addScheduleLog(schedule,teamEntity,memberEntity);
		return scheduleResponse;
	}


	@Override
	public ScheduleResponse scheduleUpdate(ScheduleEntity schedule) {
		if(schedule == null) return ScheduleResponse.createNotFoundResponse("没有行程");

		//验证字段长度
		String feature = schedule.getFeature();//行程特色
		if (!StringUtils.isEmpty(feature) && feature.length() > 20000){
			return ScheduleResponse.createInvalidParameterResponse("行程特色内容过长，请重新输入");
		}

		String emergencyContact = schedule.getEmergencyContact();//紧急联系人
		if (!StringUtils.isEmpty(emergencyContact) && emergencyContact.length() > 5000){
			return ScheduleResponse.createInvalidParameterResponse("紧急联系人内容过长，请重新输入");
		}

		ScheduleResponse verifyResponse = schedule.dataVerify();
		if (verifyResponse.getRetCode() != 0) {
			return verifyResponse;
		}
		TeamEntity teamEntity = teamUpdate(schedule);
		ScheduleEntity originalSchedule = getSchedule(schedule.getId());

		// 如果是修改领队  则需要删除团里原来领队
		if (schedule.getLeaderId() != null &&
				!schedule.getLeaderId().equals(originalSchedule.getLeaderId())) {

			Integer type = originalSchedule.getType();
			Long originalLeaderId = originalSchedule.getLeaderId();

			// 如果原领队ID不为空, 则从member里面删除原领队
			if (null != originalLeaderId) {
				deleteMemberForLeader(originalSchedule);
			}

			//将新领队添加到成员表中
			MemberEntity member = addMemberForLeader(schedule, teamEntity);

			// 如果行程已经确认了, 则需要更新IM群组成员
			// 如果行程还没有确认, IM群组中还没有成员, 因此不需要处理
			if (originalSchedule.getProcessStatus() == 2) {
				try {
					// 如果原领队ID不为空, 则从IM群里删除原有的领队
					if (null != originalLeaderId) {
						LeaderEntity leaderEntity = leaderService.getLeader(originalLeaderId);
						UserEntity use = userService.getUserById(leaderEntity.getUserId());
						deleteGroupMember(teamEntity.getTxGroupid(), use.getUserName());
					}

					// 将新添加的领队加到 IM群组, 并且更新团队成员信息,发送邀请短信等
					UserEntity user = userService.getUserByPhone(member.getPhone());
					if (user != null) {
						member.setUserId(user.getId());
						member.setJoinStatus(1);
						member.setTeamId(teamEntity.getTxGroupid());
						memberService.updateMember(member);

						MemberEntity memberEntity = memberService.getMember(member.getId());
						addTXGroup(memberEntity, user, teamEntity.getTxGroupid());

						//发送提醒
						userService.sendInvitationMessage(member, teamEntity.getTxGroupid());
						//重新生成团头像
						String teamImageUrl = generateTeamImg(teamEntity);
						teamEntity.setPhotoUrl(teamImageUrl);
						teamService.updateTeam(teamEntity);
						// 更改IM中的群头像
						ModifyGroupBaseInfoRequest request = new ModifyGroupBaseInfoRequest();
						request.setGroupId(teamEntity.getTxGroupid());
						request.setFaceUrl(teamImageUrl);
						timRestAPI.modifyGroupBaseInfo(request);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		updateSchedule(schedule,originalSchedule);
		ScheduleResponse response = new ScheduleResponse();
		response.setScheduleEntity(originalSchedule);
		//添加日志
		originalSchedule.setVersion(originalSchedule.getVersion()+1);
		updateScheduleLog(originalSchedule,teamEntity);
		return response;
	}

	//保存团队表
	private TeamEntity teamSave(ScheduleEntity schedule){
		TeamEntity teamEntity = new TeamEntity();
		teamEntity.setTotalCount(0);
		teamEntity.setName(schedule.getName());
		teamEntity.setTeamNum(schedule.getTeamNum());
		//添加开始结束日期
		teamEntity.setStartDate(schedule.getStartDate());
		teamEntity.setEndDate(schedule.getEndDate());
		UserResponse userResponse = userLoginHelper.getLoginUser();
		UserEntity user = userResponse.getUserEntity();
		if(user == null || user.getId() == null){
			throw new BusinessException(ErrorConstant.USER_NOT_LOGIN,"用户未登录或登录信息过期");
		}
//		TravelAgencyEntity travel = travelService.getByUserId(user.getId());
		TravelAgencyEntity travel = travelService.getByTravelerId(user.getTravelerId());
		teamEntity.setTravelId(travel.getId());
		teamService.addTeam(teamEntity);

		return teamEntity;
	}
	//将指定领队加入成员表中。
	public MemberEntity addMemberForLeader(ScheduleEntity schedule, TeamEntity teamEntity){
		MemberEntity member = new MemberEntity();
		member.setRealName(schedule.getLeadName());
		member.setSex(schedule.getSex());
		member.setPhone(schedule.getPhone());
		member.settId(teamEntity.getId());//团队id
		member.setRole(1);//领队
		member.setIsAdmin(1);//管理员
		member.setIsNewJoin(0);//是否为新加入（即为自己通过扫描填写资料加入）【0：否；1：是】
		try {
			if(member.getSex()!=null&&member.getSex()==1){
				member.setPhotoUrl(ConfigConstant.MALE_HEAD_URL);
			}else{
				member.setPhotoUrl(ConfigConstant.FEMALE_HEAD_URL);
			}
//			String photoUrl = PhotoHelper.createMemberImage(member.getRealName(),member.getSex());
//			member.setPhotoUrl(photoUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		memberService.addMember(member);
		return member;
	}
	//更新团队表（teamNum,name）
	private TeamEntity teamUpdate(ScheduleEntity schedule){
		TeamEntity teamEntity = teamService.selectByScheduleId(schedule.getId());
		teamEntity.setName(schedule.getName());
		teamEntity.setTeamNum(schedule.getTeamNum());
		teamService.updateTeam(teamEntity);

		return teamEntity;
	}
	private void deleteMemberForLeader(ScheduleEntity originalSchedule){
		List<MemberEntity> memberLeaderList = memberService.getLeaderByTId(originalSchedule.getTeamId());
		if(!memberLeaderList.isEmpty()){
			memberService.deleteMember(memberLeaderList.get(0));
		}
	}

	@Override
	public int deleteSchedule(long id) {
		return scheduleDao.delete(id);
	}

	@Override
	public ScheduleEntity getSchedule(long id) {
		ScheduleEntity entity = scheduleDao.select(id);

		if(entity == null){
			entity = new ScheduleEntity();
		}else{
			List<ScheduleDetailEntity> scheduleDetailList  = entity.getScheduleDetailList();
			logger.info("获取行程景点的图片信息");
			for (ScheduleDetailEntity scheduleDetailEntity: scheduleDetailList) {
				List<ScheduleDetailScenicEntity> scheduleDetailScenicList = scheduleDetailEntity.getScheduleDetailScenicEntitys();
				for (ScheduleDetailScenicEntity scheduleDetailScenicEntity:scheduleDetailScenicList) {
					AlbumScheduleEntity request =  new AlbumScheduleEntity();
					request.setScheduleDetailId(scheduleDetailScenicEntity.getScheduleDetailId());
					request.setObjectId(scheduleDetailScenicEntity.getId());
					request.setType(1);
					List<AlbumScheduleEntity> albumScheduleList = albumScheduleDao.getAlbumSchedule(request);
					List<String> imageList = new ArrayList<>();
					if(null!=albumScheduleList){
						for (AlbumScheduleEntity albumSchedule:albumScheduleList) {
                            if(null==albumSchedule.getPhotoUrl()|| "null" .equals(albumSchedule.getPhotoUrl())) continue;
							imageList.add("\""+albumSchedule.getPhotoUrl()+"\"");
						}
					}
					scheduleDetailScenicEntity.setAlbumScheduleEntities(albumScheduleList);
					scheduleDetailScenicEntity.setImageList(imageList);
				}

				HotelScheduleEntity hotelSchedule = scheduleDetailEntity.getHotelSchedule();
				if (hotelSchedule != null){
					List<String> photoUrls = new ArrayList<>();
					List<AlbumScheduleEntity> albumSchedules = albumScheduleService.getAlbumSchedule(hotelSchedule.getScheduleDetailId(), hotelSchedule.getId(), 2);
					if (albumSchedules != null){
						for (AlbumScheduleEntity albumScheduleEntity:albumSchedules) {
							photoUrls.add("\""+albumScheduleEntity.getPhotoUrl()+"\"");
						}
						hotelSchedule.setAlbumScheduleList(albumSchedules);
					}
					hotelSchedule.setPhotoUrls(photoUrls);
				}

				//增加每日行程对应的自费项目及相册
				List<OwnExpenseScheduleEntity> ownExpenseScheduleList = scheduleDetailEntity.getOwnExpenseScheduleList();
				if (ownExpenseScheduleList != null){
					for (OwnExpenseScheduleEntity ownExpenseSchedule:ownExpenseScheduleList) {
						AlbumScheduleEntity albumScheduleEntity =  new AlbumScheduleEntity();
						albumScheduleEntity.setScheduleDetailId(ownExpenseSchedule.getScheduleDetailId());
						albumScheduleEntity.setObjectId(ownExpenseSchedule.getId());
						albumScheduleEntity.setType(3);
						List<AlbumScheduleEntity> albumScheduleList = albumScheduleDao.getAlbumSchedule(albumScheduleEntity);
						if (albumScheduleList != null){
							List<String> images = new ArrayList<>();
							for (AlbumScheduleEntity albumSchedule:albumScheduleList) {
								images.add("\""+albumSchedule.getPhotoUrl()+"\"");
							}
							ownExpenseSchedule.setPhotoUrls(images);
							ownExpenseSchedule.setAlbumScheduleList(albumScheduleList);
						}
					}
				}
			}
		}
		return entity;
	}

	/**
	 * 后台获取单个行程
	 * @param id
	 * @return
     */
	@Override
	public ScheduleEntity getScheduleByUser(long id) {
		ScheduleEntity entity = scheduleDao.selectByUser(id);

		if(entity == null){
			entity = new ScheduleEntity();
		}
		return entity;
	}


	@Override
	public ScheduleEntity getSchedule(String teamId) {
		return scheduleDao.selectByTeamId(teamId);
	}

	@Override
	public ScheduleResponse getScheduleInfo(String teamId) {
		ScheduleEntity schedule = getSchedule(teamId);
		if(schedule == null){
			return ScheduleResponse.createNotFoundResponse("没有发现该行程");
		}
		PromptInfoEntity promptInfo = promptService.getByScheduleId(schedule.getId());

		ScheduleResponse scheduleResponse = new ScheduleResponse();
		scheduleResponse.setScheduleEntity(schedule);
//		scheduleResponse.setPromptInfoEntity(promptInfo);
		return scheduleResponse;
	}

	@Override
	public SchedulePageResponse getSchedules(ScheduleRequest request) {

//		关联查分页
		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		Page<Long> idList = scheduleDao.selectAllPages(request);

		request.setScheduleIds(idList);
		Page<ScheduleEntity> list =scheduleDao.selectAll(request);

		SchedulePageResponse response = new SchedulePageResponse();
		response.setScheduleList(list);
		response.setTotals(idList.getTotal());
		response.setPages(idList.getPages());
		return response;
	}

	/**
	 * 后台分页查询行程列表
	 * @param request
	 * @return
     */
	@Override
	public SchedulePageResponse getSchedulesByUser(ScheduleRequest request) {

//		关联查分页
		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		Page<Long> idList = scheduleDao.selectAllPagesByUser(request);

		request.setScheduleIds(idList);
		Page<ScheduleEntity> list =scheduleDao.selectAllByUser(request);

		SchedulePageResponse response = new SchedulePageResponse();
		response.setScheduleList(list);
		response.setTotals(idList.getTotal());
		response.setPages(idList.getPages());
		return response;
	}

	@Override
	public List<ScheduleEntity> getTravelListByStatus(ScheduleRequest request,Integer status){
		request.setPageNum(Constant.pageNum);
		request.setPageSize(5);
		request.setStatus(status);
		//SchedulePageResponse response = getSchedules(request);
        SchedulePageResponse  response =  getSchedulesForTravel(request);
		return response.getScheduleList();
	}

    /**
     * 获取行程信息
     * @param request
     * @return
     */
    private SchedulePageResponse getSchedulesForTravel(ScheduleRequest request) {
        PageHelper.startPage(request.getPageNum(),request.getPageSize());
        Page<ScheduleEntity> list = scheduleDao.selectScheduleForTravel(request);
        SchedulePageResponse response = new SchedulePageResponse();
        response.setScheduleList(list);
        response.setTotals(list.getTotal());
        response.setPages(list.getPages());
        return response;
    }

    @Override
	public int updateSchedule(ScheduleEntity scheduleEntity) {
		scheduleEntity.setUpdateTime(new Date());
		return scheduleDao.update(scheduleEntity);
	}

	private int updateSchedule(ScheduleEntity scheduleEntity ,ScheduleEntity originalSchedule){
		originalSchedule.setTeamNum(scheduleEntity.getTeamNum());
		originalSchedule.setName(scheduleEntity.getName());
		originalSchedule.setStartPlace(scheduleEntity.getStartPlace());
		originalSchedule.setStartDate(scheduleEntity.getStartDate());
		originalSchedule.setEndDate(scheduleEntity.getEndDate());
		originalSchedule.setCollectionTime(scheduleEntity.getCollectionTime());
		originalSchedule.setCollectionPlace(scheduleEntity.getCollectionPlace());
		originalSchedule.setFeature(scheduleEntity.getFeature());
		originalSchedule.setFeaturePhoto(scheduleEntity.getFeaturePhoto());
		originalSchedule.setEmergencyContact(scheduleEntity.getEmergencyContact());
		originalSchedule.setEndPlace(scheduleEntity.getEndPlace());
		Integer type = originalSchedule.getType();
		if (type != 2){//1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
			originalSchedule.setLeaderId(scheduleEntity.getLeaderId());
		}else {
			originalSchedule.setLeaderId(-1l);
		}
		return updateSchedule(originalSchedule);
	}

	//1：出行中；2：即将出行；3：已出行
	@Override
	public Page<ScheduleEntity> getSchedulesByLeaderAndStatus(Integer status,Long id){
		ScheduleRequest request = new ScheduleRequest(1,5);
		request.setStatus(status);
		request.setLeaderId(id);
		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		return scheduleDao.selectAll(request);
	}

	@Override
	public Page<ScheduleEntity> getSchedulesByUserAndStatus(Integer status,Long id){
		ScheduleRequest request = new ScheduleRequest(1,5);
		request.setStatus(status);
		request.setLeaderId(id);
		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		return scheduleDao.selectAllByUser(request);
	}

	@Override
	public List<Date> calculateLeaderWorkDate(List<ScheduleEntity> travelingList,List<ScheduleEntity> prepareTravelList){
		List<Date> dateList = new ArrayList<Date>();
		for(ScheduleEntity schedule: travelingList){
			List<ScheduleDetailEntity> detailList = schedule.getScheduleDetailList();
			for (ScheduleDetailEntity detail:detailList){
				dateList.add(detail.getScheduleDate());
			}
		}
		for(ScheduleEntity schedule: prepareTravelList){
			List<ScheduleDetailEntity> detailList = schedule.getScheduleDetailList();
			for (ScheduleDetailEntity detail:detailList){
				dateList.add(detail.getScheduleDate());
			}
		}
		return  dateList;
	}

	@Override
	public CommonResponse confirmSchedule(Long teamId,Long travelId) throws UnsupportedEncodingException {
		//修改行程状态为已发布待确认
		ScheduleEntity scheduleEntity= getScheduleByTeamId(teamId);
		if( scheduleEntity == null|| scheduleEntity.getId()==null) {
			return CommonResponse.createEmptyParameterResponse("行程不存在或行程id为空");
		}
		//判断团队是否有领队
		Long leaderId = scheduleEntity.getLeaderId();
		if (leaderId == null){
			return CommonResponse.createNotFoundResponse("需要确认领队后才能发布行程");
		}

		TeamEntity teamEntity=new TeamEntity();
		if(travelId!=null){
			teamEntity = teamService.getTeamById(teamId);
		}else{
			teamEntity = teamService.selectById(teamId);
		}
		if(teamEntity == null) return CommonResponse.createNotFoundResponse("没有发现"+teamId+"的团队");

		//根据游客生成团队头像，头像上传腾迅云，更新团队。
		if(StringUtils.isEmpty(teamEntity.getPhotoUrl())){
			String teamImageUrl = generateTeamImg(teamEntity);
			teamEntity.setPhotoUrl(teamImageUrl);
		}
		//创建腾迅云群组
		CreateGroupResponse txResponse =createGroup(teamEntity);
		if(txResponse.getErrorCode() != 0){
			return CommonResponse.createTimGroupErrorResponse(txResponse.getErrorCode());
		}
		//更新团队中腾迅云群组id
		teamEntity.setTxGroupid(txResponse.getGroupId());
		teamService.updateTeam(teamEntity);

		List<MemberEntity> memberList =memberService.getMemberListByTId(teamEntity.getId());
		//List<MemberEntity> memberEntityList = memberService.getTourGuideList(teamId, null);//领队和导游集合
		//判断团队中新成员是否已经有用户（手机号判断）
		for (MemberEntity member: memberList){
			if(!StringUtils.isEmpty(member.getPhone())){
				UserEntity user = userService.getUserByPhone(member.getPhone());
				if(user != null){//更新已有用户的新成员
					member.setUserId(user.getId());
					member.setJoinStatus(1);
					member.setTeamId(teamEntity.getTxGroupid());
					memberService.updateMember(member);
					//将已有用户加入腾迅云群组中
 					addTXGroup(member,user,teamEntity.getTxGroupid());
					//发送欢迎加入信息
					userService.sendInvitationMessage(member,teamEntity.getTxGroupid());
					//增加好友关系
					//addFriend(user.getUserName(), memberEntityList);
				}
			}
		}
		ScheduleEntity schedule=new ScheduleEntity();
		schedule.setId(scheduleEntity.getId());
		//发布状态：1全部-2已发布-3未发布-4待确认
		schedule.setProcessStatus(2);
		updateScheduleProcessStatus(schedule);
		//给成员发送邀请短信
		messageService.sendMessage(teamEntity,memberList);
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setRetMsg("行程确认成功");
		return commonResponse;
	}
	private String generateTeamImg(TeamEntity teamEntity){
		MemberRequest memberRequest = new MemberRequest();
		memberRequest.settId(teamEntity.getId());
		List<MemberEntity> memberList = memberService.getMemberList(memberRequest);
		List<String> nameList = new ArrayList<String>();
		List<Integer> sexList = new ArrayList<Integer>();
		for (MemberEntity member:memberList){
			nameList.add(member.getRealName());
			sexList.add(member.getSex());
		}
		try {
			return PhotoHelper.createGroupOrTeamImage(nameList,sexList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	//创建腾迅云群组
	private CreateGroupResponse createGroup(TeamEntity teamEntity){
		CreateGroupRequest request = new CreateGroupRequest("", "Private", getGroupName(teamEntity.getName()), teamEntity.getPhotoUrl());
		List<AppDefinedData> appDataList = getAppDefinedData(teamEntity);
		request.setAppDefinedData(appDataList);
		return timRestAPI.createGroup(request);
	}

	private String getGroupName(String name) {
		if(name == null) return "";
		if(name.length() <= 8) return name;
		return name.substring(0, 8);
	}
	//定义腾迅群组自定义字段：出行状态和团名称
	private List<AppDefinedData> getAppDefinedData(TeamEntity teamEntity){
		List<AppDefinedData> list = new ArrayList<AppDefinedData>();
		AppDefinedData statusData = new AppDefinedData();
		statusData.setKey("TripStage");//出行状态
		statusData.setValue("2");//即将出行
		list.add(statusData);

		AppDefinedData teamNameData = new AppDefinedData();
		teamNameData.setKey("TripData");//团名称
		teamNameData.setValue(teamEntity.getName());
		list.add(teamNameData);

		return list;
	}
	//加入腾迅云群组和导游领队添加好友关系。
	public void addTXGroup(MemberEntity member, UserEntity user, String teamId) {
		// 设置成员自定义字段(角色)
		List<AppMemberDefinedData> memberFieldList = new ArrayList<AppMemberDefinedData>();
		AppMemberDefinedData memberRoleField = new AppMemberDefinedData("MemberRole",String.valueOf(member.getRole()));
		memberFieldList.add(memberRoleField);
		// 调用腾讯Rest API的增加群组成员接口
		MemberList mem = new MemberList(user.getUserName());
		mem.setAppMemberDefinedData(memberFieldList);
		List<MemberList> memberList = new ArrayList<MemberList>();
		memberList.add(mem);

		AddGroupMemberRequest request = new AddGroupMemberRequest(teamId, memberList);
		AddGroupMemberResponse response = timRestAPI.addGroupMember(request);
		//设置群名片
		ModifyGroupMemberInfoRequest req = new ModifyGroupMemberInfoRequest();
		req.setGroupId(teamId);
		req.setMemberAccount(user.getUserName());
		req.setNameCard(member.getRealName());
		timRestAPI.modifyGroupMemberInfo(req);
	}

	/*
	public void addFriend(String username, List<MemberEntity> memberEntityList) {
		if (CollectionUtils.isEmpty(memberEntityList)) return;
		List<AddFriendItem> addFriendItems = new ArrayList<AddFriendItem>();
		for (MemberEntity memberEntity : memberEntityList) {
			if (memberEntity.getUserEntity() != null) {
				AddFriendItem addFriendItem = new AddFriendItem();
				addFriendItem.setToAccount(memberEntity.getUserEntity().getUserName());
				// 根据用户设备，设置添加好友的来源
				DeviceEntity deviceEntity = deviceService.getDeviceByUserId(memberEntity.getUserId());
				if ("Android".equals(deviceEntity.getDeviceType())) {
					addFriendItem.setAddSource(AddSource.getAddSource_Type_Android());
				} else if ("iOS".equals(deviceEntity.getDeviceType())){
					addFriendItem.setAddSource(AddSource.getAddSource_Type_iOS());
				} else {
					addFriendItem.setAddSource(AddSource.getAddSource_Type_Service());
				}
				addFriendItems.add(addFriendItem);
			}
		}
		//添加‘跟上小二’为好友（系统用户，用来给游客推送消息）
		AddFriendItem addFriendItem = new AddFriendItem();
		addFriendItem.setToAccount("letsgo@gsxe");
		addFriendItem.setAddSource(AddSource.getAddSource_Type_Service());
		addFriendItems.add(addFriendItem);

		AddFriendRequest request = new AddFriendRequest();
		request.setFromAccount(username);
		request.setAddFriendItem(addFriendItems);
		AddFriendResponse response = timRestAPI.addFriend(request);
	}
	*/

	// 封装推送行程信息
	private Map<String, String> getParamMap(TeamEntity teamEntity) {
		// 获取团队行程
		ScheduleEntity scheduleEntity = getSchedule(teamEntity.getTxGroupid());

		Map<String, String> map = new HashMap<>();
		map.put("type", "5");
		map.put("teamName", teamEntity.getName());
		map.put("teamNumber", teamEntity.getTeamNum());
		map.put("startTime", DateUtils.date2String("yyyy年MM月dd日", scheduleEntity.getStartDate()));
		map.put("startPlace", scheduleEntity.getStartPlace());
		map.put("gatherTime", DateUtils.date2String("yyyy年MM月dd日", scheduleEntity.getCollectionTime()));
		map.put("gatherPlace", scheduleEntity.getCollectionPlace());
		map.put("targetURL", "http://google.com.cn");

		return map;
	}

	//推送行程信息
	/*private void pushMessage(List<Long> userIdList, Map<String, String> param){
		if(!userIdList.isEmpty()){
			List<DeviceEntity> deviceList = deviceService.getDeviceTypeAndUserName(userIdList);
			try {
				pushService.pushMIMessage(deviceList,"跟上","行程开始！", param);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/

	@Override
	public Page<ScheduleEntity> getSchedulesByMember(WebMemberRequest memberRequest) {
		PageHelper.startPage(memberRequest.getPageNums(),memberRequest.getPageSizes());
		return scheduleDao.selectScheduleForMember(memberRequest);
	}

	@Override
	public Page<ScheduleEntity> getSchedulesByMember(Long memberId,int status) {
		WebMemberRequest request = new WebMemberRequest(1,5);
		request.setId(memberId);
		request.setStatus(status);
		return getSchedulesByMember(request);
	}

	/**
	 * 获取行程单
	 * @param teamId
	 * @return
     */
	@Override
	public ScheduleInfoResponse getScheduleEntity(Long teamId, UserEntity user) {
		// 查询当前用户的所在的所有团队行程
		List<TeamEntity> teamList = teamService.getTeamEntityList(user.getId());
		List<Long> teamIds = new ArrayList<>();
		for (TeamEntity teamEntity : teamList) {
			teamIds.add(teamEntity.getId());
		}

		// 根据客户端上传的ID，进行团队行程过滤
		ScheduleReq request = new ScheduleReq();
		request.setTeamId(teamId);
		request.setTeamIds(teamIds);
		List<ScheduleEntity> scheduleEntities = scheduleDao.selectScheduleBetweenTeamId(request);
		if (CollectionUtils.isEmpty(scheduleEntities)) {
			ScheduleInfoResponse response = new ScheduleInfoResponse();
			ScheduleInfo info = new ScheduleInfo();
			response.setScheduleInfo(info);
			return response;
		}

		// 从行程实体复制行程单信息
		ScheduleEntity entity = scheduleEntities.get(0);
		ScheduleInfo info = new ScheduleInfo();
		info.setTeamName(entity.getName());
		info.setTeamNumber(entity.getTeamNum());
		info.setStartDate(entity.getStartDate());
		info.setStartPlace(entity.getStartPlace());
		info.setCollectionTime(entity.getCollectionTime());
		info.setCollectionPlace(entity.getCollectionPlace());

		info.setTargetURL(Constant.WEB_BASE_URL + "schedule/getScheduleShow?");
		info.setFlag("introduce");
		info.setTeamId(entity.getTxGroupId());

		ScheduleInfoResponse response = new ScheduleInfoResponse();
		response.setScheduleInfo(info);
		return response;
	}

	/**
	 * 获取最近十天的目的地
	 * @return
     */
	@Override
	public List<RegionEntity> getScheduleCitiesInLatestTenDay() {
		List<String> cities = getDestinationIdsInLatestTenDay();
		logger.info("城市：" + cities.toString());
		List<RegionEntity> destLsit = new ArrayList<>();
		if (!CollectionUtils.isEmpty(cities)) {
			destLsit = regionService.getDestinationByDestIds(cities);
		}

		return destLsit;
	}

	/**
	 * 获取所有出行中行程的城市Id
	 * @return
     */
	private List<String> getDestinationIdsInLatestTenDay() {
		List<String> cities = new ArrayList<>();
		List<ScheduleEntity> scheduleEntityList = scheduleDao.selectScheduleCityBetterDate();

		for (ScheduleEntity scheduleEntity : scheduleEntityList) {
			for (ScheduleDetailEntity scheduleDetailEntity : scheduleEntity.getScheduleDetailList()) {
				/*
				if (!StringUtils.isEmpty(scheduleDetailEntity.getDestination1())) {
					cities.add(scheduleDetailEntity.getDestination1());
				}
				if (!StringUtils.isEmpty(scheduleDetailEntity.getDestination2())) {
					cities.add(scheduleDetailEntity.getDestination2());
				}
				if (!StringUtils.isEmpty(scheduleDetailEntity.getDestination3())) {
					cities.add(scheduleDetailEntity.getDestination3());
				}
				if (!StringUtils.isEmpty(scheduleDetailEntity.getDestination4())) {
					cities.add(scheduleDetailEntity.getDestination4());
				}
				*/
				if (!StringUtils.isEmpty(scheduleDetailEntity.getStartPlaceId())){
					cities.add(scheduleDetailEntity.getStartPlaceId());
				}
				if (!StringUtils.isEmpty(scheduleDetailEntity.getDestinationId1())) {
					cities.add(scheduleDetailEntity.getDestinationId1());
				}
				if (!StringUtils.isEmpty(scheduleDetailEntity.getDestinationId2())) {
					cities.add(scheduleDetailEntity.getDestinationId2());
				}
				if (!StringUtils.isEmpty(scheduleDetailEntity.getDestinationId3())) {
					cities.add(scheduleDetailEntity.getDestinationId3());
				}
				if (!StringUtils.isEmpty(scheduleDetailEntity.getDestinationId4())) {
					cities.add(scheduleDetailEntity.getDestinationId4());
				}
			}
		}

		return new ArrayList<>(new HashSet(cities));
	}

	@Override
	public CommonResponse updateSchedulePhotos(ScheduleEntity scheduleEntity) {
		if (scheduleEntity == null) {
			return CommonResponse.createNotFoundResponse("没有发现该行程");
		}
		scheduleDao.updateSchedulePhotos(scheduleEntity);
		return new CommonResponse();
	}
	@Override
	public ScheduleAddResponse createSchedule(CreateScheduleRequest request){
	if(teamService.hasTeamNum(request.getTeamNum())){
		return ScheduleAddResponse.createNotFoundResponse("该团号已经存在");
	 }
	 ScheduleEntity scheduleEntity = new ScheduleEntity();
	 scheduleEntity.setName(request.getName());
	 scheduleEntity.setTeamNum(request.getTeamNum());
	 scheduleEntity.setStartPlace(request.getStartPlace());
	 scheduleEntity.setStartDate(request.getStartDate());
	 scheduleEntity.setEndDate(request.getEndDate());
	 //1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
	 scheduleEntity.setType(2);
		//创建行程未发布状态-发布状态：1全部-2已发布-3未发布-4待确认processStatus
		scheduleEntity.setProcessStatus(3);
		ScheduleResponse scheduleResponse=createScheduleSave(scheduleEntity);
		ScheduleAddResponse addScheduleResponse= new ScheduleAddResponse();
		if(scheduleResponse==null || scheduleResponse.getScheduleEntity()==null){
			return addScheduleResponse;
		}
		addScheduleResponse.setId(scheduleResponse.getScheduleEntity().getId());
	 return addScheduleResponse;
	}

	public ScheduleResponse createScheduleSave(ScheduleEntity schedule) {
		if(schedule == null) return ScheduleResponse.createNotFoundResponse("没有行程");
		TeamEntity teamEntity = teamSave(schedule);
		schedule.setTeamId(teamEntity.getId());
		UserResponse userResponse = userLoginHelper.getLoginUser();
		UserEntity user = userResponse.getUserEntity();
		if(user == null || user.getId() == null){
			throw new BusinessException(ErrorConstant.USER_NOT_LOGIN,"用户未登录或登录信息过期");
		}
		schedule.setLeaderId(-1l);
		schedule.setSex(user.getSex());
		schedule.setLeadName(user.getRealName());
		schedule.setPhone(user.getPhone());
		schedule.setUserId(user.getId());
		addSchedule(schedule);
		//如果有领队，将领队添加到成员表中。
		MemberEntity memberEntity=null;
		memberEntity=addMemberForLeaderByApp(schedule,teamEntity,user.getId());
		int count =0;
		if(teamEntity.getTotalCount()!=null){
			count=teamEntity.getTotalCount();
		}
		teamEntity.setTotalCount(count+1);
		teamService.updateTeam(teamEntity);
		ScheduleResponse scheduleResponse = new ScheduleResponse();
		scheduleResponse.setScheduleEntity(schedule);
		//新增日志
		addScheduleLog(schedule,teamEntity,memberEntity);
		return scheduleResponse;
	}

	public  ScheduleListResponse  searchScheduleList(ScheduleRequest request) {
		//关联查分页
		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		Page<ScheduleEntity>  scheduleList = scheduleDao.searchScheduleList(request);
		ScheduleListResponse response= new ScheduleListResponse();
		Page<ScheduleEntity> schedules = new Page<ScheduleEntity>();
		if(scheduleList!=null && scheduleList.size()>0){
			for (int i = 0; i <scheduleList.size() ; i++) {
				ScheduleEntity schedule =scheduleList.get(i);
				if(schedule!=null){
					List<String> str = new ArrayList<String>();
					String processPhotosUrl =schedule.getProcessPhotosUrl();
					if(!StringUtils.isEmpty(processPhotosUrl)){
						String [] processPhotosUrlList =processPhotosUrl.split(";");
						if(processPhotosUrlList!=null && processPhotosUrlList.length >0){
							for (int j = 0; j <processPhotosUrlList.length ;j++){
								if(!StringUtils.isEmpty(processPhotosUrlList[j])) {
									str.add(processPhotosUrlList[j]);
								}
							}
						}else{
							str.add(processPhotosUrl);
						}
					}
					schedule.setProcessPhotosUrls(str);
					schedules.add(schedule);
				}
			}
		}
		response.setPages(scheduleList.getPages());
		response.setTotals(scheduleList.getTotal());
		response.setScheduleList(schedules);
		return response;
	}

	/**
	 * 后台查询行程列表
	 * @param request
	 * @return
     */
	public  ScheduleListResponse  searchScheduleListByUser(ScheduleRequest request) {
		//关联查分页
		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		Page<ScheduleEntity>  scheduleList = scheduleDao.searchScheduleListByUser(request);
		ScheduleListResponse response= new ScheduleListResponse();
		Page<ScheduleEntity> schedules = new Page<ScheduleEntity>();
		if(scheduleList!=null && scheduleList.size()>0){
			for (int i = 0; i <scheduleList.size() ; i++) {
				ScheduleEntity schedule =scheduleList.get(i);
				if(schedule!=null){
					List<String> str = new ArrayList<String>();
					String processPhotosUrl =schedule.getProcessPhotosUrl();
					if(!StringUtils.isEmpty(processPhotosUrl)){
						String [] processPhotosUrlList =processPhotosUrl.split(";");
						if(processPhotosUrlList!=null && processPhotosUrlList.length >0){
							for (int j = 0; j <processPhotosUrlList.length ;j++){
								if(!StringUtils.isEmpty(processPhotosUrlList[j])) {
									str.add(processPhotosUrlList[j]);
								}
							}
						}else{
							str.add(processPhotosUrl);
						}
					}
					schedule.setProcessPhotosUrls(str);
					schedules.add(schedule);
				}
			}
		}
		response.setPages(scheduleList.getPages());
		response.setTotals(scheduleList.getTotal());
		response.setScheduleList(schedules);
		return response;
	}


	/**
	 * 获取行程列表
	 */
	@Override
	public ScheduleListResponse getScheduleList(ScheduleRequest request){
		//行程排序 -- 已创建    已发布待确认  出行中 行程   出行中围观    即将出行行程  即将出行围观    结束的   （时间倒序）
		Page<LeaderScheduleVo> list = new Page<LeaderScheduleVo>();
		int has=0;//标识当前用户是否有行程:1：代表有行程；0：代表没有行程
		Page<ScheduleEntity> scheduleList= new Page<ScheduleEntity>();
		ScheduleListResponse response= new ScheduleListResponse();
		response.setHasSchedule(has);
		response.setLeaderScheduleVoList(list);
		//获取参与的围观行程
		Page<ScheduleEntity> onlookSchedules=onlookersUserService.getSchedules(request.getUserId());
		scheduleList.addAll(onlookSchedules);
//		//1根据用户ID获取团队ID集合
//		List<Long>	teamIds=memberService.getTeamIds(request.getUserId());
//		if(teamIds==null ||teamIds.size()<=0){
//			return new ScheduleListResponse();
//		}
//		request.setTeamIds(teamIds);
		//关联查分页
		//PageHelper.startPage(request.getPageNum(),request.getPageSize());
       //根据团队ID集合获取行程列表
		Page<ScheduleEntity> schedules=scheduleDao.getUserScheduleList(request);
		scheduleList.addAll(schedules);
        if (scheduleList!=null && scheduleList.size() >0){
			has=1;
			//点击进入-消息小红点取消
			UserResponse userResponse = userLoginHelper.getLoginUser();
			UserEntity user = userResponse.getUserEntity();
			if(user == null || user.getId() == null){
				throw new BusinessException(ErrorConstant.USER_NOT_LOGIN,"用户未登录或登录信息过期");
			}
			noticeDetailService.updateNotClick(user.getId(),null,null);
			response = this.getAllSchedules(scheduleList,request);
		}
	  return response;
   }

	public  ScheduleListResponse  getAllSchedules(Page<ScheduleEntity> scheduleList,ScheduleRequest request){
		Page<LeaderScheduleVo> list = new Page<LeaderScheduleVo>();
		ScheduleListResponse response= new ScheduleListResponse();
		int has=0;//标识当前用户是否有行程:1：代表有行程；0：代表没有行程
		int num=1;
		//行程排序 -- 已创建    已发布待确认  出行中 行程   出行中围观    即将出行行程  即将出行围观    结束的   （时间倒序）
		Page<LeaderScheduleVo> ycjList = new Page<LeaderScheduleVo>();
		Page<LeaderScheduleVo> yfbList = new Page<LeaderScheduleVo>();
		Page<LeaderScheduleVo> jjcxxcList = new Page<LeaderScheduleVo>();
		Page<LeaderScheduleVo> jjcxwgList = new Page<LeaderScheduleVo>();
		Page<LeaderScheduleVo> cxzxcList = new Page<LeaderScheduleVo>();
		Page<LeaderScheduleVo> cxzwgList = new Page<LeaderScheduleVo>();
		Page<LeaderScheduleVo> yjsList = new Page<LeaderScheduleVo>();
		for (int i = 0; i < scheduleList.size(); i++) {
			ScheduleEntity schedule = scheduleList.get(i);
			if (schedule != null) {
				//1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
				if (schedule.getType()!=null && schedule.getType()==2 && StringUtils.isEmpty(schedule.getProcessPhotosUrl()) && schedule.getDel()==0) {
					//更新行程 将无效的行程逻辑删除 如果领队创建的行程没有传图 则逻辑删除
					this.delSchdule(schedule.getId());
				} else {
					LeaderScheduleVo lsvo = new LeaderScheduleVo();
					lsvo.setStartDate(schedule.getStartDate());
					lsvo.setProcessStatus(schedule.getProcessStatus());
					lsvo.setName(schedule.getName());
					lsvo.setEndDate(schedule.getEndDate());
					lsvo.setTxGroupId(schedule.getTxGroupId());
					//添加微信参数
					lsvo.setQrURL(ConfigConstant.QR_URL);
					lsvo.setQrType("t");//二维码类型：t：快速入团
					String txGroupId = schedule.getTxGroupId();
					if (!StringUtils.isEmpty(txGroupId)){

						lsvo.setQrParam(schedule.getTxGroupId());
					}else {
						lsvo.setQrParam("");
					}
					String qrCode = this.getqrCode(schedule);
					lsvo.setQrCode(qrCode);
					lsvo.settPhotoUrl(schedule.gettPhotoUrl());
					if(schedule.getOnlookerType()!=null&&schedule.getOnlookerType()==2){
						//围观行程没有身份
					}else{
						//身份【1：领队；2：导游；3：游客】
						lsvo.setRole(schedule.getRole());
						//是否为管理员【1：是；0：否】
						lsvo.setIsAdmin(schedule.getIsAdmin());
					}
					lsvo.setStatus(schedule.getStatus());
					lsvo.setScheduleId(schedule.getId());
					lsvo.setOnlookerUserId(schedule.getOnlookerUserId());
					lsvo.setOnlookerType(0);// 是否围观行程：0正常行程  1围观行程
					//行程总天数
					lsvo.setTotalDays(DateUtils.dayBetween(schedule.getStartDate(), schedule.getEndDate()) + 1);
					//距离团队出行剩余天数
					//已完成天数
					//出行状态【1：出行中；2：即将出行；3：已出行】
					if (schedule.getStatus() != null) {
						if (schedule.getStatus() == 1) {//出行中
							lsvo.setDays(0);
							lsvo.setFinishDays(DateUtils.dayBetween(schedule.getStartDate(), DateUtils.getSysDateTimestamp())+1);
							lsvo.setFeaturePhoto(ConfigConstant.XCLB_CXZBJURL);
						} else if (schedule.getStatus() == 3) {//已经完成
							lsvo.setDays(0);
							lsvo.setFinishDays(DateUtils.dayBetween(schedule.getStartDate(), schedule.getEndDate()) + 1);
							lsvo.setFeaturePhoto(ConfigConstant.XCLB_YWCBJURL);
						} else if (schedule.getStatus() == 2) {//即将出行
							lsvo.setFinishDays(0);
							lsvo.setDays(DateUtils.dayBetween(DateUtils.getSysDateTimestamp(), schedule.getStartDate())>=0?DateUtils.dayBetween(DateUtils.getSysDateTimestamp(), schedule.getStartDate()):0);
							if (num == 1) {
								lsvo.setFeaturePhoto(ConfigConstant.XCLB_JJCXBJURL1);
							}
							if (num == 2) {
								lsvo.setFeaturePhoto(ConfigConstant.XCLB_JJCXBJURL2);
							}
							if (num == 3) {
								lsvo.setFeaturePhoto(ConfigConstant.XCLB_JJCXBJURL3);
							}
							if (num == 4) {
								lsvo.setFeaturePhoto(ConfigConstant.XCLB_JJCXBJURL4);
							}
							if (num == 5) {
								lsvo.setFeaturePhoto(ConfigConstant.XCLB_JJCXBJURL5);
							}
							if (num == 5) {
								num = 1;
							} else {
								num++;
							}
						}
					} else {
						lsvo.setDays(0);
						lsvo.setFinishDays(0);

					}
					//消息分类【1：集合；2：通知；3：公告；4：围观】
					lsvo.setAnnouncementUnread(noticeDetailService.getNumsByTypeAndUserId(3, request.getUserId(), schedule.getTxGroupId()));
					lsvo.setGatherUnread(noticeDetailService.getNumsByTypeAndUserId(1, request.getUserId(), schedule.getTxGroupId()));
					lsvo.setNoticeUnread(noticeDetailService.getNumsByTypeAndUserId(2, request.getUserId(), schedule.getTxGroupId()));
					//lsvo.setWatchUnreadCount(noticeDetailService.getNumsByTypeAndUserId(4,request.getUserId(),schedule.getTxGroupId()));
					//围观未读数
					lsvo.setWatchUnreadCount(onlookersDetailsService.getLastReply(request.getUserId(),schedule.getId()).size());
					//已创建
					if(schedule.getProcessStatus()==3 && schedule.getStatus()==2){
						ycjList.add(lsvo);
					}
					//已发布待审核
					if(schedule.getProcessStatus()==4 && schedule.getStatus()==2){
						yfbList.add(lsvo);
					}
                    //即将出行
					if(schedule.getProcessStatus()!=null&&schedule.getProcessStatus()==2 && schedule.getStatus()!=null&&schedule.getStatus()==2){
						//围观行程
						if(schedule.getOnlookerType()!=null&&schedule.getOnlookerType()==2){
							lsvo.setFeaturePhoto(ConfigConstant.XCLB_WGBJURL);
							lsvo.setOnlookerType(1);
							jjcxwgList.add(lsvo);
						}else{
							jjcxxcList.add(lsvo);
						}

					}
					//出行中
					if(schedule.getProcessStatus()==2 && schedule.getStatus()==1){
						//围观行程
						if(schedule.getOnlookerType()!=null&&schedule.getOnlookerType()==2){
							lsvo.setOnlookerType(1);
							lsvo.setFeaturePhoto(ConfigConstant.XCLB_WGBJURL);
							cxzwgList.add(lsvo);
						}else{
							cxzxcList.add(lsvo);
						}
					}

				}
			}
		}
		//获取最新的已经结束的行程
		yjsList=this.getEndScheduleLastest(request);
		if(yfbList!=null&&yfbList.size()>0){
            has=1;
		}
		if(cxzxcList!=null&&cxzxcList.size()>0){
			has=1;
		}
		if(jjcxxcList!=null&&jjcxxcList.size()>0){
			has=1;
		}
		if(yjsList!=null&&yjsList.size()>0){
			has=1;
		}
		list.addAll(ycjList);
		list.addAll(yfbList);
		list.addAll(cxzxcList);
		list.addAll(cxzwgList);
		list.addAll(jjcxxcList);
		list.addAll(jjcxwgList);
		list.addAll(yjsList);
		response.setHasSchedule(has);
		response.setLeaderScheduleVoList(list);
		return response;
	}

	public Page<LeaderScheduleVo> getEndScheduleLastest(ScheduleRequest request){
		Page<LeaderScheduleVo> yjsList = new Page<LeaderScheduleVo>();
		//最新已完成行程
		ScheduleEntity scheduleOne=scheduleDao.getEndScheduleOne(request);
		//最新已完成围观行程
		ScheduleEntity onlookScheduleOne=onlookersUserService.getEndScheduleOne(request.getUserId());
		if(scheduleOne==null && onlookScheduleOne==null){
			return yjsList;
		}
		if(scheduleOne!=null && onlookScheduleOne==null){
			LeaderScheduleVo lsvo = new LeaderScheduleVo();
			lsvo.setName(scheduleOne.getName());
			lsvo.setStatus(scheduleOne.getStatus());
			lsvo.setTxGroupId(scheduleOne.getTxGroupId());
			lsvo.setScheduleId(scheduleOne.getId());
			lsvo.setFeaturePhoto(ConfigConstant.XCLB_YWCBJURL);
			lsvo.setOnlookerType(0);// 是否围观行程：0正常行程  1围观行程
			lsvo.setEndDays(DateUtils.dayBetween(scheduleOne.getEndDate(), DateUtils.getSysDateTimestamp()));
			//添加微信参数
			lsvo.setQrURL(ConfigConstant.QR_URL);
			lsvo.setQrType("t");//二维码类型：t：快速入团
			String txGroupId = scheduleOne.getTxGroupId();
			if (!StringUtils.isEmpty(txGroupId)){

				lsvo.setQrParam(scheduleOne.getTxGroupId());
			}else {
				lsvo.setQrParam("");
			}
			String qrCode = this.getqrCode(scheduleOne);
			lsvo.setQrCode(qrCode);
			lsvo.settPhotoUrl(scheduleOne.gettPhotoUrl());
			yjsList.add(lsvo);
			return yjsList;
		}
		if(scheduleOne==null && onlookScheduleOne!=null){
			LeaderScheduleVo lsvo = new LeaderScheduleVo();
			lsvo.setName(onlookScheduleOne.getName());
			lsvo.setTxGroupId(onlookScheduleOne.getTxGroupId());
			lsvo.setScheduleId(onlookScheduleOne.getId());
			lsvo.setStatus(onlookScheduleOne.getStatus());
			lsvo.setFeaturePhoto(ConfigConstant.XCLB_YWCBJURL);
			lsvo.setOnlookerType(1);// 是否围观行程：0正常行程  1围观行程
			lsvo.setEndDays(DateUtils.dayBetween(onlookScheduleOne.getEndDate(), DateUtils.getSysDateTimestamp()));
			//添加微信参数
			lsvo.setQrURL(ConfigConstant.QR_URL);
			lsvo.setQrType("t");//二维码类型：t：快速入团
			String txGroupId = onlookScheduleOne.getTxGroupId();
			if (!StringUtils.isEmpty(txGroupId)){

				lsvo.setQrParam(onlookScheduleOne.getTxGroupId());
			}else {
				lsvo.setQrParam("");
			}
			String qrCode = this.getqrCode(onlookScheduleOne);
			lsvo.setQrCode(qrCode);
			lsvo.settPhotoUrl(onlookScheduleOne.gettPhotoUrl());
			yjsList.add(lsvo);
			return yjsList;
		}
		if(scheduleOne!=null && onlookScheduleOne!=null) {
			if (DateUtils.compareTime(scheduleOne.getEndDate(), onlookScheduleOne.getEndDate(), Calendar.SECOND) >= 0) {
				LeaderScheduleVo lsvo = new LeaderScheduleVo();
				lsvo.setName(scheduleOne.getName());
				lsvo.setStatus(scheduleOne.getStatus());
				lsvo.setScheduleId(scheduleOne.getId());
				lsvo.setTxGroupId(scheduleOne.getTxGroupId());
				lsvo.setFeaturePhoto(ConfigConstant.XCLB_YWCBJURL);
				lsvo.setOnlookerType(0);// 是否围观行程：0正常行程  1围观行程
				lsvo.setEndDays(DateUtils.dayBetween(scheduleOne.getEndDate(), DateUtils.getSysDateTimestamp()));
				//添加微信参数
				lsvo.setQrURL(ConfigConstant.QR_URL);
				lsvo.setQrType("t");//二维码类型：t：快速入团
				String txGroupId = scheduleOne.getTxGroupId();
				if (!StringUtils.isEmpty(txGroupId)){

					lsvo.setQrParam(scheduleOne.getTxGroupId());
				}else {
					lsvo.setQrParam("");
				}
				String qrCode = this.getqrCode(scheduleOne);
				lsvo.setQrCode(qrCode);
				lsvo.settPhotoUrl(scheduleOne.gettPhotoUrl());
				yjsList.add(lsvo);
			} else {
				LeaderScheduleVo lsvo = new LeaderScheduleVo();
				lsvo.setName(onlookScheduleOne.getName());
				lsvo.setTxGroupId(onlookScheduleOne.getTxGroupId());
				lsvo.setScheduleId(onlookScheduleOne.getId());
				lsvo.setStatus(onlookScheduleOne.getStatus());
				lsvo.setFeaturePhoto(ConfigConstant.XCLB_YWCBJURL);
				lsvo.setOnlookerType(1);// 是否围观行程：0正常行程  1围观行程
				lsvo.setEndDays(DateUtils.dayBetween(onlookScheduleOne.getEndDate(), DateUtils.getSysDateTimestamp()));
				//添加微信参数
				lsvo.setQrURL(ConfigConstant.QR_URL);
				lsvo.setQrType("t");//二维码类型：t：快速入团
				String txGroupId = onlookScheduleOne.getTxGroupId();
				if (!StringUtils.isEmpty(txGroupId)){

					lsvo.setQrParam(onlookScheduleOne.getTxGroupId());
				}else {
					lsvo.setQrParam("");
				}
				String qrCode = this.getqrCode(onlookScheduleOne);
				lsvo.setQrCode(qrCode);
				lsvo.settPhotoUrl(onlookScheduleOne.gettPhotoUrl());
				yjsList.add(lsvo);
			}
		}
		return yjsList;
	}

	//根据团队的状态获取行程列表
	@Override
	public ScheduleListResponse getLeaderScheduleList(ScheduleRequest request){
		//1根据用户ID获取团队ID集合
		List<Long>	teamIds=memberService.getTeamIds(request.getUserId());
		if(teamIds==null ||teamIds.size()<=0){
			return new ScheduleListResponse();
		}
		request.setTeamIds(teamIds);
//		//根据领队ID 获取行程列表
//		Long leaderId=leaderService.getIdByUserId(request.getUserId());
//		if(leaderId==null){
//			return ScheduleListResponse.createNotFoundResponse("没有该领队");
//		}
//		request.setLeaderId(leaderId);
		//关联查分页
		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		//根据团队ID集合获取行程列表
		Page<ScheduleEntity> scheduleList=scheduleDao.getLeaderScheduleList(request);
		Page<LeaderScheduleVo> list = new Page<LeaderScheduleVo>();
		ScheduleListResponse response= new ScheduleListResponse();
		int has=0;
		if (scheduleList!=null && scheduleList.size() >0){
			//点击进入-消息小红点取消
			UserResponse userResponse = userLoginHelper.getLoginUser();
			UserEntity user = userResponse.getUserEntity();
			if(user == null || user.getId() == null){
				throw new BusinessException(ErrorConstant.USER_NOT_LOGIN,"用户未登录或登录信息过期");
			}
			noticeDetailService.updateNotClick(user.getId(),null,request.getStatus());
			int num=1;
			for (int i = 0; i < scheduleList.size(); i++) {
				ScheduleEntity schedule = scheduleList.get(i);
				if (schedule != null) {
					//1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
					if (schedule.getType()!=null && schedule.getType()==2 && StringUtils.isEmpty(schedule.getProcessPhotosUrl()) && schedule.getDel()==0) {
						//更新行程 将无效的行程逻辑删除 如果领队创建的行程没有传图 则逻辑删除
						this.delSchdule(schedule.getId());
					} else {
						LeaderScheduleVo lsvo = new LeaderScheduleVo();
						lsvo.setStartDate(schedule.getStartDate());
						lsvo.setProcessStatus(schedule.getProcessStatus());
						lsvo.setName(schedule.getName());
						lsvo.setEndDate(schedule.getEndDate());
						lsvo.setTxGroupId(schedule.getTxGroupId());
						//添加微信参数
						lsvo.setQrURL(ConfigConstant.QR_URL);
						lsvo.setQrType("t");//二维码类型：t：快速入团
						String txGroupId = schedule.getTxGroupId();
						if (!StringUtils.isEmpty(txGroupId)){
							lsvo.setQrParam(schedule.getTxGroupId());
						}else {
							lsvo.setQrParam("");
						}
						String qrCode = this.getqrCode(schedule);
						lsvo.setQrCode(qrCode);
						lsvo.settPhotoUrl(schedule.gettPhotoUrl());
						//背景图需要先上传设计好的图片再存返回回来的地址，有5个，循环保存
						//出行状态【1：出行中；2：即将出行；3：已出行】
						//出行中
						if (schedule.getStatus() == 1) {
							lsvo.setFeaturePhoto(ConfigConstant.XCLB_CXZBJURL);
						}
						//已经完成
						if (schedule.getStatus() == 3) {
							lsvo.setFeaturePhoto(ConfigConstant.XCLB_YWCBJURL);
						}
						//即将出行
						if (schedule.getStatus() == 2) {
							if (num == 1) {
								lsvo.setFeaturePhoto(ConfigConstant.XCLB_JJCXBJURL1);
							}
							if (num == 2) {
								lsvo.setFeaturePhoto(ConfigConstant.XCLB_JJCXBJURL2);
							}
							if (num == 3) {
								lsvo.setFeaturePhoto(ConfigConstant.XCLB_JJCXBJURL3);
							}
							if (num == 4) {
								lsvo.setFeaturePhoto(ConfigConstant.XCLB_JJCXBJURL4);
							}
							if (num == 5) {
								lsvo.setFeaturePhoto(ConfigConstant.XCLB_JJCXBJURL5);
							}
							if (num == 5) {
								num = 1;
							} else {
								num++;
							}
						}
						lsvo.setScheduleId(schedule.getId());
						//行程总天数
						lsvo.setTotalDays(DateUtils.dayBetween(schedule.getStartDate(), schedule.getEndDate()) + 1);
						//距离团队出行剩余天数
						//已完成天数
						//出行状态【1：出行中；2：即将出行；3：已出行】
						if (schedule.getStatus() != null) {
							if (schedule.getStatus() == 1) {
								has = 1;
								lsvo.setDays(0);
								lsvo.setFinishDays(DateUtils.dayBetween(schedule.getStartDate(), DateUtils.getSysDateTimestamp())+1);
							} else if (schedule.getStatus() == 3) {
								lsvo.setDays(0);
								lsvo.setFinishDays(DateUtils.dayBetween(schedule.getStartDate(), schedule.getEndDate()) + 1);
							} else if (schedule.getStatus() == 2) {
								lsvo.setFinishDays(0);
								lsvo.setDays(DateUtils.dayBetween(DateUtils.getSysDateTimestamp(), schedule.getStartDate()));
							}
						} else {
							lsvo.setDays(0);
							lsvo.setFinishDays(0);

						}
						//消息分类【1：集合；2：通知；3：公告；4：回复】
						lsvo.setAnnouncementUnread(noticeDetailService.getNumsByTypeAndUserId(3, schedule.getLeaderId(), schedule.getTxGroupId()));
						lsvo.setGatherUnread(noticeDetailService.getNumsByTypeAndUserId(1, schedule.getLeaderId(), schedule.getTxGroupId()));
						lsvo.setNoticeUnread(noticeDetailService.getNumsByTypeAndUserId(2, schedule.getLeaderId(), schedule.getTxGroupId()));
						list.add(lsvo);
					}
				}
			}
			response.setHasSchedule(has);
			response.setPages(scheduleList.getPages());
			response.setTotals(scheduleList.getTotal());
			response.setLeaderScheduleVoList(list);
		}
		return response;
	}
	//获取用户已经结束的行程列表
	@Override
	public ScheduleListResponse getEndScheduleList(ScheduleRequest request){
		ScheduleListResponse response= new ScheduleListResponse();
		Page<LeaderScheduleVo> list = new Page<LeaderScheduleVo>();
		Page<ScheduleEntity> scheduleList= new Page<ScheduleEntity>();
		//围观已经结束的列表
		Page<ScheduleEntity> onlookSchedules=onlookersUserService.getEndSchedules(request.getUserId());
		scheduleList.addAll(onlookSchedules);
		//行程已结束列表
		Page<ScheduleEntity> endScheduleList=scheduleDao.getEndScheduleList(request);
		scheduleList.addAll(endScheduleList);
		if(scheduleList==null&&scheduleList.size()<=0){
			response.setLeaderScheduleVoList(list);
			return response;
		}
		for (int i = 0; i < scheduleList.size(); i++) {
			ScheduleEntity schedule = scheduleList.get(i);
			if (schedule != null) {
					LeaderScheduleVo lsvo = new LeaderScheduleVo();
				    lsvo.setName(schedule.getName());
				    lsvo.setTxGroupId(schedule.getTxGroupId());
				    SimpleDateFormat sdfDateTime = new SimpleDateFormat(DateUtils.DATE_FORMAT_DATETIME);
				    lsvo.setEndDate(DateUtils.toSqlDate(sdfDateTime.format(schedule.getEndDate())));
				    lsvo.setFeaturePhoto(ConfigConstant.XCLB_YWCBJURL);
				    lsvo.setEndDays(DateUtils.dayBetween(schedule.getEndDate(), DateUtils.getSysDateTimestamp()));
				    lsvo.setStatus(schedule.getStatus());
				    lsvo.setScheduleId(schedule.getId());
					//添加微信参数
					lsvo.setQrURL(ConfigConstant.QR_URL);
					lsvo.setQrType("t");//二维码类型：t：快速入团
					String txGroupId = schedule.getTxGroupId();
					if (!StringUtils.isEmpty(txGroupId)){
						lsvo.setQrParam(schedule.getTxGroupId());
					}else {
						lsvo.setQrParam("");
					}
					String qrCode = this.getqrCode(schedule);
					lsvo.setQrCode(qrCode);
					lsvo.settPhotoUrl(schedule.gettPhotoUrl());
					if (schedule.getOnlookerType() != null && schedule.getOnlookerType() == 2) {
						lsvo.setOnlookerUserId(schedule.getOnlookerUserId());
						lsvo.setOnlookerType(1);// 是否围观行程：0正常行程  1围观行程
					} else {
						lsvo.setOnlookerType(0);// 是否围观行程：0正常行程  1围观行程
					}
				 list.add(lsvo);
				}
			}

		Collections.sort(list);//按照endDate升序
		Collections.reverse(list);//按照endDate降序
		response.setLeaderScheduleVoList(list);
		return response;
	}

	@Override
	public TeamScheduleResponse getSchedulesByTravelId(ScheduleRequest request) {
		//关联查分页
		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		TeamScheduleResponse t=new TeamScheduleResponse();
	     Page<TeamScheduleVo> list= scheduleDao.getSchedulesByTravelId(request);
		t.setPages(list.getPages());
		t.setTotals(list.getTotal());
		t.setPage(list);
		return t;
	}
	//通过团队teamId查询行程
	@Override
	public ScheduleEntity getScheduleByTeamId(Long teamId){
		return scheduleDao.getScheduleByTeamId(teamId);
	}
	//修该行程状态
	@Override
	public int updateScheduleProcessStatus(ScheduleEntity scheduleEntity){
		return scheduleDao.updateScheduleProcessStatus(scheduleEntity);
	}
	//获取线路评价数据vo集合
	@Override
	public	List<ScheduleLineVo> getScheduleList(){
		return scheduleDao.getScheduleList();
	}
	//保存数据到线路统计表
	@Override
	public boolean setLineEvaluate(ScheduleLineVo vo){
		if(vo==null || vo.getId()==null){return false;}
		LineEvaluateEntity lineEvaluateEntity = new LineEvaluateEntity();
		lineEvaluateEntity.setCount(1);
		lineEvaluateEntity.setCountry(vo.getCountryName());
		lineEvaluateEntity.setEndDate(vo.getEndDate());
		lineEvaluateEntity.setStartDate(vo.getStartDate());
		lineEvaluateEntity.setEvaluateNum(vo.getTouristCount());
		lineEvaluateEntity.setTravelId(vo.getTravelId());
		//大洲ID
		//通过国家名称获取所属大洲ID
		//01：亚洲02：欧洲03：非洲04：大洋洲05：北美洲06：南美洲
		String continent=vo.getCountryName();
		String continentId="";
		if(!StringUtils.isEmpty(continent)){
			//多国游情况，取一个国所属的洲就行了
			String [] countryNames =continent.split(";");
			if(countryNames!=null && countryNames.length>0){
				for (String name:countryNames) {
					if(!StringUtils.isEmpty(name)){
						continentId=regionService.getDeltaByCountryName(name);
						break;
					}
				}
			 }else{
				continentId=regionService.getDeltaByCountryName(continent);
			}
		}
		 lineEvaluateEntity.setContinent(continentId);
		//综合评分
		DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
		String grades="5.0";
		if(vo.getTouristCount()>0 && vo.getGrade()>0) {
			double grade = vo.getGrade() / vo.getTouristCount();
			grades=df.format(grade);
		}

		lineEvaluateEntity.setGrade(Double.parseDouble(grades));

		 lineEvaluateService.add(lineEvaluateEntity);
		return true;
	}

	@Override
	public List<Long> getTeamIdsInTeamJourney(TouristRequest request) {
		List<Long> teamIds = scheduleDao.selectTeamIdsWithIDAndConditions(request);
		return teamIds;
	}

	//获取登陆游客的行程列表
	@Override
	public UserScheduleResponse getAllTripList(MemberRequest request){
		 UserScheduleResponse response=new UserScheduleResponse();
		Page<ScheduleEntity>  scheduleList = new Page<ScheduleEntity>();

		long a1 = System.currentTimeMillis();
		//获取参与的围观行程
		List<OnlookersUserEntity> onlookersUserEntity=onlookersUserService.getScheduleIdByOnlookerUserId(request.getUserId());
		if(onlookersUserEntity!=null && onlookersUserEntity.size()>0){
			for (OnlookersUserEntity onlookersUser:onlookersUserEntity) {
				if(onlookersUser!=null){
					ScheduleRequest req = new ScheduleRequest();
					req.setId(onlookersUser.getScheduleId());
				    ScheduleEntity scheduleEntity = scheduleDao.getAllTripListOne(req);
							if (scheduleEntity != null) {
								ScheduleEntity  onlookersSchedule=new ScheduleEntity();
								onlookersSchedule.setId(scheduleEntity.getId());
								onlookersSchedule.setTxGroupId(scheduleEntity.getTxGroupId());
								onlookersSchedule.setEndDate(scheduleEntity.getEndDate());
								onlookersSchedule.setStartDate(scheduleEntity.getStartDate());
								onlookersSchedule.setName(scheduleEntity.getName());
								onlookersSchedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_WGBJURL);
								onlookersSchedule.setStatus(scheduleEntity.getStatus());
								// 是否围观行程：0正常行程  1围观行程
								onlookersSchedule.setOnlookerType(1);
								if (scheduleEntity.getStatus()==1||scheduleEntity.getStatus()==2){
									onlookersSchedule.setWatchUnreadCount(noticeDetailService.getNumsByTypeAndUserId(4,request.getUserId(),scheduleEntity.getTxGroupId()));
								}
								onlookersSchedule.setOnlookerUserId(onlookersUser.getUserId());
								scheduleList.add(onlookersSchedule);
					 }
				}
			}
		}
		long a2 = System.currentTimeMillis();
		logger.info("获取围观的数据耗时" + (a2-a1));

		//1根据用户ID获取团队ID集合
		List<Long>	teamIds = memberService.getTeamIds(request.getUserId());
		if(teamIds==null ||teamIds.size()<=0){
			response.setScheduleList(scheduleList);
			return response;
		}
		ScheduleRequest re =new ScheduleRequest();
		re.setTeamIds(teamIds);
		long a3 = System.currentTimeMillis();
		logger.info("获取要查询的团的信息耗时"+ (a3-a2) );

		//2根据团队ID集合获取行程列表
		//关联查分页
		PageHelper.startPage(re.getPageNum(),re.getPageSize());
		Page<ScheduleEntity>  schedules =scheduleDao.getAllTripList(re);

		long a4 = System.currentTimeMillis();
		logger.info("查询行程耗时"+ (a4-a3) );
		int num=1;
		if (schedules!=null && schedules.size() >0){
			for (int i = 0; i < schedules.size(); i++) {
				ScheduleEntity schedule = schedules.get(i);
				if(schedule!=null){
					ScheduleEntity lsvo = new ScheduleEntity();
					lsvo.setId(schedule.getId());
					lsvo.setStartDate(schedule.getStartDate());
					lsvo.setEndDate(schedule.getEndDate());
					lsvo.setName(schedule.getName());
					lsvo.setStatus(schedule.getStatus());
					lsvo.setTxGroupId(schedule.getTxGroupId());
					//背景图需要先上传设计好的图片再存返回回来的地址，有5个，循环保存
					//出行状态【1：出行中；2：即将出行；3：已出行】
					//出行中
					if(schedule.getStatus()==1){
						lsvo.setBackgroundPhotoUrl(ConfigConstant.XCLB_CXZBJURL);
					}
					//已经完成
					if(schedule.getStatus()==3){
						lsvo.setBackgroundPhotoUrl(ConfigConstant.XCLB_YWCBJURL);
					}
					//即将出行
					if(schedule.getStatus()==2){
						if(num==1){
							lsvo.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL1);
						}
						if(num==2){
							lsvo.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL2);
						}
						if(num==3){
							lsvo.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL3);
						}
						if(num==4){
							lsvo.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL4);
						}
						if(num==5){
							lsvo.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL5);
						}
						if(num==5){
							num=1;
						}else {
							num++;
						}
					}
                    // 是否围观行程：0正常行程  1围观行程
                    lsvo.setOnlookerType(0);
                    if(schedule.getStatus()==1||schedule.getStatus()==2){
                        //消息分类【1：集合；2：通知；3：公告；4：回复】
                        MemberEntity memberEntity = memberService.getMemberWithTeamIdAndUserId(schedule.getTxGroupId(),request.getUserId());
                        lsvo.setGatherUnreadCount(noticeDetailService.getUnReadNum(1,request.getUserId(),schedule.getTxGroupId(),memberEntity));
                        lsvo.setNoticeUnreadCount(noticeDetailService.getUnReadNum(2,request.getUserId(),schedule.getTxGroupId(),memberEntity));
                        lsvo.setAnnUnreadCount(noticeDetailService.getUnReadNum(3,request.getUserId(),schedule.getTxGroupId(),memberEntity));

                        //watchUnreadCount围观未读数量
                        lsvo.setWatchUnreadCount(noticeDetailService.getUnReadNum(4,request.getUserId(),schedule.getTxGroupId(),memberEntity));
                    }

                    //消息分类【1：集合；2：通知；3：公告；4：回复】
					/*lsvo.setGatherUnreadCount(noticeDetailService.getNumsByTypeAndUserId(1,request.getUserId(),schedule.getTxGroupId()));
					lsvo.setNoticeUnreadCount(noticeDetailService.getNumsByTypeAndUserId(2,request.getUserId(),schedule.getTxGroupId()));
					lsvo.setAnnUnreadCount(noticeDetailService.getNumsByTypeAndUserId(3,request.getUserId(),schedule.getTxGroupId()));
					//watchUnreadCount围观未读数量
					lsvo.setWatchUnreadCount(noticeDetailService.getNumsByTypeAndUserId(4,request.getUserId(),schedule.getTxGroupId()));*/
					scheduleList.add(lsvo);
				}
			}
			response.setPages(schedules.getPages());
			response.setTotals(schedules.getTotal());
			response.setScheduleList(scheduleList);
		}
		long a5 = System.currentTimeMillis();
		logger.info("组装小红点数据耗时"+ (a5-a4) );

		logger.info("总耗时为"+ (a5-a1) );
		return response;
	}
	@Override
	public ScheduleEntity getTeamName(String teamId) {
		return scheduleDao.getTeamName(teamId);
	}

	/**
	 * 根据月份区间查询行程
	 * 例如：6月到8月的
	 * @param userId
	 * @return
     */
	@Override
	public List<ScheduleEntity> getScheduleBetweenDateList(Long userId, String startTime, String endTime) {
		List<Long> teamIds = memberService.getTeamIds(userId);
		if (teamIds.size()<=0){
			return new ArrayList<ScheduleEntity>();
		}
		ScheduleReq req = new ScheduleReq();
		req.setTeamIds(teamIds);
		req.setStartTime(startTime);
		req.setEndTime(endTime);
		return scheduleDao.selectTeamIdsWithStartTimeAndEndTime(req);
	}

	/**
	 * 查询用户几个月内的行程（从当前月起计算）
	 * @param userId
	 * @param months 需要查询几个月
     * @return
     */
	@Override
	public List<ScheduleEntity> getScheduleInSexMonths(Long userId, Integer months) {
		// 获取当前月的第一天
		Date firstDate = DateUtils.getFirstDateOfMonth(new Date());
		// 获取六个月之后月份的最后一天
		Date lastDate = DateUtils.getLastDateOfMonth(DateUtils.addMonths(new Date(), months));

		// 转换成字符串，作为查询条件
		String startTime = DateUtils.date2String("yyyy-MM-dd", firstDate);
		String endTime = DateUtils.date2String("yyyy-MM-dd", lastDate);

		return this.getScheduleBetweenDateList(userId, startTime, endTime);
	}

	/**
	 * 获取旅行社的不同行程状态的行程数量
	 * @param travelId
	 * @param status
     * @return
     */
	@Override
	public int getScheduleCountInTravel(Long travelId, Integer status) {
		ScheduleRequest request = new ScheduleRequest();
		request.setTravelId(travelId);
		request.setStatus(status);
		if(status == 1){
			request.setStartDate(new Date());
		}
		int count = scheduleDao.getTripCountByStatus(request);
		return count;
	}


	/**
	 * 获取旅行社的不同行程状态的全部行程
	 * @param travelId
	 * @param status
     * @return
     */
	@Override
	public List<ScheduleDetailEntityVo> getScheduleListInTravel(Long travelId, Integer status) {
		ScheduleDetailRequest request = new ScheduleDetailRequest();
		request.setTravelId(travelId);
		request.setStatus(status);
        request.setScheduleDate(new Date());
		List<ScheduleDetailEntityVo> scheduleDetailList = scheduleDetailsServiceImpl.selectCountry(request);
		if (org.springframework.util.CollectionUtils.isEmpty(scheduleDetailList)) {
			return new ArrayList<>();
		}
		if (null != scheduleDetailList ){
			for (ScheduleDetailEntityVo scheduleDetail:scheduleDetailList){
                if(null!=scheduleDetail){
                    //国家的经纬
                    if(null != scheduleDetail.getCountryId1() && scheduleDetail.getCountryId1()!="".toString()){
                        RegionEntity regionInfo = regionService.getById(scheduleDetail.getCountryId1());
                        scheduleDetail.setLat(regionInfo.getLat());
                        scheduleDetail.setLng(regionInfo.getLng());
                        //各个国家的行程数
                        ScheduleDetailRequest countryCountRequest = new ScheduleDetailRequest();
                        countryCountRequest.setTravelId(travelId);
                        countryCountRequest.setStatus(status);
                        countryCountRequest.setScheduleDate(new Date());
                        countryCountRequest.setCountryId1(scheduleDetail.getCountryId1());
                        int tcount = scheduleDetailsServiceImpl.selectCountryCount(countryCountRequest);
                        scheduleDetail.setTcount(tcount);
                        if (tcount == 1){ //一个团 第几天 总共天数
                            //行程名称
                            ScheduleRequest requestName = new ScheduleRequest();
                            requestName.setTravelId(travelId);
                            requestName.setStatus(status);
                            requestName.setStartDate(new Date());
                            requestName.setCountryId1(scheduleDetail.getCountryId1());
                            List<ScheduleEntity> scheduleNameList = this.selectScheleName(requestName);
                            scheduleDetail.setName(scheduleNameList.get(0).getName());
                            scheduleDetail.setJourId(scheduleNameList.get(0).getId());//行程id
                            //第几天
                            ScheduleDetailRequest dayNumRequest = new ScheduleDetailRequest();
                            dayNumRequest.setCountryId1(scheduleDetail.getCountryId1());
                            dayNumRequest.setScheduleDate(new Date());
                            List<ScheduleDetailEntity> scheduleDetailListOne = scheduleDetailsServiceImpl.selectdayNum(dayNumRequest);
                            scheduleDetail.setDimDD(scheduleDetailListOne.get(0).getDayNum());

                            //总共天数
                            List<ScheduleEntity> totalDayNumList = this.selectTotalDayNum(scheduleDetailListOne.get(0).getJourId());
                            long totalDays = DateUtils.daysBetween(totalDayNumList.get(0).getStartDate(), totalDayNumList.get(0).getEndDate())+1;
                            logger.info("***"+totalDays);
                            scheduleDetail.setTotalDays(totalDays);
                        }
                    }
                }



			}

		}
		return scheduleDetailList;
	}




	/**
	 * 根据腾迅云id获取接团社信息和地接社信息
	 * @param teamId
	 * @return
     */
	@Override
	public TravelAgencyInfoVo getTravelAgencyInfoVoByTeamId(String teamId){
		TravelAgencyInfoVo travelAgencyInfoVo = new TravelAgencyInfoVo();
		// 根据腾迅云id获取行程
		ScheduleEntity scheduleEntity = scheduleDao.selectByTeamId(teamId);
		if (null != scheduleEntity){
			Long id = scheduleEntity.getId();
			PromptInfoEntity promptInfoEntity = iPromptInfoService.getByScheduleId(id);

			//接团社信息
			travelAgencyInfoVo.setGroupClubInfo(promptInfoEntity.getGroupClubInfo());
			//地接社信息
			travelAgencyInfoVo.setTravelAgencyInfo(promptInfoEntity.getTravelAgencyInfo());
		}

		return travelAgencyInfoVo;
	}


	@Override
	public int getRemainingDaysByTeamId(String teamId) {
		return scheduleDao.getRemainingDaysByTeamId(teamId);
	}

	//通过行程ID获取 腾讯组ID
	@Override
	public String getTxGroupIdByScheduleId(Long scheduleId){
		return scheduleDao.getTxGroupIdByScheduleId(scheduleId);
	}

	//领队端创建行程 将指定领队加入成员表中。
	public MemberEntity addMemberForLeaderByApp(ScheduleEntity schedule, TeamEntity teamEntity,Long userId){
		MemberEntity member = new MemberEntity();
		member.setRealName(schedule.getLeadName());
		member.setSex(schedule.getSex());
		member.setPhone(schedule.getPhone());
		member.settId(teamEntity.getId());//团队id
		member.setRole(1);//领队
		member.setIsAdmin(1);//管理员
		member.setIsNewJoin(0);//是否为新加入（即为自己通过扫描填写资料加入）【0：否；1：是】
		//用户ID
		member.setUserId(userId);
		try {
			if(member.getSex()!=null&&member.getSex()==1){
				member.setPhotoUrl(ConfigConstant.MALE_HEAD_URL);
			}else{
				member.setPhotoUrl(ConfigConstant.FEMALE_HEAD_URL);
			}
//			String photoUrl = PhotoHelper.createMemberImage(member.getRealName(),member.getSex());
//			member.setPhotoUrl(photoUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		memberService.addMember(member);
		return member;
	}
	//更新行程 将无效的行程逻辑删除
	 public int  delSchdule(Long id){
      return scheduleDao.delScheduleById(id);
	}

	//通过团ID获取行程明细ID集合
	public ScheduleEntity selectBytId(Long teamId){
		return scheduleDao.selectBytId(teamId);
	}



	//删除群里的某个成员
	private void deleteGroupMember(String txGroupId,String username){
		DeleteGroupMemberRequest delMemberRequest = new DeleteGroupMemberRequest();
		delMemberRequest.setGroupId(txGroupId);
		delMemberRequest.setSilence(1);

		List<String> accountList = new ArrayList<String>();
		accountList.add(username);
		delMemberRequest.setMemberToDelAccount(accountList);

		timRestAPI.deleteGroupMember(delMemberRequest);
	}
    /*
       通过旅行社id和出行状态获取（即将出行）列表
     */
	@Override
	public SchedulePageResponse getJjTripList(ScheduleRequest request) {

		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		Page<ScheduleEntity> ScheduleEntity = scheduleDao.getJjTripList(request);

		SchedulePageResponse response =new SchedulePageResponse();
		response.setPages(ScheduleEntity.getPages());
		response.setTotals(ScheduleEntity.getTotal());
		response.setScheduleList(ScheduleEntity);
		return response;
	}

	/*
	 通过旅行社id和出行状态获取（已出行）列表
	 */
	@Override
	public SchedulePageResponse getEndTripList(ScheduleRequest request) {

		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		Page<ScheduleEntity> ScheduleEntity = scheduleDao.getEndTripList(request);

		SchedulePageResponse response =new SchedulePageResponse();
		response.setPages(ScheduleEntity.getPages());
		response.setTotals(ScheduleEntity.getTotal());
		response.setScheduleList(ScheduleEntity);
		return response;
	}

	//总共天数
	@Override
	public List<ScheduleEntity> selectTotalDayNum(Long id) {
		List<ScheduleEntity> totalDayNumList = scheduleDao.selectTotalDayNum(id);
		return totalDayNumList;
	}

	//行程名称
	@Override
	public List<ScheduleEntity> selectScheleName(ScheduleRequest request) {
		List<ScheduleEntity> scheduleNameList = scheduleDao.selectScheleName(request);
		return scheduleNameList;
	}

	@Override
	public List<ScheduleEntity> selectScheduleForboundMember(WebMemberRequest memberRequest) {
		int num=1;
		Page<ScheduleEntity> scheduleEntities = scheduleDao.selectScheduleForboundMember(memberRequest);
		if (scheduleEntities!=null && scheduleEntities.size() >0){
			for (int i = 0; i < scheduleEntities.size(); i++) {
				ScheduleEntity schedule =scheduleEntities.get(i);
				if(schedule!=null){
					if(schedule.getStatus()==1){
						schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_CXZBJURL);
					}
					//已经完成
					if(schedule.getStatus()==3){
						schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_YWCBJURL);
					}
					//即将出行
					if(schedule.getStatus()==2){
						if(num==1){
							schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL1);
						}
						if(num==2){
							schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL2);
						}
						if(num==3){
							schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL3);
						}
						if(num==4){
							schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL4);
						}
						if(num==5){
							schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL5);
						}
						if(num==5){
							num=1;
						}else {
							num++;
						}
					}
				}
			}

		}
		return scheduleEntities;

	}

	@Override
	public List<ScheduleEntity> selectScheduleForUnboundMember(WebMemberRequest memberRequest) {
//		PageHelper.startPage(memberRequest.getPageNums(),memberRequest.getPageSizes());
		int num=1;
		Page<ScheduleEntity> scheduleEntities =scheduleDao.selectScheduleForUnboundMember(memberRequest);
		if (scheduleEntities!=null && scheduleEntities.size() >0){
			for (int i = 0; i < scheduleEntities.size(); i++) {
				ScheduleEntity schedule =scheduleEntities.get(i);
				if(schedule!=null){
					if(schedule.getStatus()==1){
						schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_CXZBJURL);
					}
					//已经完成
					if(schedule.getStatus()==3){
						schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_YWCBJURL);
					}
					//即将出行
					if(schedule.getStatus()==2){
						if(num==1){
							schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL1);
						}
						if(num==2){
							schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL2);
						}
						if(num==3){
							schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL3);
						}
						if(num==4){
							schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL4);
						}
						if(num==5){
							schedule.setBackgroundPhotoUrl(ConfigConstant.XCLB_JJCXBJURL5);
						}
						if(num==5){
							num=1;
						}else {
							num++;
						}
					}
				}
			}

		}
		return scheduleEntities;
	}

	@Override
	public ScheduleEntity selectScheduleIdByTeamName(String teamNum) {
		ScheduleEntity scheduleEntity = scheduleDao.selectScheduleByTeamName(teamNum);
		if (null != scheduleEntity){
			return  scheduleEntity;
		}
		return null;
	}


	public String getqrCode(ScheduleEntity schedule){
		String qrCode = "";
		if (!StringUtils.isEmpty(schedule.getTxGroupId())){
			//获取团队头像
			TeamEntity teamEntity = teamService.getTeamByTXGroupId(schedule.getTxGroupId());
			//公众号二维码链接
			if(StringUtils.isEmpty(teamEntity.getQrCode())) {
				WxMpQrCodeTicket ticket = null;
				try {
					ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(Integer.parseInt(teamEntity.getId().toString()));
				} catch (WxErrorException e) {
					e.printStackTrace();
				}
				if (ticket != null&&!StringUtils.isEmpty(ticket.getUrl())) {
					qrCode = ticket.getUrl();
					teamEntity.setQrCode(ticket.getUrl());
					//保存二维码URL
					teamService.updateTeam(teamEntity);
				}
			}else{
				qrCode = teamEntity.getQrCode();
			}
		}
		return qrCode;
	}


	//新增 行程 添加日志
	public void addScheduleLog(ScheduleEntity schedule,TeamEntity teamEntity,MemberEntity memberEntity){
	LogManage log=new LogManage();
	//1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
	if(schedule.getType()!=null&&schedule.getType()==1){
		UserEntity userEntity =userService.getUserRole(schedule.getUserId());
		if(userEntity!=null&&userEntity.getRole()!=null){
			//5旅行社；6计调；7销售（门店）role
			//日志-账号类型:0旅行社管理员，1门店销售（门店），2计调，3领队，
			if(userEntity.getRole()==5){
				log.setAccountType(HelpConstant.LOGZHLX_LXS);
			}
			if(userEntity.getRole()==6){
				log.setAccountType(HelpConstant.LOGZHLX_JD);
			}
			if(userEntity.getRole()==7){
				log.setAccountType(HelpConstant.LOGZHLX_XS);
			}
		}

	}
	if(schedule.getType()!=null&&schedule.getType()==2){
		log.setAccountType(HelpConstant.LOGZHLX_LD);
	}
	log.setOperateModel(HelpConstant.LOGCZMK_TDXC);
	log.setOperateTime(schedule.getCreateTime());
	log.setOperateType("新增行程");
	String name=userService.getUserById(schedule.getUserId()).getRealName();
	log.setName(name);
	log.setUserId(schedule.getUserId());
	StringBuffer str = new StringBuffer("");
	str.append(name).append("创建了一个行程{");
	str.append("行程名称:").append(schedule.getName()).append(",");
	str.append("团号:").append(teamEntity.getTeamNum()).append(",");
	if(memberEntity!=null && memberEntity.getRealName()!=null){
		str.append("领队:").append(memberEntity.getRealName()).append(",");
	}
	str.append("出发地:").append(schedule.getStartPlace()).append(",");
	str.append("开始日期:").append(DateUtils.toString(schedule.getStartDate(),DateUtils.DATE_FORMAT_DATEONLY)).append(",");
	str.append("结束日期:").append(DateUtils.toString(schedule.getEndDate(),DateUtils.DATE_FORMAT_DATEONLY)).append(",");
	str.append("集合时间:").append(DateUtils.toString(schedule.getCollectionTime(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
	str.append("集合地点:").append(schedule.getCollectionPlace()).append(",");
	str.append("创建时间:").append(DateUtils.toString(schedule.getCreateTime(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
	str.append("版本号:").append(schedule.getVersion()).append("}");
	log.setOperateContent(str.toString());
	logManageService.add(log);
	}

	//编辑 行程 添加日志
	public void updateScheduleLog(ScheduleEntity schedule,TeamEntity teamEntity){
		LogManage log=new LogManage();
		//1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
		if(schedule.getType()!=null&&schedule.getType()==1){
			UserEntity userEntity =userService.getUserRole(schedule.getUserId());
			if(userEntity!=null&&userEntity.getRole()!=null){
				//5旅行社；6计调；7销售（门店）role
				//日志-账号类型:0旅行社管理员，1门店销售（门店），2计调，3领队，
				if(userEntity.getRole()==5){
					log.setAccountType(HelpConstant.LOGZHLX_LXS);
				}
				if(userEntity.getRole()==6){
					log.setAccountType(HelpConstant.LOGZHLX_JD);
				}
				if(userEntity.getRole()==7){
					log.setAccountType(HelpConstant.LOGZHLX_XS);
				}
			}

		}
		if(schedule.getType()!=null&&schedule.getType()==2){
			log.setAccountType(HelpConstant.LOGZHLX_LD);
		}
		log.setOperateModel(HelpConstant.LOGCZMK_TDXC);
		log.setOperateTime(schedule.getCreateTime());
		log.setOperateType("新增行程");
		String name=userService.getUserById(schedule.getUserId()).getRealName();
		log.setName(name);
		log.setUserId(schedule.getUserId());
		StringBuffer str = new StringBuffer("");
		str.append(name).append("修改了一个行程{");
		str.append("行程名称:").append(schedule.getName()).append(",");
		str.append("团号:").append(teamEntity.getTeamNum()).append(",");
		str.append("出发地:").append(schedule.getStartPlace()).append(",");
		str.append("开始日期:").append(DateUtils.toString(schedule.getStartDate(),DateUtils.DATE_FORMAT_DATEONLY)).append(",");
		str.append("结束日期:").append(DateUtils.toString(schedule.getEndDate(),DateUtils.DATE_FORMAT_DATEONLY)).append(",");
		str.append("集合时间:").append(DateUtils.toString(schedule.getCollectionTime(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
		str.append("集合地点:").append(schedule.getCollectionPlace()).append(",");
		str.append("修改时间:").append(DateUtils.toString(schedule.getUpdateTime(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
		str.append("版本号:").append(schedule.getVersion()).append("}");
		log.setOperateContent(str.toString());
		logManageService.add(log);
	}

	/**
	 * 根据团队ID获取行程（全部字段）
	 * @param teamId
	 * @return
     */
	@Override
	public ScheduleEntity getScheduleByTid(Long teamId){
		return scheduleDao.getScheduleByTid(teamId);
	}

}
