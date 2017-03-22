package com.umessage.letsgo.domain.vo.journey.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.TravelNotesVo;

public class TravelsListResponse extends CommonResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303974590699053864L;

	/**
     * 行程id
     */
    @ApiModelProperty(value="行程id")
	private Long scheduleId;
	
	/**
     * 游记id
     */
    @ApiModelProperty(value="游记id")
    private Long travelNotesId;
	
    /**
     * 行程图片
     */
    @ApiModelProperty(value="行程图片")
    private String scheduleImgurl;
    
    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;
	
	private List<TravelNotesVo> travelList;// 游记情况列表   

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Long getTravelNotesId() {
		return travelNotesId;
	}

	public void setTravelNotesId(Long travelNotesId) {
		this.travelNotesId = travelNotesId;
	}

	public List<TravelNotesVo> getTravelList() {
		return travelList;
	}

	public void setTravelList(List<TravelNotesVo> travelList) {
		this.travelList = travelList;
	}

	public String getScheduleImgurl() {
		return scheduleImgurl;
	}

	public void setScheduleImgurl(String scheduleImgurl) {
		this.scheduleImgurl = scheduleImgurl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
