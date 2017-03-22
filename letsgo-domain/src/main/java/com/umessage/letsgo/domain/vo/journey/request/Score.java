package com.umessage.letsgo.domain.vo.journey.request;

import java.io.Serializable;

public class Score implements Serializable {
	
	private static final long serialVersionUID = -4777064564463410633L;
	
	private String scoreOption;//评分项 
	private Integer score;//评分   
	public String getScoreOption() {
		return scoreOption;
	}
	public void setScoreOption(String scoreOption) {
		this.scoreOption = scoreOption;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
}
