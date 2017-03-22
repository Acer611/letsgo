package com.umessage.letsgo.service.impl.activity;

import com.umessage.letsgo.core.annotation.dataitem.DataItem;
import com.umessage.letsgo.core.timezone.TimeZoneUtil;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.dao.activity.LocationDao;
import com.umessage.letsgo.dao.security.DeviceDao;
import com.umessage.letsgo.domain.po.activity.LocationEntity;
import com.umessage.letsgo.domain.po.activity.LocationHistoryEntity;
import com.umessage.letsgo.domain.po.security.DeviceEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.activity.request.LocationRequest;
import com.umessage.letsgo.domain.vo.activity.response.LocationRespone;
import com.umessage.letsgo.service.api.activity.ILocationService;
import com.umessage.letsgo.service.api.system.IPushService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.impl.team.TeamServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class LocationServiceImpl implements ILocationService {
	@Resource
	private LocationDao locationMapper;
	@Resource
	private IMemberService memberService;
	@Resource
	private DeviceDao deviceDao;
	@Resource
	private IPushService pushService;
	@Resource
	private Mapper dozerBeanMapper;
	@Resource
	private LocationHistoryServiceImpl locationHistoryService;
	@Resource
	private TeamServiceImpl teamService;

	private Logger logger = Logger.getLogger(LocationServiceImpl.class);

	@Override
	public int addLocation(LocationEntity locationEntity) {
		locationEntity.setCreateTime(new Date());
		locationEntity.setVersion(0l);
		return locationMapper.insert(locationEntity);
	}
	@Override
	public LocationEntity select(Long id){
		LocationEntity locationEntity = locationMapper.select(id);
		if(locationEntity == null){
			locationEntity = new LocationEntity();
		}
		return locationEntity;
	}

	@Override
	public int updateLocation(LocationEntity locationEntity) {
		return locationMapper.update(locationEntity);
	}
	
	@Override
	public LocationEntity getLocationByUserId(Long userId) {
		LocationEntity entity = locationMapper.selectLocationWithUserId(userId);
		if(entity == null){
			entity = new LocationEntity();
		}
		return entity;
	}

	@Override
	public List<UserEntity> getUploadLocationUsers(List<UserEntity> userList){
		Iterator<UserEntity> iterator = userList.iterator();
		while (iterator.hasNext()){
			UserEntity user = iterator.next();
			LocationEntity location = getLocationByUserId(user.getId());
			//用户以前没有位置，则需要推送上传位置
			if(location.getLongitude() == null){
				continue;
			}
			//用户有位置，上传位置的时间在5分钟之内，则不需要上传位置
			Date addFiveTime = addMinute(location.getCreateTime(),5);
			if(addFiveTime.after(new Date())){
				iterator.remove();
				continue;
			}

			/*// 根据位置获取时区。
			TimeZone timeZone = TimeZoneUtil.getTimeZoneWithCoordinate(location.getLatitude(),location.getLongitude());
			// 根据时区计算当前时间。
			Date date = TimeZoneUtil.getTimeZoneDate(new Date(),timeZone);
			// 判断时间不在上传范围内的用户，从集合中删除
			Date startTime = setDayTime(date,6,0,0);
			Date endTime = setDayTime(date,23,0,0);
			if(date.before(startTime) || date.after(endTime)){
				iterator.remove();
			}*/

			if (!isUploadNextTime(user.getId(), location.getLongitude(), location.getLatitude(), 0)) {
				iterator.remove();
			}
		}
		return userList;
	}


	private Date setDayTime(Date date,int hour,int minute,int second){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,hour);
		calendar.set(Calendar.MINUTE,minute);
		calendar.set(Calendar.SECOND,second);
		return calendar.getTime();
	}
	@Override
	@DataItem
	public List<LocationEntity> getLocationList(String teamId, Long userId, boolean isPushed) throws Exception {
		// 1、获取团队用户IDs
		MemberEntity member = memberService.getMemberWithTeamIdAndUserId(teamId,userId);

		List<LocationEntity> locationList = null;
		if (member.getRole() != null && member.getRole() < 3) {
			locationList = getTouristList(teamId);
		} else {
			locationList = getTourGuideList(teamId,1);
		}

		List<Long> userIds = new ArrayList<Long>();
		for (LocationEntity location:locationList){
			//最后一次上传位置的时间超过5（假定）分钟，则发送需上传位置的推送。
			Date date = addMinute(location.getFinishTime(), 5);
//			if(date.before(new Date())){
//				userIds.add(location.getUserId());
//			}
			userIds.add(location.getUserId());
			// 判断每个用户的位置是实时位置还是历史位置
			Date beforeDate = DateUtils.addMinute(new Date(), -5);	// 5分钟前的时间
			if (location.getCreateTime().after(beforeDate) && location.getCreateTime().before(new Date())) {
				location.setIsCurrentLocation(1);
			} else {
				location.setIsCurrentLocation(2);
			}

			// 计算两个时间之差
			long times = DateUtils.daysBetweenMins(location.getCreateTime(), new Date());
			/*String descn = "";

			if (times > 59) {
				long hours = times / 60;
				long mins = times % 60;
				if (mins == 0) {
					descn = hours + "小时前";
				} else {
					descn = hours + "小时" + mins + "分钟前";
				}
			} else {
				descn = times + "分钟前";
			}*/
			location.setBeforeTime(times);
		}
		if(isPushed && !userIds.isEmpty()){
			try {
				List<DeviceEntity> deviceList = deviceDao.selectDeviceWithUserIds(userIds);
				Map<String, String> param = new HashMap<>();
				param.put("type", "4");
//				if (member.getRole() != null && member.getRole() < 3) {
//					pushService.pushMIMessageToTourist(deviceList, "小主快快打开跟上APP告诉我们您在哪儿吧", param);
				//} else {
					pushService.pushMIMessageToGuide(deviceList, "小主快快打开跟上APP告诉我们您在哪儿吧", param);
				//}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return locationList;
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

	private List<LocationEntity> getTouristList(String teamId){
		LocationRequest request = new LocationRequest();
		request.setTeamId(teamId);
		request.setRole("eq3");

		List<LocationEntity> locationEntityList= locationMapper.selectLocationsByTeamId(request);
		if (CollectionUtils.isEmpty(locationEntityList)) {
			return Collections.emptyList();
		}
		for (LocationEntity locationEntity:locationEntityList) {
			MemberEntity memberEntity = locationEntity.getMemberEntity();
			memberEntity = this.changePhotoUrl(memberEntity);
		}
		return locationEntityList;
	}

	private List<LocationEntity> getTourGuideList(String teamId,Integer isAdmin){
		LocationRequest request = new LocationRequest();
		request.setTeamId(teamId);
		request.setIsAdmin(isAdmin);
		request.setRole("lt3");
		request.setSort_role("asc");

		List<LocationEntity> locationEntityList= locationMapper.selectLocationsByTeamId(request);
		if (CollectionUtils.isEmpty(locationEntityList)) {
			return Collections.emptyList();
		}
		for (LocationEntity locationEntity:locationEntityList) {
			MemberEntity memberEntity = locationEntity.getMemberEntity();
			memberEntity = this.changePhotoUrl(memberEntity);
		}
		return locationEntityList;
	}

	/**
	 * 设置团队头像地址为用户头像地址 lizhen
	 * @param memberEntity
	 * @return
	 */
	private MemberEntity changePhotoUrl(MemberEntity memberEntity){
		if(null == memberEntity){
			return memberEntity;
		}
		UserEntity userEntity = memberEntity.getUserEntity();
		if (userEntity != null){
			String photoUrl = userEntity.getPhotoUrl();
			//判断关联用户的头像地址是否为空，不为空设置团队头像地址为用户头像地址
			if (!StringUtils.isEmpty(photoUrl)){
				memberEntity.setPhotoUrl(photoUrl);
			}
		}

		return memberEntity;
	}


	@Override
	@Transactional
	public LocationRespone createLocation(Long userId, Double lng, Double lat) {
		logger.info("提交位置之前");
		locationMapper.deleteByUserId(userId);//删除之前的位置。

		LocationEntity location = new LocationEntity();
		location.setUserId(userId);
		location.setLongitude(lng);
		location.setLatitude(lat);
		location.setFinishTime(new Date());
		addLocation(location); //创建新位置
		addLocationHistory(location);//将新位置保存到历史表。

		LocationRespone locationRespone = new LocationRespone();
		locationRespone.setLocationEntity(location);

		locationRespone.setInterval(Constant.INTERVAL_TIME);

		if (isUploadNextTime(userId, lng, lat, Constant.INTERVAL_TIME)) {
			locationRespone.setIsUpload(1);		// 需要上传
		} else {
			locationRespone.setIsUpload(0);		// 不需要上传
		}

		logger.info("提交位置之后");
		return locationRespone;
	}

	private void addLocationHistory(LocationEntity locationEntity){
		LocationHistoryEntity locationHistory = dozerBeanMapper.map(locationEntity, LocationHistoryEntity.class);
		locationHistoryService.addLocationHistory(locationHistory);
	}

	/**
	 * 判断用户下一次是否需要上传位置
	 * @param lng
	 * @param lat
	 * @param time
     * @return
     */
	private boolean isUploadNextTime(Long userId, Double lng, Double lat, int time) {
		boolean flag = false;
		// 获取当前用户是否有出行中团队
		List<TeamEntity> teamEntityList = teamService.getTeamEntityList(userId);
		for (TeamEntity teamEntity : teamEntityList) {
			if (teamEntity.getStatus() == 1) {
				flag = true;
				break;
			}
		}

		// 根据位置获取时区。
		TimeZone timeZone = TimeZoneUtil.getTimeZoneWithCoordinate(lat, lng);
		// 根据时区计算当前时间[系统时间，即北京时间]后的几分钟【time】时间。
		Date afterTenMinDate = DateUtils.addMinute(new Date(), time);
		Date date = TimeZoneUtil.getTimeZoneDate(afterTenMinDate, timeZone);

		Date startTime = setDayTime(date,6,0,0);
		Date endTime = setDayTime(date,23,0,0);

		// 当前用户存在出行中的团队行程且当前时间在允许上传位置时间段内
		if( flag && (date.after(startTime) && date.before(endTime)) ) {
			flag = true;
		}

		return flag;
	}


	 //获取围观位置
	@Override
	@DataItem
	public List<LocationEntity> getOnlookLocation(String teamId, Long userId, boolean isPushed) throws Exception {

		List<LocationEntity> locationList = getTouristList(teamId);
		List<Long> userIds = new ArrayList<Long>();
		for (LocationEntity location:locationList){
			//最后一次上传位置的时间超过5（假定）分钟，则发送需上传位置的推送。
			Date date = addMinute(location.getFinishTime(), 5);
			if(date.before(new Date())){
				userIds.add(location.getUserId());
			}

			// 判断每个用户的位置是实时位置还是历史位置
			Date beforeDate = DateUtils.addMinute(new Date(), -5);	// 5分钟前的时间
			if (location.getCreateTime().after(beforeDate) && location.getCreateTime().before(new Date())) {
				location.setIsCurrentLocation(1);
			} else {
				location.setIsCurrentLocation(2);
			}

			// 计算两个时间之差
			long times = DateUtils.daysBetweenMins(location.getCreateTime(), new Date());
			/*String descn = "";

			if (times > 59) {
				long hours = times / 60;
				long mins = times % 60;
				if (mins == 0) {
					descn = hours + "小时前";
				} else {
					descn = hours + "小时" + mins + "分钟前";
				}
			} else {
				descn = times + "分钟前";
			}*/
			location.setBeforeTime(times);
		}
		if(isPushed && !userIds.isEmpty()){
			try {
				List<DeviceEntity> deviceList = deviceDao.selectDeviceWithUserIds(userIds);
				Map<String, String> param = new HashMap<>();
				param.put("type", "4");
					pushService.pushMIMessageToGuide(deviceList, "小主快快打开跟上APP告诉我们您在哪儿吧", param);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return locationList;
	}

}
