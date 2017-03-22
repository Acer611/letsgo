package com.umessage.letsgo.service.impl.notice;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.dao.notice.AnnouncementDao;
import com.umessage.letsgo.domain.po.notice.AnnouncementEntity;
import com.umessage.letsgo.domain.po.notice.NoticeDetailEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.notice.request.AnnouRequest;
import com.umessage.letsgo.domain.vo.notice.request.AnnouncementRequest;
import com.umessage.letsgo.domain.vo.notice.respone.AnnouncementResponse;
import com.umessage.letsgo.service.api.notice.IAnnouncementService;
import com.umessage.letsgo.service.api.notice.INoticeDetailService;
import com.umessage.letsgo.service.api.security.IWxSendMessageService;
import com.umessage.letsgo.service.api.system.IPushService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AnnouncementServiceImpl implements IAnnouncementService {




	@Resource
	private AnnouncementDao announcementMapper;
	@Resource
	private IMemberService memberService;
	@Resource
	private ITeamService teamService;
	@Resource
	private IPushService pushService;
	@Resource
	private Mapper dozerBeanMapper;
	@Resource
	private INoticeDetailService noticeDetailService;
	@Resource
	private IWxSendMessageService wxSendMessageService;

	@Override
	public int addAnnouncementEntity(AnnouncementEntity announcementEntity) {
		announcementEntity.setCreateTime(new Date());
		announcementEntity.setVersion(0l);
		return announcementMapper.insert(announcementEntity);
	}

	@Override
	public List<AnnouncementEntity> getAnnouncementList(AnnouRequest request) {
		List<AnnouncementEntity> list = announcementMapper.selectAnnouncementListWithConditions(request);
		if(list == null){
			list = Collections.emptyList();
		}
		return list;
	}

	/**
	 * 新建公告
	 * @param request
	 * @return
	 * @throws Exception
     */
	@Override
	public CommonResponse saveAnnouncement(AnnouncementRequest request) {
		// 1、把请求VO实体，映射到公告实体中
		AnnouncementEntity announcement = dozerBeanMapper.map(request, AnnouncementEntity.class);

		// 2、找出用户的真实姓名，设置“发布人”属性，新增一条公告信息
		MemberEntity member = memberService.getMemberWithTeamIdAndUserId(request.getTeamId(), request.getUserId());
		if (member.getId() == null) {
			return CommonResponse.createNotFoundResponse("该用户没有参加该团队行程，不能发布给该团队的公告！");
		}
		announcement.setSenderName(member.getRealName());
		this.addAnnouncementEntity(announcement);

//		List<MemberEntity> memberEntityList = memberService.getTouristList(request.getTeamId());

		List<MemberEntity> memberEntityList = memberService.getTouristListOne(request.getTeamId(),request.getUserId());

		this.createDetails(announcement, memberEntityList);

		// 3、推送
		this.pushMessageToUser(memberEntityList,announcement,member);

		//发送微信模板消息
		wxSendMessageService.sendAnnouncementTemplateMessage(memberEntityList, announcement, member);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setRetMsg("成功发布一条公告！");
		return commonResponse;
	}

	/**
	 * 添加详情，用于计数
	 * @param announcementEntity
	 * @param memberEntityList
     */
	private void createDetails(AnnouncementEntity announcementEntity, List<MemberEntity> memberEntityList) {
		for (MemberEntity memberEntity : memberEntityList) {
			if (memberEntity.getUserId() != -1) {
				NoticeDetailEntity detailEntity = new NoticeDetailEntity();
				detailEntity.setType(3);
				detailEntity.setNoticeId(announcementEntity.getId());
				detailEntity.setTeamId(announcementEntity.getTeamId());
				detailEntity.setMemberId(memberEntity.getId());
				detailEntity.setGroupId(memberEntity.getGroupId());
				detailEntity.setUserId(memberEntity.getUserId());
				detailEntity.setIsActive(0);
				detailEntity.setActiveStatus(0);

				noticeDetailService.addNoticeDetail(detailEntity);
			}
		}
	}

	/**
	 * 调用小米推送
	 * @param memberEntityList
     */
	private void pushMessageToUser(List<MemberEntity> memberEntityList, AnnouncementEntity announcement, MemberEntity member) {
		//获取团队
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
		String desc = this.getRoleName(member.getRole())+member.getRealName()+"在“"+teamName+"”中发布了一条公告："+announcement.getTitle();
		Map<String, String> param = new HashMap<>();
		param.put("type", "3");
		param.put("message", "");

		try {
			pushService.pushMessage(desc, param, memberEntityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	 * 获取公告列表
	 * @param teamId
	 * @param userId
     * @return
     */
	@Override
	public AnnouncementResponse getAnnouncementList(String teamId, Long userId) {
		List<AnnouncementEntity> announcementList = new ArrayList<AnnouncementEntity>();
		// 1、获取公告列表
		if (!StringUtils.isEmpty(teamId)) {
			// 获取团队内的公告列表
			announcementList = this.getTeamAnnouncementLsit(teamId, userId);
		} else {
			// 获取全局公告列表
			announcementList = this.getAllAnnouncementLsit(userId);
		}

		AnnouncementResponse response = new AnnouncementResponse();
		response.setAnnouncementList(announcementList);

		for (AnnouncementEntity announcementEntity:announcementList) {
			MemberEntity memberEntity = memberService.getMemberWithTeamIdAndUserId(teamId, announcementEntity.getUserId());
			if (memberEntity != null){
				announcementEntity.setSenderId(memberEntity.getId());
			}
		}

		// 2、设置权限字段
		if (!StringUtils.isEmpty(teamId)) {
			List<Integer> roles = teamService.roleStatus(teamId, userId);
			Integer teamStatus = teamService.teamStatus(teamId);
			if (teamStatus == null || roles.get(0) ==null || roles.get(1) == null){
				throw new BusinessException(ErrorConstant.NOT_FOUND, "没有发现团队或成员！");
			}
			response.setRoleStatus(roles.get(0));
			response.setAdminStatus(roles.get(1));
			response.setTeamStatus(teamStatus);
		} else {
			response.setIsOperable(teamService.isOperate(null, userId, 1));
		}

		return response;
	}

	/**
	 * 获取公告未读数量【全局】
	 * @param userId
	 * @param annouId
     * @return
     */
	@Override
	public int getAnnouncementCount(Long userId, Long annouId) {
		List<String> teamIds = memberService.getTeamIdsByUserId(userId);
		if (CollectionUtils.isEmpty(teamIds)) {
			return 0;
		}
		AnnouRequest request = new AnnouRequest();
		request.setTeamIds(teamIds);
		request.setLastId(annouId);
		List<AnnouncementEntity> announcements = this.getAnnouncementList(request);
		return announcements.size();
	}

	/**
	 * 获取最新的公告实体
	 * @param userId
	 * @return
     */
	@Override
	public AnnouncementEntity getLatestAnnouncement(Long userId, String teamId) {
		List<AnnouncementEntity> announcementList = new ArrayList<AnnouncementEntity>();
		// 1、获取公告列表
		if (!StringUtils.isEmpty(teamId)) {
			// 获取团队内的公告列表
			announcementList = this.getTeamAnnouncementLsit(teamId, userId);
		} else {
			// 获取全局公告列表
			announcementList = this.getAllAnnouncementLsit(userId);
		}

		if (CollectionUtils.isEmpty(announcementList)) {
			return new AnnouncementEntity();
		}
		
		return announcementList.get(0);
	}

	/**
	 * 获取团队中的公告列表
	 * @param teamId
	 * @return
     */
	private List<AnnouncementEntity> getTeamAnnouncementLsit(String teamId, Long userId) {
		List<String> teamIds = memberService.getTeamIdsByUserId(userId);
		if (CollectionUtils.isEmpty(teamIds)) {
			return Collections.emptyList();
		}
		if (!teamIds.contains(teamId)) {
			return Collections.emptyList();
		}

		AnnouRequest request = new AnnouRequest();
		request.setTeamId(teamId);
		return this.getAnnouncementList(request);

	}

	/**
	 * 获取全局公告列表
	 * @param userId
	 * @return
     */
	private List<AnnouncementEntity> getAllAnnouncementLsit(Long userId) {
		List<String> teamIds = memberService.getTeamIdsByUserId(userId);
		if (CollectionUtils.isEmpty(teamIds)) {
			return Collections.emptyList();
		}
		AnnouRequest request = new AnnouRequest();
		request.setTeamIds(teamIds);
		return this.getAnnouncementList(request);
	}
	@Override
	public AnnouncementEntity selectAnnouncementByUserIdAndTeamId(Long userId, String teamId){
		AnnouncementRequest request =new AnnouncementRequest();
		request.setTeamId(teamId);
		request.setUserId(userId);
		AnnouncementEntity announcementEntity=announcementMapper.selectAnnouncementByUserIdAndTeamId(request);
	    if(announcementEntity==null){
			return new AnnouncementEntity();
		}
		return announcementEntity;
	}

	@Override
	public AnnouncementEntity selectAnnouncementByUserIdAndTeamIdToTourist(Long userId, String teamId){
		AnnouncementRequest request =new AnnouncementRequest();
		request.setTeamId(teamId);
		request.setUserId(userId);
		AnnouncementEntity announcementEntity=announcementMapper.selectAnnouncementByUserIdAndTeamIdToTourist(request);
		return announcementEntity;
	}
	public AnnouncementEntity getById(Long id){
		return announcementMapper.getById(id);
	}

	@Override
	public AnnouncementEntity selectWxAnnouncementById(Long id) {
		AnnouncementEntity announcementEntity = announcementMapper.selectWxAnnouncementById(id);
		if(null != announcementEntity){
			return announcementEntity;
		}
		return null;
	}
}
