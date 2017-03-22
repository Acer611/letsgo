package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.security.ResourceDao;
import com.umessage.letsgo.service.api.security.IResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class ResourceServiceImpl implements IResourceService {
    @Resource 
    private ResourceDao resourceMapper;
    
//    public int create(ResourceEntity resource){
//        resource.setCreateTime(new java.util.Date());
//        resource.setVersion(0l);
//    	return resourceMapper.insert(resource);
//    }
    
//    public int deleteByPk(Object pk){
//    	return resourceMapper.delete((Long) pk);
//    }
    
//    public int delete(ResourceEntity t){
//    	return resourceMapper.delete(t.getId());
//    }
    
//    public int delete(Map<String, Object> conditions){
//    	return resourceMapper.delete(conditions);
//    }

//    public int update(ResourceEntity resource){
//    	resource.setUpdateTime(new java.util.Date());
//    	Map<String,Object> args=JsonUtils.pojo2map(resource);
//    	Object id=resource.getId();
//    	args.put("_id",id);
//    	//开启乐观锁
//    	Long version=resource.getVersion();
//    	args.put("version",version+1);
//    	args.put("_version",version);
//    	return resourceMapper.updateWithConditions(args);
//    }
    
    /**
    *条件前加“_” 设置值不用变
    */
//    public int updateAll(Map<String, Object> conditions){
//    	return resourceMapper.updateWithConditions(conditions);
//    }
    
//    public ResourceEntity get(Object id){
//    	ResourceEntity resource = resourceMapper.selectWithConditions(UtilMisc.toMap("id",id));
//    	return(resource != null) ? resource : null;
//    }
    
    /**
	*亦可使用startIndex，endIndex限制查询结果数量
    */
//    public List<ResourceEntity> findAll(Map<String, Object> conditions){
//    	return resourceMapper.selectAllWithConditions(conditions);
//    }
    
//    public int findCount(Map<String, Object> conditions){
//    	return resourceMapper.selectCountWithConditions(conditions);
//    }
//    
//    public PageInfo<ResourceEntity> findList(int pageSize, int currentPage,final Map<String, Object> conditions){
//        Integer totalCount = resourceMapper.selectCountWithConditions(conditions);
//        return SmartPageInfo.createPage(new SmartPageInfo.Paging<ResourceEntity>(){
//            @Override
//            public List<ResourceEntity> records(int startIndex, int endIndex) {
//                conditions.putAll(UtilMisc.toMap("startIndex",startIndex, "pageSize",endIndex-startIndex));
//                return resourceMapper.selectAllWithConditions(conditions);
//            }
//            
//        },totalCount, pageSize, currentPage);
//    }
//    
//    public ResourceEntity getRich(Object id){
//    	List<ResourceEntity> list=resourceMapper.selectAllWithConditions(UtilMisc.toMap("id",id));
//    	return(list!=null&&list.size()>0)?list.get(0):null;
//    }
    
    /**
	*亦可使用startIndex，endIndex限制查询结果数量
    */
//    public List<ResourceEntity> findRichAll(Map<String, Object> conditions){
//    	return resourceMapper.selectAllWithConditions(conditions);
//    }
//    
//    public PageInfo<ResourceEntity> findRichList(int pageSize, int currentPage,final Map<String, Object> conditions){
//        Integer totalCount = resourceMapper.selectCountWithConditions(conditions);
//        return SmartPageInfo.createPage(new SmartPageInfo.Paging<ResourceEntity>(){
//            @Override
//            public List<ResourceEntity> records(int startIndex, int endIndex) {
//                conditions.putAll(UtilMisc.toMap("startIndex",startIndex, "pageSize",endIndex-startIndex));
//                return resourceMapper.selectAllWithConditions(conditions);
//            }
//            
//        },totalCount, pageSize, currentPage);
//    }
}
