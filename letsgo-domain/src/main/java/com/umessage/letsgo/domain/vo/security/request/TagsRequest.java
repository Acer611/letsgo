package com.umessage.letsgo.domain.vo.security.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class TagsRequest extends CommonRequest {
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value="用户ID")
	private Long userId;

	/**
	 * 标签名称；多个以分号隔开
	 */
	@ApiModelProperty(value="标签名称；多个以分号隔开")
	private String name;

	/**
	 * 好友id
	 */
	@ApiModelProperty(value="好友id")
	private Long labeledUserId;

	/**
	 * 标签ID
	 */
	@ApiModelProperty(value="标签ID")
	private Long tagId;

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLabeledUserId() {
		return labeledUserId;
	}

	public void setLabeledUserId(Long labeledUserId) {
		this.labeledUserId = labeledUserId;
	}
}
