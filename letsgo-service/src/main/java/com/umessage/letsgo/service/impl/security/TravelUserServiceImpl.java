package com.umessage.letsgo.service.impl.security;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.dao.security.UserDao;
import com.umessage.letsgo.dao.security.UserRoleDao;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.security.UserRoleEntity;
import com.umessage.letsgo.domain.po.security.ValidationCodeEntity;
import com.umessage.letsgo.domain.po.system.LogManage;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.request.UserVo;
import com.umessage.letsgo.domain.vo.security.respone.UserListResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.security.ITravelUserService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.security.IValidationCodeService;
import com.umessage.letsgo.service.api.system.ILogManageService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.common.constant.HelpConstant;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by ZhaoYidong on 2016/6/17.
 */
@Service
public class TravelUserServiceImpl implements ITravelUserService {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(TravelUserServiceImpl.class);

    @Resource
    private UserDao userDao;
    @Resource
    private UserRoleDao userRoleDao;
    
    @Resource
    private ShaPasswordEncoder shaPasswordEncoder;
    @Resource
    private IValidationCodeService validationCodeService;
    @Resource
    private ITravelAgencyService travelService;
	@Resource
	private UserLoginHelper userLoginHelper;
	@Resource
	private IUserService userService;
	@Resource
	private ILogManageService logManageService;

