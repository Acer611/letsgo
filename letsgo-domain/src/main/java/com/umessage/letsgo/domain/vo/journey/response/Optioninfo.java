package com.umessage.letsgo.domain.vo.journey.response;

import java.io.Serializable;

/**
 * Created by zengguoqing on 2016/9/8.
 */
public class Optioninfo implements Serializable{
    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    private Long optionId;
    private String optionContent;
}
