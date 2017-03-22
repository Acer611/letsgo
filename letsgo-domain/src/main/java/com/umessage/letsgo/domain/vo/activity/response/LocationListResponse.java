package com.umessage.letsgo.domain.vo.activity.response;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.activity.LocationEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.SignVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/19.
 */
public class LocationListResponse extends CommonResponse {
    /**
     * 成员位置列表
     */
    @ApiModelProperty("成员位置列表")
    @Catalog
    List<LocationEntity> locationEntityList;

    @ApiModelProperty(value = "浏览器各链接的签名")
    private Map<String,SignVo> signMap;

    @ApiModelProperty(value = "腾迅云组Id")
    private String teamId;

    /**
     * 当前团队出行状态标识
     */
    @ApiModelProperty(value = "当前团队出行状态标识【值为1，代表出行中；值为2，代表即将出行；值为3，代表已出行】")
    private int teamStatus;
    /**
     * 当前用户在当前团队的身份标识
     */
    @ApiModelProperty(value = "当前用户在当前团队的身份标识【值为1，代表领队；值为2，代表导游；值3，代表游客】")
    private int roleStatus;
    /**
     * 当前用户在当前团队的管理权限标识
     */
    @ApiModelProperty(value = "当前用户在当前团队的管理权限标识【值为1，代表有管理权；值为0，代表无管理权】")
    private int adminStatus;

    public List<LocationEntity> getLocationEntityList() {
        return locationEntityList;
    }

    public void setLocationEntityList(List<LocationEntity> locationEntityList) {
        this.locationEntityList = locationEntityList;
    }

    public Map<String, SignVo> getSignMap() {
        return signMap;
    }

    public void setSignMap(Map<String, SignVo> signMap) {
        this.signMap = signMap;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public int getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(int teamStatus) {
        this.teamStatus = teamStatus;
    }

    public int getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(int roleStatus) {
        this.roleStatus = roleStatus;
    }

    public int getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(int adminStatus) {
        this.adminStatus = adminStatus;
    }

    public static LocationListResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends LocationListResponse {

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

    public static LocationListResponse createUserNotAccessResponse(){
        class UserNotAccessResponse extends LocationListResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.ACCESS_DENIED;
            }

            @Override
            public String getRetMsg() {
                return "用户不能触发成员位置事件，没有权限操作！";
            }
        }

        return new UserNotAccessResponse();
    }

}
