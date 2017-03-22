package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.SurveyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.SurveyAnswerRequest;
import com.umessage.letsgo.domain.vo.journey.request.SurveyRequest;
import com.umessage.letsgo.domain.vo.journey.response.SurveyDownloadResponse;
import com.umessage.letsgo.domain.vo.journey.response.SurveyQuestionResponse;
import com.umessage.letsgo.domain.vo.journey.response.SurveyResponse;

import java.util.List;


/**
 * Created by zengguoqing on 2016/9/7.
 */
public interface ISurveyService {

    SurveyEntity selectByTeamId(Long teamId);

    Long insert(SurveyEntity surveyEntity);

    // 发放问卷
    CommonResponse issueSurvey(String txGroupId);

    //获得调查问卷
    SurveyQuestionResponse getSurvey(SurveyRequest request);

    SurveyEntity selectById(Long id);

    // 获取团队问卷填写状态
    SurveyResponse getAllSurveyStatusById(Long userId, String txGroupid);


    // 提交调查问卷
    CommonResponse submitSurvey(SurveyAnswerRequest request);


    List<SurveyEntity> getAllSurvey(Long id);

    // 获取游客的调查问卷及签名
    SurveyQuestionResponse getSurveyWithSign(Long surveyUserId);

    // 根据行程ID，获取问卷调查
    SurveyEntity getSurveyByScheduleId(Long scheduleId);

    /**
     * 下载已确认问卷
     * @param userId
     * @param txGroupid
     * @return
     */
    SurveyDownloadResponse  downloadSurvey(Long userId, String txGroupid);


    /**
     * 获取满意度调查的问卷信息
     * @return
     */
    List<String> surveyStatistics();



}
