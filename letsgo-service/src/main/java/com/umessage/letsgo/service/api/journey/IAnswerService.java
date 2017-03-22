package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.AnswerEntity;
import com.umessage.letsgo.domain.vo.journey.request.AnswerRequest;
import com.umessage.letsgo.domain.vo.journey.response.vo.AnswerVo;

import java.util.List;

/**
 * Created by pw on 2016/9/13.
 */
public interface IAnswerService {
    int add(AnswerEntity answer);

    int update(AnswerEntity answer);
     //判断是否已经填写答案
    boolean hasAnswer(AnswerRequest request);
    /**
     * 根据用户id和团队id查询答案
     * @param request
     * @return
     */
    List<AnswerVo> selectAnswerByUserIdAndTeamId(AnswerRequest request);
}
