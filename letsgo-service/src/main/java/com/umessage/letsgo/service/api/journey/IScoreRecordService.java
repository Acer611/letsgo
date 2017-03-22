package com.umessage.letsgo.service.api.journey;


import com.umessage.letsgo.domain.po.journey.ScoreRecordEntity;

import java.util.List;

public interface IScoreRecordService {

	int delete(Long id);

	int insert(ScoreRecordEntity scoreRecordEntity);

	ScoreRecordEntity select(Long id);

	List<ScoreRecordEntity> selectAll();

	int update(ScoreRecordEntity scoreRecordEntity);

	boolean insertForJob(ScoreRecordEntity scoreRecordEntity);
}
