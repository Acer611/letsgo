package com.umessage.letsgo.domain.vo.system.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class MessageRequest implements Serializable {
    /**
     * 接收人手机
     */
    @ApiModelProperty(value="接收人手机")
    private String phone;
    /**
     * 短信有效性【1：有效；0：无效】
     */
    @ApiModelProperty(value="短信有效性【1：有效；0：无效】")
    private Integer valid;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String mark;

    /***************短信模板需要的参数*******************/

    /**
     * 被邀请人【游客|领队|导游】的姓名
     */
    @JsonIgnore
    private String invitee;
    /**
     * 邀请人
     */
    @JsonIgnore
    private String inviter;
    /**
     * 团队所属旅行社
     */
    @JsonIgnore
    private String travelAgency;
    /**
     * 团队名称
     */
    @JsonIgnore
    private String teamName;
    /**
     * 下载链接【领队端|游客端】
     */
    @JsonIgnore
    private String downloadURL;


    public MessageRequest() {
    }

    public MessageRequest(String phone, String invitee, String travelAgency, String teamName, String downloadURL) {
        this.phone = phone;
        this.invitee = invitee;
        this.travelAgency = travelAgency;
        this.teamName = teamName;
        this.downloadURL = downloadURL;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getInvitee() {
        return invitee;
    }

    public void setInvitee(String invitee) {
        this.invitee = invitee;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getTravelAgency() {
        return travelAgency;
    }

    public void setTravelAgency(String travelAgency) {
        this.travelAgency = travelAgency;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "phone='" + phone + '\'' +
                ", valid=" + valid +
                ", mark='" + mark + '\'' +
                ", invitee='" + invitee + '\'' +
                ", inviter='" + inviter + '\'' +
                ", travelAgency='" + travelAgency + '\'' +
                ", teamName='" + teamName + '\'' +
                ", downloadURL='" + downloadURL + '\'' +
                '}';
    }
}
