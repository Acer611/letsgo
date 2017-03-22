package com.umessage.letsgo.service.api.system;

import com.umessage.letsgo.domain.po.system.DataItemEntity;

import java.util.List;
import java.util.Map;

public interface IDataItemService {

	List<DataItemEntity> LoadAll();
	
	Map<String, String> LoadAllToMap();
}
