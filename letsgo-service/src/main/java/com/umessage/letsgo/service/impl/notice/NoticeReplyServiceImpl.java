package com.umessage.letsgo.service.impl.notice;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.dao.notice.NoticeReplyDao;
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.po.notice.NoticeReplyEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.notice.request.DetailRequest;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeReplyResponse;
import com.umessage.letsgo.service.api.notice.INoticeReplyService;
import com.umessage.letsgo.service.api.notice.INoticeService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class NoticeReplyServiceImpl implements INoticeReplyService {
	@Resource
	private NoticeReplyDao noticeReplyMapper;
	@Resource
	private INoticeService noticeService;
	@Resource
	private IMemberService memberService;
	@Resource
	private ITeamService teamService;
	@Resource
	private IUserService userService;

	@Override
	public int addNoticeReply(NoticeReplyEntity noticeReplyEntity) {
		noticeReplyEntity.setCreateTime(new Date());
		noticeReplyEntity.setVersion(0l);
		return noticeReplyMapper.insert(noticeReplyEntity);
	}

	/**
	 * 回复通知|集合
	 * @param detailRequest
	 * @return
     */
	@Override
	public CommonResponse replyNotice(DetailRequest detailRequest) {
		// 1、根据用户ID和团队ID，获取成员信息
		MemberEntity member = memberService.getMemberWithTeamIdAndUserId(detailRequest.getTeamId(), detailRequest.getUserId());

		// 2、添加一条回复
		NoticeReplyEntity reply = new NoticeReplyEntity();
		reply.setNoticeId(detailRequest.getNoticeId());
		reply.setMemberId(member.getId());
		reply.setUserId(detailRequest.getUserId());
		reply.setReply(detailRequest.getReply());
		this.addNoticeReply(reply);

		CommonResponse response = new CommonResponse();
		response.setRetCode(ErrorConstant.SUCCESS);
		response.setRetMsg("成功回复信息！");
		return response;
	}

	/**
	 * 获取回复列表
	 * @param noticeId
	 * @return
     */
	@Override
	public NoticeReplyResponse getNoticeRelayList(Long noticeId, UserEntity user) {
		// 1、获取回复列表
		NoticeEntity notice = noticeService.getNotice(noticeId);
		List<NoticeReplyEntity> replyList = notice.getNoticeReplyList();

		// 2、设置姓名及头像
		if (!CollectionUtils.isEmpty(replyList)) {
			for (NoticeReplyEntity reply : replyList) {
				// 比较当前用户是不是发布这条通知的发送人，是设置为“我”
				if (reply.getUserId().equals(user.getId())) {
					reply.setMemberName("我");
					reply.setPhotoUrl(user.getPhotoUrl());
				} else {
					MemberEntity memberEntity = memberService.getMember(reply.getMemberId());
					reply.setMemberName(memberEntity.getRealName());
					reply.setPhotoUrl(memberEntity.getPhotoUrl());
				}
			}
		}

		// 3、设置回复响应实体
		NoticeReplyResponse response = new NoticeReplyResponse();
		response.setNoticeReplyList(replyList);
		response.setIsOperable(teamService.isOperate(notice.getTeamId(), user.getId(), 2));
		return response;
	}

}
