package com.umessage.letsgo.service.impl.journey;


import com.umessage.letsgo.core.utils.ELUtil;
import com.umessage.letsgo.dao.journey.ScheduleDetailCommentDao;
import com.umessage.letsgo.dao.journey.ScoreDao;
import com.umessage.letsgo.domain.po.journey.*;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.request.ParameMsg;
import com.umessage.letsgo.domain.vo.common.respone.AppMessageResponse;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.common.respone.CustomMsg;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDetailCommentRequest;
import com.umessage.letsgo.domain.vo.journey.response.DetailCommentResponse;
import com.umessage.letsgo.service.api.journey.IScheduleDetailCommentService;
import com.umessage.letsgo.service.api.journey.IScheduleDetailsService;
import com.umessage.letsgo.service.api.security.IAppSendMessageService;
import com.umessage.letsgo.service.api.security.IWxSendMessageService;
import com.umessage.letsgo.service.common.constant.WxConstant;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleDetailCommentServiceImpl implements IScheduleDetailCommentService {
	Logger logger = Logger.getLogger(ScheduleDetailCommentServiceImpl.class);

    @Resource
    private ScheduleDetailCommentDao scheduleDetailCommentDao;

    @Resource
    private ScoreDao scoreDao;
	@Resource
	private IAppSendMessageService appSendMessageService;
	@Resource
	private IScheduleDetailsService scheduleDetailsService;
	@Resource
	private IWxSendMessageService wxSendMessageService;

    @Transactional
	@Override
	public CommonResponse submitJournarComment(ScheduleDetailCommentRequest scheduleDetailCommentRequest)throws Exception {
		
 		Date createTime = new Date();
 		ScheduleDetailCommentEntity scheduleDetailCommentEntity = new ScheduleDetailCommentEntity();
		PropertyUtils.copyProperties(scheduleDetailCommentEntity, scheduleDetailCommentRequest);
		scheduleDetailCommentEntity.setCreateTime(createTime);
		scheduleDetailCommentEntity.setVersion(0l);
		scheduleDetailCommentDao.insert(scheduleDetailCommentEntity);
		
		for (ScoreEntity scoreEntity : scheduleDetailCommentRequest.getScoreEntities()) {
			scoreEntity.setCreateTime(createTime);
			scoreEntity.setVersion(0L);
			scoreEntity.setScheduleDetailCommentId(scheduleDetailCommentEntity.getId());
			scoreDao.insert(scoreEntity);
		}
		
		return new CommonResponse();
	}

	@Override
	public DetailCommentResponse selectComment(Long scheduleDetailId, Long userId) {
		ScheduleDetailCommentEntity scheduleDetailCommentEntity = scheduleDetailCommentDao.selectComment(scheduleDetailId,userId);
		if (scheduleDetailCommentEntity == null){
			return DetailCommentResponse.createNotFoundResponse("未找到行程点评");
		}
		DetailCommentResponse detailCommentResponse = new DetailCommentResponse();
		detailCommentResponse.setDetailCommentEntity(scheduleDetailCommentEntity);
		return detailCommentResponse;
	}

	@Override
	public DetailCommentResponse selectCommentDefault(Long scheduleDetailId, Long userId) {
		ScheduleDetailCommentEntity scheduleDetailCommentEntity = scheduleDetailCommentDao.selectComment(scheduleDetailId,userId);
		//如果没有找到对应的每日点评则进行默认好评
		if (scheduleDetailCommentEntity == null && scheduleDetailId != null && userId != null){
			ScheduleDetailCommentEntity scheduleDetailCommentEntityNewSet = new ScheduleDetailCommentEntity();
			scheduleDetailCommentEntityNewSet.setUserId(userId);
			scheduleDetailCommentEntityNewSet.setSatisfiedStatus(5);
			scheduleDetailCommentEntityNewSet.setCreateTime(new Date());
			scheduleDetailCommentEntityNewSet.setVersion(0l);
			scheduleDetailCommentEntityNewSet.setScheduleDetailId(scheduleDetailId);
			scheduleDetailCommentDao.insert(scheduleDetailCommentEntityNewSet);
			scheduleDetailCommentEntity = scheduleDetailCommentEntityNewSet;
		}
		DetailCommentResponse detailCommentResponse = new DetailCommentResponse();
		detailCommentResponse.setDetailCommentEntity(scheduleDetailCommentEntity);
		return detailCommentResponse;
	}

	/**
	 * 定时任务设置默认行程点评
	 * @param scheduleDetailIdUserIdEntity
	 * @return
     */
	@Override
	public boolean setSatisfiedStatusForUserId(ScheduleDetailIdUserIdEntity scheduleDetailIdUserIdEntity) {
		//根据行程详情查询行程点评
		if (scheduleDetailIdUserIdEntity != null){
			this.getScheduleDetailCommentEntity(scheduleDetailIdUserIdEntity);
		}
		return true;
	}

	public ScheduleDetailCommentEntity getComment(Long scheduleDetailId, Long userId){
		return scheduleDetailCommentDao.selectComment(scheduleDetailId,userId);
	}

	public void getScheduleDetailCommentEntity(ScheduleDetailIdUserIdEntity scheduleDetailIdUserIdEntity){
		//根据用户id和行程详情ID查询行程点评
		ScheduleDetailCommentEntity scheduleDetailCommentEntityBack = scheduleDetailCommentDao.selectCommentByUserIdAndScheduleDetailId(scheduleDetailIdUserIdEntity);
		//如果没有对应的点评则设置默认点评
		if (scheduleDetailCommentEntityBack == null){
			ScheduleDetailCommentEntity scheduleDetailCommentEntityNewSet = new ScheduleDetailCommentEntity();
			scheduleDetailCommentEntityNewSet.setUserId(scheduleDetailIdUserIdEntity.getUserId());
			scheduleDetailCommentEntityNewSet.setSatisfiedStatus(5);
			scheduleDetailCommentEntityNewSet.setCreateTime(new Date());
			scheduleDetailCommentEntityNewSet.setVersion(0l);
			scheduleDetailCommentEntityNewSet.setScheduleDetailId(scheduleDetailIdUserIdEntity.getScheduleDetailId());
			scheduleDetailCommentDao.insert(scheduleDetailCommentEntityNewSet);
		}
	}

	/**
	 * 每日点评模板消息
	 * @param memberEntity
	 * @return
     */
	@Override
	public boolean sendEveryDayComment(MemberEntity memberEntity) {
		//发送微信模板消息
		wxSendMessageService.sendCommentNoticeTemplateMessage(memberEntity);
		//发送系统信息
		AppMessageResponse response = getCustomMsg(memberEntity);
		appSendMessageService.sendMessageToJoinInMember(response, memberEntity.getUserEntity().getUserName());
		return true;
	}

	/**
	 * 记录每日点评数据记录
	 * @return
     */
	@Override
	public List<ScoreRecordEntity> selectAllByScheduleDate() {
		//获取昨天时间
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-1);
		Date yesterday = calendar.getTime();
		ScoreRecordEntity scoreRecordEntity = new ScoreRecordEntity();
		scoreRecordEntity.setCreateTime(yesterday);
		return scoreDao.selectAllByScheduleDate(scoreRecordEntity);
	}


	private AppMessageResponse getCustomMsg(MemberEntity memberEntity) {
		AppMessageResponse response = new AppMessageResponse();

		ScheduleDetailEntity scheduleDetailEntity = scheduleDetailsService.getTomorrowScheduleDetail(memberEntity.gettId());
		logger.info("每日点评：");
		List<CustomMsg> customMsgs = new ArrayList<>();
		String name = memberEntity.gettName();
		if (!StringUtils.isEmpty(name)){
			name = ELUtil.substring(name, 15);
		}
		// 设置响应对象
		response.setScheduleName(name);
		response.setTitle("点评提醒");
		response.setDesc("尊敬的"+memberEntity.getRealName()+"您好，美好的一天即将结束，跟上小二邀请您做个评价");
		response.setPictureUrl("http://letsgoimg-10049120.image.myqcloud.com/static_pic/waiter/comment.png");
		response.setMsg(customMsgs);
		response.setBottom("立即点评");
		//String urlEncodeTeamId = ELUtil.getUrlEncodeTeamId(memberEntity.getTeamId());
		response.setTargetUrl(WxConstant.APP_EVERYDAY_COMMENT/*+"?teamId="+urlEncodeTeamId+"&userID="+memberEntity.getUserId()*/);
		response.setFlag("preview");
		response.setMsgType(3);

		ParameMsg parameMsg = new ParameMsg();
		List<ParameMsg> parameMsgList = new ArrayList<>();
		parameMsg.setParameKey("teamId");
		parameMsg.setParameValue(memberEntity.getTeamId());
		parameMsgList.add(parameMsg);
		response.setParameMsg(parameMsgList);

		response.setDate(null!=scheduleDetailEntity.getScheduleDate()? scheduleDetailEntity.getScheduleDate().getTime():null);
		response.setTeamId(memberEntity.getTeamId());
		response.setScheduleDetaildId(scheduleDetailEntity.getId());
		response.setSubject("每日点评");

		logger.info(response);
		logger.info("封装每日点评消息完毕");
		return response;
	}

}
