package com.umessage.letsgo.domain.vo.activity.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.vo.activity.request.vo.RoomDetailVo;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class RoomRequest extends CommonRequest {
    /**
     * 团队ID
     */
	@ApiModelProperty(value = "团队id")
    private String teamId;

    /**
     * 房间号
     */
	@ApiModelProperty(value = "房间号")
    private String roomNum;

	/**
	 * 房间详细集合（请求保存房间详细的必须字段）
	 */
	@ApiModelProperty(value = "房间详细vo集合（请求保存房间详细的必须字段）")
	private List<RoomDetailVo> roomDetailVos;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public List<RoomDetailVo> getRoomDetailVos() {
		return roomDetailVos;
	}

	public void setRoomDetailVos(List<RoomDetailVo> roomDetailVos) {
		this.roomDetailVos = roomDetailVos;
	}

	@Override
	public String toString() {
		return "RoomRequest{" +
				", teamId=" + teamId +
				", roomNum='" + roomNum + '\'' +
				", roomDetailVos=" + roomDetailVos +
				'}';
	}
}
