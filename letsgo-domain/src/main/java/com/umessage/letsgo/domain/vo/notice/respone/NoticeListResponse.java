package com.umessage.letsgo.domain.vo.notice.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
public class NoticeListResponse extends CommonResponse {

    /**
     * 权限标识
    */
    @ApiModelProperty(value = "权限标识【值为1，代表有权限；值为0，代表没有权限】")
    private int isOperable;
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

    /**
     * 通知/集合列表
     */
    @ApiModelProperty(value = "通知/集合列表")
    private List<NoticeEntity> noticeList;

    public int getIsOperable() {
        return isOperable;
    }

    public void setIsOperable(int isOperable) {
        this.isOperable = isOperable;
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

    public List<NoticeEntity> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<NoticeEntity> noticeList) {
        this.noticeList = noticeList;
    }

    public static NoticeListResponse createNotFoundResponse(){
        class NotFoundResponse extends NoticeListResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }

            @Override
            public String getRetMsg() {
                return "未找到对应的通知/集合";
            }
        }

        return new NotFoundResponse();
    }

    public static NoticeListResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends NoticeListResponse {

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
