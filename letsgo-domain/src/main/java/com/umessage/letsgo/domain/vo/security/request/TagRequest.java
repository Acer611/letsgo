package com.umessage.letsgo.domain.vo.security.request;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

/**
 * Created by wendy on 2016/9/8.
 */
public class TagRequest extends CommonRequest {

    private Long labellingUserId;

    private Long labeledUserId;

    public Long getLabellingUserId() {
        return labellingUserId;
    }

    public void setLabellingUserId(Long labellingUserId) {
        this.labellingUserId = labellingUserId;
    }

    public Long getLabeledUserId() {
        return labeledUserId;
    }

    public void setLabeledUserId(Long labeledUserId) {
        this.labeledUserId = labeledUserId;
    }
}
