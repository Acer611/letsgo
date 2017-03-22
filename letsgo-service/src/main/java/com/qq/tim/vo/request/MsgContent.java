package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * 自定义消息元素
 * Created by Administrator on 2016/4/29.
 */
public class MsgContent {
    @ApiModelProperty(value = "自定义消息数据")
    @JsonProperty(value = "Data")
    private String data;
    @ApiModelProperty(value = "自定义消息描述信息")
    @JsonProperty(value = "Desc")
    private String desc;
    @ApiModelProperty(value = "扩展字段")
    @JsonProperty(value = "Ext")
    private String ext;
    @ApiModelProperty(value = "自定义APNS推送铃音")
    @JsonProperty(value = "Sound")
    private String sound;

    public MsgContent() {
    }

    public MsgContent(String data, String desc, String ext, String sound) {
        this.data = data;
        this.desc = desc;
        this.ext = ext;
        this.sound = sound;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    @Override
    public String toString() {
        return "MsgContent{" +
                "data='" + data + '\'' +
                ", desc='" + desc + '\'' +
                ", ext='" + ext + '\'' +
                ", sound='" + sound + '\'' +
                '}';
    }
}
