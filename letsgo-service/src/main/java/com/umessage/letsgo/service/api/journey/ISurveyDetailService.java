package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.SurveyDetailEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.SurveyDetailRequest;
import com.umessage.letsgo.domain.vo.journey.request.SurveyRequest;

import java.util.List;

/**
 * 线路评价Service接口
 * @author pw
 *
 */
public interface ISurveyDetailService {
	int add(SurveyDetailEntity surveyDetailEntity);

	int addBatch(List<SurveyDetailEntity> surveyDetailEntityList);

	int update(SurveyDetailEntity surveyDetailEntity);

	//获取最新的调查问卷
	SurveyDetailEntity getByUseIdAndTxgroupId(SurveyDetailRequest request);

	List<SurveyDetailEntity> getPdfList(Long surveyId);

	List<SurveyDetailEntity> getPdfListOne(Long surveyId);

	SurveyDetailEntity getById(Long id);

	// 确认问卷
	CommonResponse confirmSurvey(String surveyDetailId, String basePath);

	List<SurveyDetailEntity> getNotFilledSurveyDetailList(SurveyDetailRequest request);

	List<SurveyDetailEntity> getFilledSurveyDetailList(SurveyDetailRequest request);

    //游客提交签名
	CommonResponse submitSignUrl(SurveyRequest request);
	//获取确认问卷集合
	List<SurveyDetailEntity> getConfirmSurvey(SurveyDetailRequest request);

	//新的获取确认问卷集合
	List<SurveyDetailEntity> getConfirmSurveyOne(SurveyDetailRequest request);

	//更新问卷调查的pdfurl
	public void updatePdfURL(SurveyDetailEntity surveyDetailEntity);

	//更新问卷调查的pngurl
	public void updatePngURL(SurveyDetailEntity surveyDetailEntity);
    //点击去填写后修改值
	int updateFirst(SurveyDetailEntity surveyDetailEntity);


	/**
	 * 批量确认问卷
	 * @param ids
     */
	CommonResponse  batchConfirmSurvey(String[] ids);
}
