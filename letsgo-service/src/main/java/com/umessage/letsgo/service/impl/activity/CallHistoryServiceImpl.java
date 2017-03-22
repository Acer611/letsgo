package com.umessage.letsgo.service.impl.activity;

import com.umessage.letsgo.dao.activity.CallHistoryDao;
import com.umessage.letsgo.domain.po.activity.CallHistoryEntity;
import com.umessage.letsgo.service.api.activity.ICallHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CallHistoryServiceImpl implements ICallHistoryService {
	@Resource
	private CallHistoryDao callHistoryMapper;

	@Override
	public int add(CallHistoryEntity callHistoryEntity) {
		callHistoryEntity.setCreateTime(new Date());
		callHistoryEntity.setVersion(0l);
		return callHistoryMapper.insert(callHistoryEntity);
	}

}
