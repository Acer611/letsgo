package com.umessage.letsgo.domain.vo.team.requset;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class MemberRequest extends CommonRequest {
	/**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    private Long userId;
	/**
     *
     */
    @ApiModelProperty(value="腾迅群组ID")
    private String teamId;

	@ApiModelProperty(value="团队ID")
	private Long tId;
    /**
     * 小组ID
     */
    @ApiModelProperty(value="小组ID")
    private Long groupId;
    /**
     * 是否为管理员【1：是；0：否】
     */
    @ApiModelProperty(value="是否为管理员【1：是；0：否】")
    private Integer isAdmin;
    /**
     * 成员ID列表
     */
    @ApiModelProperty(value="成员ID列表")
    private List<Long> memberIds;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String phone;
	/**
	 * 身份【lt3：领队&导游；eq2：领队；eq3：游客】
	 */
	private String role;
	/**
	 * 排序字段：is_leader，降序desc，升序asc
	 */
	private String sort_isLeader;
	/**
	 * 排序字段：joinStatus，降序desc，升序asc
	 */
	private String sort_joinStatus;
	/**
	 * 排序字段：joinStatus，降序desc，升序asc
	 */
	private String sort_role;
	/**
	 * 排序字段：is_admin，降序desc，升序asc
	 */
	private String sort_isAdmin;

	/**
	 * 是否加入
	 */
	private Integer joinStatus;
	/**
	 * 成员类型
	 */
	private Integer type;

	// 旅行社ID
	private Long travelId;
	// 团队状态
	private Integer teamStatus;

	private Date startDate;

	private Date endDate;

	//行程详细ID
	private Long sdId;
	//成员角色
	private Integer mRole;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<Long> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSort_isLeader() {
		return sort_isLeader;
	}

	public void setSort_isLeader(String sort_isLeader) {
		this.sort_isLeader = sort_isLeader;
	}

	public String getSort_joinStatus() {
		return sort_joinStatus;
	}

	public void setSort_joinStatus(String sort_joinStatus) {
		this.sort_joinStatus = sort_joinStatus;
	}

	public String getSort_role() {
		return sort_role;
	}

	public void setSort_role(String sort_role) {
		this.sort_role = sort_role;
	}

	public Long gettId() {
		return tId;
	}

	public void settId(Long tId) {
		this.tId = tId;
	}

	public Integer getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(Integer joinStatus) {
		this.joinStatus = joinStatus;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSort_isAdmin() {
		return sort_isAdmin;
	}

	public void setSort_isAdmin(String sort_isAdmin) {
		this.sort_isAdmin = sort_isAdmin;
	}

	public Long getTravelId() {
		return travelId;
	}

	public void setTravelId(Long travelId) {
		this.travelId = travelId;
	}

	public Integer getTeamStatus() {
		return teamStatus;
	}

	public void setTeamStatus(Integer teamStatus) {
		this.teamStatus = teamStatus;
	}

	public Long getSdId() {
		return sdId;
	}

	public void setSdId(Long sdId) {
		this.sdId = sdId;
	}

	public Integer getmRole() {
		return mRole;
	}

	public void setmRole(Integer mRole) {
		this.mRole = mRole;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
