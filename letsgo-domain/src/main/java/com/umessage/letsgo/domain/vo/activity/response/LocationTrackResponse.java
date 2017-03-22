package com.umessage.letsgo.domain.vo.activity.response;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.activity.LocationHistoryEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by zhajl on 17/2/7.
 */
public class LocationTrackResponse extends CommonResponse {
    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    private Long userId;
    /**
     * 用户实体
     */
    @ApiModelProperty(value="用户实体")
    @Catalog
    private UserEntity userEntity;

    /**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID")
    /*@JsonIgnore*/
    private Long tId;

    /**
     * 历史位置列表
     */
    @ApiModelProperty("历史位置列表")
    @Catalog
    private List<LocationHistoryEntity> locationHistoryEntityList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public List<LocationHistoryEntity> getLocationHistoryEntityList() {
        return locationHistoryEntityList;
    }

    public void setLocationHistoryEntityList(List<LocationHistoryEntity> locationHistoryEntityList) {
        this.locationHistoryEntityList = locationHistoryEntityList;
    }

    public static LocationTrackResponse createUserNotFoundResponse(){
        class UserNotFoundResponse extends LocationTrackResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }

            @Override
            public String getRetMsg() {
                return "未找的对应的用户信息";
            }
        }

        return new UserNotFoundResponse();
    }

    public static LocationTrackResponse createTeamNotFoundResponse(){
        class TeamNotFoundResponse extends LocationTrackResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }

            @Override
            public String getRetMsg() {
                return "未找的对应的团队信息";
            }
        }

        return new TeamNotFoundResponse();
    }
}
