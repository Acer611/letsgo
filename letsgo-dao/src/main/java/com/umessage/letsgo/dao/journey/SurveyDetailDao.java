package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.SurveyDetailEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.SurveyDetailRequest;
import com.umessage.letsgo.domain.vo.journey.request.SurveyRequest;

import java.util.List;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public interface SurveyDetailDao {
    //获取最新的调查问卷
    SurveyDetailEntity getByUseIdAndTxGroupId(SurveyDetailRequest request);

    int insert(SurveyDetailEntity surveyDetailEntity);

    int insertBatch(List<SurveyDetailEntity> surveyDetailEntityList);

    int update(SurveyDetailEntity surveyDetailEntity);

    List<SurveyDetailEntity> getPdfList(Long surveyId);

    List<SurveyDetailEntity> getPdfListOne(Long surveyId);

    SurveyDetailEntity getSurveyDetailById(Long id);

    List<SurveyDetailEntity> getNotFilledSurveyDetailList(SurveyDetailRequest request);

    List<SurveyDetailEntity> getFilledSurveyDetailList(String txgroupId);
    List<SurveyDetailEntity> getFilledSurveyDetailList(SurveyDetailRequest request);

    //游客提交签名
    int submitSignUrl(SurveyRequest request);

    //获取确认问卷集合
    List<SurveyDetailEntity> getConfirmSurvey(SurveyDetailRequest request);

    //新的获取确认问卷集合
    List<SurveyDetailEntity> getConfirmSurveyOne(SurveyDetailRequest request);

    //更新问卷调查的pdfurl
    void updatePdfUrl(SurveyDetailEntity surveyDetailEntity);

    //更新缩略图的pngurl
    void updatePngUrl(SurveyDetailEntity surveyDetailEntity);

    int updateFirst(SurveyDetailEntity surveyDetailEntity);

    /**
     * 批量确认问卷
     * @param idList
     */
    void batchUpdate(List<String> idList);
}
