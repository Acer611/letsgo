package com.umessage.letsgo.service.impl.journey;


import com.umessage.letsgo.dao.journey.QuestionRecordDao;
import com.umessage.letsgo.domain.po.journey.QuestionRecordEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.journey.request.AnswerRequest;
import com.umessage.letsgo.domain.vo.journey.response.vo.AnswerVo;
import com.umessage.letsgo.service.api.journey.IAnswerService;
import com.umessage.letsgo.service.api.journey.IQuestionRecordService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class QuestionRecordServiceImpl implements IQuestionRecordService {
	Logger logger = Logger.getLogger(QuestionRecordServiceImpl.class);

    @Resource
    private QuestionRecordDao questionRecordDao;

	@Resource
	private IAnswerService answerService;

	@Override
	public int delete(Long id) {
		return questionRecordDao.delete(id);
	}

	@Override
	public int insert(QuestionRecordEntity questionRecordEntity) {
		return questionRecordDao.insert(questionRecordEntity);
	}

	@Override
	public QuestionRecordEntity select(Long id) {
		return questionRecordDao.select(id);
	}

	@Override
	public List<QuestionRecordEntity> selectAll() {
		return questionRecordDao.selectAll();
	}

	@Override
	public int update(QuestionRecordEntity questionRecordEntity) {
		return questionRecordDao.update(questionRecordEntity);
	}

	@Override
	public boolean insertForMember(MemberEntity memberEntity) {
		AnswerRequest answerRequest = new AnswerRequest();
		answerRequest.setUserId(memberEntity.getUserId());
		answerRequest.setTeamId(memberEntity.gettId());
		QuestionRecordEntity questionRecordEntity = new QuestionRecordEntity();
		//根据用户id和团队id查询答案
		List<AnswerVo> answerVos = answerService.selectAnswerByUserIdAndTeamId(answerRequest);
		if (answerVos != null && answerVos.size() > 0){
			questionRecordEntity.setTeamNum(answerVos.get(0).getTeamNum());
			questionRecordEntity.setScheduleName(answerVos.get(0).getName());
			questionRecordEntity.setStartDate(answerVos.get(0).getStartDate());
			questionRecordEntity.setEndDate(answerVos.get(0).getEndDate());
			questionRecordEntity.setRealName(answerVos.get(0).getRealName());
			questionRecordEntity.setSex(answerVos.get(0).getSex());
			questionRecordEntity.setPhone(answerVos.get(0).getPhone());
			questionRecordEntity.setCreateTime(new Date());
			for (AnswerVo answerVo:answerVos) {
				Integer questionType = answerVo.getQuestionType();
				//如果是模板问题
				if (questionType == 4){
					//如果是景点问题
					if (answerVo.getType()==1){
						questionRecordEntity.setMouldQuestion1(answerVo.getOptionAnswer());
					}else if (answerVo.getType()==2){//如果是当地交通问题
						questionRecordEntity.setMouldQuestion2(answerVo.getOptionAnswer());
					}else if (answerVo.getType()==3){//如果是住宿问题
						questionRecordEntity.setMouldQuestion3(answerVo.getOptionAnswer());
					}else if (answerVo.getType()==4){//如果是用餐问题
						questionRecordEntity.setMouldQuestion4(answerVo.getOptionAnswer());
					}else if (answerVo.getType()==5){//如果是服务人员问题
						questionRecordEntity.setMouldQuestion5(answerVo.getOptionAnswer());
					}else if (answerVo.getType()==6){//如果是整体问题
						questionRecordEntity.setMouldQuestion6(answerVo.getOptionAnswer());
					}
				}else if (questionType == 1){//如果是单选题
					questionRecordEntity = this.setQuestionRecordEntityCustomOne(questionRecordEntity, answerVo);
				}else if (questionType == 2){//多选
					questionRecordEntity = this.setQuestionRecordEntityCustomMore(questionRecordEntity, answerVo);
				}else if (questionType == 3){//问答
					questionRecordEntity = this.setQuestionRecordEntityCustomContent(questionRecordEntity, answerVo);
				}
			}
			questionRecordDao.insert(questionRecordEntity);
		}
		return true;
	}


	public QuestionRecordEntity setQuestionRecordEntityCustomOne(QuestionRecordEntity questionRecordEntity, AnswerVo answerVo){
		if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion1())){
			questionRecordEntity.setCustomQuestion1(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion2())){
			questionRecordEntity.setCustomQuestion2(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion3())){
			questionRecordEntity.setCustomQuestion3(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion4())){
			questionRecordEntity.setCustomQuestion4(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion5())){
			questionRecordEntity.setCustomQuestion5(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion6())){
			questionRecordEntity.setCustomQuestion6(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion7())){
			questionRecordEntity.setCustomQuestion7(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion8())){
			questionRecordEntity.setCustomQuestion8(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion9())){
			questionRecordEntity.setCustomQuestion9(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion10())){
			questionRecordEntity.setCustomQuestion10(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion11())){
			questionRecordEntity.setCustomQuestion11(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion12())){
			questionRecordEntity.setCustomQuestion12(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion13())){
			questionRecordEntity.setCustomQuestion13(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion14())){
			questionRecordEntity.setCustomQuestion14(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion15())){
			questionRecordEntity.setCustomQuestion15(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion16())){
			questionRecordEntity.setCustomQuestion16(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion17())){
			questionRecordEntity.setCustomQuestion17(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion18())){
			questionRecordEntity.setCustomQuestion18(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion19())){
			questionRecordEntity.setCustomQuestion19(answerVo.getQuestionOptionId().toString());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion20())){
			questionRecordEntity.setCustomQuestion20(answerVo.getQuestionOptionId().toString());
		}
		return questionRecordEntity;
	}


	public QuestionRecordEntity setQuestionRecordEntityCustomMore(QuestionRecordEntity questionRecordEntity, AnswerVo answerVo){
		if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion1())){
			questionRecordEntity.setCustomQuestion1(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion2())){
			questionRecordEntity.setCustomQuestion2(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion3())){
			questionRecordEntity.setCustomQuestion3(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion4())){
			questionRecordEntity.setCustomQuestion4(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion5())){
			questionRecordEntity.setCustomQuestion5(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion6())){
			questionRecordEntity.setCustomQuestion6(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion7())){
			questionRecordEntity.setCustomQuestion7(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion8())){
			questionRecordEntity.setCustomQuestion8(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion9())){
			questionRecordEntity.setCustomQuestion9(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion10())){
			questionRecordEntity.setCustomQuestion10(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion11())){
			questionRecordEntity.setCustomQuestion11(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion12())){
			questionRecordEntity.setCustomQuestion12(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion13())){
			questionRecordEntity.setCustomQuestion13(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion14())){
			questionRecordEntity.setCustomQuestion14(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion15())){
			questionRecordEntity.setCustomQuestion15(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion16())){
			questionRecordEntity.setCustomQuestion16(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion17())){
			questionRecordEntity.setCustomQuestion17(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion18())){
			questionRecordEntity.setCustomQuestion18(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion19())){
			questionRecordEntity.setCustomQuestion19(answerVo.getMoreOption());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion20())){
			questionRecordEntity.setCustomQuestion20(answerVo.getMoreOption());
		}
		return questionRecordEntity;
	}


	public QuestionRecordEntity setQuestionRecordEntityCustomContent(QuestionRecordEntity questionRecordEntity, AnswerVo answerVo){
		if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion1())){
			questionRecordEntity.setCustomQuestion1(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion2())){
			questionRecordEntity.setCustomQuestion2(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion3())){
			questionRecordEntity.setCustomQuestion3(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion4())){
			questionRecordEntity.setCustomQuestion4(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion5())){
			questionRecordEntity.setCustomQuestion5(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion6())){
			questionRecordEntity.setCustomQuestion6(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion7())){
			questionRecordEntity.setCustomQuestion7(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion8())){
			questionRecordEntity.setCustomQuestion8(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion9())){
			questionRecordEntity.setCustomQuestion9(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion10())){
			questionRecordEntity.setCustomQuestion10(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion11())){
			questionRecordEntity.setCustomQuestion11(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion12())){
			questionRecordEntity.setCustomQuestion12(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion13())){
			questionRecordEntity.setCustomQuestion13(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion14())){
			questionRecordEntity.setCustomQuestion14(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion15())){
			questionRecordEntity.setCustomQuestion15(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion16())){
			questionRecordEntity.setCustomQuestion16(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion17())){
			questionRecordEntity.setCustomQuestion17(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion18())){
			questionRecordEntity.setCustomQuestion18(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion19())){
			questionRecordEntity.setCustomQuestion19(answerVo.getContent());
		}else if (StringUtils.isEmpty(questionRecordEntity.getCustomQuestion20())){
			questionRecordEntity.setCustomQuestion20(answerVo.getContent());
		}
		return questionRecordEntity;
	}
}
