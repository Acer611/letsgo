package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.QuestionEntity;
import com.umessage.letsgo.domain.vo.journey.request.SurveyRequest;
import com.umessage.letsgo.domain.vo.team.respone.QuestionInfo;

import java.util.List;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public interface QuestionDao {
    List<QuestionEntity> selectModel(Long id);

    List<QuestionInfo> selectQuestion(QuestionEntity questionEntity);

    List<QuestionEntity> selectAnswer(Long id);

    Long insert(QuestionEntity questionEntity);

    //获得调查问卷
    List<QuestionEntity> getSurvey(SurveyRequest request);
}
