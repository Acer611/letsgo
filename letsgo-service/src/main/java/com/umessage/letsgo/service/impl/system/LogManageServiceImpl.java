package com.umessage.letsgo.service.impl.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.umessage.letsgo.dao.system.LogManageDao;
import com.umessage.letsgo.dao.team.LeaderDao;
import com.umessage.letsgo.domain.po.system.LogManage;
import com.umessage.letsgo.domain.vo.system.request.LogManageRequest;
import com.umessage.letsgo.domain.vo.system.respone.LogManageResponse;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.ILogManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pw on 2016/9/8.
 */
@Service
public class LogManageServiceImpl implements ILogManageService {
	@Resource
	private LogManageDao logManageDao;
	@Resource
	private LeaderDao leaderDao;
	@Resource
	private IUserService userService;

	@Override
	public int add(LogManage logManage) {
		logManage.setCreateTime(new Date());
		logManage.setVersion(0l);
		return logManageDao.insert(logManage);
	}

	//获取日志列表
	public LogManageResponse getLogList(LogManageRequest request){
		List<Long> userIds = new ArrayList<Long>();
		//角色：1领队；2导游；3游客；4管理员；5旅行社；6计调；7销售（门店）
		//账号类型:0旅行社管理员，1门店销售（门店），2计调，3领队，
	    if(request.getAccountType()!=null){
			Integer accountType=request.getAccountType();
		if(request.getAccountType()==0){
			//获取该旅行社下 5旅行社管理员用户userID集合
			request.setAccountType(5);
			userIds=userService.getUserIdsByTravelerId(request);
		}else if(request.getAccountType()==3){
			//获取该旅行社下 根据旅行社ID获取领队用户userID集合
			userIds =leaderDao.getLeaderIds(request.getTravelId());
		}else if(request.getAccountType()==1){
			//获取该旅行社下 7旅行社门店(销售)用户userID集合
			request.setAccountType(7);
			userIds=userService.getUserIdsByTravelerId(request);
		}else if(request.getAccountType()==2){
			//获取该旅行社下 6旅行社计调用户userID集合
			request.setAccountType(6);
			userIds=userService.getUserIdsByTravelerId(request);
		}
			request.setAccountType(accountType);
		}else{
			request.setAccountType(null);
			userIds=userService.getUserIdsByTravelerId(request);
		}
		request.setUserIds(userIds);
		//关联查分页
		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		Page<LogManage> LogManage =logManageDao.getLogList(request);
		LogManageResponse response =new LogManageResponse();
		response.setLogManageList(LogManage);
		response.setPages(LogManage.getPages());
		response.setTotals(LogManage.getTotal());
		return response;
	}
}
