package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.dao.journey.AnswerDao;
import com.umessage.letsgo.domain.po.journey.AnswerEntity;
import com.umessage.letsgo.domain.vo.journey.request.AnswerRequest;
import com.umessage.letsgo.domain.vo.journey.response.vo.AnswerVo;
import com.umessage.letsgo.service.api.journey.IAnswerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by pw on 2016/9/13.
 */
@Service
public class AnswerServiceImpl implements IAnswerService {
	@Resource
	private AnswerDao answerDao;

	@Override
	public int add(AnswerEntity answer) {
		answer.setUpdateTime(new Date());
		answer.setCreateTime(new Date());
		answer.setVersion(0l);
		return answerDao.insert(answer);
	}

	public int update(AnswerEntity answer){
		answer.setUpdateTime(new Date());
		return answerDao.update(answer);
	}
	//判断是否已经填写答案
	public boolean hasAnswer(AnswerRequest request){
		int i = answerDao.hasAnswer(request);
		if(i>0){
			return true;
		}
      return false;
	}

	@Override
	public List<AnswerVo> selectAnswerByUserIdAndTeamId(AnswerRequest request) {
		List<AnswerVo> answerVos = answerDao.selectAnswerByUserIdAndTeamId(request);
		return answerVos;
	}
}
