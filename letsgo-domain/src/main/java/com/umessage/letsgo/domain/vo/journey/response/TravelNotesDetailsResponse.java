package com.umessage.letsgo.domain.vo.journey.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.ScenicTravelVo;

public class TravelNotesDetailsResponse extends CommonResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3493455459290814504L;
	
	/**
     * 酒店id
     */
    @ApiModelProperty(value="酒店id")
	private String hotelId;
    
    /**
     * 酒店名称
     */
    @ApiModelProperty(value="酒店名称")
	private String hotelName;
    
    /**
     * 酒店等级
     */
    @ApiModelProperty(value="酒店等级")
	private String hotelLv;
	
    /**
     * 行程路线图片地址
     */
    @ApiModelProperty(value="行程路线图片地址")
	private String scheduleDetaiImgurl;
	
	private List<ScenicTravelVo> travelList;

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelLv() {
		return hotelLv;
	}

	public void setHotelLv(String hotelLv) {
		this.hotelLv = hotelLv;
	}

	public List<ScenicTravelVo> getTravelList() {
		return travelList;
	}

	public void setTravelList(List<ScenicTravelVo> travelList) {
		this.travelList = travelList;
	}

	public String getScheduleDetaiImgurl() {
		return scheduleDetaiImgurl;
	}

	public void setScheduleDetaiImgurl(String scheduleDetaiImgurl) {
		this.scheduleDetaiImgurl = scheduleDetaiImgurl;
	}

}
