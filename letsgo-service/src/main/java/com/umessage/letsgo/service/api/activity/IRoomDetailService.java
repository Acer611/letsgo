package com.umessage.letsgo.service.api.activity;

import com.umessage.letsgo.domain.po.activity.RoomDetailEntity;

public interface IRoomDetailService {
	// 添加房间明细
	int addRoomDetail(RoomDetailEntity roomDetailEntity);
	
	// 删除房间明细
	int deleteRoomDetail(RoomDetailEntity roomDetailEntity);
	
	// 更新房间明细
	int updateRoomDetail(RoomDetailEntity roomDetailEntity);

	RoomDetailEntity getRoomDetail(Long MemberId);
	
}
