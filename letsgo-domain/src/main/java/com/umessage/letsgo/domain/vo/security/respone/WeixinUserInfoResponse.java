package com.umessage.letsgo.domain.vo.security.respone;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * 微信用户个人信息
 */
public class WeixinUserInfoResponse extends CommonResponse {

	@ApiModelProperty(value="微信唯一标识unionid")
	private String unionid;

	@ApiModelProperty(value="微信用户openid")
	private String openid;

	@ApiModelProperty(value="微信应用的ID")
	private String appId;

	@ApiModelProperty(value="昵称nickname")
	private String nickname;

	@ApiModelProperty(value="性别sex普通用户性别，1为男性，2为女性")
	private int sex;

	@ApiModelProperty(value="省份")
	private String province;

	@ApiModelProperty(value="国家，如中国为CN")
	private String country;

	@ApiModelProperty(value="城市")
	private String city;

	@ApiModelProperty(value="头像URL")
	private String headimgurl;

	@ApiModelProperty(value="用户特权信息，json数组，如微信沃卡用户为（chinaunicom）")
	private String privilege;

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public static WeixinUserInfoResponse createNotFoundResponse(){
		class NotFoundResponse extends WeixinUserInfoResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}
			
			@Override
			public String getRetMsg() {
				return "未找到对应的用户";
			}
		}
		
		return new NotFoundResponse();
	}
	
	public static WeixinUserInfoResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends WeixinUserInfoResponse {

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
