package com.umessage.letsgo.domain.po.security;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lizhen on 2016/11/23.
 */
public class WxTemplateMessageEntity implements Serializable {

    @ApiModelProperty(value="ID")
    private Long id;

    @ApiModelProperty(value="微信用户openID")
    private String openid;

    @ApiModelProperty(value="模板ID")
    private String templateId;

    @ApiModelProperty(value="模板跳转路径")
    private String url;

    @ApiModelProperty(value="模板数据")
    private String templateData;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTemplateData() {
        return templateData;
    }

    public void setTemplateData(String templateData) {
        this.templateData = templateData;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "WxTemplateMessageEntity{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", templateId='" + templateId + '\'' +
                ", url='" + url + '\'' +
                ", templateData='" + templateData + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
