package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.dao.journey.QuestionOptionDao;
import com.umessage.letsgo.domain.po.journey.QuestionOptionEntity;
import com.umessage.letsgo.service.api.journey.IQuestionOptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by zengguoqing on 2016/9/8.
 */
@Service
public class QuestionOptionServiceImpl implements IQuestionOptionService{
    @Resource
    private QuestionOptionDao questionOptionDao;
    @Transactional
    @Override
    public Long insert(Long surveyId, Long questionId,String title, Integer type) {
        QuestionOptionEntity questionOptionEntity=new QuestionOptionEntity();
          questionOptionEntity.setVersion(0l);
        questionOptionEntity.setSurveyId(surveyId);
        questionOptionEntity.setUpdateTime(new Date());
        questionOptionEntity.setCreateTime(new Date());
        questionOptionEntity.setQuestionId(questionId);
        questionOptionEntity.setQuestionOption(title);
        return questionOptionDao.insert(questionOptionEntity);
    }
}
