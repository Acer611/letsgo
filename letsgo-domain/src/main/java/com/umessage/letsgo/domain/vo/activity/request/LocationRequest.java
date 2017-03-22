package com.umessage.letsgo.domain.vo.activity.request;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by ZhaoYidong on 2016/5/18.
 */
public class LocationRequest extends CommonRequest {

    /**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID")
    private String teamId;

    /**
     * 是否为管理员【1：是；0：否】
     */
    @ApiModelProperty(value="是否为管理员【1：是；0：否】")
    private Integer isAdmin;

    /**
     * 身份【lt3：领队&导游；eq2：领队；eq3：游客】
     */
    @ApiModelProperty(value="身份【lt3：领队&导游；eq1：领队；eq3：游客】")
    private String role;

    /**
     * 排序字段：is_leader，降序desc，升序asc
     */
    @ApiModelProperty("排序字段：role，降序desc，升序asc")
    private String sort_role;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSort_role() {
        return sort_role;
    }

    public void setSort_role(String sort_role) {
        this.sort_role = sort_role;
    }
}
