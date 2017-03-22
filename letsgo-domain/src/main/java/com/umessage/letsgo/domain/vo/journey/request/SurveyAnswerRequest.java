package com.umessage.letsgo.domain.vo.journey.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.po.journey.AnswerEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pw on 2016/9/12.
 */
public class SurveyAnswerRequest implements Serializable {
    /**
     * 问卷答案对象
     */
    private List<AnswerEntity> answerList;

    private String img64Str;

    private String txGroupId;
    /**
     * 0新增  1编辑
     */
    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<AnswerEntity> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AnswerEntity> answerList) {
        this.answerList = answerList;
    }

    public String getImg64Str() {
        return img64Str;
    }

    public void setImg64Str(String img64Str) {
        this.img64Str = img64Str;
    }

    public String getTxGroupId() {
        return txGroupId;
    }

    public void setTxGroupId(String txGroupId) {
        this.txGroupId = txGroupId;
    }
}
