package com.umessage.letsgo.domain.vo.system.request;

/**
 * Created by wendy on 2016/8/26.
 */
public class ValidationCodeRequest {
    private String phone;
    private Integer scope;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }
}
