package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.security.RoleResourceDao;
import com.umessage.letsgo.service.api.security.IRoleResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleResourceServiceImpl implements IRoleResourceService {
    @Resource 
    private RoleResourceDao roleResourceMapper;
    
//    public int create(RoleResourceEntity roleResource){
//        roleResource.setCreateTime(new java.util.Date());
//        roleResource.setVersion(0l);
//    	return roleResourceMapper.insert(roleResource);
//    }
    
//    public int deleteByPk(Object pk){
//    	return roleResourceMapper.delete(UtilMisc.toMap("id",pk));
//    }
    
//    public int delete(RoleResourceEntity t){
//    	return roleResourceMapper.delete(t.getId());
//    }
    
//    public int delete(Map<String, Object> conditions){
//    	return roleResourceMapper.delete(conditions);
//    }

//    public int update(RoleResourceEntity roleResource){
//    	roleResource.setUpdateTime(new java.util.Date());
//    	Map<String,Object> args=JsonUtils.pojo2map(roleResource);
//    	Object id=roleResource.getId();
//    	args.put("_id",id);
//    	//开启乐观锁
//    	Long version=roleResource.getVersion();
//    	args.put("version",version+1);
//    	args.put("_version",version);
//    	return roleResourceMapper.updateWithConditions(args);
//    }
    /**
    *条件前加“_” 设置值不用变
    */
//    public int updateAll(Map<String, Object> conditions){
//    	return roleResourceMapper.updateWithConditions(conditions);
//    }
//    
//    public RoleResourceEntity get(Object id){
//    	RoleResourceEntity roleResource = roleResourceMapper.selectWithConditions(UtilMisc.toMap("id",id));
//    	return(roleResource != null) ? roleResource : null;
//    }
    
    /**
	*亦可使用startIndex，endIndex限制查询结果数量
    */
//    public List<RoleResourceEntity> findAll(Map<String, Object> conditions){
//    	return roleResourceMapper.selectAllWithConditions(conditions);
//    }
//    public int findCount(Map<String, Object> conditions){
//    	return roleResourceMapper.selectCountWithConditions(conditions);
//    }
//    
//    public PageInfo<RoleResourceEntity> findList(int pageSize, int currentPage,final Map<String, Object> conditions){
//        Integer totalCount = roleResourceMapper.selectCountWithConditions(conditions);
//        return SmartPageInfo.createPage(new SmartPageInfo.Paging<RoleResourceEntity>(){
//            @Override
//            public List<RoleResourceEntity> records(int startIndex, int endIndex) {
//                conditions.putAll(UtilMisc.toMap("startIndex",startIndex, "pageSize",endIndex-startIndex));
//                return roleResourceMapper.selectAllWithConditions(conditions);
//            }
//            
//        },totalCount, pageSize, currentPage);
//    }
//    
//    public RoleResourceEntity getRich(Object id){
//    	List<RoleResourceEntity> list=roleResourceMapper.selectAllWithConditions(UtilMisc.toMap("id",id));
//    	return(list!=null&&list.size()>0)?list.get(0):null;
//    }
    
    /**
	*亦可使用startIndex，endIndex限制查询结果数量
    */
//    public List<RoleResourceEntity> findRichAll(Map<String, Object> conditions){
//    	return roleResourceMapper.selectAllWithConditions(conditions);
//    }
//    
//    public PageInfo<RoleResourceEntity> findRichList(int pageSize, int currentPage,final Map<String, Object> conditions){
//        Integer totalCount = roleResourceMapper.selectCountWithConditions(conditions);
//        return SmartPageInfo.createPage(new SmartPageInfo.Paging<RoleResourceEntity>(){
//            @Override
//            public List<RoleResourceEntity> records(int startIndex, int endIndex) {
//                conditions.putAll(UtilMisc.toMap("startIndex",startIndex, "pageSize",endIndex-startIndex));
//                return roleResourceMapper.selectAllWithConditions(conditions);
//            }
//            
//        },totalCount, pageSize, currentPage);
//    }

//	@Override
//	public List<RoleResourceUnion> findRoleResoureUnionList(String moudle) {
//		return null;
//		return roleResourceMapper.findRoleResoureUnionList(moudle);
//	}
}
