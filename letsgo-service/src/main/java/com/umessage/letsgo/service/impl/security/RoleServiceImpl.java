package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.security.RoleDao;
import com.umessage.letsgo.domain.po.security.RoleEntity;
import com.umessage.letsgo.service.api.security.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class RoleServiceImpl implements IRoleService {
    @Resource 
    private RoleDao roleMapper;
    
//    public int create(RoleEntity role){
//        role.setCreateTime(new java.util.Date());
//        role.setVersion(0l);
//    	return roleMapper.insert(role);
//    }
    
//    public int deleteByPk(Object pk){
//    	return roleMapper.delete((Long) pk);
//    }
    
//    public int delete(RoleEntity t){
//    	return roleMapper.delete(t.getId());
//    }
    
//    public int delete(Map<String, Object> conditions){
//    	return roleMapper.delete(conditions);
//    }

//    public int update(RoleEntity role){
//    	role.setUpdateTime(new java.util.Date());
//    	Map<String,Object> args=JsonUtils.pojo2map(role);
//    	Object id=role.getId();
//    	args.put("_id",id);
//    	//开启乐观锁
//    	Long version=role.getVersion();
//    	args.put("version",version+1);
//    	args.put("_version",version);
//    	return roleMapper.updateWithConditions(args);
//    }
    /**
    *条件前加“_” 设置值不用变
    */
//    public int updateAll(Map<String, Object> conditions){
//    	return roleMapper.updateWithConditions(conditions);
//    }

    /**
     * 获取单个身份数据
     * @param id
     * @return
     */
    public RoleEntity get(Long id){
    	RoleEntity role = roleMapper.select(id);
        if (role == null) {
            return new RoleEntity();
        }
    	return role;
    }
    /**
	*亦可使用startIndex，endIndex限制查询结果数量
    */
//    public List<RoleEntity> findAll(Map<String, Object> conditions){
//    	return roleMapper.selectAllWithConditions(conditions);
//    }
//    public int findCount(Map<String, Object> conditions){
//    	return roleMapper.selectCountWithConditions(conditions);
//    }
//    public PageInfo<RoleEntity> findList(int pageSize, int currentPage,final Map<String, Object> conditions){
//        Integer totalCount = roleMapper.selectCountWithConditions(conditions);
//        return SmartPageInfo.createPage(new SmartPageInfo.Paging<RoleEntity>(){
//            @Override
//            public List<RoleEntity> records(int startIndex, int endIndex) {
//                conditions.putAll(UtilMisc.toMap("startIndex",startIndex, "pageSize",endIndex-startIndex));
//                return roleMapper.selectAllWithConditions(conditions);
//            }
//            
//        },totalCount, pageSize, currentPage);
//    }
//    
//    public RoleEntity getRich(Object id){
//    	List<RoleEntity> list=roleMapper.selectAllWithConditions(UtilMisc.toMap("id",id));
//    	return(list!=null&&list.size()>0)?list.get(0):null;
//    }
    /**
	*亦可使用startIndex，endIndex限制查询结果数量
    */
//    public List<RoleEntity> findRichAll(Map<String, Object> conditions){
//    	return roleMapper.selectAllWithConditions(conditions);
//    }
//    
//    public PageInfo<RoleEntity> findRichList(int pageSize, int currentPage,final Map<String, Object> conditions){
//        Integer totalCount = roleMapper.selectCountWithConditions(conditions);
//        return SmartPageInfo.createPage(new SmartPageInfo.Paging<RoleEntity>(){
//            @Override
//            public List<RoleEntity> records(int startIndex, int endIndex) {
//                conditions.putAll(UtilMisc.toMap("startIndex",startIndex, "pageSize",endIndex-startIndex));
//                return roleMapper.selectAllWithConditions(conditions);
//            }
//            
//        },totalCount, pageSize, currentPage);
//    }
}
