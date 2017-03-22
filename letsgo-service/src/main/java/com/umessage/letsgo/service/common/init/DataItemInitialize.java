package com.umessage.letsgo.service.common.init;

import com.umessage.letsgo.core.init.constant.InitConstant;
import com.umessage.letsgo.service.api.system.IDataItemService;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;

public class DataItemInitialize implements InitializingBean {

	@Resource
	private IDataItemService dataItemServiceImpl;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// 加载数据字典到内存
		InitConstant.DATA_ITEM_MAP = dataItemServiceImpl.LoadAllToMap();
	}

}
