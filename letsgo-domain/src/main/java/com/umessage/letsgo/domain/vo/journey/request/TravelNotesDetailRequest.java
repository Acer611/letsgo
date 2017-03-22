package com.umessage.letsgo.domain.vo.journey.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import com.umessage.letsgo.domain.po.journey.TravelNotesDetailCommentEntity;

public class TravelNotesDetailRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1226679187038442637L;

	/**
     * 行程明细id
     */
    @ApiModelProperty(value="行程明细id")
    private Long scheduleDetaiIid;
    /**
     * 游记id
     */
    @ApiModelProperty(value="游记id")
    private Long travelNotesId;
    /**
     * 每日行程图片
     */
    @ApiModelProperty(value="每日行程图片")
    private String scheduleDetaiImgurl;
    
	private List<TravelNotesDetailCommentEntity> commentEntities;

	public Long getScheduleDetaiIid() {
		return scheduleDetaiIid;
	}

	public void setScheduleDetaiIid(Long scheduleDetaiIid) {
		this.scheduleDetaiIid = scheduleDetaiIid;
	}

	public Long getTravelNotesId() {
		return travelNotesId;
	}

	public void setTravelNotesId(Long travelNotesId) {
		this.travelNotesId = travelNotesId;
	}

	public String getScheduleDetaiImgurl() {
		return scheduleDetaiImgurl;
	}

	public void setScheduleDetaiImgurl(String scheduleDetaiImgurl) {
		this.scheduleDetaiImgurl = scheduleDetaiImgurl;
	}

	public List<TravelNotesDetailCommentEntity> getCommentEntities() {
		return commentEntities;
	}

	public void setCommentEntities(
			List<TravelNotesDetailCommentEntity> commentEntities) {
		this.commentEntities = commentEntities;
	}
}
