package com.umessage.letsgo.service.api.activity;

import com.umessage.letsgo.domain.po.activity.CallHistoryEntity;

public interface ICallHistoryService {
	int add(CallHistoryEntity callHistoryEntity);
	
}
