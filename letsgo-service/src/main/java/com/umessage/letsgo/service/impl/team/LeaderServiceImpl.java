package com.umessage.letsgo.service.impl.team;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.umessage.letsgo.core.config.constant.ConfigConstant;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.dao.team.LeaderDao;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.system.LogManage;
import com.umessage.letsgo.domain.po.team.LeaderEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.LeaderRequest;
import com.umessage.letsgo.domain.vo.team.requset.MemberRequest;
import com.umessage.letsgo.domain.vo.team.respone.LeaderResponse;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.ICloudFileOperateService;
import com.umessage.letsgo.service.api.system.ILogManageService;
import com.umessage.letsgo.service.api.team.ILeaderService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.IWaitingService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.constant.HelpConstant;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LeaderServiceImpl implements ILeaderService {


	private static final Logger logger = Logger.getLogger(LeaderServiceImpl.class);
	@Resource
	private LeaderDao leaderDao;
	@Resource
	private ICloudFileOperateService cloudFileOperateService;
	@Resource
	private IUserService userService;
	@Resource
	private IWaitingService waitingService;
	@Resource
	private UserLoginHelper userLoginHelper;
	@Resource
	private ILogManageService logManageService;
	@Resource
	private IMemberService memberService;
	@Override
	public int addLeader(LeaderEntity leaderEntity) {
		Date date = new Date();
		leaderEntity.setCreateTime(date);
		leaderEntity.setUpdateTime(date);
		leaderEntity.setVersion(0L);
		leaderEntity.setPhotoUrl(getPhotoUrl(leaderEntity));
//		leaderEntity.setPhone(QueryUtils.getPhone(leaderEntity.getPhone()));
		//国家代号
		String countryCode = null!=leaderEntity.getCountryCode()?leaderEntity.getCountryCode():"";
		leaderEntity.setCountryCode(countryCode);
		//leaderEntity.setPhone(leaderEntity.getCountryCode() + leaderEntity.getPhone());
		leaderEntity.setPhone(countryCode + leaderEntity.getPhone());
		int i =leaderDao.insert(leaderEntity);
		//添加日志
		addLeaderLog(leaderEntity);
		return i;
	}

	@Override
	public void createLeaderByUser(UserEntity user, Integer leaderType) {
		LeaderEntity leaderEntity = new LeaderEntity();
		leaderEntity.setUserId(user.getId());
		String phone = user.getPhone();
		phone = phone.trim();
		leaderEntity.setPhone(phone);
		try {
			if (!StringUtils.isBlank(user.getBirthday())) {
				Date birthDay = DateUtils.parseDate(user.getBirthday(), DateUtils.DATE_FORMAT_DATEONLY);
				leaderEntity.setBirthday(birthDay);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		leaderEntity.setSex(user.getSex());
		leaderEntity.setName(user.getRealName());
		leaderEntity.setLeaderType(1);
		leaderEntity.setLeaderStatus(1);
		this.addLeader(leaderEntity);
	}

	/**
	 * 创建并关联领队
	 * @param user
     */
	public void associateLeaderAndUser(UserEntity user, Integer leaderType){
		LeaderRequest request = new LeaderRequest();
		String phone = user.getPhone();
		phone = phone.trim();
		request.setPhone(phone);
		LeaderEntity leaderEntity = leaderDao.selectByPhoneAndTravelId(request);

		if (leaderEntity != null){
			// 关联领队用户Id
			leaderEntity.setUserId(user.getId());
			update(leaderEntity);
		} else {
			// 创建领队
			createLeaderByUser(user, leaderType);
		}
	}

	public String getPhotoUrl(LeaderEntity leaderEntity){
		try {
			if(leaderEntity.getSex()!=null&&leaderEntity.getSex()==1){
				return ConfigConstant.MALE_HEAD_URL;
			}else{
				return ConfigConstant.FEMALE_HEAD_URL;
			}
			//return PhotoHelper.createMemberImage(leaderEntity.getName(),leaderEntity.getSex());
		} catch (Exception e) {
			logger.warn(leaderEntity.getName()+"领队生成头像失败\n"+e.getMessage(),e);
		}
		return null;
	}
	@Override
	public int deleteLeader(long id) {
		return leaderDao.delete(id);
	}

	@Override
	public LeaderEntity getLeader(long id) {
		LeaderEntity leaderEntity = leaderDao.select(id);
		if(leaderEntity == null){
			leaderEntity = new LeaderEntity();
		}
		int length = leaderEntity.getCountryCode().length();
		String phone = leaderEntity.getPhone();
		String finalPhone = phone.substring(length);
		leaderEntity.setPhone(finalPhone);
		return leaderEntity;
	}

	@Override
	public LeaderResponse getLeaders(LeaderRequest request) {
		//根据手机号过滤掉所有该团队成员
		Long tId = request.gettId();
		if (tId != null){
			MemberRequest memberRequest = new MemberRequest();
			memberRequest.settId(tId);
			memberRequest.setRole("eq4");
			List<String> phones = memberService.selectMemberByTidConditons(memberRequest);
			request.setPhones(phones);
		}
		request.setNowDate(DateUtils.getSysDateString());
		String interval = request.getInterval();
		if(!StringUtils.isEmpty(interval) && interval.contains("-")){
			String[] years = interval.split("-");
			request.setStartDate(getLeaderDate(years[1].trim()));
			request.setEndDate(getLeaderDate(years[0].trim()));
		}
		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		Page<LeaderEntity> list = new Page<LeaderEntity>();
		Page<LeaderEntity> listAll =leaderDao.selectAll(request);
		if(!StringUtils.isEmpty(request.getStartTime()) && !StringUtils.isEmpty(request.getEndTime())){
			//根据出发日期和结束日期查询空闲的领队
			list=getList(listAll,request);
		}else{
			list=listAll;
		}
		LeaderResponse leaderResponse = new LeaderResponse();
		leaderResponse.setPages(list.getPages());
		leaderResponse.setTotals(list.getTotal());

		list =calculateLeadTime(list);//计算领队带团时间
		leaderResponse.setList(list);
		return leaderResponse;
	}

	//获得n年前的当前时间
	private Date getLeaderDate(String num){
		if(StringUtils.isEmpty(num)) return null;
		try{
			Integer i = Integer.parseInt(num);
			Calendar calendar =Calendar.getInstance();
			calendar.add(Calendar.YEAR,-i);
			return calendar.getTime();
		}catch (Exception e){
			logger.warn(num+"带团时间解析失败\n"+e.getMessage(),e);
		}
		return null;
	}
	//计算领队带团时间
	private Page<LeaderEntity> calculateLeadTime(Page<LeaderEntity> list){
		for (LeaderEntity leader:list) {
			Date leadTime = leader.getLeadTime();
			if(leadTime != null){
				int leadYear =DateUtils.getAge(leadTime);
				leader.setLeadYears(leadYear);
			}
		}
		return list;
	}

	private int update(LeaderEntity leaderEntity){
		leaderEntity.setUpdateTime(new Date());
		return leaderDao.update(leaderEntity);
	}

	@Override
	@Transactional
	public int updateLeader(LeaderEntity leaderEntity) {
		LeaderEntity entity =leaderDao.select(leaderEntity.getId());
		if(entity == null) return 0;

		leaderEntity.setUserId(entity.getUserId());
		leaderEntity.setTravelId(entity.getTravelId());
		leaderEntity.setCreateTime(entity.getCreateTime());
		if(isNeedRebuild(leaderEntity,entity)){//需要重新生成头像
			cloudFileOperateService.deleteFile(entity.getPhotoUrl(), Constant.FACE_URL);
			leaderEntity.setPhotoUrl(getPhotoUrl(leaderEntity));
		}else{
			leaderEntity.setPhotoUrl(entity.getPhotoUrl());
		}
//		leaderEntity.setPhone(QueryUtils.getPhone(leaderEntity.getPhone()));
		leaderEntity.setCountryCode(leaderEntity.getCountryCode());
		leaderEntity.setPhone(leaderEntity.getCountryCode()+leaderEntity.getPhone());
		int count = this.update(leaderEntity);
		//添加日志
		leaderEntity.setVersion(entity.getVersion()+1);
		updateLeaderLog(leaderEntity);
		userService.leaderUpdateForUser(leaderEntity);
		return count;
	}

	private boolean isNeedRebuild(LeaderEntity leaderEntity,LeaderEntity originalEntity){
		if(originalEntity.getName().equals(leaderEntity.getName())
				&& originalEntity.getSex().equals(leaderEntity.getSex())){
			return false;
		}
		return true;
	}

	@Override
	public boolean leaderPhoneIsRepeat(String phone,Long travelId,Long tId){
		LeaderRequest request = new LeaderRequest();
		request.setPhone(phone);
		request.setTravelId(travelId);
		//查询领队表和团队对应的成员手机号
		List<String> phones = new ArrayList<>();
		if (tId != null){
			MemberRequest memberRequest = new MemberRequest();
			memberRequest.settId(tId);
			phones = memberService.selectMemberByTidConditons(memberRequest);
		}
		if (!StringUtils.isEmpty(phone)){
			for (String mphone:phones) {
				if (mphone.equals(phone)){
					return true;
				}
			}
		}

		List<LeaderEntity> leaderEntities = leaderDao.selectByPhoneAndMemberPhone(request);
		return leaderEntities.size()>0;
	}

	@Override
	public CommonResponse updateLeaderContent(Long userId, String type, String content)throws Exception {
		CommonResponse commonResponse = new CommonResponse();
		LeaderEntity leaderEntity = leaderDao.selectByUserId(userId);
		if(leaderEntity==null){
			commonResponse = new CommonResponse(ErrorConstant.INTERNAL_SERVER_ERROR,"未查询到领队信息");
		}else{
			if("1".equals(type)){
				leaderEntity.setEvaluationTag(content);
			}else if("2".equals(type)){
				leaderEntity.setLanguages(content);
			}else if("3".equals(type)){
				leaderEntity.setTourRoutes(content);
			}
			leaderEntity.setUpdateTime(new Date());
			leaderDao.update(leaderEntity);
		}
		return commonResponse;
	}




	@Override
	public LeaderEntity getLeaderByUserId(Long userId) {
		LeaderEntity leaderEntity = leaderDao.selectByUserId(userId);
		return leaderEntity;
	}
	//根据用户id获取领队ID
	public Long getIdByUserId(Long userId){
		return leaderDao.getIdByUserId(userId);
	}

	//过滤掉在行程期间忙碌的领队
	public Page<LeaderEntity> getList(Page<LeaderEntity> listAll,LeaderRequest request){
        if(listAll==null&&listAll.size()<=0){
			return listAll;
		}
		List<String> listTime= getTime(request);
		for (int i = 0; i <listAll.size() ; i++) {
			LeaderEntity leader=listAll.get(i);
			if(leader!=null && leader.getUserId()!=null){
				//判断该领队是否忙碌
				for (String time:listTime) {
					if(waitingService.hasTime(leader.getUserId(),time)){
						listAll.remove(i);
						break;
					}
				}
			}
		}
		return listAll;
	}

	public List<String> getTime(LeaderRequest request){
		List<String> list = new ArrayList<String>();
		String startTime=request.getStartTime();
		String endTime=request.getEndTime();
		int num=DateUtils.dayBetween(DateUtils.toDate(startTime), DateUtils.toDate(endTime));
		//如果num 大于1则行程不止两天
		if(num>1){
			list.add(DateUtils.sdfDateOnly.format(DateUtils.toDate(startTime)));
			for (int i = 1; i <num ; i++) {
				list.add(DateUtils.sdfDateOnly.format(DateUtils.addDay(DateUtils.toDate(startTime),i)));
			}
			list.add(DateUtils.sdfDateOnly.format(DateUtils.toDate(endTime)));
		}else{
			list.add(DateUtils.sdfDateOnly.format(DateUtils.toDate(startTime)));
			list.add(DateUtils.sdfDateOnly.format(DateUtils.toDate(endTime)));
		}
		return list;
	}

	//新增 领队导游 添加日志
	public int addLeaderLog(LeaderEntity leaderEntity){
		UserResponse userResponse = userLoginHelper.getLoginUser();
		UserEntity user = userResponse.getUserEntity();
		if(user == null || user.getId() == null){
         return 0;
		}
		LogManage log=new LogManage();
		//1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
			UserEntity userEntity =userService.getUserRole(user.getId());
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
		log.setOperateModel(HelpConstant.LOGCZMK_LDDY);
		log.setOperateTime(leaderEntity.getCreateTime());
		log.setOperateType("新增领队或导游");
		log.setName(user.getRealName());
		log.setUserId(user.getId());
		StringBuffer str = new StringBuffer("");
		str.append(user.getRealName()).append("创建了一个");
		if(leaderEntity.getLeaderType()!=null&&leaderEntity.getLeaderType()==1){
			str.append("领队{");
		}
		if(leaderEntity.getLeaderType()!=null&&leaderEntity.getLeaderType()==2){
			str.append("导游{");
		}
		if(leaderEntity.getLeaderType()!=null&&leaderEntity.getLeaderType()==3){
			str.append("领队兼导游{");
		}
		str.append("姓名:").append(leaderEntity.getName()).append(",");
        if(leaderEntity.getSex()==1){
			str.append("性别:").append("男性").append(",");
		}else{
			str.append("性别:").append("女性").append(",");
		}
		str.append("出生日期:").append(DateUtils.toString(leaderEntity.getBirthday(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
		str.append("手机号码:").append(leaderEntity.getPhone()).append(",");
		str.append("创建时间:").append(DateUtils.toString(leaderEntity.getCreateTime(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
		str.append("版本号:").append(leaderEntity.getVersion()).append("}");
		log.setOperateContent(str.toString());
		logManageService.add(log);
		return 0;
	}

	//修改 领队导游 添加日志
	public int updateLeaderLog(LeaderEntity leaderEntity){
		UserResponse userResponse = userLoginHelper.getLoginUser();
		UserEntity user = userResponse.getUserEntity();
		if(user == null || user.getId() == null){
			return 0;
		}
		LogManage log=new LogManage();
		//1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
		UserEntity userEntity =userService.getUserRole(user.getId());
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
		log.setOperateModel(HelpConstant.LOGCZMK_LDDY);
		log.setOperateTime(new Date());
		log.setOperateType("修改领队或导游");
		log.setName(user.getRealName());
		log.setUserId(user.getId());
		StringBuffer str = new StringBuffer("");
		str.append(user.getRealName()).append("修改了一个");
		if(leaderEntity.getLeaderType()!=null&&leaderEntity.getLeaderType()==1){
			str.append("领队{");
		}
		if(leaderEntity.getLeaderType()!=null&&leaderEntity.getLeaderType()==2){
			str.append("导游{");
		}
		if(leaderEntity.getLeaderType()!=null&&leaderEntity.getLeaderType()==3){
			str.append("领队兼导游{");
		}
		str.append("姓名:").append(leaderEntity.getName()).append(" ,");
		if(leaderEntity.getSex()==1){
			str.append("性别:").append("男性").append(",");
		}else{
			str.append("性别:").append("女性").append(",");
		}

		str.append("出生日期:").append(DateUtils.toString(leaderEntity.getBirthday(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
		str.append("手机号码:").append(leaderEntity.getPhone()).append(",");
		str.append("修改时间:").append(DateUtils.toString(leaderEntity.getUpdateTime(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
		str.append("版本号:").append(leaderEntity.getVersion()).append("}");
		log.setOperateContent(str.toString());
		logManageService.add(log);
		return 0;
	}
}
