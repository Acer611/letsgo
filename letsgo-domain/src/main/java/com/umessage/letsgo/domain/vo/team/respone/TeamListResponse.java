package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.annotation.dataitem.DataItem;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
public class TeamListResponse extends CommonResponse {
    /**
     * 团队列表
     */
    @ApiModelProperty(value = "团队列表")
    @Catalog
    private List<TeamEntity> teamList = new ArrayList<>();

    public List<TeamEntity> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<TeamEntity> teamList) {
        this.teamList = teamList;
    }

    public static TeamListResponse createNotFoundResponse(){
        class NotFoundResponse extends TeamListResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }

            @Override
            public String getRetMsg() {
                return "未找到对应的团队";
            }
        }

        return new NotFoundResponse();
    }

    public static TeamListResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends TeamListResponse {

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
