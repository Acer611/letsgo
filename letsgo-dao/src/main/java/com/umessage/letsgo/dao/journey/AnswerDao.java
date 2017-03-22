package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.AnswerEntity;
import com.umessage.letsgo.domain.vo.journey.request.AnswerRequest;
import com.umessage.letsgo.domain.vo.journey.response.vo.AnswerVo;

import java.util.List;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public interface AnswerDao {
    /**
     * Description:
     * @param answer 新增
     * @return int
     */
    int insert(AnswerEntity answer);

    /**
     * Description:
     * @param answer 编辑
     * @return int
     */
    int update(AnswerEntity answer);

    //判断是否已经填写答案
    int hasAnswer(AnswerRequest request);

    /**
     * 根据用户id和团队id查询答案
     * @param request
     * @return
     */
    List<AnswerVo> selectAnswerByUserIdAndTeamId(AnswerRequest request);
}
