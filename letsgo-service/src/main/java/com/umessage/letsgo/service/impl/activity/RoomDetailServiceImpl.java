package com.umessage.letsgo.service.impl.activity;

import com.umessage.letsgo.dao.activity.RoomDetailDao;
import com.umessage.letsgo.domain.po.activity.RoomDetailEntity;
import com.umessage.letsgo.service.api.activity.IRoomDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class RoomDetailServiceImpl implements IRoomDetailService {
	@Resource
	private RoomDetailDao roomDetailMapper;

	@Override
	public int addRoomDetail(RoomDetailEntity roomDetailEntity) {
		roomDetailEntity.setCreateTime(new Date());
		roomDetailEntity.setVersion(0l);
		return roomDetailMapper.insert(roomDetailEntity);
	}

	@Override
	public int deleteRoomDetail(RoomDetailEntity roomDetailEntity) {
		return roomDetailMapper.delete(roomDetailEntity.getId());
	}

	@Override
	public int updateRoomDetail(RoomDetailEntity roomDetailEntity) {
		roomDetailEntity.setUpdateTime(new Date());
		return roomDetailMapper.update(roomDetailEntity);
	}

	@Override
	public RoomDetailEntity getRoomDetail(Long memberId) {
		RoomDetailEntity roomDetailEntity = roomDetailMapper.selectWithMenberId(memberId);
		if (roomDetailEntity == null) {
			return new RoomDetailEntity();
		}
		return roomDetailEntity;
	}

}
