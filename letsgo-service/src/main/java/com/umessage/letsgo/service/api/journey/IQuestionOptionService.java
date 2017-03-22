package com.umessage.letsgo.service.api.journey;

/**
 * Created by zengguoqing on 2016/9/8.
 */
public interface IQuestionOptionService {
    Long insert(Long surveyId, Long questionId,String title, Integer type);
}
