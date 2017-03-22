package com.qq.tim.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2016/4/29.
 */
public class AppDefinedData {
    @JsonProperty(value = "Key")
    private String key;
    @JsonProperty(value = "Value")
    private String value;

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
