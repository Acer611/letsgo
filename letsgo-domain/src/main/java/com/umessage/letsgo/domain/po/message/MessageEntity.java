package com.umessage.letsgo.domain.po.message;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by gaofei on 2017/1/12.
 */
public class MessageEntity implements Serializable{
   /* id                   bigint not null comment '主键自增',
    travel_id            bigint comment '旅行社的唯一标识',
    member_id            bigint comment '成员的唯一标识',
    team_id              bigint comment '团的唯一标识',
    team_name            varchar(512) comment '团名称',
    team_num             varchar(512) comment '团号',
    create_time          timestamp,
    update_time          timestamp comment '修改时间，每有一条回复时，都要更新此字段',
    status               tinyint comment '0 为未读 1 为已读为回复 2 为 已读回复',*/

    //主键自增
    private Long ID;
    //旅行社的唯一标识
    private Long teavelId;
    //成员的唯一标识
    private Long memberId;
    //成员的唯一标识
    private Long userId;
    //团的唯一标识
    private Long teamId;
    //旅行社头像地址
    private String travelHeadUrl;
    //头像地址
    private String headUrl;
    //腾讯组ID
    private String tGroupId;
    //团名称
    private String teamName;
    //团号
    private String teamNum;
    //创建时间
    private Date createTime;
    //修改时间 每有一条回复时，都要更新此字段
    private Date updateTime;
    //最新回复消息的状态  0 为未读 1 为已读为回复 2 为 已读回复
    private Integer status;

    //旅行社展示的临时字段
    //用户名
    private String userName;



    private List<MessageTextEntity> messageTextEntityList;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getTeavelId() {
        return teavelId;
    }

    public void setTeavelId(Long teavelId) {
        this.teavelId = teavelId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getTravelHeadUrl() {
        return travelHeadUrl;
    }

    public void setTravelHeadUrl(String travelHeadUrl) {
        this.travelHeadUrl = travelHeadUrl;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String gettGroupId() {
        return tGroupId;
    }

    public void settGroupId(String tGroupId) {
        this.tGroupId = tGroupId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<MessageTextEntity> getMessageTextEntityList() {
        return messageTextEntityList;
    }

    public void setMessageTextEntityList(List<MessageTextEntity> messageTextEntityList) {
        this.messageTextEntityList = messageTextEntityList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
