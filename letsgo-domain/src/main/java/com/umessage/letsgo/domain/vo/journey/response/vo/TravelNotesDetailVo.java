package com.umessage.letsgo.domain.vo.journey.response.vo;

import java.util.List;

import com.umessage.letsgo.domain.po.journey.TravelNotesDetailCommentEntity;
import com.umessage.letsgo.domain.po.journey.TravelNotesDetailEntity;

public class TravelNotesDetailVo extends TravelNotesDetailEntity{
	private String scheduleDate;
	
	private int dayNum;
	
	private List<TravelNotesDetailCommentEntity> commentEntities;

	public List<TravelNotesDetailCommentEntity> getCommentEntities() {
		return commentEntities;
	}

	public void setCommentEntities(
			List<TravelNotesDetailCommentEntity> commentEntities) {
		this.commentEntities = commentEntities;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public int getDayNum() {
		return dayNum;
	}

	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}
	
	
}
