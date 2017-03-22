package com.umessage.letsgo.service.api.activity;

import com.umessage.letsgo.domain.po.activity.RoomEntity;
import com.umessage.letsgo.domain.vo.activity.request.RoomRequest;
import com.umessage.letsgo.domain.vo.activity.response.RoomResponse;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

/**
 * 分房Service接口
 * @author Administrator
 *
 */
public interface IRoomService {
	// 添加房间
	int addRoom(RoomEntity roomEntity);
	
	// 删除房间
	int deleteRoom(RoomEntity roomEntity);
	
	// 更新房间
	int updateRoom(RoomEntity roomEntity);

	//根据id查看房间
	RoomEntity selectRoom(Long id);
	
	// 新建分房方案
	CommonResponse createRoom(List<RoomRequest> roomRequest);
	
	// 团队分房情况
	RoomResponse getTeamRoomDetailList(String teamId, Long userId);

	// 个人分房情况
	RoomResponse getPersonalRoomDetailList(String teamId,Long userId);

	//根据成员ID查询对应的房间
	RoomEntity selectByMemberId(Long memberId);
}
