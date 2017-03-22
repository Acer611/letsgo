package com.umessage.letsgo.domain.vo.security.respone;

import java.util.List;

import com.umessage.letsgo.domain.po.security.TagsResultEntity;
import com.umessage.letsgo.domain.po.security.UserTagEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

public class TagListResponse extends CommonResponse{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -2100779318813151252L;

	private List<TagsResultEntity> tagResult;

	public List<TagsResultEntity> getTagResult() {
		return tagResult;
	}

	public void setTagResult(List<TagsResultEntity> tagResult) {
		this.tagResult = tagResult;
	}
}
