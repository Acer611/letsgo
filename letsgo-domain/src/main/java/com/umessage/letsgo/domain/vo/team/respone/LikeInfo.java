package com.umessage.letsgo.domain.vo.team.respone;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pw on 2016/9/20.
 */
public class LikeInfo implements Serializable{
    @ApiModelProperty(value = "点赞人腾讯云id")
    private String ortherId;
    @ApiModelProperty(value = "点赞人名称")
    private String name;
    @ApiModelProperty(value = "点赞人头像")
    private String headUrl;

    public String getOrtherId() {
        return ortherId;
    }

    public void setOrtherId(String ortherId) {
        this.ortherId = ortherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
