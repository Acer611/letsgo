package com.umessage.letsgo.domain.vo.security.respone;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.security.TagsEntity;
import com.umessage.letsgo.domain.po.team.EventRecordEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TagsResponse extends CommonResponse {
	/**
	 * 成员对象集合
	 */
	@ApiModelProperty(value = "成员对象集合")
	private List<TagsEntity> tags;

	public List<TagsEntity> getTags() {
		return tags;
	}

	public void setTags(List<TagsEntity> tags) {
		this.tags = tags;
	}

	public static TagsResponse createNotFoundResponse(){
		class NotFoundResponse extends TagsResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}

			@Override
			public String getRetMsg() {
				return "未找到对应的成员";
			}
		}

		return new NotFoundResponse();
	}

	public static TagsResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends TagsResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.USER_NOT_LOGIN;
			}

			@Override
			public String getRetMsg() {
				return "用户未登录或登录信息过期";
			}
		}

		return new UserNotLoginResponse();
	}

}
