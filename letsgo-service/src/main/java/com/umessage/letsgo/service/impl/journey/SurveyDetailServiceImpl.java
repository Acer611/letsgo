package com.umessage.letsgo.service.impl.journey;


import com.umessage.letsgo.dao.journey.SurveyDetailDao;
import com.umessage.letsgo.domain.po.journey.SurveyDetailEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.SurveyDetailRequest;
import com.umessage.letsgo.domain.vo.journey.request.SurveyRequest;
import com.umessage.letsgo.domain.vo.journey.response.SurveyQuestionResponse;
import com.umessage.letsgo.service.api.journey.ISurveyDetailService;
import com.umessage.letsgo.service.api.journey.ISurveyService;
import com.umessage.letsgo.service.impl.journey.Helper.HtmlPdfHelper;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SurveyDetailServiceImpl implements ISurveyDetailService {

	private Logger logger = Logger.getLogger(SurveyDetailServiceImpl.class);

	@Resource
	private SurveyDetailDao surveyDetailDao;
	@Resource
	private HtmlPdfHelper htmlPdfHelper;
	@Resource
	private ISurveyService surveyService;
	@Autowired
	protected VelocityEngine velocityEngine ;

	@Override
	public int add(SurveyDetailEntity surveyDetailEntity) {
		surveyDetailEntity.setConfirmStatus(0);
		surveyDetailEntity.setCreateTime(new Date());
		surveyDetailEntity.setVersion(0l);
		surveyDetailEntity.setFirst(1);
		return surveyDetailDao.insert(surveyDetailEntity);
	}

	@Override
	public int addBatch(List<SurveyDetailEntity> surveyDetailEntityList) {
		for (SurveyDetailEntity surveyDetailEntity : surveyDetailEntityList) {
			surveyDetailEntity.setConfirmStatus(0);
			surveyDetailEntity.setCreateTime(new Date());
			surveyDetailEntity.setFirst(1);
			surveyDetailEntity.setVersion(0l);
		}
		return surveyDetailDao.insertBatch(surveyDetailEntityList);
	}

	@Override
	public int update(SurveyDetailEntity surveyDetailEntity) {
		return surveyDetailDao.update(surveyDetailEntity);
	}

	//获取最新的调查问卷
	public SurveyDetailEntity getByUseIdAndTxgroupId(SurveyDetailRequest request){
		SurveyDetailEntity surveyDetailEntity =surveyDetailDao.getByUseIdAndTxGroupId(request);
		if(surveyDetailEntity==null){
			return new SurveyDetailEntity();
		}
		return surveyDetailEntity;
	}

	@Override
	public List<SurveyDetailEntity> getPdfList(Long surveyId) {
		return surveyDetailDao.getPdfList(surveyId);
	}

	@Override
	public List<SurveyDetailEntity> getPdfListOne(Long surveyId) {
		return surveyDetailDao.getPdfListOne(surveyId);
	}
	@Override
	public SurveyDetailEntity getById(Long id) {
		return surveyDetailDao.getSurveyDetailById(id);
	}


	/**
	 * 确认问卷
	 * @param surveyDetailId
	 * @return
	 */
	@Override
	public CommonResponse confirmSurvey(String surveyDetailId, String basePath) {
		System.out.println("当前要确认问卷的surveyId是：" + surveyDetailId);
		SureSurvey(surveyDetailId);
		/*String baseUrl = basePath+"survey/getSurveyWithSign?surveyUserId="+surveyDetailId;
		htmlPdfHelper.htmlZpfd(surveyDetailId, baseUrl);*/

		CommonResponse response = new CommonResponse();
		response.setRetMsg("确认成功");
		return response;
	}



	private void SureSurvey(String surveyDetailid) {
		SurveyDetailEntity surveyDetailEntity = this.getById(Long.parseLong(surveyDetailid));
		if (surveyDetailEntity != null) {
            surveyDetailEntity.setConfirmStatus(1);
            this.update(surveyDetailEntity);
         }
	}




	/**
	 * 获取未填写状态列表
	 * @param request
	 * @return
     */
	@Override
	public List<SurveyDetailEntity> getNotFilledSurveyDetailList(SurveyDetailRequest request) {
		List<SurveyDetailEntity> surveyDetailEntityList = surveyDetailDao.getNotFilledSurveyDetailList(request);

		if (CollectionUtils.isEmpty(surveyDetailEntityList)) {
			return new ArrayList<>();
		}
		return surveyDetailEntityList;
	}

	/**
	 * 获取已填写状态列表
	 * @param request
	 * @return
     */
	@Override
	public List<SurveyDetailEntity> getFilledSurveyDetailList(SurveyDetailRequest request) {
		List<SurveyDetailEntity> surveyDetailEntityList = surveyDetailDao.getFilledSurveyDetailList(request);

		if (CollectionUtils.isEmpty(surveyDetailEntityList)) {
			return new ArrayList<>();
		}
		return surveyDetailEntityList;
	}
	//游客提交签名
	@Override
	public CommonResponse submitSignUrl(SurveyRequest request){
		request.setSubmitTime(new Date());
		surveyDetailDao.submitSignUrl(request);
		return new CommonResponse();
	}
	//获取确认问卷集合
	public List<SurveyDetailEntity> getConfirmSurvey(SurveyDetailRequest request){
		List<SurveyDetailEntity> surveyDetailList=surveyDetailDao.getConfirmSurvey(request);
		if(CollectionUtils.isEmpty(surveyDetailList)){
			return  new ArrayList<SurveyDetailEntity>();
		}
		return surveyDetailList;
	}
	//新的获取确认问卷集合
	public List<SurveyDetailEntity> getConfirmSurveyOne(SurveyDetailRequest request){
		List<SurveyDetailEntity> surveyDetailList = surveyDetailDao.getConfirmSurveyOne(request);
		if(CollectionUtils.isEmpty(surveyDetailList)){
			return  new ArrayList<SurveyDetailEntity>();
		}
		return surveyDetailList;
	}
	@Override
	public void updatePdfURL(SurveyDetailEntity surveyDetailEntity) {
		surveyDetailDao.updatePdfUrl(surveyDetailEntity);
	}

	@Override
	public void updatePngURL(SurveyDetailEntity surveyDetailEntity) {
		surveyDetailDao.updatePngUrl(surveyDetailEntity);
	}

	//点击去填写后修改值
	public int updateFirst(SurveyDetailEntity surveyDetailEntity){
		return surveyDetailDao.updateFirst(surveyDetailEntity);
	}

	/**
	 * 批量确认问卷
	 * @param ids
     */
	@Override
	public CommonResponse  batchConfirmSurvey(String[] ids) {

		List<String> idList = new ArrayList<>();
		for (String id:ids) {
			idList.add(id);
		}
		logger.info("批量确认问卷的问卷ID为" + idList.toString());
		surveyDetailDao.batchUpdate(idList);

		CommonResponse response = new CommonResponse();
		response.setRetMsg("确认成功");
		return response;
	}
}
