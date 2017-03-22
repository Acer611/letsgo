package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wendy on 2016/6/8.
 */
public class AppMemberDefinedData {
    @JsonProperty(value = "Key")
    private String key;
    @JsonProperty(value = "Value")
    private String value;

    public AppMemberDefinedData() {
    }

    public AppMemberDefinedData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"key\":\"" + key + "\""
                + ",                         \"value\":\"" + value + "\""
                + "}";
    }
}
