package com.umessage.letsgo.service.impl.notice;

import com.umessage.letsgo.dao.notice.NoticeDetailDao;
import com.umessage.letsgo.domain.po.notice.NoticeDetailEntity;
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.po.notice.NoticeSignEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.GroupEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleUnreadResponse;
import com.umessage.letsgo.domain.vo.notice.request.DetailParamRequest;
import com.umessage.letsgo.domain.vo.notice.request.DetailRequest;
import com.umessage.letsgo.domain.vo.notice.request.NoticeSignRequest;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeDetailResponse;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeSignDetailResponse;
import com.umessage.letsgo.service.api.notice.INoticeDetailService;
import com.umessage.letsgo.service.api.notice.INoticeService;
import com.umessage.letsgo.service.api.team.IGroupService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class NoticeDetailServiceImpl implements INoticeDetailService {

    private Logger logger = LogManager.getLogger(NoticeDetailServiceImpl.class.getName());
	@Resource
	private NoticeDetailDao noticeDetailMapper;
	@Resource
	private IMemberService memberService;
	@Resource
	private INoticeService noticeService;
	@Resource
	private IGroupService groupService;
	@Resource
	private ITeamService teamService;

	@Override
	public int addNoticeDetail(NoticeDetailEntity noticeDetailEntity) {
		noticeDetailEntity.setActiveStatus(0);
		noticeDetailEntity.setIsOption(0);	// 0代表尚未执行点击操作
		noticeDetailEntity.setCreateTime(new Date());
		noticeDetailEntity.setVersion(0l);
		return noticeDetailMapper.insert(noticeDetailEntity);
	}

	@Override
	public int updateNoticeDetail(NoticeDetailEntity noticeDetailEntity) {
		noticeDetailEntity.setUpdateTime(new Date());
		return noticeDetailMapper.update(noticeDetailEntity);
	}

	@Override
	public NoticeDetailEntity getNoticeDetail(Long noticeId, Long userId,Integer type) {
		DetailParamRequest request = new DetailParamRequest();
		request.setNoticeId(noticeId);
		request.setUserId(userId);
		request.setType(type);
		NoticeDetailEntity detailEntity = noticeDetailMapper.selectNoticeDetailWithConditions(request);
		if (detailEntity == null) {
			return new NoticeDetailEntity();
		}
		return detailEntity;
	}

	/**
	 * 根据确认状态获取需要确认的通知明细列表
	 */
	@Override
	public List<NoticeDetailEntity> getNoticeDetailListByConditions(DetailParamRequest request) {
		List<NoticeDetailEntity> list = noticeDetailMapper.selectNoticeDetailListWithConditions(request);
		if(list == null){
			list = Collections.emptyList();
		}
		return list;
	}

	/**
	 * 获取未读数量
	 * @param type
	 * @param userId
     * @return
     */
	@Override
	public int getUnreadCount(int type, Long userId, String teamId) {
		DetailParamRequest request = new DetailParamRequest();
		request.setType(type);
		request.setUserId(userId);
		request.setTeamId(teamId);
		request.setIsActive(0);
		request.setActiveStatus(0);
		return noticeDetailMapper.selectCountWithConditions(request);
	}

	/**
	 * 获取未确认数量
	 * @param type
	 * @param userId
     * @return
     */
	@Override
	public int getUnconfirmedCount(int type, Long userId, String teamId) {
		DetailParamRequest request = new DetailParamRequest();
		request.setType(type);
		request.setUserId(userId);
		request.setTeamId(teamId);
		request.setIsActive(1);
		request.setActiveStatus(0);
		return noticeDetailMapper.selectCountWithConditions(request);
	}

	/**
	 * 确认通知|集合
	 * @param detailRequest
	 * @return
     */
	@Override
	public CommonResponse comfirmNotice(DetailRequest detailRequest) {
		// 1、获取成员
		MemberEntity member = memberService.getMemberWithTeamIdAndUserId(detailRequest.getTeamId(), detailRequest.getUserId());
		if (member == null) {
			return CommonResponse.createNotFoundResponse("找不到对应的成员");
		}

		NoticeDetailEntity detail = this.getNoticeDetail(detailRequest.getNoticeId(), detailRequest.getUserId(),detailRequest.getType());
		if (detail.getId() == null) {
			return CommonResponse.createNotFoundResponse("找不到对应的通知明细");
		}

		if (detail.getIsActive() == 1 && detail.getActiveStatus() == 0) {// 如果需要确认并没有确认的情况
			// 2、更新确认状态及确认时间
			detail.setActiveStatus(1);
			detail.setActiveTime(new Date());
			this.updateNoticeDetail(detail);

			// 3、更新通知表里的已确认人数和未确认人数
			NoticeEntity notice = noticeService.getNotice(detailRequest.getNoticeId());
			if (notice == null) {
				return CommonResponse.createNotFoundResponse("找不到对应的通知");
			}

			this.updateSureCountAndNotsureCount(notice, detail);
		}

		CommonResponse response = new CommonResponse();
		response.setRetMsg("确认收到信息！");
		return response;
	}

	/**
	 * 获取通知|集合确认详情列表(包括未确认列表和已确认列表)
	 * @param noticeId
	 * @return
     */
	@Override
	public NoticeDetailResponse getNoticeDetilList(Long noticeId,Integer type) {
		// 1、获取通知
		NoticeEntity notice = noticeService.getNotice(noticeId);

		// 2、获取未确认详情列表
		List<NoticeDetailEntity> notsureList = this.getNotsureList(noticeId,type);

		// 3、获取已确认详情列表
		List<NoticeDetailEntity> sureList = this.getSureList(noticeId,type);

		// 3、设置响应实体
		NoticeDetailResponse response = new NoticeDetailResponse();
		response.setNotsureCount(notice.getNotsureCount());
		response.setNotsureList(notsureList);
		response.setSureCount(notice.getSureCount());
		response.setSureList(sureList);
		return response;
	}

	@Override
	public CommonResponse updateUnReads(DetailParamRequest request) {
		noticeDetailMapper.updateUnReads(request);
		return new CommonResponse();
	}

	/**
	 * 获取已确认的通知明细列表
	 * @param noticeId
	 * @return
     */
	private List<NoticeDetailEntity> getSureList(Long noticeId,Integer type) {
		DetailParamRequest request = new DetailParamRequest();
		request.setNoticeId(noticeId);
		request.setIsActive(1);
		request.setActiveStatus(1);
		request.setType(type);
		request.setSort_activeTime("desc");

		List<NoticeDetailEntity> sureList = this.getNoticeDetailListByConditions(request);
		if (!CollectionUtils.isEmpty(sureList)) {
			this.setMemberOrGroup(sureList);
		}

		return sureList;
	}

	/**
	 * 获取未确认的通知明细列表
	 * @param noticeId
	 * @return
     */
	private List<NoticeDetailEntity> getNotsureList(Long noticeId,Integer type) {
		DetailParamRequest request = new DetailParamRequest();
		request.setNoticeId(noticeId);
		request.setIsActive(1);
		request.setActiveStatus(0);
		request.setType(type);
		request.setSort_activeTime("desc");

		List<NoticeDetailEntity> notsureList = this.getNoticeDetailListByConditions(request);
		if (!CollectionUtils.isEmpty(notsureList)) {
			this.setMemberOrGroup(notsureList);
		}

		return notsureList;
	}

	/**
	 * 设置成员实体或者小组实体
	 * @param list
     */
	private void setMemberOrGroup(List<NoticeDetailEntity> list) {
		for (NoticeDetailEntity detailEntity : list) {
			if (detailEntity.getGroupId() == -1) {
				MemberEntity memberEntity = memberService.getMember(detailEntity.getMemberId());
				detailEntity.setMemberEntity(memberEntity);
			} else {
				GroupEntity groupEntity = groupService.getGroupMemberList(detailEntity.getGroupId());
				if (groupEntity.getId() != null) {	// 如果未找到小组，则获取原小组的组长
					detailEntity.setGroupEntity(groupEntity);
				} else {
					// 如果小组以被删除，则把小组ID设置为-1
					detailEntity.setGroupId(-1l);
					updateNoticeDetail(detailEntity);

					MemberEntity memberEntity = memberService.getMember(detailEntity.getMemberId());
					detailEntity.setMemberEntity(memberEntity);
				}
			}
		}
	}

	/**
	 * 更新已确认人数和未确认人数
	 * @param notice
	 * @param detail
	 */
	private void updateSureCountAndNotsureCount(NoticeEntity notice, NoticeDetailEntity detail) {
		int sureCount = 0;
		int notsureCount = 0;
		if (detail.getGroupId() == -1) {
			sureCount = notice.getSureCount() + 1;
			notsureCount = notice.getNotsureCount() - 1;
		} else {
			GroupEntity groupEntity = groupService.getGroup(detail.getGroupId());
			sureCount = notice.getSureCount() + groupEntity.getTotalCount();
			notsureCount = notice.getNotsureCount() - groupEntity.getTotalCount();
		}
		notice.setSureCount(sureCount);
		notice.setNotsureCount(notsureCount);
		noticeService.updateNotice(notice);
	}

	/**
	 * 根据条件查询领队未读信息（集合、通知、公告）
	 * 【1：集合；2：通知；3：公告；4：回复】
	 */
	@Override
	public int getNumsByTypeAndUserId(int type, Long userId,String txgroupId) {
		if(StringUtils.isEmpty(txgroupId)){
			return 0;
		}
		DetailParamRequest request = new DetailParamRequest();
		// 根据团队的腾讯群组ID和用户ID，获取团队中的某一个成员
		MemberEntity memberEntity = memberService.getMemberWithTeamIdAndUserId(txgroupId,userId);
		request.setType(type);
		request.setUserId(userId);
		if(memberEntity!=null&&memberEntity.getRole()!=null&&memberEntity.getRole()==3){
			if(type==1 || type==2){//集合通知游客需要确认
				request.setIsActive(1);
			}else{
				request.setIsActive(0);
			}
		}else{
			request.setIsActive(0);
		}
		request.setActiveStatus(0);
		request.setTeamId(txgroupId);
		return noticeDetailMapper.getNumsByTypeAndUserId(request);
	}
	/**
	 * 根据条件查询领队未读信息（集合、通知、公告）
	 * 【1：集合；2：通知；3：公告；4：回复】
	 */
	@Override
	public int getNumsByTypeAndLeaderUserId(int type, Long userId,String txgroupId) {
		DetailParamRequest request = new DetailParamRequest();
		request.setType(type);
		request.setUserId(userId);
		request.setIsActive(0);
		request.setActiveStatus(0);
		request.setTeamId(txgroupId);
		return noticeDetailMapper.getNumsByTypeAndUserId(request);
	}

	@Override
	public int updateNotClick(Long userId, String teamId, Integer teamStatus) {
		DetailParamRequest request = new DetailParamRequest();
		request.setTeamId(teamId);
		request.setUserId(userId);
		request.setTeamStatus(teamStatus);

		return noticeDetailMapper.updateNotClick(request);
	}


	/**
	 * 根据用户ID和行程团队状态统计操作数量
	 * @param userId
	 * @param status
     * @return
     */
	@Override
	public int getNotClickCountByUserAndStatus(Long userId, Integer status, Integer type) {
		DetailParamRequest request = new DetailParamRequest();
		request.setUserId(userId);
		request.setTeamStatus(status);
		request.setType(type);
		return noticeDetailMapper.getNotOptionCountByUserIdAndStatus(request);
	}

	@Override
	public int getTotalCountByUserId(Long userId, Integer type) {
		DetailParamRequest request = new DetailParamRequest();
		request.setUserId(userId);
		request.setType(type);
		return noticeDetailMapper.getTotalCountByUserId(request);
	}

	@Override
	public ScheduleUnreadResponse getScheduleUnread(Long userId) {
		// 获取即将出行的数量。只统计集合、通知、公告
		int paperTravelCount = this.getNotClickCountByUserAndStatus(userId, 2, 3);

		// 获取出行中的数量。只统计集合、通知、公告
		int travelingCount = this.getNotClickCountByUserAndStatus(userId, 1, 3);

		// 获取用户的未读总数量
		int totalCount = this.getTotalCountByUserId(userId, 3);

		ScheduleUnreadResponse response = new ScheduleUnreadResponse();
		// 如果统计数量不为0，则有小红点，值为1
		response.setPaperTravelUnread(paperTravelCount == 0 ? 0 : 1);
		response.setTravelingUnread(travelingCount == 0 ? 0 : 1);
		response.setAllUnread(totalCount);
		return response;
	}


	/**
	 * 根据成员id查询通知或集合
	 * @param memberId
	 * @param type
     * @return
     */
	@Override
	public List<NoticeDetailEntity> getNoticeDetailByMemberId(Long memberId, Integer type) {
		DetailParamRequest request = new DetailParamRequest();
		request.setMemberId(memberId);
		request.setType(type);
		List<NoticeDetailEntity> noticeDetailEntities = noticeDetailMapper.selectNoticeDetailListWithConditions(request);
		if (noticeDetailEntities == null) {
			return new ArrayList<NoticeDetailEntity>();
		}
		return noticeDetailEntities;
	}

	@Override
	public int delete(Long id) {
		return noticeDetailMapper.delete(id);
	}

	@Override
	public Integer getUnReadNum(int type, Long userId, String txGroupId, MemberEntity memberEntity) {
		if(StringUtils.isEmpty(txGroupId)){
			return 0;
		}
		DetailParamRequest request = new DetailParamRequest();
		// 根据团队的腾讯群组ID和用户ID，获取团队中的某一个成员
		request.setType(type);
		request.setUserId(userId);
		if(memberEntity!=null&&memberEntity.getRole()!=null&&memberEntity.getRole()==3){
			if(type==1 || type==2){//集合通知游客需要确认
				request.setIsActive(1);
			}else{
				request.setIsActive(0);
			}
		}else{
			request.setIsActive(0);
		}
		request.setActiveStatus(0);
		request.setTeamId(txGroupId);
		return noticeDetailMapper.getNumsByTypeAndUserId(request);
	}

	/**
	 * 获取通知详情签名信息
	 * @param teamId
	 * @param noticeId
	 * @param roleStatus
     * @return
     */
	@Override
	public NoticeSignDetailResponse getNoticeSignInfo(String teamId, Long noticeId, Integer roleStatus, UserEntity user) {

        logger.info("service层传入的参数为：" + teamId +" ," +noticeId + ","+roleStatus+ ","+ user.getRealName());
		NoticeEntity noticeEntity = noticeDetailMapper.getNoticeSignInfo(noticeId);

        NoticeSignDetailResponse response = new NoticeSignDetailResponse();
        logger.info("组装公共返回的信息..");
        response = this.getNoticeSignResponse(noticeEntity,response,user);

        List<NoticeDetailEntity> noticeDetailEntityList = noticeEntity.getNoticeDetailList();
        logger.info("处理签名图片返回信息..");
        //处理签名图片返回信息
        response = getSign(teamId, roleStatus, user, response, noticeDetailEntityList);
        return response;
	}


	//处理签名图片返回信息
	private NoticeSignDetailResponse getSign(String teamId, Integer roleStatus, UserEntity user, NoticeSignDetailResponse response, List<NoticeDetailEntity> noticeDetailEntityList) {
        if(roleStatus == 3){
            logger.info("组装游客签名图片信息..");
            MemberEntity memberEntity =  memberService.getMemberWithTeamIdAndUserId(teamId,user.getId());
            if(null!=noticeDetailEntityList){
                for (NoticeDetailEntity noticeDetail:noticeDetailEntityList) {
                    if(noticeDetail.getMemberId().longValue() == memberEntity.getId().longValue()){
                        response.setId(noticeDetail.getId());
                        response.setNoticeSignEntityList(noticeDetail.getNoticeSignEntityList());
						response.setSignCount(noticeDetail.getNoticeSignEntityList().size());
                        break;
                    }
                }
            }
		}else{
            logger.info("组装导游领队签名图片信息..");
            if(null!=noticeDetailEntityList){
                List<NoticeSignEntity> noticSignList = new ArrayList<>();
                for (NoticeDetailEntity noticeDetail:noticeDetailEntityList) {
                    noticSignList.addAll(noticeDetail.getNoticeSignEntityList());
                }
                response.setNoticeSignEntityList(noticSignList);
            }
		}
        return response;
    }
	//组装公共返回的信息
    private NoticeSignDetailResponse getNoticeSignResponse(NoticeEntity noticeEntity, NoticeSignDetailResponse response,UserEntity user) {
        if(null!=noticeEntity){
            response.setNoticeId(noticeEntity.getId());
            response.setCreateTime(noticeEntity.getCreateTime());
            response.setUserId(noticeEntity.getUserId());
            response.setContent(noticeEntity.getContent());
            response.setSenderId(noticeEntity.getSenderId());
            response.setTeamId(noticeEntity.getTeamId());
            if (user.getId().equals(noticeEntity.getUserId())) {
                response.setSenderName("我");
            }else{
                response.setSenderName(noticeEntity.getSenderName());
            }
            response.setSignCount(noticeEntity.getSignCount());
        }
        return response;
    }


	/**
	 * 根据明细id获取签名信息
	 * @param id
	 * @return
     */
	@Override
	public List<NoticeSignEntity> getNoticeSignByDetailId(Long id) {
		List<NoticeSignEntity> noticeSignEntityList = noticeDetailMapper.getNoticeSignByDetailId(id);
		return noticeSignEntityList;
	}

	/**
	 * 保存签名图片
	 * @param noticeSignRequest
	 * @return
     */
	@Override
	public CommonResponse saveSignImages(NoticeSignRequest noticeSignRequest,UserEntity user) {
        logger.info("进入保存签名图片的service层......");
		// 保存签名图片到 notice sign 表
        //获取成员信息
        MemberEntity memberEntity = memberService.getMemberWithTeamIdAndUserId(noticeSignRequest.getTeamId(),user.getId());
        logger.info("获取成员信息......");
        //组装要插入的数据信息
        List<NoticeSignEntity> noticeSignEntityList = this.getNoticeSignEntity(memberEntity,noticeSignRequest);
        logger.info("组装要插入的数据信息......");
        noticeDetailMapper.insterBatch(noticeSignEntityList);
        logger.info("批量插入签名图片完成......");
		// 更新 notice 表sign_count字段
        List<String> signUrlList = noticeSignRequest.getSignUrlList();
        if(signUrlList !=null && signUrlList.size()>0){
            NoticeEntity notice = new NoticeEntity();
            notice.setSignCount(signUrlList.size());
            notice.setId(noticeSignRequest.getNoticeId());
            notice.setUpdateTime(new Date());
            noticeService.updateNoticeSignCount(notice);
            logger.info("更新notice表图片签名数量完成......");
        }
		return new CommonResponse();
	}

    //组装要插入的签名的信息
    private List<NoticeSignEntity> getNoticeSignEntity(MemberEntity memberEntity, NoticeSignRequest noticeSignRequest) {
        List<NoticeSignEntity> noticeSignEntityList = new ArrayList<>();
        List<String> signUrlList = noticeSignRequest.getSignUrlList();
        if(null!=signUrlList){
            for (String signUrl:signUrlList) {
                NoticeSignEntity noticeSignEntity = new NoticeSignEntity();
                noticeSignEntity.setMemberId(memberEntity.getId());
                noticeSignEntity.setSignUserName(memberEntity.getRealName());
                noticeSignEntity.setNoticeDetailId(noticeSignRequest.getNoticeDetailId());
                noticeSignEntity.setSignUrl(signUrl);
                noticeSignEntity.setCreateTime(new Date());
                noticeSignEntity.setUpdateTime(new Date());
                noticeSignEntityList.add(noticeSignEntity);
            }
        }
        return noticeSignEntityList;
    }

    //根据类型获取最新的提醒ID
	public Long getNewNoticeId(DetailParamRequest request){
		return noticeDetailMapper.getNewNoticeId(request);
	}
}