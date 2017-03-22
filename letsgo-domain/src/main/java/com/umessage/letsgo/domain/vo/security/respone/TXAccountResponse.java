package com.umessage.letsgo.domain.vo.security.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

public class TXAccountResponse extends CommonResponse {
	/**
	 * 应用ID：sdkAppId，腾讯云通讯中的应用ID
	 */
	@ApiModelProperty(value="应用ID：sdkAppId")
	private int sdkAppId;
	/**
     * 用户名：identifier，既可以作为腾讯云的用户名，也可以作为小米推送的别名
     */
    @ApiModelProperty(value="用户名：identifier，既可以作为腾讯云的用户名，也可以作为小米推送的别名")
    private String identifier;
    /**
     * 帐号类型：accountType，腾讯云通讯自动分配的
     */
    @ApiModelProperty(value="帐号类型：accountType")
    private int accountType;
	/**
	 * 使用自有帐号或腾讯开放帐号时，填写为与sdkAppId相同的字符串：appIdAt3rd
	 */
	@ApiModelProperty(value="使用自有帐号或腾讯开放帐号时，填写为与sdkAppId相同的字符串：appIdAt3rd")
	private int appIdAt3rd;
    /**
     * 腾讯云通信（IM）签名：userSig
     */
    @ApiModelProperty(value="腾讯云通信（IM）签名：userSig")
    private String usersig;
	/**
	 * 腾讯云通信签名（IM）的有效时间：expireTime，单位为秒
	 */
	@ApiModelProperty(value="腾讯云通信签名（IM）的有效时间：expireTime")
	private int expireTime;

	/**
	 * 腾讯云存储签名，用于上传语音，图片等
	 */
	@ApiModelProperty(value="腾讯云存储签名，用于上传语音，图片等")
	private String cloudSign;

	/**
	 * H5行程链接
	 */
	@ApiModelProperty(value="H5行程链接")
	private String h5BaseLink;

	/**
	 * API链接
	 */
	@ApiModelProperty(value="API链接")
	private String apiBaseLink;

	/**
	 * 登录访问链接
	 */
	@ApiModelProperty(value="登录访问链接")
	private String loginBaseLink;

	public int getSdkAppId() {
		return sdkAppId;
	}

	public void setSdkAppId(int sdkAppId) {
		this.sdkAppId = sdkAppId;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public int getAppIdAt3rd() {
		return appIdAt3rd;
	}

	public void setAppIdAt3rd(int appIdAt3rd) {
		this.appIdAt3rd = appIdAt3rd;
	}

	public String getUsersig() {
		return usersig;
	}

	public void setUsersig(String usersig) {
		this.usersig = usersig;
	}

	public int getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}

	public String getCloudSign() {
		return cloudSign;
	}

	public void setCloudSign(String cloudSign) {
		this.cloudSign = cloudSign;
	}

	public String getH5BaseLink() {
		return h5BaseLink;
	}

	public void setH5BaseLink(String h5BaseLink) {
		this.h5BaseLink = h5BaseLink;
	}

	public String getApiBaseLink() {
		return apiBaseLink;
	}

	public void setApiBaseLink(String apiBaseLink) {
		this.apiBaseLink = apiBaseLink;
	}

	public String getLoginBaseLink() {
		return loginBaseLink;
	}

	public void setLoginBaseLink(String loginBaseLink) {
		this.loginBaseLink = loginBaseLink;
	}

	@Override
	public String toString() {
		return "TXAccountResponse{" +
				"sdkAppId=" + sdkAppId +
				", identifier='" + identifier + '\'' +
				", accountType=" + accountType +
				", appIdAt3rd=" + appIdAt3rd +
				", usersig='" + usersig + '\'' +
				", expireTime=" + expireTime +
				", cloudSign='" + cloudSign + '\'' +
				", h5BaseLink='" + h5BaseLink + '\'' +
				", apiBaseLink='" + apiBaseLink + '\'' +
				", loginBaseLink='" + loginBaseLink + '\'' +
				'}';
	}

	public static TXAccountResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends TXAccountResponse {

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
