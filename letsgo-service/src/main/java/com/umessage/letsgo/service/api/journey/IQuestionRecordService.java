package com.umessage.letsgo.service.api.journey;


import com.umessage.letsgo.domain.po.journey.QuestionRecordEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;

import java.util.List;

public interface IQuestionRecordService {

	int delete(Long id);

	int insert(QuestionRecordEntity questionRecordEntity);

	QuestionRecordEntity select(Long id);

	List<QuestionRecordEntity> selectAll();

	int update(QuestionRecordEntity questionRecordEntity);

	boolean insertForMember(MemberEntity memberEntity);
}
