package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2016/4/29.
 */
public class GroupBaseInfoFilter {
    @JsonProperty(value = "Name")
    private String name;
    @JsonProperty(value = "FaceUrl")
    private String faceUrl;

    public GroupBaseInfoFilter() {
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupBaseInfoFilter(String name, String faceUrl) {

        this.name = name;
        this.faceUrl = faceUrl;
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"name\":\"" + name + "\""
                + ",                         \"faceUrl\":\"" + faceUrl + "\""
                + "}";
    }
}
