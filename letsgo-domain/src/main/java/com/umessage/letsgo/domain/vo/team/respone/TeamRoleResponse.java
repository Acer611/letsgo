package com.umessage.letsgo.domain.vo.team.respone;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.AnalyticalData;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

public class TeamRoleResponse extends CommonResponse{
	/**
	 * 是否为管理员【1：是；0：否】
	 */
	@ApiModelProperty(value="是否为管理员【1：是；0：否】")
	private Integer isAdmin;

	/**
	 * 身份【1：领队；2：导游；3：游客】
	 */
	@ApiModelProperty(value="身份【1：领队；2：导游；3：游客】")
	private Integer role;

	/**
	 * 问卷状态
	 */
	@ApiModelProperty(value="问卷状态，1:无问卷;2：未发放问卷；3：已发放问卷；4：已全部填写")
	private Integer isIssueSurvey;

	/**
	 * 行程Id
	 */
	@ApiModelProperty(value="行程Id")
	private Long scheduleId;

	/**
	 * 行程名称
	 */
	@ApiModelProperty(value="行程名称")
	private String name;

	/**
	 * 组团机构
	 */
	@ApiModelProperty(value="组团机构")
	private String groupClubInfo;

	/**
	 * 团队头像
	 */
	@ApiModelProperty(value="团队头像")
	private String photoUrl;

	/**
	 * 团队的管理员信息实体
	 */
	@ApiModelProperty(value = "团队的管理员信息实体")
	private AdministratorInfo administratorInfo;

	/**
	 * 团的状态：1：出行中；2：即将出行；3：已出行
	 */
	@ApiModelProperty(value="团的状态：1：出行中；2：即将出行；3：已出行")
	private Integer status;

	/**
	 * 二维码URL
	 */
	@ApiModelProperty(value="二维码URL")
	private String qrURL;

	/**
	 * 二维码类型：t：快速入团
	 */
	@ApiModelProperty(value="二维码类型：t：快速入团")
	private String qrType;

	/**
	 * 二维码参数：腾讯组ID
	 */
	@ApiModelProperty(value="二维码参数：腾讯组ID")
	private String qrParam;
	/**
	 * 微信公众号二维码带团ID参数eventKey
	 */
	@ApiModelProperty(value="微信公众号二维码带团ID参数")
	private String qrCode;

	public static TeamRoleResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends TeamRoleResponse {

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
	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getQrURL() {
		return qrURL;
	}

	public void setQrURL(String qrURL) {
		this.qrURL = qrURL;
	}

	public String getQrType() {
		return qrType;
	}

	public void setQrType(String qrType) {
		this.qrType = qrType;
	}

	public String getQrParam() {
		return qrParam;
	}

	public void setQrParam(String qrParam) {
		this.qrParam = qrParam;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getGroupClubInfo() {
		return groupClubInfo;
	}

	public void setGroupClubInfo(String groupClubInfo) {
		this.groupClubInfo = groupClubInfo;
	}

	public AdministratorInfo getAdministratorInfo() {
		return administratorInfo;
	}

	public void setAdministratorInfo(AdministratorInfo administratorInfo) {
		this.administratorInfo = administratorInfo;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getIsIssueSurvey() {
		return isIssueSurvey;
	}

	public void setIsIssueSurvey(Integer isIssueSurvey) {
		this.isIssueSurvey = isIssueSurvey;
	}

	public static TeamRoleResponse createNotFoundResponse(String retMsg){
		class NotFoundResponse extends TeamRoleResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}
		}

		TeamRoleResponse response = new NotFoundResponse();
		response.setRetMsg(retMsg);
		return response;
	}
}
