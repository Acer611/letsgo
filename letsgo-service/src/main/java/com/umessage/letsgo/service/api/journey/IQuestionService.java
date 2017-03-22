package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.QuestionEntity;
import com.umessage.letsgo.domain.po.journey.SurveyEntity;
import com.umessage.letsgo.domain.vo.journey.request.QuestionRequest;
import com.umessage.letsgo.domain.vo.team.respone.QuestionInfo;

import java.util.List;

/**
 * Created by zengguoqing on 2016/9/8.
 */
public interface IQuestionService {
    List<QuestionEntity> selectModel(Long id);

    List<QuestionInfo> selectQuestion(Long id, Integer i);

    List<QuestionEntity> selectAnswer(Long id);


    void saveQuestionAndOpiton(List<QuestionRequest> list);

    void createQuestionAndOpiton(List<QuestionRequest> list, SurveyEntity surveyEntity);
}
