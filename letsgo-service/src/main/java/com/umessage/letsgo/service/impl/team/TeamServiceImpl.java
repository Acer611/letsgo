package com.umessage.letsgo.service.impl.team;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jrmf360.vo.request.TransferRequest;
import com.jrmf360.vo.response.TransferResponse;
import com.qq.tim.ITimRestAPI;
import com.qq.tim.vo.request.*;
import com.umessage.letsgo.core.annotation.dataitem.DataItem;
import com.umessage.letsgo.core.config.constant.ConfigConstant;
import com.umessage.letsgo.core.timezone.TimeZoneUtil;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.core.utils.ELUtil;
import com.umessage.letsgo.core.utils.JsonUtils;
import com.umessage.letsgo.dao.security.UserDao;
import com.umessage.letsgo.dao.team.TeamDao;
import com.umessage.letsgo.domain.po.journey.*;
import com.umessage.letsgo.domain.po.security.RewardDetailsEntity;
import com.umessage.letsgo.domain.po.security.RewardType;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.system.SpotEntityPo;
import com.umessage.letsgo.domain.po.team.AnalyticalData;
import com.umessage.letsgo.domain.po.team.GroupEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.request.ParameMsg;
import com.umessage.letsgo.domain.vo.common.respone.AppMessageResponse;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.common.respone.CustomMessageResponse;
import com.umessage.letsgo.domain.vo.common.respone.CustomMsg;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleRequest;
import com.umessage.letsgo.domain.vo.notice.request.DetailParamRequest;
import com.umessage.letsgo.domain.vo.team.requset.GuideMemberRequest;
import com.umessage.letsgo.domain.vo.team.requset.MemberRequest;
import com.umessage.letsgo.domain.vo.team.requset.TeamRequest;
import com.umessage.letsgo.domain.vo.team.respone.*;
import com.umessage.letsgo.service.api.activity.ILocationHistoryService;
import com.umessage.letsgo.service.api.journey.*;
import com.umessage.letsgo.service.api.notice.INoticeDetailService;
import com.umessage.letsgo.service.api.security.*;
import com.umessage.letsgo.service.api.system.IRegionService;
import com.umessage.letsgo.service.api.system.ISpotService;
import com.umessage.letsgo.service.api.team.IGroupService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.api.wallet.IWalletService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.constant.WxConstant;
import com.weixin.service.ITemplateMessage;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TeamServiceImpl implements ITeamService {

	Logger logger = Logger.getLogger(TeamServiceImpl.class);

	@Resource
	private TeamDao teamMapper;
	@Resource
	private IMemberService memberService;
	@Resource
	private IGroupService groupService;
	@Resource
	private IDestinationService destinationService;
	@Resource
	private Mapper dozerBeanMapper;
	@Resource
	private ITimRestAPI timRestAPI;
	@Resource
	private IUserService userService;
	@Resource
	private IScheduleService scheduleService;
	@Resource
	private INoticeDetailService noticeDetailService;
	@Resource
	private ISurveyService surveyService;
	@Resource
	private IRegionService regionService;
	@Resource
	private IPromptInfoService promptService;
	@Resource
	private ISpotService spotService;
	@Resource
	private ILocationHistoryService locationHistoryService;
	@Resource
	private IRewardDetailsService rewardDetailsService;
	@Resource
	private UserDao userDao;
	@Resource
	private IWalletService walletService;
	@Resource
	protected WxMpService wxMpService;
	@Resource
	private ITemplateMessage templateMessageService;
	@Resource
	private IWxInfoService wxInfoService;
	@Resource
	private IAppSendMessageService appSendMessageService;
	@Resource
	private IScheduleDetailsService scheduleDetailsService;
	@Resource
	private IScheduleDetailCommentService scheduleDetailCommentService;
	@Resource
	private IWxSendMessageService wxSendMessageService;

	@Override
	public int addTeam(TeamEntity teamEntity) {
		teamEntity.setStatus(2);
		teamEntity.setCreateTime(new Date());
		teamEntity.setVersion(0l);
		return teamMapper.insert(teamEntity);
	}

	@Override
	public int deleteTeam(TeamEntity teamEntity) {
		return teamMapper.delete(teamEntity.getId());
	}

	@Override
	public int updateTeam(TeamEntity teamEntity) {
		teamEntity.setUpdateTime(new Date());
		return teamMapper.update(teamEntity);
	}

	@Override
	@DataItem
	public TeamEntity getTeamById(Long id) {
		TeamEntity teamEntity  = teamMapper.select(id);
		if (teamEntity == null) {
			return new TeamEntity();
		}

		return teamEntity;
	}

	public TeamEntity selectById(Long id) {
		TeamEntity teamEntity  = teamMapper.selectById(id);
		if (teamEntity == null) {
			return new TeamEntity();
		}

		return teamEntity;
	}


	@Override
	public TeamEntity selectByScheduleId(Long id) {
		TeamEntity teamEntity  = teamMapper.selectByScheduleId(id);
		if (teamEntity == null) {
			return new TeamEntity();
		}
		return teamEntity;
	}

	/**
	 * 根据腾讯群组ID获取团队实体
	 * @param txGroupId
	 * @return
     */
	@Override
	public TeamEntity getTeamByTXGroupId(String txGroupId) {
		TeamEntity teamEntity = teamMapper.selectTeamWithTXGroupId(txGroupId);
		if (teamEntity == null) {
			return new TeamEntity();
		}
		return teamEntity;
	}

	@Override
	public List<TeamEntity> getTeamEntityList(Long userId) {
		// 获取用户的所有团队对象
		List<String> teamIdList = new ArrayList<>();
		teamIdList = memberService.getTeamIdsByUserId(userId);
		if(teamIdList==null && teamIdList.size()<=0){
			return new ArrayList<>();
		}
		List<TeamEntity> teamList = teamMapper.selectTeamListWithIDs(teamIdList);
		if (CollectionUtils.isEmpty(teamIdList)) {
			return new ArrayList<>();
		}
		return teamList;
	}

	/**
	 * 获取团队详情
	 * @param txGroupId
	 * @return
     */
	@Override
	@DataItem
	public TeamRespone getTeamDetail(String txGroupId) {
		// 1、获取团队对象
		TeamEntity teamEntity = this.getTeamByTXGroupId(txGroupId);
		if (teamEntity == null) {
			return TeamRespone.createNotFoundResponse();
		}

		// 2、获取团队中的全部成员，赋值到团队对象中
		List<MemberEntity> members = memberService.getMemberListByTeamId(txGroupId);
		teamEntity.setMembers(members);

		// 3、获取行程问卷
		SurveyEntity surveyEntity = surveyService.selectByTeamId(teamEntity.getId());
		if (surveyEntity == null) {	// 无问卷
			teamEntity.setIsIssueSurvey(1);
		} else if(CollectionUtils.isEmpty(surveyEntity.getSurveyDetailEntityList())) {
			teamEntity.setIsIssueSurvey(2);	// 未发放问卷
		} else {
			int flag = 4;
			for (SurveyDetailEntity surveyDetailEntity : surveyEntity.getSurveyDetailEntityList()) {
				if (surveyDetailEntity.getConfirmStatus() == 0) {
					flag = 3;	// 如果有未填写的问卷，设置状态值（3），跳出循环
					break;
				}
			}
			teamEntity.setIsIssueSurvey(flag);	// 已发放问卷
		}

		TeamRespone teamRespone = new TeamRespone();
		teamRespone.setTeamEntity(teamEntity);
		return teamRespone;
	}

	/**
	 * 获取团队列表
	 * @param userId
	 * @return
     */
	@Override
	@DataItem
	public TeamListResponse getTeamList(Long userId, Integer flag) {
		// 获取用户的所有团队对象
		List<String> teamIdList = new ArrayList<>();
		List<TeamEntity> teamList = new ArrayList<>();
		List<TeamEntity> newTeamList = new ArrayList<>();

		if (flag !=null && flag == 1) {
			teamIdList = memberService.getTeamIdsByUserIdAndAdmin(userId);
			if(teamIdList!=null && teamIdList.size()>0){
				teamList = teamMapper.selectUnfinishedTeamListWithIDs(teamIdList);
			}

		} else {
			teamIdList = memberService.getTeamIdsByUserId(userId);
			if(teamIdList!=null && teamIdList.size()>0) {
				teamList = teamMapper.selectTeamListWithIDs(teamIdList);
			}
		}
		if (CollectionUtils.isEmpty(teamList)) {
//			return TeamListResponse.createNotFoundResponse();
			return new TeamListResponse();
		}


		// 获取行程问卷状态
		for (TeamEntity teamEntity : teamList) {
			SurveyEntity surveyEntity = surveyService.selectByTeamId(teamEntity.getId());
			if (surveyEntity == null) {	// 无问卷
				teamEntity.setIsIssueSurvey(1);
			} else if(CollectionUtils.isEmpty(surveyEntity.getSurveyDetailEntityList())) {
				teamEntity.setIsIssueSurvey(2);	// 未发放问卷
			} else {
				int f = 4;
				for (SurveyDetailEntity surveyDetailEntity : surveyEntity.getSurveyDetailEntityList()) {
					if (surveyDetailEntity.getConfirmStatus() == 0) {
						f = 3;	// 如果有未填写的问卷，设置状态值（3），跳出循环
						break;
					}
				}
				teamEntity.setIsIssueSurvey(f);	// 已发放问卷
			}
			newTeamList.add(teamEntity);
		}

		TeamListResponse teamRespone = new TeamListResponse();
		teamRespone.setTeamList(newTeamList);
		return teamRespone;
	}

	/**
	 * 获取团队成员列表
	 * @param txGroupId
	 * @return
     */
	@Override
	public TeamMemberResponse getTeamMemberList(String txGroupId) {
		TeamMemberResponse teamMemberResponse = new TeamMemberResponse();
		teamMemberResponse.setTeamId(txGroupId);	// 放入团队ID
		TeamEntity teamEntity = this.getTeamByTXGroupId(txGroupId);

		// 1、获取领队和导游，放入VO实体的领队&导游列表中
		List<MemberEntity> administrator = memberService.getTourGuideList(teamEntity.getId(), null);
		teamMemberResponse.setAdministratorList(administrator);

		// 2、获取所有小组及组员，放入VO实体的小组列表中
		List<GroupEntity> groups = fetchGroupDetailListByTeamId(teamEntity.getId());
		teamMemberResponse.setGroupList(groups);

		// 3、获取未分组的成员，放入VO实体的个人列表中
		List<MemberEntity> personals = fetchNotInGroupMemberListByTeamId(teamEntity.getId());
		teamMemberResponse.setPersonalList(personals);

		// 计算游客数
		Integer count = 0;
		for (GroupEntity group : groups) {
			count = count + group.getTotalCount();
		}
		count = count + personals.size();
		teamMemberResponse.setTouristCount(count);

		return teamMemberResponse;
	}

	@Override
	public List<GroupEntity> fetchGroupDetailListByTeamId(Long teamId){
		List<GroupEntity> groups = groupService.getGroupListByTeamId(teamId);

//		for (GroupEntity group : groups) {
//			// 获取小组组员
//			List<MemberEntity> memberList = memberService.getGroupMembertList(group.getId());
//			group.setMemberList(memberList);
//		}
		return groups;
	}

	@Override
	public List<MemberEntity> fetchNotInGroupMemberListByTeamId(String teamId){
		MemberRequest condition = new MemberRequest();
		condition.setTeamId(teamId);
		condition.setGroupId(-1l);
		condition.setRole("eq3");
		condition.setSort_joinStatus("desc");
		return memberService.getMemberList(condition);
	}

	@Override
	public List<MemberEntity> fetchNotInGroupMemberListByTeamId(Long teamId) {
		MemberRequest condition = new MemberRequest();
		condition.settId(teamId);
		condition.setGroupId(-1l);
		condition.setRole("eq3");
		condition.setSort_joinStatus("desc");
		return memberService.getMemberList(condition);
	}

	@Override
	public List<MemberEntity> fetchLeaderAndMemberListByTeamId(Long teamId) {
		MemberRequest condition = new MemberRequest();
		condition.settId(teamId);
		condition.setGroupId(-1l);
		condition.setRole("noeq2");
		condition.setSort_joinStatus("desc");
		return memberService.getMemberList(condition);
	}

	/**
	 * 获取最近的出行中或者即将出行的团队行程
	 * 如果传入teamId，则获取指定团队信息，否则获取出行中的团队信息或者即将出行的团队信息
	 * @param teamId
	 * @param userId
	 * @return
     */
	@Override
	public TeamRespone getLatestTeam(Long userId, String teamId) {
		TeamEntity team = new TeamEntity();
		if (StringUtils.isEmpty(teamId)) {
			List<String> teamIds = memberService.getTeamIdsByUserIdAndAdmin(userId);
			List<TeamEntity> teamList = teamMapper.selectUnfinishedTeamListWithIDs(teamIds);
			team = teamList.get(0);
		} else {
			team = this.getTeamByTXGroupId(teamId);
		}

		if (team.getId() == null || team.getStatus() == 3) {
			return TeamRespone.createNotAnyTravelResponse();
		}

		// 2、获取团队中的所有游客及游客总人数
		List<MemberEntity> touristList = memberService.getTouristList(team.getTxGroupid());
		List<MemberEntity> memberList = new ArrayList<>();
		for (MemberEntity member : touristList) {
			if (member.getGroupId() == -1) {
				memberList.add(member);	// 未分组
			} else if (member.getGroupId() != -1 && member.getIsLeader() == 1) {
				memberList.add(member);	// 有小组且为组长
			} else {	// 有小组但不是组长
				continue;
			}
		}

		team.setMembers(memberList);
		team.setTouristCount(touristList.size());

		TeamRespone teamRespone = new TeamRespone();
		teamRespone.setTeamEntity(team);
		return teamRespone;
	}

	/**
	 * 增加导游
	 * @param guide
	 * @return
     */
	@Override
	public CommonResponse addTourGuide(GuideMemberRequest guide) {

		TeamEntity teamEntity = this.getTeamByTXGroupId(guide.getTeamId());
		// 验证手机号是否在该团存在
		List<MemberEntity> memberEntityList = memberService.getMemberListByTId(teamEntity.getId());
		for (MemberEntity memberEntity : memberEntityList) {
			if (guide.getPhone().equals(memberEntity.getPhone())) {
				return CommonResponse.createExistPhoneResponse();
			}
		}

		// 1、设置成员的身份为导游，默认没有管理权限
		MemberEntity member = dozerBeanMapper.map(guide, MemberEntity.class);
		member.setTeamId(guide.getTeamId());
		member.settId(teamEntity.getId());
		member.setRole(2);
		member.setRoleDescn("导游");
		member.setIsAdmin(0);
		member.setIsNewJoin(0);//是否为新加入（即为自己通过扫描填写资料加入）【0：否；1：是】
		// 2、生成头像并设置
		String ImgURL = null;
		try {
			//ImgURL = PhotoHelper.createMemberImage(member.getRealName(), member.getSex());
			if(member.getSex()!=null&&member.getSex()==1){
				ImgURL= ConfigConstant.MALE_HEAD_URL;
			}else{
				ImgURL= ConfigConstant.FEMALE_HEAD_URL;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		member.setPhotoUrl(ImgURL);
		memberService.addMember(member);

		// 检查添加的导游是否有用户
		UserEntity user = userService.getUserByPhone(member.getPhone());
		if(user != null) {//更新已有用户的新成员
			member.setUserId(user.getId());
			member.setJoinStatus(1);
			String txGroupId = teamEntity.getTxGroupid();
			member.setTeamId(txGroupId);
			memberService.updateMember(member);
			//将已有用户加入腾迅云群组中
			if(!org.apache.commons.lang3.StringUtils.isEmpty(txGroupId)){
				//将已有用户加入腾迅云群组中
				scheduleService.addTXGroup(member, user, txGroupId);
				//发送欢迎加入信息
				userService.sendInvitationMessage(member,teamEntity.getTxGroupid());
				//增加好友关系
				//List<MemberEntity> members = memberService.getTouristList(txGroupId);
				//scheduleService.addFriend(user.getUserName(), members);
			}
		}

		// 3、更新团队中的人数
		int total = teamEntity.getTotalCount() + 1;
		teamEntity.setTotalCount(total);
		teamMapper.update(teamEntity);

		// 4、发送邀请加入短信
		try {
			userService.sendInvitationMessage(member.getId(), member.getPhone());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		CommonResponse response = new CommonResponse();
		response.setRetMsg("成功添加导游！");
		return response;
	}

	/**
	 * 获取需要变更为出行中和已出行的团队
	 * @return
     */
	@Override
	public List<TeamEntity> getTripStageTeam() {
		List<TeamEntity> teamEntityList = teamMapper.selectUnfinishedTeam();
		if (CollectionUtils.isEmpty(teamEntityList)) {
			return Collections.emptyList();
		}
		return teamEntityList;
	}

	/**
	 * 获取出行结束的团队
	 * @return
     */
	@Override
	public List<TeamEntity> getFinishedTeam() {
		//获取昨天时间
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-1);
		Date yesterday = calendar.getTime();
		//设置活动时间
		Date activityDate = this.getSimpleDate("2016-12-31");
		ScheduleRequest scheduleRequest = new ScheduleRequest();
		scheduleRequest.setYesterday(yesterday);
		scheduleRequest.setActivityDate(activityDate);
		List<TeamEntity> teamEntityList = teamMapper.selectFinishedTeam(scheduleRequest);
		if (CollectionUtils.isEmpty(teamEntityList)) {
			return Collections.emptyList();
		}
		List<TeamEntity> teamEntities = this.getrealTeamEntity(teamEntityList);
		return teamEntities;
	}



	public List<TeamEntity> getrealTeamEntity(List<TeamEntity> teamEntityList){
		//有效的行程的团队
		List<TeamEntity> realTeamEntityList = new ArrayList<>();
		//查询行程是否真实有效
		for (TeamEntity teamEntity:teamEntityList) {
			if (teamEntity != null){
				//根据团队ID获取团队成员领队
				List<Long> leaderMemberUserId = memberService.getLeaderUserIdByTId(teamEntity.getId());

				//根据团队ID获取团队成员游客
				List<Long> memberUserIdList = memberService.getMemberUserIdList(teamEntity.getId());

				//根据团队ID获取所有景点
				List<SpotEntityPo> spotListByTeam = spotService.getSpotListByTeam(teamEntity.getId());
				for (SpotEntityPo spotEntity:spotListByTeam) {
					if (spotEntity != null){
						//根据领队用户ID和创建时间和坐标距离查询用户位置数量
						int li = locationHistoryService.selectCountWithUserIdAndDate(leaderMemberUserId, spotEntity.getJs_start_date(), spotEntity.getJs_end_date(), Double.valueOf(spotEntity.getLng()), Double.valueOf(spotEntity.getLat()));
						//根据游客用户ID和创建时间和坐标距离查询用户位置数量
						int mi = locationHistoryService.selectCountWithUserIdAndDate(memberUserIdList, spotEntity.getJs_start_date(), spotEntity.getJs_end_date(), Double.valueOf(spotEntity.getLng()), Double.valueOf(spotEntity.getLat()));

						if (li > 0 && mi >= 3){
							realTeamEntityList.add(teamEntity);
							break;
						}
					}
				}
			}
		}
		return realTeamEntityList;
	}

	/**
	 * 发放奖励
	 * @param teamEntity
	 * @return
     */
	@Override
	public boolean issueReward(TeamEntity teamEntity) {
		//设置活动时间
		Date startDate = this.getSimpleDate("2016-10-01");
		Date endDate = this.getSimpleDate("2016-11-30");
		if (teamEntity != null){
			//根据团队ID关联用户表查询团队成员表（memeber）为领队角色
			List<MemberEntity> leaderMemberEntity = memberService.getLeaderByTId(teamEntity.getId());
			MemberEntity memberEntity = leaderMemberEntity.get(0);
			if (memberEntity != null){
				//查询邀请奖励明细没有发放的
				List<RewardDetailsEntity> rewardDetailsEntities = rewardDetailsService.selectInviteRewardByUserId(null, memberEntity.getUserId(), 1, 0, startDate, endDate, null, null);
				if (rewardDetailsEntities.size() > 0){
					RewardDetailsEntity rewardDetailsEntity = rewardDetailsEntities.get(0);
					//设置邀请奖励
					this.setInviteReward(rewardDetailsEntity);
				}
				//设置带团人数奖励
				this.setToursReward(teamEntity,memberEntity,startDate,endDate);
			}

		}

		/*
		//查询所有活动时间范围内的邀请奖励没有发放的明细
		List<RewardDetailsEntity> rewardDetailsEntities = rewardDetailsService.selectInviteRewardByUserId(null, 1, 0, startDate, endDate, null);
		for (RewardDetailsEntity rewardDetailsEntity:rewardDetailsEntities) {
			//设置邀请奖励
			if (rewardDetailsEntity != null){
				this.setInviteReward(rewardDetailsEntity);
			}
		}
		*/

		/*
		//查询所有的带团人数奖励没有发放的明细
		List<RewardDetailsEntity> rewardDetailsEntitieList = rewardDetailsService.selectInviteRewardByUserId(null, 2, 0, null, null, null);
		for (RewardDetailsEntity reward:rewardDetailsEntitieList) {
			if (reward.getUserId() != null){
				//获取邀请人用户
				UserEntity userEntity = userService.getUserById(reward.getUserId());
				if (userEntity != null){
					//转账到钱包账户
					this.setToursRewardNotIssueReward(userEntity,reward);
				}
			}else {
				//获取被邀请人用户
				UserEntity fromUserEntity = userService.getUserById(reward.getFromId());
				if (fromUserEntity != null){
					//转账到钱包账户
					this.setToursRewardNotIssueReward(fromUserEntity,reward);
				}
			}
		}
		*/
		return true;
	}

	/**
	 * 设置邀请奖励
	 * @param rewardDetailsEntity
     */
	public void setInviteReward(RewardDetailsEntity rewardDetailsEntity){
		//获取邀请人用户
		UserEntity userEntity = userService.getUserById(rewardDetailsEntity.getUserId());
		this.setReward(userEntity,rewardDetailsEntity);
	}


	/**
	 * 带团人数奖励没有发放未成功的
	 * @param userEntity
	 * @param rewardDetailsEntity
	 */
	public void setToursRewardNotIssueReward(UserEntity userEntity, RewardDetailsEntity rewardDetailsEntity){
		this.setReward(userEntity,rewardDetailsEntity);
	}


	public void setReward(UserEntity userEntity, RewardDetailsEntity rewardDetailsEntity){
		//转账到钱包账户
		TransferResponse transferResponse = this.setTransferToUser(userEntity, rewardDetailsEntity.getAmount());
		if ("0000".equals(transferResponse.getRespstat())){
			//设置用户奖励
			userEntity.setTotalReward(userEntity.getTotalReward() + rewardDetailsEntity.getAmount());
			userService.update(userEntity);
			rewardDetailsEntity.setIsInto(1);
			rewardDetailsEntity.setDescn(RewardType.getDescn("INVITE"));
			rewardDetailsService.update(rewardDetailsEntity);
			logger.info("用户Id:" + userEntity.getId() + "，渠道转账至个人用户成功");
		}else {
			logger.error("用户Id:" + userEntity.getId() + "，渠道转账至个人用户失败, 原因：" +
					transferResponse.getRespstat() + "," + transferResponse.getRespmsg());
		}
	}


	/**
	 * 设置带团人数奖励
	 * @param startDate
     * @param endDate
     */
	public void setToursReward(TeamEntity teamEntity, MemberEntity memberEntity, Date startDate, Date endDate){
		//根据团队ID关联用户表查询团队成员表（memeber）为游客角色的人数，判断人数是否大于等于5，如果大于等于5则加添奖励
		List<MemberEntity> memberEntityList = memberService.getTouristList(teamEntity.getId());
		if (memberEntityList.size() >= 5){
			//获取邀请人用户邀请奖励明细
			List<RewardDetailsEntity> rewardDetailsEntitiesList = rewardDetailsService.selectInviteRewardByUserId(null, memberEntity.getUserId(), 1, null, startDate, endDate, null, null);
			if(rewardDetailsEntitiesList.size() > 0){
				RewardDetailsEntity rewardDetailsEntity = rewardDetailsEntitiesList.get(0);

				//获取邀请人用户
				UserEntity userEntity = userService.getUserById(rewardDetailsEntity.getUserId());
				//查询邀请人用户的带团奖励明细没有发放的
				List<RewardDetailsEntity> rewardDetailsEntities = rewardDetailsService.selectInviteRewardByUserId(null, null, 2, 0, null, null, teamEntity.getId(), 1);
				//根据团队ID和且奖励类型是邀请人带团人头奖励的查询奖励明细表判断是否为null
				if (rewardDetailsEntities.size() <= 0){
					//邀请人转账及添加奖励明细
					this.setNotToursRewardForUser(userEntity,rewardDetailsEntity,new Double(memberEntityList.size()),teamEntity,memberEntity);
				}

				//获取被邀请人用户
				UserEntity fromUserEntity = userService.getUserById(memberEntity.getUserId());
				//查询被邀请人用户的带团奖励明细没有发放的
				List<RewardDetailsEntity> fromRewardDetailsEntities = rewardDetailsService.selectInviteRewardByUserId(null, null, 2, 0, null, null, teamEntity.getId(), 2);
				//根据团队ID和且奖励类型是被邀请人带团人头奖励的查询奖励明细表判断是否为null
				if(fromRewardDetailsEntities.size() <= 0){
					//被邀请人转账及添加奖励明细
					this.setNotToursRewardForFromUser(fromUserEntity,rewardDetailsEntity,new Double(memberEntityList.size()),teamEntity);
				}
			}
		}
	}


	/**
	 * 设置邀请人带团人数奖励没有发放的
	 * @param userEntity
	 * @param rewardDetailsEntity
	 */
	public void setNotToursRewardForUser(UserEntity userEntity, RewardDetailsEntity rewardDetailsEntity, Double amount, TeamEntity teamEntity, MemberEntity memberEntity){
		//转账到钱包账户
		TransferResponse transferResponse = this.setTransferToUser(userEntity, amount);
		if ("0000".equals(transferResponse.getRespstat())){
			//设置邀请人用户带团人头奖励
			userEntity.setTotalReward(userEntity.getTotalReward() + amount);
			userService.update(userEntity);
			//添加邀请人用户带团人头奖励的奖励明细
			this.addRewardDetailsEntity(userEntity.getId(),amount,"邀请人带团人头奖励",1,memberEntity.getUserId(),2,teamEntity.getId(),1);
			logger.info("用户Id:" + userEntity.getId() + "，渠道转账至个人用户成功");
		}else {
			//添加邀请人用户带团人头奖励的奖励明细
			this.addRewardDetailsEntity(userEntity.getId(),amount,"邀请人带团人头奖励",0,memberEntity.getUserId(),2,teamEntity.getId(),1);
			logger.error("用户Id:" + userEntity.getId() + "，渠道转账至个人用户失败, 原因：" +
					transferResponse.getRespstat() + "," + transferResponse.getRespmsg());
		}
	}

	/**
	 * 设置被邀请人带团人数奖励没有发放的
	 * @param fromUserEntity
	 * @param rewardDetailsEntity
	 */
	public void setNotToursRewardForFromUser(UserEntity fromUserEntity, RewardDetailsEntity rewardDetailsEntity, Double amount, TeamEntity teamEntity){
		//转账到钱包账户
		TransferResponse transferResponseFrom = this.setTransferToUser(fromUserEntity, amount);
		if ("0000".equals(transferResponseFrom.getRespstat())){
			//设置被邀请人用户带团人头奖励
			fromUserEntity.setTotalReward(fromUserEntity.getTotalReward() + amount);
			userService.update(fromUserEntity);
			//添加被邀请人用户带团人头奖励的奖励明细
			this.addRewardDetailsEntity(rewardDetailsEntity.getUserId(),amount,"被邀请人带团人头奖励",1,fromUserEntity.getId(),2,teamEntity.getId(),2);
			logger.info("用户Id:" + fromUserEntity.getId() + "，渠道转账至个人用户成功");
		}else {
			//添加被邀请人用户带团人头奖励的奖励明细
			this.addRewardDetailsEntity(rewardDetailsEntity.getUserId(),amount,"被邀请人带团人头奖励",0,fromUserEntity.getId(),2,teamEntity.getId(),2);
			logger.error("用户Id:" + fromUserEntity.getId() + "，渠道转账至个人用户失败, 原因：" +
					transferResponseFrom.getRespstat() + "," + transferResponseFrom.getRespmsg());
		}
	}

	/**
	 * 转账方法
	 * @param userEntity
	 * @param amount
	 * @return
	 */
	public TransferResponse setTransferToUser(UserEntity userEntity, Double amount){
		TransferRequest transferRequestFrom = new TransferRequest();
		transferRequestFrom.setCustUid(userEntity.getId().toString());
		transferRequestFrom.setTransferAmount(amount);
		transferRequestFrom.setCustOrderno(UUID.randomUUID().toString());
		transferRequestFrom.setTransferDesc("转账");
		transferRequestFrom.setCustMobile(userEntity.getPhone());
		TransferResponse transferResponse = walletService.transferToUser(transferRequestFrom);
		return transferResponse;
	}

	//添加奖励明细
	public void addRewardDetailsEntity(Long userId, Double amount, String descn, Integer isInto, Long fromId, Integer rewardType, Long teamId, Integer rewardFrom){
		RewardDetailsEntity rewardDetailsEntity = new RewardDetailsEntity();
		rewardDetailsEntity.setUserId(userId);
		rewardDetailsEntity.setAmount(amount);
		rewardDetailsEntity.setDescn(descn);
		rewardDetailsEntity.setIsInto(isInto);
		rewardDetailsEntity.setCreateTime(new Date());
		rewardDetailsEntity.setFromId(fromId);
		rewardDetailsEntity.setRewardType(rewardType);
		rewardDetailsEntity.setTeamId(teamId);
		rewardDetailsEntity.setRewardFrom(rewardFrom);
		rewardDetailsService.add(rewardDetailsEntity);
	}

	/**
	 * 处理出行状态的变更
	 * @param teamEntity
	 * @return
     */
	@Override
	public boolean processTripStage(TeamEntity teamEntity) {
		Date startDate = teamEntity.getStartDate();
		Date endDate = DateUtils.addDay(teamEntity.getEndDate(), 1);	// 行程结束日期 + 1
		Date currentDate = new Date();

		// 更改IM中的字段
		ModifyGroupBaseInfoRequest request = new ModifyGroupBaseInfoRequest();
		request.setGroupId(teamEntity.getTxGroupid());
		List<AppDefinedData> appDefinedDatas = new ArrayList<>();
		AppDefinedData appDefinedData = new AppDefinedData();

		SendGroupMsgRequest groupMsgRequest = new SendGroupMsgRequest();
		groupMsgRequest.setGroupId(teamEntity.getTxGroupid());
		List<MsgBody> bodyList = new ArrayList<>();

		// 如果团队处于即将出行的状态，则由即将出行变为出行中
		if (teamEntity.getStatus() == 2 && (startDate.equals(currentDate) || startDate.before(currentDate)) ) {    // 将状态由即将出行改为出行中
			teamEntity.setStatus(1);

			// 更改IM中的自定义字段
			appDefinedData.setKey("TripStage");
			appDefinedData.setValue("1");
			request.setNotification("群资料【团队的出行状态】进行变更为出行中");

			// 发送一条系统消息
			bodyList = createMsgBody("美好的行程马上开始，预祝大家玩得尽兴");

		}

		if (teamEntity.getStatus() == 1 && (endDate.equals(currentDate) || endDate.before(currentDate)) ) {    // 将状态由出行中改为已出行
			teamEntity.setStatus(3);

			// 更改IM中的自定义字段
			appDefinedData.setKey("TripStage");
			appDefinedData.setValue("3");
			request.setNotification("群资料【团队的出行状态】进行变更为已出行");

			// 发送一条系统消息
			bodyList = createMsgBody("行程已落下帷幕，记得常回来和团友们聚聚");
		}

		// 更改数据库中的出行状态
		teamMapper.update(teamEntity);

		// 更改IM中的团队出行状态自定义字段
		appDefinedDatas.add(appDefinedData);
		request.setAppDefinedData(appDefinedDatas);
		timRestAPI.modifyGroupBaseInfo(request);

		// 使用IM 发送系统消息，告知游客团队状态有变化
		int random = (int) (Math.random() * 1000000);
		groupMsgRequest.setRandom(random);
		groupMsgRequest.setMsgBody(bodyList);
		timRestAPI.sendGroupMsg(groupMsgRequest);

		//this.updateUnreadMsg(teamEntity.getTxGroupid()); 	// 更改为已读状态

		return true;
	}

	/**
	 * 更改团队中所有成员的未读及未确认信息为已读状态
	 * @param teamId
     */
	private void updateUnreadMsg(String teamId) {
		DetailParamRequest request = new DetailParamRequest();
		request.setTeamId(teamId);
		request.setActiveStatus(0);

		noticeDetailService.updateUnReads(request);
		noticeDetailService.updateNotClick(null, teamId, null);
	}

	private List<MsgBody> createMsgBody(String msgContent) {
		List<MsgBody> bodyList = new ArrayList<>();
		MsgContent content = new MsgContent();
		CustomMessageResponse response = new CustomMessageResponse();
		List<CustomMsg> customMsgs = new ArrayList<>();
		CustomMsg msg = new CustomMsg();
		msg.setContent(msgContent);
		customMsgs.add(msg);

		response.setMsg(customMsgs);
		response.setMsgType(2);

		content.setData(JsonUtils.obj2json(response));
		content.setDesc(msgContent);

		MsgBody body = new MsgBody();
		body.setMsgType(MsgType.getTIMCustomElem());
		body.setMsgContent(content);

		bodyList.add(body);

		return bodyList;
	}

	/**
	 * 获取需要发送明日行程及天气的团队
	 * @return
     */
	@Override
	public List<TeamEntity> getSendMessageTeam() {
		List<TeamEntity> teamEntityList = teamMapper.selectSendMsgTeam();
		if (CollectionUtils.isEmpty(teamEntityList)) {
			return Collections.emptyList();
		}
		return teamEntityList;
	}

	/**
	 * 获取需要发送点评的团队
	 * @return
     */
	public List<TeamEntity> getSendCommnetTeam() {
		List<TeamEntity> teamEntityList = teamMapper.selectSendCommnetTeam(new Date());
		if (CollectionUtils.isEmpty(teamEntityList)) {
			return Collections.emptyList();
		}
		return teamEntityList;
	}

	/**
	 * 向团队发送自定义的行程信息
	 * @param teamEntity
	 * @param scheduleDetail
     */
	public void sendScheduleMessage(TeamEntity teamEntity, ScheduleDetailEntity scheduleDetail) {
		// 封装行程消息
		//CustomMessageResponse response = getCustomMsg(scheduleDetailEntity, teamEntity.getTxGroupid());

		// 获取已加入的团队成员
		MemberRequest request = new MemberRequest();
		request.settId(teamEntity.getId());
		request.setJoinStatus(1);
		List<MemberEntity> memberEntities = memberService.getMemberList(request);

		for (MemberEntity memberEntity : memberEntities) {
            logger.info("获取已加入的团队成员,团成员id分别为:" + memberEntity.getUserId());

            //发送系统信息
            //sendMessageToJoinInMember(response, memberEntity.getUserEntity().getUserName());
            //发送天气 微信模板消息
            wxSendMessageService.sendWeatherRemindTemplateMessage(memberEntity,scheduleDetail);
            //发送行程 微信模板消息
            wxSendMessageService.sendScheduleRemindTemplateMessage(memberEntity,scheduleDetail);
            //发送次日行程系统信息
            AppMessageResponse responseSchedule = getCustomScheduleMsg(memberEntity,scheduleDetail);
            appSendMessageService.sendMessageToJoinInMember(responseSchedule, memberEntity.getUserEntity().getUserName());
        }
	}

	@Override
	public void sendCommnetMessage(TeamEntity teamEntity) {

		// 获取已加入的团队成员
		MemberRequest request = new MemberRequest();
		request.settId(teamEntity.getId());
		request.setJoinStatus(1);
		request.setRole("eq3");
		List<MemberEntity> memberEntities = memberService.getMemberList(request);

		for (MemberEntity memberEntity : memberEntities) {
			scheduleDetailCommentService.sendEveryDayComment(memberEntity);
		}
	}

	/**
	 * 获取行程出发地的本地时间(小时)
	 * @param scheduleDetail
	 * @return
     */
	public int getScheduleStartHours(ScheduleDetailEntity scheduleDetail) {
		// 默认当前时区
		Calendar cal = Calendar.getInstance();
		TimeZone timeZone = cal.getTimeZone();

		// 取行程出发地的当地时区
		if (scheduleDetail != null && !StringUtils.isEmpty(scheduleDetail.getStartTimeZone())){
			timeZone = TimeZone.getTimeZone(scheduleDetail.getStartTimeZone());
		}

		Date date = TimeZoneUtil.getTimeZoneDate(new Date(), timeZone);
		logger.info("获取更改时区后的日期:"+date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取行程目的地的本地时间(小时)
	 * @param scheduleDetail
	 * @return
     */
	public int getScheduleDestinationHours(ScheduleDetailEntity scheduleDetail) {
		// 默认当前时区
		Calendar cal = Calendar.getInstance();
		TimeZone timeZone = cal.getTimeZone();

		// 取行程目的地的当地时区
		if (scheduleDetail != null && !StringUtils.isEmpty(scheduleDetail.getDestinationTimeZone())){
			timeZone = TimeZone.getTimeZone(scheduleDetail.getDestinationTimeZone());
		}

		Date date = TimeZoneUtil.getTimeZoneDate(new Date(), timeZone);
		logger.info("获取更改时区后的日期:"+date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	private CustomMessageResponse getCustomMsg(ScheduleDetailEntity scheduleDetailEntity, String teamId) {
		CustomMessageResponse response = new CustomMessageResponse();
		String weatherDescn = scheduleDetailEntity.getWeatherDescn();
        logger.info("获取行程的天气描述："+weatherDescn);
		List<CustomMsg> customMsgs = new ArrayList<>();
		// 设置天气
		CustomMsg msg1 = new CustomMsg();
		msg1.setTitle("天气：");
		msg1.setContent(weatherDescn);
		customMsgs.add(msg1);

		// 设置行程
		CustomMsg msg2 = new CustomMsg();
		msg2.setTitle("行程：");
		msg2.setContent(scheduleDetailEntity.getDesc());
		customMsgs.add(msg2);

		// 设置响应对象
		response.setDate(scheduleDetailEntity.getScheduleDate().getTime());
		response.setMsg(customMsgs);
		response.setMsgType(1);
		response.setTargetUrl(Constant.WEB_BASE_URL + "schedule/getScheduleShow?");
		response.setFlag("preview");
		response.setScheduleDetaildId(scheduleDetailEntity.getId());
		response.setTeamId(teamId);

		logger.info(response);
		logger.info("封装行程消息完毕");
		return response;
	}

	/**
	 * 给团队内已加入的成员发送系统信息（明日行程介绍及明日天气）
	 * @param response
	 * @param toAccount
     */
	private void sendMessageToJoinInMember(CustomMessageResponse response, String toAccount) {
		List<MsgBody> bodyList = new ArrayList<>();
		MsgContent content = new MsgContent();
		content.setData(JsonUtils.obj2json(response));
		content.setDesc("行程提醒");

		logger.info("团员名字为："+toAccount);

		MsgBody body = new MsgBody();
		body.setMsgType(MsgType.getTIMCustomElem());
		body.setMsgContent(content);

		bodyList.add(body);
		String fromAccount = "letsgo@gsxe";	// 指定发送账号，跟上小二
		// 使用IM 发送明日行程消息
		int random = (int) (Math.random() * 1000000);
		int timestamp = (int) (System.currentTimeMillis() / 1000);
		SendMsgRequest request = new SendMsgRequest(fromAccount, toAccount, random, timestamp, bodyList);
		timRestAPI.sendmsg(request);
		logger.info("random"+random+",timestamp"+timestamp);
		logger.info("消息发送方账号"+fromAccount+",消息接收方账号"+toAccount);
		for (int i =0 ;i<bodyList.size();i++){
			logger.info("消息内容为："+bodyList.get(i));
		}

	}


	public String getDestination(ScheduleDetailEntity scheduleDetailEntity){
		String destination = "";
		String startPlace = scheduleDetailEntity.getStartPlace();
		String destination4 = scheduleDetailEntity.getDestination4();
		String destination3 = scheduleDetailEntity.getDestination3();
		String destination2 = scheduleDetailEntity.getDestination2();
		String destination1 = scheduleDetailEntity.getDestination1();
		if (!StringUtils.isEmpty(startPlace)) {
			destination = startPlace;
		} else if (!StringUtils.isEmpty(destination1)) {
			destination = destination + "-" + destination1;
		} else if (!StringUtils.isEmpty(destination2)) {
			destination = destination + "-" + destination2;
		} else if (!StringUtils.isEmpty(destination3)) {
			destination = destination + "-" + destination3;
		} else if (!StringUtils.isEmpty(destination4)) {
			destination = destination + "-" + destination4;
		}
		return destination;
	}



	//获取系统消息
	private AppMessageResponse getCustomScheduleMsg(MemberEntity memberEntity, ScheduleDetailEntity scheduleDetailEntity) {
		AppMessageResponse response = new AppMessageResponse();
		logger.info("明日行程安排：");
		//内容
		List<CustomMsg> customMsgs = new ArrayList<>();

		CustomMsg msg1 = new CustomMsg();
		msg1.setTitle("行程安排：");
		msg1.setContent(this.getDestination(scheduleDetailEntity));
		customMsgs.add(msg1);

		//如果开始地没有，则开始地数据其实为目的地
		String destination1 = scheduleDetailEntity.getDestination1();
		if (StringUtils.isEmpty(destination1)){
			CustomMsg msg2 = new CustomMsg();
			msg2.setTitle("目的地天气：");
			msg2.setContent(scheduleDetailEntity.getStartPlace()+"，"+scheduleDetailEntity.getStartPlaceWeatherDescn());
			customMsgs.add(msg2);
		}else {
			CustomMsg msg2 = new CustomMsg();
			msg2.setTitle("目的地天气：");
			msg2.setContent(scheduleDetailEntity.getDestination1()+"，"+scheduleDetailEntity.getFirstDayWeatherDescn());
			customMsgs.add(msg2);
		}

		CustomMsg msg3 = new CustomMsg();
		msg3.setTitle("行程概要：");
		msg3.setContent(scheduleDetailEntity.getDesc());
		customMsgs.add(msg3);

		//参数
		List<ParameMsg> parameMsgs = new ArrayList<>();

		ParameMsg parameMsg1 = new ParameMsg();
		parameMsg1.setParameKey("teamId");
		parameMsg1.setParameValue(memberEntity.getTeamId());
		parameMsgs.add(parameMsg1);

		ParameMsg parameMsg2 = new ParameMsg();
		parameMsg2.setParameKey("scheduleDetaildId");
		parameMsg2.setParameValue(scheduleDetailEntity.getId().toString());
		parameMsgs.add(parameMsg2);

		ParameMsg parameMsg3 = new ParameMsg();
		parameMsg3.setParameKey("flag");
		parameMsg3.setParameValue("preview");
		parameMsgs.add(parameMsg3);

		//团队名称
		String name = memberEntity.gettName();
		if (!StringUtils.isEmpty(name)){
			name = ELUtil.substring(name, 15);
		}
		// 设置响应对象
		response.setScheduleName(name);
		response.setTitle("明日行程安排");
		response.setDesc("尊敬的"+memberEntity.getRealName()+"您好，跟上小二提醒您明日("+DateUtils.toString(scheduleDetailEntity.getScheduleDate(), "yyyy年MM月dd日")+")行程");
		response.setPictureUrl("http://letsgoimg-10049120.image.myqcloud.com/static_pic/waiter/tomorrow.png");
		response.setMsg(customMsgs);
		response.setBottom("立即查看");
		//response.setTargetUrl(WxConstant.TOMORROWSCHEDULE+"?flag=preview&teamId="+urlEncodeTeamId+"&scheduleDetaildId="+scheduleDetailEntity.getId());
		response.setTargetUrl(WxConstant.APP_SCHEDULE);
		response.setFlag("preview");
		response.setMsgType(3);
		response.setParameMsg(parameMsgs);

		response.setDate(null!=scheduleDetailEntity.getScheduleDate()? scheduleDetailEntity.getScheduleDate().getTime():null);
		response.setTeamId(memberEntity.getTeamId());
		response.setScheduleDetaildId(scheduleDetailEntity.getId());
		response.setSubject("行程");

		logger.info(response);
		logger.info("封装明日行程安排消息完毕");
		return response;
	}


	@Override
	public TeamInfoResponse getTeamInfo(String teamId, Long userId) {
		AdministratorInfo administratorInfo = getAdministrator(teamId);

		TeamInfoResponse response = new TeamInfoResponse();
		response.setAdministratorInfo(administratorInfo);
		return response;
	}

	/**
	 * 获取团队出行状态及当前用户的身份和管理权，返回一个标识，用于判断当前用户是否有对接口的操作权限
	 * @param teamId
	 * @param userId
	 * @param model 1：领队端独有的功能；2：领队端和游客端都有的功能
     * @return
     */
	@Override
	public int isOperate(String teamId, Long userId, Integer model) {
		int flag = 1;	// 1:有权限；0：没有权限

		if (org.springframework.util.StringUtils.isEmpty(teamId)) {
			flag = 0;
			List<TeamEntity> teamList = this.getTeamEntityList(userId);
			for (TeamEntity teamEntity : teamList) {
				MemberEntity memberEntity = memberService.getMemberWithTeamIdAndUserId(teamEntity.getTxGroupid(), userId);
				if ((teamEntity.getStatus() == 1 || teamEntity.getStatus() == 2) && (memberEntity.getIsAdmin() == 1)) {
					flag = 1;
				}
			}
		} else {
			TeamEntity team = this.getTeamByTXGroupId(teamId);
			if (team.getStatus() == 3) {
				flag = 0;
			} else {
				MemberEntity member = memberService.getMemberWithTeamIdAndUserId(teamId, userId);
				if (member.getIsAdmin() == 1) {
					flag = 1;
				} else if (member.getRole() == 3 && model == 2) {
					flag = 1;
				} else {
					flag = 0;
				}
			}
		}

		return flag;
	}

	@Override
	public Integer teamStatus(String teamId) {
		TeamEntity team = this.getTeamByTXGroupId(teamId);
		if (team == null) return null;
		return team.getStatus();
	}

	@Override
	public List<Integer> roleStatus(String teamId, Long userId) {
		MemberEntity member = memberService.getMemberWithTeamIdAndUserId(teamId, userId);
		List<Integer> list = new ArrayList<>();
		list.add(member.getRole());
		list.add(member.getIsAdmin());
		return list;
	}

	private AdministratorInfo getAdministrator(String teamId) {
		AdministratorInfo info = new AdministratorInfo();
		List<MemberEntity> memberEntityList = memberService.getTourGuideList(teamId, 1);

		// 判断是否有管理权限的导游
		info.setExistGuide(0);
		for (MemberEntity memberEntity : memberEntityList) {
			if (memberEntity.getUserId() != -1) {
				String peer = memberEntity.getUserEntity().getUserName();
				if (memberEntity.getRole() == 1) {
					info.setLeaderPeer(peer);
				} else {
					info.setTourGuidePeer(peer);
					info.setExistGuide(1);
				}
			}
		}

		if (org.springframework.util.StringUtils.isEmpty(info.getLeaderPeer())) {
			info.setExistGuide(2);	// 领队还处于未加入状态
		}

		return info;
	}

	@Override
	public boolean teamNumIsRepeat(String teamNum, Long travelId) {
		ScheduleRequest scheduleRequest = new ScheduleRequest();
		scheduleRequest.setTravelId(travelId);
		scheduleRequest.setTeamNum(teamNum);
		List<TeamEntity> teamList = teamMapper.selectByTeamNumAndTravelId(scheduleRequest);
		return !CollectionUtils.isEmpty(teamList);
	}

	public AnalyticalDataResponse getTeamAnalyticalData(String teamId, Long userId) {
		AnalyticalDataResponse response = new AnalyticalDataResponse();
		Page<AnalyticalData> analyticalDataList = teamMapper.queryAnalyticalDataList(teamId);
		if(CollectionUtils.isEmpty(analyticalDataList)){
			return response;
		}

		AnalyticalData currentUser = new AnalyticalData();
		for(AnalyticalData data : analyticalDataList){
			Integer age = calculateAge(data);
			Integer procode = data.getProCode();
			String proName = data.getProName();
			if(procode==null || StringUtils.isEmpty(proName)){
				data.setIsSubmitNext(1);
			}else{
				data.setIsSubmitNext(0);
			}
			data.setAge(age);

			if (data.getUserId().equals(userId)){
				currentUser = data;
			}
		}
		response.setCurrentUser(currentUser);
		response.setAnalyticalDataList(analyticalDataList);
		return response;
	}
	
	//计算年龄
    private Integer calculateAge(AnalyticalData data){
        Integer age = null;    
    	String birthday = data.getBirthday();
            if(birthday != null){
                try {
                    Date date =DateUtils.sdfDateOnly.parse(birthday);
                    age = DateUtils.getAge(date);
                } catch (ParseException e) {
                    logger.warn("不能解析日期："+birthday,e);
                }
            }
        return age;
    }

	public CommonResponse saveProfession(String teamId,Long userId,String proCode,
			String proName) {
		teamMapper.saveProfession(teamId,userId,proCode,proName);
		return new CommonResponse();
	}

	/**
	 * 获取同团记录
	 * @param firstUserId
	 * @param secondUserId
     * @return
     */
	@Override
	public List<String> getSameTeamByUserId(Long firstUserId, Long secondUserId) {
		TeamRequest request = new TeamRequest();
		request.setFirstUserId(firstUserId);
		request.setSecondUserId(secondUserId);
		List<String> teamNameList = teamMapper.getTeamListBetweenTwoUserId(request);
		if (org.springframework.util.CollectionUtils.isEmpty(teamNameList)) {
			return new ArrayList<>();
		}
		return teamNameList;
	}
	//判断团号是否存在
	public boolean hasTeamNum(String teamNum){
		int i =teamMapper.hasTeamNum(teamNum);
		if(i>0){
			return  true;
		}
		return false;
	}

	/**
	 * 查询用户的角色以及是否发放问卷
	 * @param teamId
	 * @param userId
     * @return
     */
	public TeamRoleResponse getUserRoleAndSurvey(String teamId, Long userId) {
		TeamRoleResponse response = new TeamRoleResponse();
		//根据腾讯组ID获取行程
		ScheduleEntity scheduleEntity=scheduleService.getSchedule(teamId);
		if(scheduleEntity==null){
			return  TeamRoleResponse.createNotFoundResponse("没有找到对应的行程");
		}
		response.setScheduleId(scheduleEntity.getId());
		response.setName(scheduleEntity.getName());
		MemberEntity memberEntity=memberService.getMemberWithTeamIdAndUserId(teamId,userId);
		if(memberEntity==null){
			return  TeamRoleResponse.createNotFoundResponse("没有找到对应的成员");
		}
		//获取团队头像
		TeamEntity teamEntity = this.getTeamByTXGroupId(teamId);
		if(teamEntity==null){
			return  TeamRoleResponse.createNotFoundResponse("没有找到对应的团");
		}
		response.setStatus(teamEntity.getStatus());
		response.setPhotoUrl(teamEntity.getPhotoUrl());
		//获取组团机构
		PromptInfoEntity promptInfoEntity = promptService.getByScheduleId(scheduleEntity.getId());
		if(promptInfoEntity!=null&&promptInfoEntity.getTravelAgencyInfo()!=null){
			response.setGroupClubInfo(promptInfoEntity.getTravelAgencyInfo());
		}else{
			response.setGroupClubInfo("");
		}
		response.setIsAdmin(memberEntity.getIsAdmin());
		response.setRole(memberEntity.getRole());
		// 3、获取行程问卷
		SurveyEntity surveyEntity = surveyService.selectByTeamId(memberEntity.gettId());
		if (surveyEntity == null) {	// 无问卷
			response.setIsIssueSurvey(1);
		} else if(CollectionUtils.isEmpty(surveyEntity.getSurveyDetailEntityList())) {
			response.setIsIssueSurvey(2);	// 未发放问卷
		} else {
			int flag = 4;
			for (SurveyDetailEntity surveyDetailEntity : surveyEntity.getSurveyDetailEntityList()) {
				if (surveyDetailEntity.getConfirmStatus() == 0) {
					flag = 3;	// 如果有未填写的问卷，设置状态值（3），跳出循环
					break;
				}
			}
			response.setIsIssueSurvey(flag);	// 已发放问卷
		}
		//获取团队中的领队和有管理权限的导游
		AdministratorInfo administratorInfo = getAdministrator(teamId);
		response.setAdministratorInfo(administratorInfo);
		//保存二维码信息
		response.setQrURL(ConfigConstant.QR_URL);
		response.setQrType("t");//二维码类型：t：快速入团
		response.setQrParam(teamId);
		//公众号二维码链接
		if(StringUtils.isEmpty(teamEntity.getQrCode())) {
			WxMpQrCodeTicket ticket = null;
			try {
				ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(Integer.parseInt(teamEntity.getId().toString()));
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			if (ticket != null&&!StringUtils.isEmpty(ticket.getUrl())) {
				response.setQrCode(ticket.getUrl());
				teamEntity.setQrCode(ticket.getUrl());
				//保存二维码URL
				this.updateTeam(teamEntity);
			}
		}else{
			response.setQrCode(teamEntity.getQrCode());
		}
		return response;
	}


	@Override
	public TeamPageRespone selectTeamListByCountryN(TeamRequest request) {

		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		Page<TeamEntity> ScheduleEntity = teamMapper.selectTeamListByCountryN(request);

		TeamPageRespone response =new TeamPageRespone();
		response.setPages(ScheduleEntity.getPages());
		response.setTotals(ScheduleEntity.getTotal());
		response.setScheduleList(ScheduleEntity);
		return response;

	}


	public Date getSimpleDate(String simpleDate){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = simpleDateFormat.parse(simpleDate);
		}catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return date;
	}


}