    @Override
    public int addTravelUser(UserEntity userEntity) {
        Date date = new Date();
        userEntity.setCreateTime(date);
        userEntity.setUpdateTime(date);
        userEntity.setVersion(1L);
        userEntity.setUserStatus(1);//审核中

        String encodePassword = shaPasswordEncoder.encodePassword(userEntity.getPassword(), userEntity.getUserName());
        userEntity.setPassword(encodePassword);
        return userDao.insert(userEntity);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userCache", key = "#user.userName"),
            @CacheEvict(value = "userCache", key = "#user.phone"),
            @CacheEvict(value = "userCache", key = "#user.mail") })
    public CommonResponse updateTravelUser(UserEntity user) {
        UserEntity entity = userDao.select(user.getId());
        if(entity == null) return CommonResponse.createNotFoundResponse("没有发现该用户"+user.getId());
        if (!shaPasswordEncoder.isPasswordValid(entity.getPassword(), user.getPassword(), entity.getUserName())) {
            return CommonResponse.createNotSamePassResponse();
        }

        // 2、设置新密码，更新用户信息
        String newEncodePassword = shaPasswordEncoder.encodePassword(user.getNewPasswords(), user.getUserName());
        entity.setUserName(user.getUserName());
        entity.setPassword(newEncodePassword);
        entity.setUpdateTime(new Date());
        userDao.update(entity);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setRetMsg("密码修改成功！");
        return commonResponse;
    }

    @Override
    public boolean accountIsRepeated(String account) {
        UserEntity user =userDao.selectWithAccount(account);
        return user != null;
    }

    @Override
    public boolean checkCodeIsError(String validationCode, String phone) {
        ValidationCodeEntity codeEntity = validationCodeService.getValidCodeByPhone(phone);
        return !validationCode.equals(codeEntity.getCode());
    }

	@Override
	public CommonResponse saveOrUpdatenAccount(UserVo userVo) throws Exception{
		Date time = new Date();
		if(userVo.getId()==null){
			TravelAgencyEntity travel = travelService.getCurrentTravel();;
			UserEntity user = new UserEntity();
			PropertyUtils.copyProperties(user, userVo);
			user.setCreateTime(time);
			user.setTravelerId(travel.getId());
			user.setUserStatus(1);
			user.setVersion(1l);
			userDao.insert(user);
			UserRoleEntity userRoleEntity = new UserRoleEntity();
			userRoleEntity.setUserId(user.getId());
			userRoleEntity.setRoleId(Long.parseLong(user.getRole()+""));
			userRoleEntity.setCreateTime(time);
			userRoleEntity.setVersion(1l);
			userRoleDao.insert(userRoleEntity);
			//添加日志
			addAccountLog(user);
		}else{
			UserEntity user = userDao.select(userVo.getId());
			if(user==null){
				return new CommonResponse(ErrorConstant.INTERNAL_SERVER_ERROR,"子帐号不存在");
			}else{
				PropertyUtils.copyProperties(user, userVo);
				user.setUpdateTime(time);
				userDao.update(user);
				userRoleDao.updateByUserId(userVo.getId(), Long.parseLong(user.getRole()+""));
				//添加日志
				user.setVersion(user.getVersion()+1);
				updateAccountLog(user);
			}
		}
		return new CommonResponse();
	}

	@Override
	public UserListResponse getSonAccountList(Integer pageNo) {
		TravelAgencyEntity travel = travelService.getCurrentTravel();
		Long travelerId = travel.getId();
		PageHelper.startPage(pageNo,10);
		Page<UserEntity> userEntities = userDao.getSonAccountList(travelerId);
		UserListResponse listResponse = new UserListResponse();
		if(userEntities!=null && userEntities.size() >0){
			listResponse.setPages(userEntities.getPages());
			listResponse.setTotals(userEntities.getTotal());
			listResponse.setUserList(new ArrayList<UserVo>());
			for (UserEntity userEntity : userEntities) {
				try {
					UserVo userVo = new UserVo();

					PropertyUtils.copyProperties(userVo, userEntity);
					listResponse.getUserList().add(userVo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return listResponse;
	}

	@Override
	public CommonResponse deleteAccount(Long accountId) {
		userDao.delete(accountId);
		return new CommonResponse();
	}

	@Override
	public UserVo queryAccount(Long accountId) {
		UserEntity user = userDao.select(accountId);
		if(user!=null){
			try {
				UserVo userVo = new UserVo();
				PropertyUtils.copyProperties(userVo, user);
				return userVo;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void updateAccount(Long accountId) {
		userDao.updateAccount(accountId);
		//添加日志
		deleteAccountLog(accountId);
	}
	//新增 子账号 添加日志
	public int addAccountLog(UserEntity userEntity){
		UserResponse userResponse = userLoginHelper.getLoginUser();
		UserEntity user = userResponse.getUserEntity();
		if(user == null || user.getId() == null){
			return 0;
		}
		LogManage log=new LogManage();
		//1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
		UserEntity userEntity1 =userService.getUserRole(user.getId());
		if(userEntity1!=null&&userEntity1.getRole()!=null){
			//5旅行社；6计调；7销售（门店）role
			//日志-账号类型:0旅行社管理员，1门店销售（门店），2计调，3领队，
			if(userEntity1.getRole()==5){
				log.setAccountType(HelpConstant.LOGZHLX_LXS);
			}
			if(userEntity1.getRole()==6){
				log.setAccountType(HelpConstant.LOGZHLX_JD);
			}
			if(userEntity1.getRole()==7){
				log.setAccountType(HelpConstant.LOGZHLX_XS);
			}
		}
		log.setOperateModel(HelpConstant.LOGCZMK_ZHGL);
		log.setOperateTime(new Date());
		log.setOperateType("新增子账号");
		log.setName(user.getRealName());
		log.setUserId(user.getId());
		StringBuffer str = new StringBuffer("");
		str.append(user.getRealName()).append("创建了一个");
		if(userEntity.getRole()!=null&&userEntity.getRole()==6){
			str.append("OP计调人员{");
		}
		if(userEntity.getRole()!=null&&userEntity.getRole()==7){
			str.append("门店销售{");
		}
		str.append("姓名:").append(userEntity.getRealName()).append(",");
		str.append("账号:").append(userEntity.getUserName()).append(",");
		str.append("手机号码:").append(userEntity.getPhone()).append(",");
		str.append("电子邮箱:").append(userEntity.getMail()).append(",");
		str.append("创建时间:").append(DateUtils.toString(userEntity.getCreateTime(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
		str.append("版本号:").append(userEntity.getVersion()).append("}");
		log.setOperateContent(str.toString());
		logManageService.add(log);
		return 0;
	}

	//修改 子账号 添加日志
	public int updateAccountLog(UserEntity userEntity){
		UserResponse userResponse = userLoginHelper.getLoginUser();
		UserEntity user = userResponse.getUserEntity();
		if(user == null || user.getId() == null){
			return 0;
		}
		LogManage log=new LogManage();
		//1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
		UserEntity userEntity1 =userService.getUserRole(user.getId());
		if(userEntity1!=null&&userEntity1.getRole()!=null){
			//5旅行社；6计调；7销售（门店）role
			//日志-账号类型:0旅行社管理员，1门店销售（门店），2计调，3领队，
			if(userEntity1.getRole()==5){
				log.setAccountType(HelpConstant.LOGZHLX_LXS);
			}
			if(userEntity1.getRole()==6){
				log.setAccountType(HelpConstant.LOGZHLX_JD);
			}
			if(userEntity1.getRole()==7){
				log.setAccountType(HelpConstant.LOGZHLX_XS);
			}
		}
		log.setOperateModel(HelpConstant.LOGCZMK_ZHGL);
		log.setOperateTime(new Date());
		log.setOperateType("修改子账号");
		log.setName(user.getRealName());
		log.setUserId(user.getId());
		StringBuffer str = new StringBuffer("");
		str.append(user.getRealName()).append("修改了一个");
		if(userEntity.getRole()!=null&&userEntity.getRole()==6){
			str.append("OP计调人员{");
		}
		if(userEntity.getRole()!=null&&userEntity.getRole()==7){
			str.append("门店销售{");
		}
		str.append("姓名:").append(userEntity.getRealName()).append(",");
		str.append("账号:").append(userEntity.getUserName()).append(",");
		str.append("手机号码:").append(userEntity.getPhone()).append(",");
		str.append("电子邮箱:").append(userEntity.getMail()).append(",");
		str.append("修改时间:").append(DateUtils.toString(userEntity.getUpdateTime(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
		str.append("版本号:").append(userEntity.getVersion()).append("}");
		log.setOperateContent(str.toString());
		logManageService.add(log);
		return 0;
	}

	//删除 子账号 添加日志
	public int deleteAccountLog(Long userId){
		UserResponse userResponse = userLoginHelper.getLoginUser();
		UserEntity user = userResponse.getUserEntity();
		if(user == null || user.getId() == null){
			return 0;
		}
		UserEntity userEntity= userService.getUserById(userId);
		if(userEntity == null){
			return 0;
		}
		LogManage log=new LogManage();
		//1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
		UserEntity userEntity1 =userService.getUserRole(user.getId());
		if(userEntity1!=null&&userEntity1.getRole()!=null){
			//5旅行社；6计调；7销售（门店）role
			//日志-账号类型:0旅行社管理员，1门店销售（门店），2计调，3领队，
			if(userEntity1.getRole()==5){
				log.setAccountType(HelpConstant.LOGZHLX_LXS);
			}
			if(userEntity1.getRole()==6){
				log.setAccountType(HelpConstant.LOGZHLX_JD);
			}
			if(userEntity1.getRole()==7){
				log.setAccountType(HelpConstant.LOGZHLX_XS);
			}
		}
		log.setOperateModel(HelpConstant.LOGCZMK_ZHGL);
		log.setOperateTime(new Date());
		log.setOperateType("修改子账号");
		log.setName(user.getRealName());
		log.setUserId(user.getId());
		StringBuffer str = new StringBuffer("");
		str.append(user.getRealName()).append("删除了一个");
		if(userEntity.getRole()!=null&&userEntity.getRole()==6){
			str.append("OP计调人员{");
		}
		if(userEntity.getRole()!=null&&userEntity.getRole()==7){
			str.append("门店销售{");
		}
		str.append("姓名:").append(userEntity.getRealName()).append(",");
		str.append("账号:").append(userEntity.getUserName()).append(",");
		str.append("手机号码:").append(userEntity.getPhone()).append(",");
		str.append("电子邮箱:").append(userEntity.getMail()).append(",");
		str.append("删除时间:").append(DateUtils.toString(userEntity.getUpdateTime(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
		str.append("版本号:").append(userEntity.getVersion()).append("}");
		log.setOperateContent(str.toString());
		logManageService.add(log);
		return 0;
	}

}
