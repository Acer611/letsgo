package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.SurveyEntity;

import java.util.List;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public interface SurveyDao {
    SurveyEntity selectByTeamId(Long teamId);

    Long insert(SurveyEntity surveyEntity);

    SurveyEntity selectById(Long id);

    void updateStatus(Long id);
    //根据ID获取调查问卷对象
    SurveyEntity getSurveyById(Long id);

    List<SurveyEntity> getAllSurvey(Long id);

    SurveyEntity getSurveyWithAnswerById(Long id);

    //根据行程ID获取调查问卷对象
    SurveyEntity getSurveyByScheduleId(Long scheduleId);

}
