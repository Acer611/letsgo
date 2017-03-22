package com.umessage.letsgo.service.impl.team;

import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.core.utils.QueryUtils;
import com.umessage.letsgo.dao.team.TravelAgencyDao;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.system.LogManage;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.ILogManageService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.common.constant.HelpConstant;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TravelAgencyServiceImpl implements ITravelAgencyService {
	@Resource
	private TravelAgencyDao travelAgencyDao;
	@Resource
	private UserLoginHelper userLoginHelper;
	@Resource
	private ILogManageService logManageService;
	@Resource
	private IUserService userService;

	@Override
	public int addTravelAgency(TravelAgencyEntity travelAgencyEntity) {
		Date date = new Date();
		travelAgencyEntity.setCreateTime(date);
		travelAgencyEntity.setUpdateTime(date);
		travelAgencyEntity.setVersion(1L);
		travelAgencyEntity.setContactPhone(QueryUtils.getPhone(travelAgencyEntity.getContactPhone()));
		return travelAgencyDao.insert(travelAgencyEntity);
	}

	@Override
	public TravelAgencyEntity updateTravelAgency(TravelAgencyEntity travel) {
		TravelAgencyEntity entity = travelAgencyDao.select(travel.getId());
		if(entity == null) return travel;
		entity.setName(travel.getName());
		entity.setContactPerson(travel.getContactPerson());
		entity.setContactPhone(QueryUtils.getPhone(travel.getContactPhone()));
		entity.setEmail(travel.getEmail());
		entity.setAddress(travel.getAddress());
		entity.setDesc(travel.getDesc());
		entity.setLicenseUrl(travel.getLicenseUrl());
		entity.setUpdateTime(new Date());
		travelAgencyDao.update(entity);
		//添加日志
		entity.setVersion(entity.getVersion()+1);
		updateTravelAgencyLog(entity);
		return entity;
	}

	@Override
	public TravelAgencyEntity getTravelAgency(Long id) {
		TravelAgencyEntity travelAgencyEntity = travelAgencyDao.select(id);
		if (travelAgencyEntity == null) {
			return new TravelAgencyEntity();
		}
		return travelAgencyEntity;
	}
	@Override
	public TravelAgencyEntity getByUserId(Long id) {
		TravelAgencyEntity travelAgencyEntity = travelAgencyDao.getByUserId(id);
		if (travelAgencyEntity == null) {
			return new TravelAgencyEntity();
		}
		return travelAgencyEntity;
	}
	//获得当前登录的旅行社
	@Override
	public TravelAgencyEntity getCurrentTravel(){
		UserResponse userResponse = userLoginHelper.getLoginUser();
		UserEntity user = userResponse.getUserEntity();
		if(user == null || user.getId() == null){
			return null;
		}
//		return getByUserId(user.getId());
		return getByTravelerId(user.getTravelerId());
	}

	public int setUserId(TravelAgencyEntity travelAgencyEntity){
		return travelAgencyDao.setUserId(travelAgencyEntity);
	}

	@Override
	public TravelAgencyEntity getByTravelerId(Long id) {
		TravelAgencyEntity travelAgencyEntity = travelAgencyDao.getByTravelerId(id);
		if (travelAgencyEntity == null) {
			return new TravelAgencyEntity();
		}
		return travelAgencyEntity;
	}

	//修改 领队导游 添加日志
	public int updateTravelAgencyLog(TravelAgencyEntity entity){
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
		log.setOperateModel(HelpConstant.LOGCZMK_WDSZ);
		log.setOperateTime(new Date());
		log.setOperateType("修改账户信息");
		log.setName(user.getRealName());
		log.setUserId(user.getId());
		StringBuffer str = new StringBuffer("");
		str.append(user.getRealName()).append("修改了账户信息{");
		str.append("旅行社名称:").append(entity.getName()).append(",");
		str.append("联系人姓名:").append(entity.getContactPerson()).append(",");
		str.append("联系人手机:").append(entity.getContactPhone()).append(",");
		str.append("email:").append(entity.getEmail()).append(",");
		str.append("旅行社地址:").append(entity.getAddress()).append(",");
		str.append("旅行社简介:").append(entity.getDesc()).append(",");
		str.append("营业执照地址:").append(entity.getLicenseUrl()).append(",");
		str.append("修改时间:").append(DateUtils.toString(entity.getUpdateTime(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
		str.append("版本号:").append(entity.getVersion()).append("}");
		log.setOperateContent(str.toString());
		logManageService.add(log);
		return 0;
	}

}
