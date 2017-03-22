package com.umessage.letsgo.domain.vo.team.requset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class EventRecordRequest extends CommonRequest {
	/**
	 * ID
	 */
	@ApiModelProperty(value="ID")
	@JsonIgnore
	private Long id;

	/**
	 * 腾迅groupid
	 */
	@ApiModelProperty(value="腾迅groupid")
	private String teamId;

	/**
	 * 领队id
	 */
	@ApiModelProperty(value="领队id")
	@JsonIgnore
	private Long leaderId;

	/**
	 * 语音地址
	 */
	@ApiModelProperty(value="语音地址")
	private String voiceUrl;

	/**
	 * 语音时长
	 */
	@ApiModelProperty(value="语音时长")
	private String videoLen;

	/**
	 * 文本内容
	 */
	@ApiModelProperty(value="文本内容：文本和语音只能选择一个")
	private String content;

	/**
	 * 记录时间
	 */
	@ApiModelProperty(value="记录时间")
	private String recordTime;

	/**
	 * 事件经度
	 */
	@ApiModelProperty(value="事件经度")
	@JsonIgnore
	private String lon;

	/**
	 * 事件纬度
	 */
	@ApiModelProperty(value="事件纬度")
	@JsonIgnore
	private String lat;

	/**
	 * 事件位置
	 */
	@ApiModelProperty(value="事件位置")
	private String location;

	/**
	 * 图片地址
	 */
	@ApiModelProperty(value="图片地址:多张图分号隔开")
	private String photosUrl;

	/**
	 * 缩略图地址
	 */
	@ApiModelProperty(value="缩略图地址")
	@JsonIgnore
	private String thumbnailUrl;

	/**
	 * 创建人(用户id)
	 */
	@ApiModelProperty(value="创建人(用户id)")
	@JsonIgnore
	private Long createBy;
	/**
	 * 版本号
	 */
	@JsonIgnore
	private Long version;

	/**
	 * 创建时间
	 */
	@JsonIgnore
	private Date createTime;
	/**
	 * 更新时间
	 */
	@JsonIgnore
	private Date updateTime;

	@ApiModelProperty(value="当前页数")
	@JsonIgnore
	private int pageNum = 1;
	@ApiModelProperty(value="每页条数")
	@JsonIgnore
	private int pageSize = 10;
	/**
	 * 行程ID列表
	 */
	@JsonIgnore
	private List<Long> userIDs;
	/**
	 * 旅行社ID
	 */
	@ApiModelProperty(value="旅行社ID")
	@JsonIgnore
	private Long travelId;

	@ApiModelProperty(value="类型1旅行社端行程  2 公司后台行程")
	@JsonIgnore
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public String getVideoLen() {
		return videoLen;
	}

	public void setVideoLen(String videoLen) {
		this.videoLen = videoLen;
	}

	public Long getTravelId() {
		return travelId;
	}

	public List<Long> getUserIDs() {
		return userIDs;
	}

	public void setUserIDs(List<Long> userIDs) {
		this.userIDs = userIDs;
	}

	public void setTravelId(Long travelId) {
		this.travelId = travelId;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public Long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

	public String getVoiceUrl() {
		return voiceUrl;
	}

	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhotosUrl() {
		return photosUrl;
	}

	public void setPhotosUrl(String photosUrl) {
		this.photosUrl = photosUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
