package com.umessage.letsgo.domain.vo.journey.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.umessage.letsgo.domain.po.journey.ScoreEntity;

public class ScheduleDetailCommentRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7953921194307002704L;
	@ApiModelProperty(value="用户id")
	private long userId;//用户id	
	@ApiModelProperty(value="行程明细id")
	private long scheduleDetailId;//行程明细id	
	@ApiModelProperty(value="满意度标识	5为非常满意 4为满意 3为一般 2为不满意 1为非常不满意")
	private Integer satisfiedStatus;//满意度标识	5为非常满意 4为满意 3为一般 2为不满意 1为非常不满意
	@ApiModelProperty(value="不满意项集合")
	private List<ScoreEntity> scoreEntities = new ArrayList<ScoreEntity>();
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getScheduleDetailId() {
		return scheduleDetailId;
	}

	public void setScheduleDetailId(long scheduleDetailId) {
		this.scheduleDetailId = scheduleDetailId;
	}

	public Integer getSatisfiedStatus() {
		return satisfiedStatus;
	}

	public void setSatisfiedStatus(Integer satisfiedStatus) {
		this.satisfiedStatus = satisfiedStatus;
	}

	public List<ScoreEntity> getScoreEntities() {
		return scoreEntities;
	}

	public void setScoreEntities(List<ScoreEntity> scoreEntities) {
		this.scoreEntities = scoreEntities;
	}


}
