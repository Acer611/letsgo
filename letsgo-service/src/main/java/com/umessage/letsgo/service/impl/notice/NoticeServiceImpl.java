package com.umessage.letsgo.service.impl.notice;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.core.utils.ELUtil;
import com.umessage.letsgo.dao.notice.NoticeDao;
import com.umessage.letsgo.domain.po.journey.SurveyDetailEntity;
import com.umessage.letsgo.domain.po.notice.*;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.GroupEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.AnswerRequest;
import com.umessage.letsgo.domain.vo.journey.request.SurveyDetailRequest;
import com.umessage.letsgo.domain.vo.notice.request.DetailParamRequest;
import com.umessage.letsgo.domain.vo.notice.request.NoticeParamRequest;
import com.umessage.letsgo.domain.vo.notice.request.NoticeRequest;
import com.umessage.letsgo.domain.vo.notice.respone.LastMessageResponse;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeListResponse;
import com.umessage.letsgo.domain.vo.notice.respone.QuestionVo;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.MemberRequest;
import com.umessage.letsgo.service.api.journey.IAnswerService;
import com.umessage.letsgo.service.api.journey.ISurveyDetailService;
import com.umessage.letsgo.service.api.notice.IAnnouncementService;
import com.umessage.letsgo.service.api.notice.INoticeDetailService;
import com.umessage.letsgo.service.api.notice.INoticeService;
import com.umessage.letsgo.service.api.notice.IRemindService;
import com.umessage.letsgo.service.api.security.IWxInfoService;
import com.umessage.letsgo.service.api.security.IWxSendMessageService;
import com.umessage.letsgo.service.api.system.IPushService;
import com.umessage.letsgo.service.api.team.IGroupService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import com.weixin.service.ITemplateMessage;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class NoticeServiceImpl implements INoticeService {
	private Logger logger = LogManager.getLogger(NoticeServiceImpl.class.getName());
	@Resource
	private NoticeDao noticeMapper;
	@Resource
	private INoticeDetailService noticeDetailService;
	@Resource
	private IRemindService remindService;
	@Resource
	private IMemberService memberService;
	@Resource
	private ITeamService teamService;
	@Resource
	private IGroupService groupService;
	@Resource
	private IPushService pushService;
	@Resource
	private Mapper dozerBeanMapper;
	@Resource
	private UserLoginHelper oauth2LoginHelper;
	@Resource
	private IAnnouncementService announcementService;
	@Resource
	private ISurveyDetailService surveyDetailService;
	@Resource
	private IAnswerService answerService;
	@Resource
	private ITemplateMessage templateMessageService;
	@Resource
	private IWxInfoService wxInfoService;
	@Resource
	private WxMpService wxMpService;
	@Resource
	private IWxSendMessageService wxSendMessageService;

	@Override
	public int insertNotice(NoticeEntity noticeEntity) {
		noticeEntity.setSureCount(0);
		noticeEntity.setCreateTime(new Date());
		noticeEntity.setVersion(0l);
		return noticeMapper.insert(noticeEntity);
	}

	@Override
	public int updateNotice(NoticeEntity noticeEntity) {
		noticeEntity.setUpdateTime(new Date());
		return noticeMapper.update(noticeEntity);
	}

	@Override
	public NoticeEntity getNotice(Long id) {
		NoticeEntity entity = noticeMapper.select(id);
		if(entity == null){
			entity = new NoticeEntity();
		}
		return entity;
	}

	@Override
	public List<NoticeEntity> getNoticesWithConditions(NoticeParamRequest request) {
		List<NoticeEntity> list = noticeMapper.selectNoticeListWithConditions(request);
		if(list == null){
			list = Collections.emptyList();
		}
		return list;
	}

	/**
	 * 获取未确认通知|集合列表
	 * @param teamIds
	 * @param memberIds
     * @return
     */
	@Override
	public List<NoticeEntity> getUnconfirmNoticeList(List<String> teamIds, List<Long> memberIds, Integer type) {
		NoticeParamRequest request = new NoticeParamRequest();
		request.setType(type);
		request.setTeamIds(teamIds);
		request.setReceiverIds(memberIds);
		request.setIsActive(1);	// 1：需要确认
		request.setActiveStatus(0);	// 0：尚未确认
		List<NoticeEntity> notices = this.getNoticesWithConditions(request);

		return notices;
	}

	/**
	 * 获取未读通知|集合列表
	 * @param teamIds
	 * @param memberIds
	 * @param noticeId
     * @return
     */
	@Override
	public List<NoticeEntity> getUnreadNoticeList(List<String> teamIds, List<Long> memberIds, Long noticeId, Integer type) {
		NoticeParamRequest request = new NoticeParamRequest();
		request.setType(type);
		request.setTeamIds(teamIds);
		request.setReceiverIds(memberIds);
		request.setIsActive(0);	// 0：不需要确认
		request.setLastId(noticeId);
		List<NoticeEntity> notices = this.getNoticesWithConditions(request);

		return notices;
	}

	/**
	 * 获取未读及未确认的通知|集合数量【全局】
	 * @param userId
	 * @param noticeId
	 * @param type
     * @return
     */
	@Override
	public int getNoticeCount(Long userId, Long noticeId, Integer type) {
		List<MemberEntity> memberList = memberService.getMemberListByUserId(userId);
		if (CollectionUtils.isEmpty(memberList)) {
			return 0;
		}

		List<String> teamIds = new ArrayList<String>();
		List<Long> memberIds = new ArrayList<Long>();
		for (MemberEntity member: memberList) {
			teamIds.add(member.getTeamId());
			memberIds.add(member.getId());
		}

		// 获取需要确认而未确认的通知/集合列表
		List<NoticeEntity> unconfirmList = this.getUnconfirmNoticeList(teamIds, memberIds, type);
		// 获取不需要确认而未读的通知/集合列表
		List<NoticeEntity> unreadList = this.getUnreadNoticeList(teamIds, memberIds, noticeId, type);

		return unconfirmList.size() + unreadList.size();
	}

	@Override
	public NoticeEntity getLatestNotice(Long userId, Integer type, String teamId) {
		List<NoticeEntity> noticeEntityList = new ArrayList<>();
		if (StringUtils.isEmpty(teamId)) {
			noticeEntityList = this.getNoticeListByUserId(userId, type);
		} else {
			noticeEntityList = this.getNoticeListByTeamId(teamId, userId, type);
		}

		if (CollectionUtils.isEmpty(noticeEntityList)) {
			return new NoticeEntity();
		}

		return noticeEntityList.get(0);
	}

	/**
	 * 获取当前用户的所有通知列表或者24小时以内的集合列表
	 * @param teamId
	 * @param user
	 * @param type
     * @return
     */
	@Override
 	public NoticeListResponse getNoticeList(String teamId, UserEntity user, Integer type) {
		// 判断teamId是否有值；如果有值，则为团队内通知，如果没有，则为全局通知
		List<NoticeEntity> notices = new ArrayList<NoticeEntity>();
		if (!StringUtils.isEmpty(teamId)) {
			notices = this.getNoticeListByTeamId(teamId, user.getId(), type);
		} else {
			notices = this.getNoticeListByUserId(user.getId(), type);
		}

		if (!CollectionUtils.isEmpty(notices)) {
			for (NoticeEntity noticeEntity : notices) {
				// 获取发送人头像及名称
				if (user.getId().equals(noticeEntity.getUserId())) {
					noticeEntity.setSenderName("我");
					noticeEntity.setIsConfirm(0);
				} else {
					// 判断是否需要显示“点击确认收到”，并设置这个标识
					NoticeDetailEntity detailEntity = noticeDetailService.getNoticeDetail(noticeEntity.getId(), user.getId(), type);
					if (detailEntity.getId() != null && (detailEntity.getIsActive() == 1 && detailEntity.getActiveStatus() == 0)) {
						noticeEntity.setIsConfirm(1);
					} else {
						noticeEntity.setIsConfirm(0);
					}
				}
				// 5、计算总人数和回复条数
				noticeEntity.setTotalCount(noticeEntity.getNotsureCount() + noticeEntity.getSureCount());
				noticeEntity.setReplyCount(noticeEntity.getNoticeReplyList().size());
			}
		}

		NoticeListResponse response = new NoticeListResponse();
		response.setNoticeList(notices);

		// 设置权限
       response = getUserAuth(teamId, user, response);
        return response;
	}

	@Override
	public CommonResponse markReads(String teamId, UserEntity user, Integer type) {
		DetailParamRequest request = new DetailParamRequest(teamId, user.getId(), type);
		if (org.springframework.util.StringUtils.isEmpty(teamId)) {
			request.setTeamId(null);
		}
		request.setIsActive(0);//不需要确认
		request.setActiveStatus(0);//未读的
		return noticeDetailService.updateUnReads(request);
	}

	/**
	 * 获取团队内通知/团队内集合
	 * @param teamId
	 * @param type
	 * @return
	 */
	private List<NoticeEntity> getNoticeListByTeamId(String teamId, Long userId, Integer type) {
		// 1、获取团队成员
		MemberEntity member = memberService.getMemberWithTeamIdAndUserId(teamId, userId);

		// 2、获取团队中通知列表
		NoticeParamRequest request = new NoticeParamRequest();
		request.setType(type);
		request.setTeamId(teamId);
		request.setMemberId(member.getId());
		List<NoticeEntity> notices = this.getNoticesWithConditions(request);
		return notices;
	}
	
	/**
	 * 获取全局通知/全局集合
	 * @param userId
	 * @param type
	 * @return
	 */
	private List<NoticeEntity> getNoticeListByUserId(Long userId, Integer type) {
		// 1、获取当前用户的成员列表
		List<MemberEntity> memberList = memberService.getMemberListByUserId(userId);

		List<String> teamIds = new ArrayList<String>();
		List<Long> memberIds = new ArrayList<Long>();
		for (MemberEntity member: memberList) {
			teamIds.add(member.getTeamId());
			memberIds.add(member.getId());
		}
		
		// 2、获取通知/集合列表
		NoticeParamRequest request = new NoticeParamRequest();
		request.setType(type);
		request.setTeamIds(teamIds);
		request.setMemberIds(memberIds);
		List<NoticeEntity> notices = this.getNoticesWithConditions(request);
		return notices;
	}

	/**
	 * 新建通知
	 * @param noticeRequest
	 * @return
     */
	@Override
	public CommonResponse saveNotice(NoticeRequest noticeRequest) throws Exception {
		// 1、添加一条新通知信息
		long userID = noticeRequest.getUserId();
		NoticeEntity notice = dozerBeanMapper.map(noticeRequest, NoticeEntity.class);
		if (noticeRequest.getTime() == 0) {
			notice.setTime(null);
		}
		//获取时区ID
		if(null != noticeRequest.getTimezoneId()){
			notice.setTimezoneId(noticeRequest.getTimezoneId());
		}

		// 2、设置发布人的成员ID和成员姓名
		MemberEntity sender = memberService.getMemberWithTeamIdAndUserId(noticeRequest.getTeamId(), noticeRequest.getUserId());
		if (sender.getId() == null) {
			return CommonResponse.createNotFoundResponse("发布人不允许为空对象！");
		}
		notice.setSenderId(sender.getId());
		notice.setSenderName(sender.getRealName());

		List<Long> memberIds = noticeRequest.getMemberIds();
		if (CollectionUtils.isEmpty(memberIds)) {
			return CommonResponse.createNotFoundResponse("不允许选择人列表为空，请重新发布！");
		}

		// 3、计算游客人数
		int total = this.sumTotalNumber(memberIds);
		notice.setNotsureCount(total);

		// 4、如果发送人是导游的话，默认给领队发送一条不需要确认的通知/集合信息
		if (sender.getRole() == 2) {
			List<MemberEntity> leaders = memberService.getLeader(noticeRequest.getTeamId());
			for (MemberEntity leader : leaders) {
				memberIds.add(leader.getId());
			}
		}
		//判断userid 是否为空，若为空添加userID
		if(null == notice.getUserId()){
			notice.setUserId(userID);
		}
		this.insertNotice(notice);

		MemberRequest request = new MemberRequest();
		request.setMemberIds(memberIds);
		List<MemberEntity> memberList = memberService.getMemberList(request);

		// 4、创建通知/集合确认信息，即给每一个接受人添加一条对应的确认信息，并向用户推送
		this.createNoticeDetailAndRemind(memberList, notice);


		String messages = "";
		Integer type = noticeRequest.getType();

		//获取发送人
		MemberEntity sendMemberEntity = memberService.getMemberWithTeamIdAndUserId(noticeRequest.getTeamId(), noticeRequest.getUserId());
		//如果没有通知内容则为语音
		String content = notice.getContent();
		if (StringUtils.isEmpty(content) && !StringUtils.isEmpty(notice.getVideoUrl())){
			content = "【语音】";
		}else {
			content = ELUtil.substring(content, 150);
		}
		//获取团队
		String teamName = this.getTeamName(memberList);
		//5、发送微信模板消息
		if (1 == type){
			messages = "“"+teamName+"”的"+this.getRoleName(sendMemberEntity.getRole())+sendMemberEntity.getRealName()+"发来了一条集合消息："+content;
			//发送集合
			wxSendMessageService.sendGatherTemplateMessage(memberList, notice, sendMemberEntity, teamName);
		}else {
            //TODO  根据 notice type 判断发送签字确认单的通知
			messages = "“"+teamName+"”的"+this.getRoleName(sendMemberEntity.getRole())+sendMemberEntity.getRealName()+"发来了一条通知消息："+content;
			//发送通知
			wxSendMessageService.sendNoticeTemplateMessage(memberList, notice, sendMemberEntity, teamName);

		}


		//6、推送
		this.pushMessageToUser(messages, notice.getType(), memberList);

		CommonResponse response = new CommonResponse();
		response.setRetMsg("成功发布通知！");
		return response;
	}



	/**
	 * 获取角色名称
	 * @param role
	 * @return
	 */
	private String getRoleName(Integer role){
		String roleName = "";
		if (1 == role){
			roleName = "领队";
		}else if (2 == role){
			roleName = "导游";
		}else if (3 == role){
			roleName = "游客";
		}
		return roleName;
	}

	/**
	 * 获取团队名称
	 * @param memberEntityList
	 * @return
     */
	private String getTeamName(List<MemberEntity> memberEntityList){
		String teamName = "";
		if (memberEntityList.size() > 0){
			MemberEntity memberEntity1 = memberEntityList.get(0);
			Long tLong = memberEntity1.gettId();
			TeamEntity teamEntity = teamService.selectById(tLong);
			if (teamEntity != null){
				String name = teamEntity.getName();
				teamName = name;
			}
		}
		if (!StringUtils.isEmpty(teamName)){
			teamName = ELUtil.substring(teamName, 15);
		}
		return teamName;
	}



	/**
	 * 再次提醒
	 * @param noticeId
	 * @return
	 */
	@Override
	public CommonResponse remindNoctice(Long noticeId, List<Long> memberIds) {
		// 1、获取需要提醒的通知|集合
		NoticeEntity noticeEntity = noticeMapper.select(noticeId);

		// 2、获取推送对象
		MemberRequest request = new MemberRequest();
		request.setMemberIds(memberIds);
		List<MemberEntity> memberList = memberService.getMemberList(request);

		// 3、推送
		this.pushMessageToUser("再次提醒：您有一条重要消息，请尽快查看", noticeEntity.getType(), memberList);

		CommonResponse response = new CommonResponse();
		response.setRetMsg("成功发送提醒！");
		return response;
	}

	/**
	 * 创建通知明细并添加提醒
	 * @param memberList
	 * @param notice
	 */
	private void createNoticeDetailAndRemind(List<MemberEntity> memberList, NoticeEntity notice) {
		// 1、创建通知确认明细
		for (MemberEntity member : memberList) {
			NoticeDetailEntity detail = new NoticeDetailEntity();
			detail.setUserId(member.getUserId());
			detail.setTeamId(member.getTeamId());
			detail.setNoticeId(notice.getId());
			detail.setMemberId(member.getId());
			detail.setGroupId(member.getGroupId());
			detail.setType(notice.getType());	// 消息分类

			if (member.getRole() == 1) {	// 如果是领队，设置为不需要确认，状态为未读
				detail.setIsActive(0);
				detail.setActiveStatus(0);
			} else {	// 如果是游客，设置为需要确认，状态为未确认
				detail.setIsActive(1);
				detail.setActiveStatus(0);
			}
			noticeDetailService.addNoticeDetail(detail);

			// 2、如果是游客且设置了提醒，添加提醒
			if (member.getRole() == 3 && (notice.getFirstRemind() != null
					&& notice.getFirstRemind() != -1 && notice.getFirstRemind() != 0)) {
				this.addRemind(notice, member.getId());
			}
		}
	}

	/**
	 * 调用小米推送，推送集合|通知消息
	 * @param memberList
     */
	private void pushMessageToUser(String desc, Integer type, List<MemberEntity> memberList) {
		Map<String, String> param = new HashMap<>();
		param.put("type", String.valueOf(type));
		param.put("message", "");
		try {
			pushService.pushMessage(desc, param, memberList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加提醒
	 * @param notice
	 * @param memberId
     */
	private void addRemind(NoticeEntity notice, Long memberId) {
		RemindEntity remind = new RemindEntity();
		remind.setNoticeId(notice.getId());
		remind.setMemberId(memberId);
		remind.setAppointedTime(notice.getTime());
		remind.setFirstRemind(notice.getFirstRemind());
		remindService.addRemind(remind);
	}

	/**
	 * 计算接受通知的游客人数
	 * @param memberIds
     * @return
     */
	private int sumTotalNumber(List<Long> memberIds) {
		int totalNum = 0;
		MemberRequest request = new MemberRequest();
		request.setMemberIds(memberIds);
		List<MemberEntity> memberList = memberService.getMemberList(request);

		for (MemberEntity member : memberList) {
			if (member.getGroupId() != -1) {
				GroupEntity group = groupService.getGroup(member.getGroupId());

				totalNum = totalNum + group.getTotalCount();
			} else {
				totalNum = totalNum + 1;
			}
		}
		return totalNum;
	}



	//获取最新消息领队端 、游客端(只做了领队端，游客端需要加上最新的问卷调查消息，查出来比较谁最新展示)
	public LastMessageResponse getLastMessage(NoticeRequest request){
		  Date time1=null;
		  Date time2=null;
		  Date time3=null;
		  LastMessageVo lastMessageVo1 =new LastMessageVo();
		  LastMessageVo lastMessageVo2 =new LastMessageVo();
		  LastMessageVo lastMessageVo3 =new LastMessageVo();
		LastMessageResponse response =new LastMessageResponse();
		List<LastMessageVo> lastMessageVo =new ArrayList<LastMessageVo>();
		//获取最新调查问卷
		SurveyDetailRequest req =new SurveyDetailRequest();
		req.setTxgroupId(request.getTeamId());
		req.setUserId(request.getUserId());
		 //确认状态1:是，0:否
		req.setConfirmStatus(0);
		SurveyDetailEntity surveyDetailEntity =surveyDetailService.getByUseIdAndTxgroupId(req);
		if(surveyDetailEntity!=null && surveyDetailEntity.getId() !=null){
			//继续判断是否已经填写了答案
			AnswerRequest re =new AnswerRequest();
			re.setSurveyId(surveyDetailEntity.getSurveyId());
			re.setUserId(request.getUserId());
			boolean hasAnswer=answerService.hasAnswer(re);
			if(!hasAnswer) {
				QuestionVo questionVo = new QuestionVo();
				questionVo.setFirst(surveyDetailEntity.getFirst());
				//调查问卷ID
				questionVo.setProblem(surveyDetailEntity.getSurveyId());
				UserResponse user = oauth2LoginHelper.getLoginUser();
				if (user.getRetCode() != ErrorConstant.SUCCESS) {
					return LastMessageResponse.createUserNotLoginResponse();
				}
				questionVo.setName(user.getUserEntity().getRealName());
				questionVo.setSex(user.getUserEntity().getSex());
				response.setHasQuestion(1);
				response.setQuestionVo(questionVo);
			}else{
				response.setHasQuestion(0);
			}
		}else{
			response.setHasQuestion(0);
		}
		MemberEntity memberEntity=memberService.getMemberRole(request.getTeamId(),request.getUserId());
		if (null == memberEntity) {
			return LastMessageResponse.createNotFoundResponse();
		}
		NoticeEntity noticeM=new NoticeEntity();
		NoticeEntity noticeT=new NoticeEntity();
		AnnouncementEntity announcementEntity=new AnnouncementEntity();
		//判断是游客还是领队

		//导游
		if(memberEntity.getRole()!=null&&memberEntity.getRole()==2) {
			//获取最新集合1
			request.setType(1);
			 noticeM = noticeMapper.getLastMessage(request);
			//集合判断是否过期24
			if(noticeM!=null&&DateUtils.compareTime2(DateUtils.addDay(noticeM.getTime(),1), new Date())<0){
				noticeM=null;
			}
			//获取最新通知2
			request.setType(2);
			 noticeT = noticeMapper.getLastMessage(request);
			//获取最新公告
			 announcementEntity = announcementService.selectAnnouncementByUserIdAndTeamId(request.getUserId(), request.getTeamId());
		}
		//游客
		if(memberEntity.getRole()!=null&&memberEntity.getRole()==3) {
			//获取最新集合1
			request.setType(1);
			noticeM = noticeMapper.getLastMessageToTourist(request);
			//集合判断是否过期24
			if(noticeM!=null&&DateUtils.compareTime2(DateUtils.addDay(noticeM.getTime(),1), new Date())<0){
				noticeM=null;
			}
			//获取最新通知2
			request.setType(2);
			noticeT = noticeMapper.getLastMessageToTourist(request);
			//获取最新公告
			announcementEntity = announcementService.selectAnnouncementByUserIdAndTeamIdToTourist(request.getUserId(), request.getTeamId());
		}
		//领队
		if(memberEntity.getRole()!=null&&memberEntity.getRole()==1) {
			//获取最新集合1
			request.setType(1);
			NoticeEntity noticeM1 = noticeMapper.getLastMessage(request);
			//集合判断是否过期24
			if(noticeM1!=null&&DateUtils.compareTime2(DateUtils.addDay(noticeM1.getTime(),1), new Date())<0){
				noticeM1=null;
			}
			//获取最新通知2
			request.setType(2);
			NoticeEntity noticeT1 = noticeMapper.getLastMessage(request);
			//获取最新公告
			AnnouncementEntity announcementEntity1 = announcementService.selectAnnouncementByUserIdAndTeamId(request.getUserId(), request.getTeamId());

			//获取最新集合1
			request.setType(1);
			NoticeEntity noticeM2 = noticeMapper.getLastMessageToTourist(request);
			//集合判断是否过期24
			if(noticeM2!=null&&DateUtils.compareTime2(DateUtils.addDay(noticeM2.getTime(),1), new Date())<0){
				noticeM2=null;
			}
			//获取最新通知2
			request.setType(2);
			NoticeEntity noticeT2 = noticeMapper.getLastMessageToTourist(request);
			//获取最新公告
			AnnouncementEntity announcementEntity2 = announcementService.selectAnnouncementByUserIdAndTeamIdToTourist(request.getUserId(), request.getTeamId());

			if(noticeM1==null){
				noticeM=noticeM2;
			}
			if(noticeT1==null){
				noticeT=noticeT2;
			}
			if(announcementEntity1==null){
				announcementEntity=announcementEntity2;
			}

			if(noticeM2==null){
				noticeM=noticeM1;
			}
			if(noticeT2==null){
				noticeT=noticeT1;
			}
			if(announcementEntity2==null){
				announcementEntity=announcementEntity1;
			}
			if(noticeM1!=null&&noticeM2!=null){
				if(DateUtils.compareTime(noticeM1.getCreateTime(), noticeM2.getCreateTime(),Calendar.SECOND)>=0){
					noticeM=noticeM1;
				}else{
					noticeM=noticeM2;
				}
			}
			if(noticeT2!=null&&noticeT1!=null){
				if(DateUtils.compareTime(noticeT1.getCreateTime(), noticeT2.getCreateTime(),Calendar.SECOND)>=0){
					noticeT=noticeT1;
				}else{
					noticeT=noticeT2;
				}
			}
			if(announcementEntity2 != null && announcementEntity2.getCreateTime()!=null&&announcementEntity1.getCreateTime()!=null){
				if(DateUtils.compareTime(announcementEntity1.getCreateTime(), announcementEntity2.getCreateTime(),Calendar.SECOND)>=0){
					announcementEntity=announcementEntity1;
				}else{
					announcementEntity=announcementEntity2;
				}
			}
		}
		int isShow1=0;
		int isShow2=0;
		int isShow3=0;
		if (announcementEntity!=null && announcementEntity.getId()!=null){
			time3=announcementEntity.getCreateTime();
			lastMessageVo3.setTitle(announcementEntity.getTitle());
			lastMessageVo3.setId(announcementEntity.getId());
			lastMessageVo3.setIsShow(0);
			lastMessageVo3.setContent(announcementEntity.getContent());
			lastMessageVo3.setMessageType(3);
			lastMessageVo3.setTeamId(announcementEntity.getTeamId());
			//没被查看
			if(announcementEntity.getActiveStatus()!=null&&announcementEntity.getActiveStatus()==0){
				 isShow3=1;
			}else{
				 isShow3=0;
			}
		}
		if (noticeM!=null ){
			time1=noticeM.getCreateTime();
			lastMessageVo1.setId(noticeM.getId());
			lastMessageVo1.setIsShow(0);
			lastMessageVo1.setContent(noticeM.getContent());
			lastMessageVo1.setLocation(noticeM.getLocation());
			lastMessageVo1.setMessageType(noticeM.getType());
			lastMessageVo1.setTeamId(noticeM.getTeamId());
			lastMessageVo1.setVideoUrl(noticeM.getVideoUrl());
			lastMessageVo1.setVideoLen(noticeM.getVideoLen());
			lastMessageVo1.setTime(noticeM.getTime());
			lastMessageVo1.setTimezone(noticeM.getTimezone());
			lastMessageVo1.setTimezoneId(noticeM.getTimezoneId());
			//没被查看
			if(noticeM.getActiveStatus()!=null&&noticeM.getActiveStatus()==0){
				isShow1=1;
			}else{
				isShow1=0;
			}
		}
		if (noticeT!=null){
			time2=noticeT.getCreateTime();
			lastMessageVo2.setId(noticeT.getId());
			lastMessageVo2.setIsShow(0);
			lastMessageVo2.setContent(noticeT.getContent());
			lastMessageVo2.setLocation(noticeT.getLocation());
			lastMessageVo2.setMessageType(noticeT.getType());
			lastMessageVo2.setTeamId(noticeT.getTeamId());
			lastMessageVo2.setVideoUrl(noticeT.getVideoUrl());
			lastMessageVo2.setVideoLen(noticeT.getVideoLen());
			lastMessageVo2.setTime(noticeT.getTime());
			lastMessageVo2.setTimezone(noticeT.getTimezone());
			//没被查看
			if(noticeT.getActiveStatus()!=null&&noticeT.getActiveStatus()==0){
				isShow2=1;
			}else{
				isShow2=0;
			}
		}
		//都不为空
		if(time2!=null && time1!=null && time3!=null){
         if(DateUtils.compareTime(time2, time1,Calendar.SECOND)>=0){
			 if(DateUtils.compareTime(time2, time3,Calendar.SECOND)>=0){
				 //time2最大
				 lastMessageVo2.setIsShow(isShow2);
				 lastMessageVo.add(lastMessageVo1);
				 lastMessageVo.add(lastMessageVo2);
				 lastMessageVo.add(lastMessageVo3);
			 }else{
				 //time3最大
				 lastMessageVo3.setIsShow(isShow3);
				 lastMessageVo.add(lastMessageVo1);
				 lastMessageVo.add(lastMessageVo2);
				 lastMessageVo.add(lastMessageVo3);
			 }
		  }else{
			 if(DateUtils.compareTime(time1, time3,Calendar.SECOND)>=0){
				 //time1最大
				 lastMessageVo1.setIsShow(isShow1);
				 lastMessageVo.add(lastMessageVo1);
				 lastMessageVo.add(lastMessageVo2);
				 lastMessageVo.add(lastMessageVo3);
			 }else{
				 //time3最大
				 lastMessageVo3.setIsShow(isShow3);
				 lastMessageVo.add(lastMessageVo1);
				 lastMessageVo.add(lastMessageVo2);
				 lastMessageVo.add(lastMessageVo3);
			 }
		  }
		}
		if(time2!=null && time1==null && time3!=null){
			if(DateUtils.compareTime(time2, time3,Calendar.SECOND)>=0){
				//time2最大
				lastMessageVo2.setIsShow(isShow2);
				lastMessageVo.add(lastMessageVo2);
				lastMessageVo.add(lastMessageVo3);
			}else{
				//time3最大
				lastMessageVo3.setIsShow(isShow3);
				lastMessageVo.add(lastMessageVo2);
				lastMessageVo.add(lastMessageVo3);
			}

		}
		if(time2!=null && time1!=null && time3==null){
			if(DateUtils.compareTime(time2,time1,Calendar.SECOND)>=0){
				//time2最大
				lastMessageVo2.setIsShow(isShow2);
				lastMessageVo.add(lastMessageVo1);
				lastMessageVo.add(lastMessageVo2);
			}else{
				//time1最大
				lastMessageVo1.setIsShow(isShow1);
				lastMessageVo.add(lastMessageVo1);
				lastMessageVo.add(lastMessageVo2);
			}
		}
		if(time2==null && time1!=null && time3!=null){
			if(DateUtils.compareTime(time3,time1,Calendar.SECOND)>=0){
				//time3最大
				lastMessageVo3.setIsShow(isShow3);
				lastMessageVo.add(lastMessageVo1);
				lastMessageVo.add(lastMessageVo3);

			}else{
				//time1最大
				lastMessageVo1.setIsShow(isShow1);
				lastMessageVo.add(lastMessageVo1);
				lastMessageVo.add(lastMessageVo3);
			}
		}
		if(time2==null && time1==null && time3!=null){
			//time3最大;
			lastMessageVo3.setIsShow(isShow3);
			lastMessageVo.add(lastMessageVo3);

		}
		if(time2==null && time1!=null && time3==null){
          //time1最大
			lastMessageVo1.setIsShow(isShow1);
			lastMessageVo.add(lastMessageVo1);
		}
		if(time2!=null && time1==null && time3==null){
		 //time2最大
			lastMessageVo2.setIsShow(isShow2);
			lastMessageVo.add(lastMessageVo2);
		}
		response.setLastMessageVo(lastMessageVo);
		//消息分类【1：集合；2：通知；3：公告；4：回复】
		response.setAnnouncementUnread(noticeDetailService.getNumsByTypeAndUserId(3,request.getUserId(),request.getTeamId()));
		response.setGatherUnread(noticeDetailService.getNumsByTypeAndUserId(1,request.getUserId(),request.getTeamId()));
		response.setNoticeUnread(noticeDetailService.getNumsByTypeAndUserId(2,request.getUserId(),request.getTeamId()));
		return response;
	}

	@Override
	public NoticeEntity getWxGatherById(Long id,Long userID) {
		NoticeEntity req = new NoticeEntity();
		req.setId(id);
		req.setUserId(userID);
		NoticeEntity noticeEntity = noticeMapper.getWxGatherById(req);
		if(null != noticeEntity){
			return noticeEntity;
		}
		return null;
	}

	@Override
	public NoticeEntity getWxGatherByIdOne(Long id) {
		NoticeEntity noticeEntity = noticeMapper.getWxGatherByIdOne(id);
		if(null != noticeEntity){
			return noticeEntity;
		}
		return null;
	}

	@Override
	public NoticeListResponse getNoticeMessages(String teamId, UserEntity userEntity, int type) {
		NoticeListResponse response = new NoticeListResponse();
		//获取通知列表信息
		if (StringUtils.isEmpty(teamId)) {
			response.setRetCode(-1);
			response.setRetMsg("当前传入的teamId为空");
			return response;
		}

		List<NoticeEntity> notices = new ArrayList<NoticeEntity>();
		logger.info("获取通知的列表信息...");
		//获取通知信新
		NoticeParamRequest request = new NoticeParamRequest();
		request.setType(type);
		request.setTeamId(teamId);
		notices = noticeMapper.getNoticeMessages(request);

		logger.info("获取当前通知所属团的信息...");
		//获取团信息
		TeamEntity teamEntity = teamService.getTeamByTXGroupId(teamId);

		logger.info("组装返回的数据信息...");
		//组装返回的数据信息
		notices = this.geNoticesInfo(notices,teamEntity,userEntity);
		response.setNoticeList(notices);

		logger.info("设置权限...");

		// 设置权限
        response = getUserAuth(teamId, userEntity, response);
        return response;
	}

	@Override
	public void updateNoticeSignCount(NoticeEntity notice) {
		noticeMapper.updateNoticeSignCount(notice);
	}

	/**
     *
     * @param teamId
     * @param userEntity
     * @param response
     */
    private NoticeListResponse getUserAuth(String teamId, UserEntity userEntity, NoticeListResponse response) {
        if (!StringUtils.isEmpty(teamId)) {
            List<Integer> roles = teamService.roleStatus(teamId, userEntity.getId());
            Integer teamStatus = teamService.teamStatus(teamId);
            if (teamStatus == null || roles.get(0) ==null || roles.get(1) == null){
                throw new BusinessException(ErrorConstant.NOT_FOUND, "没有发现团队或成员！");
            }
            response.setRoleStatus(roles.get(0));
            response.setAdminStatus(roles.get(1));
            response.setTeamStatus(teamStatus);
        } else {
            response.setIsOperable(teamService.isOperate(null, userEntity.getId(), 1));
        }
        return response;
    }

    /**
	 * 组装通知的数据信息
	 * @param notices
	 * @param teamEntity
	 * @param userEntity
     * @return
     */
	private List<NoticeEntity> geNoticesInfo(List<NoticeEntity> notices, TeamEntity teamEntity, UserEntity userEntity) {
		if (!CollectionUtils.isEmpty(notices)) {
			for (NoticeEntity notice:notices) {

				logger.info("获取发送人头像及名称...");
				// 获取发送人头像及名称
				if (userEntity.getId().equals(notice.getUserId())) {
					notice.setSenderName("我");
					notice.setIsConfirm(0);
				} else {
					List<NoticeDetailEntity> noticeDetailList =  notice.getNoticeDetailList();
					for (NoticeDetailEntity noticeDetail:noticeDetailList) {
						if(noticeDetail.getUserId().longValue() == userEntity.getId().longValue()){
							// 获取签名图片信息
							List<NoticeSignEntity> noticeSignEntityList = noticeDetailService.getNoticeSignByDetailId(noticeDetail.getId());
							if(noticeSignEntityList!=null&&noticeSignEntityList.size()>0){
								notice.setSignCount(noticeSignEntityList.size());
							}else{
								notice.setSignCount(0);
							}
                            if(noticeDetail.getIsActive() == 1 && noticeDetail.getActiveStatus() == 0){
                                notice.setIsConfirm(1);
                            }else{
                                notice.setIsConfirm(0);
                            }
                            break;
						}
					}
				}
				logger.info("计算总人数和回复条数...");
				// 计算总人数和回复条数
				notice.setTotalCount((null==notice.getNotsureCount()?0:notice.getNotsureCount()) + (null==notice.getSureCount()?0:notice.getSureCount()));
				notice.setReplyCount(notice.getNoticeReplyList().size());
			}
		}

		return notices;
	}
}
