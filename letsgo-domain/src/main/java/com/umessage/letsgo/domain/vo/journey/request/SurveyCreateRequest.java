package com.umessage.letsgo.domain.vo.journey.request;

import com.alibaba.fastjson.JSONArray;
import com.umessage.letsgo.domain.po.journey.SurveyEntity;

import java.io.Serializable;

/**
 * Created by gaofei on 2017/1/18.
 */
public class SurveyCreateRequest implements Serializable {

    //问题的信息
    private JSONArray json;

    //问卷的信息
    private SurveyEntity surveryEntity;

    public JSONArray getJson() {
        return json;
    }

    public void setJson(JSONArray json) {
        this.json = json;
    }

    public SurveyEntity getSurveryEntity() {
        return surveryEntity;
    }

    public void setSurveryEntity(SurveyEntity surveryEntity) {
        this.surveryEntity = surveryEntity;
    }
}
