package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by ZhaoYidong on 2016/7/22.
 */
public class DestroyGroupRequest {

    @ApiModelProperty(value = "要操作的群组id")
    @JsonProperty(value = "GroupId")
    private String groupId;


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
