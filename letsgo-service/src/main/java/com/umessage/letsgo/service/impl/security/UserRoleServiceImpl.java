package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.dao.security.RoleDao;
import com.umessage.letsgo.dao.security.UserRoleDao;
import com.umessage.letsgo.domain.po.security.RoleEntity;
import com.umessage.letsgo.domain.po.security.UserRoleEntity;
import com.umessage.letsgo.service.api.security.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
	@Resource
	private UserRoleDao userRoleMapper;
	@Resource
	private RoleDao roleMapper;

	public int create(UserRoleEntity userRole) {
		userRole.setCreateTime(new java.util.Date());
		userRole.setVersion(0l);
		return userRoleMapper.insert(userRole);
	}

	/*public int delete(UserRoleEntity t) {
		return userRoleMapper.deleteWithConditions(JsonUtils.pojo2map(t));
	}*/

	/*public int update(UserRoleEntity userRole) {
		userRole.setUpdateTime(new java.util.Date());
		Map<String, Object> args = JsonUtils.pojo2map(userRole);
		Object id = userRole.getId();
		args.put("_id", id);
		// 开启乐观锁
		Long version = userRole.getVersion();
		args.put("version", version + 1);
		args.put("_version", version);
		return userRoleMapper.updateWithConditions(args);
	}*/

	/*
	 * public UserRoleEntity get(Object id){ List<UserRoleEntity>
	 * list=userRoleMapper.findList(UtilMisc.toMap("id",id));
	 * return(list!=null&&list.size()>0)?list.get(0):null; }
	 */

	/*
	 * public int findCount(Map<String, Object> conditions){ return
	 * userRoleMapper.findCount(conditions); } public PageInfo<UserRoleEntity>
	 * findList(int pageSize, int currentPage,final Map<String, Object>
	 * conditions){ Integer totalCount = userRoleMapper.findCount(conditions);
	 * return SmartPageInfo.createPage(new
	 * SmartPageInfo.Paging<UserRoleEntity>(){
	 *
	 * @Override public List<UserRoleEntity> records(int startIndex, int
	 * endIndex) { conditions.putAll(UtilMisc.toMap("startIndex",startIndex,
	 * "pageSize",endIndex-startIndex)); return
	 * userRoleMapper.findList(conditions); }
	 *
	 * },totalCount, pageSize, currentPage); }
	 *
	 * public UserRoleEntity getRich(Object id){ List<UserRoleEntity>
	 * list=userRoleMapper.findRichList(UtilMisc.toMap("id",id));
	 * return(list!=null&&list.size()>0)?list.get(0):null; }
	 */

	/*
	 * public List<UserRoleEntity> findRichAll(Map<String, Object> conditions){
	 * return userRoleMapper.findRichList(conditions); }
	 *
	 * public PageInfo<UserRoleEntity> findRichList(int pageSize, int
	 * currentPage,final Map<String, Object> conditions){ Integer totalCount =
	 * userRoleMapper.findCount(conditions); return SmartPageInfo.createPage(new
	 * SmartPageInfo.Paging<UserRoleEntity>(){
	 *
	 * @Override public List<UserRoleEntity> records(int startIndex, int
	 * endIndex) { conditions.putAll(UtilMisc.toMap("startIndex",startIndex,
	 * "pageSize",endIndex-startIndex)); return
	 * userRoleMapper.findRichList(conditions); }
	 *
	 * },totalCount, pageSize, currentPage); }
	 */
	/**
	 *亦可使用startIndex，endIndex限制查询结果数量
	 */

	public List<UserRoleEntity> findAll(UserRoleEntity conditions) {
		return userRoleMapper.selectUserRoleWithConditions(conditions);
	}

	/**
	 * 给一个用户进行授权
	 * @param userId
	 * @param roleId
     * @return
     */
	private UserRoleEntity grantRoleInfo(Long userId, Long roleId) {
		RoleEntity role = roleMapper.select(roleId);
		if (role == null) {
			throw new BusinessException(ErrorConstant.INVALID_PARAMETER, "该角色不存在");
		}

		UserRoleEntity userRole = new UserRoleEntity();
		userRole.setUserId(userId);
		userRole.setRoleId(role.getId());
		userRole.setCreateTime(new Date());
		userRole.setVersion(0l);
		return userRole;
	}

	@Override
	public int createUserRoleByUserId(Long userId, Long roleId) {
		// 领队(2.0改为普通用户)：roleId为1；导游：roleId为2；游客：roleId为3
		UserRoleEntity userRole = this.grantRoleInfo(userId, roleId);
		return this.create(userRole);
	}

	@Override
	public List<UserRoleEntity> getUserRoleByUserId(Long userId) {
		UserRoleEntity userRole = new UserRoleEntity();
		userRole.setUserId(userId);
		List<UserRoleEntity> userRoleList = userRoleMapper.selectUserRoleWithConditions(userRole);
		if (CollectionUtils.isEmpty(userRoleList))
			return Collections.emptyList();
		return userRoleList;
	}
}
