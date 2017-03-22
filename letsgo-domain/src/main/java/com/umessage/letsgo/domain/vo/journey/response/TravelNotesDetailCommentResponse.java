package com.umessage.letsgo.domain.vo.journey.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.TravelNotesDetailVo;

public class TravelNotesDetailCommentResponse  extends CommonResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1721524959240834981L;
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
	
    private List<TravelNotesDetailVo> detailVos;

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

	public List<TravelNotesDetailVo> getDetailVos() {
		return detailVos;
	}

	public void setDetailVos(List<TravelNotesDetailVo> detailVos) {
		this.detailVos = detailVos;
	}
    
}
