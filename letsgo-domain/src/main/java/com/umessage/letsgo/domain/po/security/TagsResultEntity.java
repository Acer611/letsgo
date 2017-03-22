package com.umessage.letsgo.domain.po.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by zhajl on 16/9/27.
 */
public class TagsResultEntity {
    @ApiModelProperty(value="标签ID")
    private Long id;

    @ApiModelProperty(value="标签名称")
    private String name;

    @ApiModelProperty(value="打标签的用户ID")
    private Long userId;

    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Long version;

    @ApiModelProperty(value="被标签的用户信息")
    private List<UserEntity> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
