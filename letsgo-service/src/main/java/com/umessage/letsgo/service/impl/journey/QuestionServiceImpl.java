package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.dao.journey.QuestionDao;
import com.umessage.letsgo.dao.journey.QuestionOptionDao;
import com.umessage.letsgo.dao.journey.SurveyDao;
import com.umessage.letsgo.domain.po.journey.QuestionEntity;
import com.umessage.letsgo.domain.po.journey.QuestionOptionEntity;
import com.umessage.letsgo.domain.po.journey.SurveyEntity;
import com.umessage.letsgo.domain.vo.journey.request.QuestionRequest;
import com.umessage.letsgo.domain.vo.team.respone.QuestionInfo;
import com.umessage.letsgo.service.api.journey.IQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/8.
 */
@Service
public class QuestionServiceImpl implements IQuestionService {
    @Resource
    private SurveyDao surveyDao;
    @Resource
    private QuestionDao questionDao;
    @Resource
    private QuestionOptionDao questionOptionDao;
    @Override
    public List<QuestionEntity> selectModel(Long id) {
        return questionDao.selectModel(id);
    }

    @Override
    public List<QuestionInfo> selectQuestion(Long id, Integer i) {
        QuestionEntity questionEntity=new QuestionEntity();
        questionEntity.setSurveyId(id);
        questionEntity.setQuestionType(i);
        return questionDao.selectQuestion(questionEntity);
    }

    @Override
    public List<QuestionEntity> selectAnswer(Long id) {
        return questionDao.selectAnswer(id);
    }

   private Long insert(QuestionEntity questionEntity) {
        questionEntity.setVersion(0l);
        questionEntity.setCreateTime(new Date());
        questionEntity.setUpdateTime(new Date());
        return questionDao.insert(questionEntity);
    }

    private void insertOption(Long questionId,Long surveyId,String  questionOption) {
     QuestionOptionEntity questionOptionEntity=new QuestionOptionEntity();
            questionOptionEntity.setCreateTime(new Date());
            questionOptionEntity.setSurveyId(surveyId);
            questionOptionEntity.setQuestionId(questionId);
            questionOptionEntity.setVersion(0l);
        questionOptionEntity.setUpdateTime(new Date());
        questionOptionEntity.setQuestionOption(questionOption);
        questionOptionDao.insert(questionOptionEntity);
    }
    @Transactional
    @Override
    public void saveQuestionAndOpiton(List<QuestionRequest> list) {
        for (QuestionRequest q:list){
            QuestionEntity questionEntity=new QuestionEntity();
            questionEntity.setQuestionType(q.getQuestionType());
            questionEntity.setSurveyId(q.getSurveyId());
            questionEntity.setTitle(q.getTitle());
            questionEntity.setType(q.getType());
              insert(questionEntity);
            if(q.getQuestionType()==1||q.getQuestionType()==2){
                List<String> op=q.getList();
                for (String s:op){
                    insertOption(questionEntity.getId(),q.getSurveyId(),s);
                }

            }
        }
        SurveyEntity surveyEntity=new SurveyEntity();
        surveyDao.updateStatus(list.get(0).getSurveyId());
    }

    @Transactional
    @Override
    public void createQuestionAndOpiton(List<QuestionRequest> list,SurveyEntity surveyEntity) {
        for (QuestionRequest q:list){
            QuestionEntity questionEntity=new QuestionEntity();
            questionEntity.setQuestionType(q.getQuestionType());
            questionEntity.setSurveyId(q.getSurveyId());
            questionEntity.setTitle(q.getTitle());
            questionEntity.setType(q.getType());
            questionEntity.setSurveyId(surveyEntity.getId());
            insert(questionEntity);
            if(q.getQuestionType()==1||q.getQuestionType()==2){
                List<String> op=q.getList();
                for (String s:op){
                    insertOption(questionEntity.getId(),surveyEntity.getId(),s);
                }

            }
        }
        surveyDao.updateStatus(surveyEntity.getId());
    }
}
