package com.umessage.letsgo.service.impl.activity;

import com.umessage.letsgo.dao.activity.CallDetailHistoryDao;
import com.umessage.letsgo.domain.po.activity.CallDetailHistoryEntity;
import com.umessage.letsgo.service.api.activity.ICallDetailHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CallDetailHistoryServiceImpl implements ICallDetailHistoryService {
	@Resource
	public CallDetailHistoryDao callDetailHistoryMapper;

	@Override
	public int add(CallDetailHistoryEntity callDetailHistoryEntity) {
		callDetailHistoryEntity.setCreateTime(new Date());
		callDetailHistoryEntity.setVersion(0l);
		return callDetailHistoryMapper.insert(callDetailHistoryEntity);
	}

}
