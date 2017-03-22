package com.umessage.letsgo.service.impl.system;

import com.umessage.letsgo.dao.system.DataItemDao;
import com.umessage.letsgo.domain.po.system.DataItemEntity;
import com.umessage.letsgo.service.api.system.IDataItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class DataItemServiceImpl implements IDataItemService {
	@Resource
	private DataItemDao dataItemMapper;

	@Override
	public List<DataItemEntity> LoadAll() {
		List<DataItemEntity> dataItemEntities = new ArrayList<DataItemEntity>();
		dataItemEntities = dataItemMapper.selectAll();
		return dataItemEntities;
	}

	@Override
	public Map<String, String> LoadAllToMap() {
		Map<String, String> dataItemMap = new HashMap<String, String>();
		List<DataItemEntity> dataItems = this.LoadAll();
		for (Iterator iterator = dataItems.iterator(); iterator.hasNext();) {
			DataItemEntity dataItem = (DataItemEntity) iterator.next();
			// key格式 catalogCode + "_" + itemCode + "_" + value
			String key = dataItem.getCatalogCode() + "_" + dataItem.getItemCode() + "_" + dataItem.getItemValue();
			String value = dataItem.getItemName();
			dataItemMap.put(key, value);
		}
		return dataItemMap;
	}

}
