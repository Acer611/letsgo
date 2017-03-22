package com.umessage.letsgo.domain.vo.team.requset;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/12.
 */
public class WebMemberRequest implements Serializable {


    @ApiModelProperty(value = "成员id")
    private Long id;

    @ApiModelProperty(value = "团队id")
    private Long tId;

    @ApiModelProperty(value = "团队id")
    private String realName;

    @ApiModelProperty(value = "sex")
    private Integer sex;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "当前第几页")
    private int pageNums=1;

    @ApiModelProperty(value = "每页条数")
    private int pageSizes=10;

    @ApiModelProperty(value="旅行社id")
    private Long travelId;

    @ApiModelProperty(value="角色")
    private Integer role;
    @ApiModelProperty(value="领队ID")
    private Long leaderId;

    @ApiModelProperty(value="微信唯一标识")
    private String unionid;

    @ApiModelProperty(value="用户id")
    private Long userid;

    private List<Long> teamIds;

    public WebMemberRequest() {
    }

    public WebMemberRequest(int pageNum, int pageSize) {
        this.pageNums = pageNum;
        this.pageSizes = pageSize;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getPageNums() {
        return pageNums;
    }

    public void setPageNums(int pageNums) {
        this.pageNums = pageNums;
    }

    public int getPageSizes() {
        return pageSizes;
    }

    public void setPageSizes(int pageSizes) {
        this.pageSizes = pageSizes;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public List<Long> getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(List<Long> teamIds) {
        this.teamIds = teamIds;
    }
}
