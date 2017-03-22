package com.umessage.letsgo.service.impl.notice;

import com.umessage.letsgo.dao.notice.RemindDao;
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.po.notice.RemindEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.service.api.notice.INoticeService;
import com.umessage.letsgo.service.api.notice.IRemindService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.security.IWxInfoService;
import com.umessage.letsgo.service.api.security.IWxSendMessageService;
import com.umessage.letsgo.service.api.system.IPushService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.weixin.service.ITemplateMessage;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RemindServiceImpl implements IRemindService {
	@Resource
	private RemindDao remindMapper;
	@Resource
	private INoticeService noticeService;
	@Resource
	private IMemberService memberService;
	@Resource
	private IPushService pushService;
	@Resource
	private ITeamService teamService;
	@Resource
	private IUserService userService;
	@Resource
	private ITemplateMessage templateMessageService;
	@Resource
	private IWxInfoService wxInfoService;
	@Resource
	private WxMpService wxMpService;
	@Resource
	private IWxSendMessageService wxSendMessageService;

	Logger logger = Logger.getLogger(RemindServiceImpl.class);
	
	@Override
	public int addRemind(RemindEntity remindEntity) {
		remindEntity.setCreateTime(new Date());
		remindEntity.setVersion(0l);
		return remindMapper.insert(remindEntity);
	}

	@Override
	public int updateRemind(RemindEntity remindEntity) {
		remindEntity.setUpdateTime(new Date());
		return remindMapper.update(remindEntity);
	}

	@Override
	public RemindEntity getRemindEntity(Long id) {
		RemindEntity entity = remindMapper.select(id);
		if(entity == null){
			entity = new RemindEntity();
		}
		return entity;
	}

	@Override
	public List<RemindEntity> getRemindList() {
		List<RemindEntity> remindEntities = remindMapper.selectAll();
		if (CollectionUtils.isEmpty(remindEntities)) {
			return Collections.emptyList();
		}
		return remindEntities;
	}

	@Override
	public boolean remindNoticeToMember(RemindEntity remindEntity) {
		// 判断是否到提醒时间
		long remindTime = remindEntity.getAppointedTime().getTime() - (remindEntity.getFirstRemind() * 60 * 1000); // 提醒时间
		long currentTime = System.currentTimeMillis();
		long appointedTime = remindEntity.getAppointedTime().getTime();

		logger.info("当前系统时间：" + currentTime);
		logger.info("提醒时间：" + remindTime);
		logger.info("事件|集合时间：" + appointedTime);

		if ( currentTime < remindTime || currentTime > appointedTime ) {
			return false;
		}

		// 1、获取需要提醒的通知|集合
		NoticeEntity noticeEntity = noticeService.getNotice(remindEntity.getNoticeId());

		// 2、获取推送对象
		MemberEntity memberEntity = memberService.getMember(remindEntity.getMemberId());
		List<MemberEntity> memberList = new ArrayList<>();
		memberList.add(memberEntity);

		// 3、推送
		this.pushMessageToUser(noticeEntity, memberList);

		//4、发送微信模板消息
		wxSendMessageService.sendGatherRemindTemplateMessage(memberEntity,noticeEntity);

		// 更新一下提醒，表示该条提醒已经执行过，下次不再获取这条提醒
		this.updateRemind(remindEntity);
		return true;
	}

	/**
	 * 调用小米推送，推送集合|通知消息
	 * @param notice
	 * @param memberList
	 */
	private void pushMessageToUser(NoticeEntity notice, List<MemberEntity> memberList) {
		Map<String, String> param = new HashMap<>();
		String desc = "";
		if (notice.getType() == 1) {
			desc = "集合提醒：" + notice.getFirstRemind() + "分钟后到达集合时间，一定要按时到哟";
		} else {
			desc = "事件提醒：" + notice.getFirstRemind() + "分钟后到达通知事件预定时间，一定别忘喽";
		}
		param.put("type", "6");
		param.put("message", desc);

		try {
			Thread.sleep(50);	// 睡眠50ms

			pushService.pushMessageByMyRingtone(desc, param, memberList);	// 推送给游客，自带铃声
			logger.info("成功推送提醒！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * 获取角色名称
	 * @param role
	 * @return
	 */
	public String getRoleName(Integer role){
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
}
