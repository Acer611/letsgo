package com.umessage.letsgo.service.impl.team;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.dao.team.EventRecordDao;
import com.umessage.letsgo.dao.team.LeaderDao;
import com.umessage.letsgo.domain.po.system.LogManage;
import com.umessage.letsgo.domain.po.team.EventRecordEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.team.requset.EventRecordRequest;
import com.umessage.letsgo.domain.vo.team.respone.EventRecordPageResponse;
import com.umessage.letsgo.domain.vo.team.respone.EventRecordResponse;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.ICloudFileOperateService;
import com.umessage.letsgo.service.api.system.ILogManageService;
import com.umessage.letsgo.service.api.team.IEventRecordService;
import com.umessage.letsgo.service.common.constant.HelpConstant;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class EventRecordServiceImpl implements IEventRecordService {

	private static final Logger logger = Logger.getLogger(EventRecordServiceImpl.class);
	@Resource
	private EventRecordDao eventRecordDao;
	@Resource
	private ICloudFileOperateService cloudFileOperateService;
	@Resource
	private UserLoginHelper oauth2LoginHelper;
	@Resource
	private LeaderDao leaderDao;
	@Resource
	private ILogManageService logManageService;
	@Resource
	private IUserService userService;
	@Resource
	private IScheduleService scheduleService;

	@Override
	public int add(EventRecordEntity eventRecordEntity) {
		Date date = new Date();
		eventRecordEntity.setCreateTime(date);
		eventRecordEntity.setUpdateTime(date);
		eventRecordEntity.setVersion(0L);
		int i=eventRecordDao.insert(eventRecordEntity);
		//添加日志
		LogManage log=new LogManage();
		log.setAccountType(HelpConstant.LOGZHLX_LD);
		log.setOperateModel(HelpConstant.LOGCZMK_TDXC);
		log.setOperateTime(date);
		log.setOperateType("新增事件");
		String name=userService.getUserById(eventRecordEntity.getCreateBy()).getRealName();
		log.setName(name);
		log.setUserId(eventRecordEntity.getCreateBy());
		StringBuffer str = new StringBuffer("");
		str.append(name).append("在团:");
		str.append(scheduleService.getSchedule(eventRecordEntity.getTeamId()).getName()).append(",创建了一条事件记录{");
		str.append("语音地址:").append(eventRecordEntity.getVoiceUrl()).append(",");
		str.append("语音时长:").append(eventRecordEntity.getVideoLen()).append(",");
		str.append("文本内容:").append(eventRecordEntity.getContent()).append(",");
		str.append("事件位置:").append(eventRecordEntity.getLocation()).append(",");
		str.append("记录时间:").append(eventRecordEntity.getRecordTime()).append(",");
		str.append("创建时间:").append(DateUtils.toString(eventRecordEntity.getCreateTime(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
		str.append("版本号:").append(eventRecordEntity.getVersion()).append("}");
		log.setOperateContent(str.toString());
		logManageService.add(log);
		return i;
	}

	@Override
	public int delete(long id) {
		return eventRecordDao.delete(id);
	}

	@Override
	public EventRecordEntity getEventRecordEntity(long id) {
		EventRecordEntity eventRecordEntity = eventRecordDao.select(id);
		if(eventRecordEntity == null){
			eventRecordEntity = new EventRecordEntity();
		}
		return eventRecordEntity;
	}

	@Override
	public EventRecordResponse getEventRecordEntitys(EventRecordRequest request) {
		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		List<EventRecordEntity> list =eventRecordDao.selectAll();
		EventRecordResponse eventRecordResponse = new EventRecordResponse();
		eventRecordResponse.setEventRecordList(list);
		return eventRecordResponse;
	}

	@Override
	@Transactional
	public int update(EventRecordEntity eventRecordEntity) {
		eventRecordEntity.setUpdateTime(new Date());
		int i=eventRecordDao.update(eventRecordEntity);
		//添加日志
		LogManage log=new LogManage();
		log.setAccountType(HelpConstant.LOGZHLX_LD);
		log.setOperateModel(HelpConstant.LOGCZMK_TDXC);
		log.setOperateTime(new Date());
		log.setOperateType("新增事件");
		String name=userService.getUserById(eventRecordEntity.getCreateBy()).getRealName();
		log.setName(name);
		log.setUserId(eventRecordEntity.getCreateBy());
		StringBuffer str = new StringBuffer("");
		str.append(name).append("在团：");
		str.append(scheduleService.getSchedule(eventRecordEntity.getTeamId()).getName()).append(",修改了一条事件记录{");
		str.append("语音地址：").append(eventRecordEntity.getVoiceUrl()).append(";");
		str.append("语音时长：").append(eventRecordEntity.getVideoLen()).append(";");
		str.append("文本内容：").append(eventRecordEntity.getContent()).append(";");
		str.append("事件位置：").append(eventRecordEntity.getLocation()).append(";");
		str.append("记录时间：").append(eventRecordEntity.getRecordTime()).append(";");
		str.append("修改时间：").append(eventRecordEntity.getUpdateTime()).append(";");
		str.append("版本号：").append(eventRecordEntity.getVersion()).append("}");
		log.setOperateContent(str.toString());
		logManageService.add(log);
		return i;
	}
	@Override
	public List<EventRecordEntity> getEventRecordList(EventRecordRequest request){
		List<EventRecordEntity> eventRecordEntity = eventRecordDao.getEventRecordList(request);
		if (CollectionUtils.isEmpty(eventRecordEntity)) {
			return Collections.emptyList();
		}
		List<EventRecordEntity> newEventRecord =new ArrayList<EventRecordEntity>();
		if(eventRecordEntity!=null && eventRecordEntity.size() >0){
			for (int i = 0; i < eventRecordEntity.size(); i++) {
				EventRecordEntity er =eventRecordEntity.get(i);
				  if(er!=null){
					  List<String> photoList =new ArrayList<String>();
					if(!StringUtils.isEmpty(er.getPhotosUrl())){
						String [] photos = er.getPhotosUrl().split(";");
						if(photos!=null && photos.length >0){
							for (int j = 0; j <photos.length ; j++) {
								if(!StringUtils.isEmpty(photos[j])){
									photoList.add(photos[j]);
								}
							}
						}else{
							photoList.add(er.getPhotosUrl());
						}
					 }
					  er.setPhotosList(photoList);
					  newEventRecord.add(er);
				}
			}
		}
		return newEventRecord;
	}

	@Override
	public EventRecordPageResponse getEventList(EventRecordRequest request){
		//1根据旅行社userID获取领队用户集合
		List<Long>  userIDs=leaderDao.getLeaderIds(request.getTravelId());
		if(userIDs==null ||userIDs.size()<=0){
			return new EventRecordPageResponse();
		}
		request.setUserIDs(userIDs);
		//类型1旅行社端行程  2 公司后台行程
		request.setType(1);
		//2根据领队id集合获取事件列表
		//关联查分页
		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		Page<EventRecordEntity> eventRecordEntity = eventRecordDao.getEventList(request);
		Page<EventRecordEntity> newEventRecord =new Page<EventRecordEntity>();
		if(eventRecordEntity!=null && eventRecordEntity.size() >0){
			for (int i = 0; i < eventRecordEntity.size(); i++) {
				EventRecordEntity er =eventRecordEntity.get(i);
				if(er!=null){
					List<String> photoList =new ArrayList<String>();
					if(!StringUtils.isEmpty(er.getPhotosUrl())){
						String [] photos = er.getPhotosUrl().split(";");
						if(photos!=null && photos.length >0){
							for (int j = 0; j <photos.length ; j++) {
								if(!StringUtils.isEmpty(photos[j])){
									photoList.add(photos[j]);
								}
							}
						}else{
							photoList.add(er.getPhotosUrl());
						}
					}
					er.setPhotosList(photoList);
					newEventRecord.add(er);
				}
			}
		}
		EventRecordPageResponse response =new EventRecordPageResponse();
		response.setPages(eventRecordEntity.getPages());
		response.setTotals(eventRecordEntity.getTotal());
		response.setEventList(newEventRecord);
		return response;
	}

	//新增事件记录
	public CommonResponse addEventRecord(EventRecordRequest request){
		EventRecordEntity eventRecordEntity = new EventRecordEntity();
		//保存用户ID
		eventRecordEntity.setCreateBy(request.getCreateBy());
		eventRecordEntity.setTeamId(request.getTeamId());
		eventRecordEntity.setPhotosUrl(request.getPhotosUrl());
		eventRecordEntity.setVoiceUrl(request.getVoiceUrl());
		eventRecordEntity.setContent(request.getContent());
		eventRecordEntity.setLocation(request.getLocation());
		eventRecordEntity.setRecordTime(request.getRecordTime());
		eventRecordEntity.setVideoLen(request.getVideoLen());
		add(eventRecordEntity);
		return new CommonResponse();
	}

}
