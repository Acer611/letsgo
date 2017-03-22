package com.umessage.letsgo.service.impl.journey;


import com.umessage.letsgo.dao.journey.ScoreRecordDao;
import com.umessage.letsgo.domain.po.journey.ScoreRecordEntity;
import com.umessage.letsgo.service.api.journey.IScoreRecordService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ScoreRecordServiceImpl implements IScoreRecordService {
	Logger logger = Logger.getLogger(ScoreRecordServiceImpl.class);

    @Resource
    private ScoreRecordDao scoreRecordDao;


	@Override
	public int delete(Long id) {
		return scoreRecordDao.delete(id);
	}

	@Override
	public int insert(ScoreRecordEntity scoreRecordEntity) {
		return scoreRecordDao.insert(scoreRecordEntity);
	}

	@Override
	public ScoreRecordEntity select(Long id) {
		return scoreRecordDao.select(id);
	}

	@Override
	public List<ScoreRecordEntity> selectAll() {
		return scoreRecordDao.selectAll();
	}

	@Override
	public int update(ScoreRecordEntity scoreRecordEntity) {
		return scoreRecordDao.update(scoreRecordEntity);
	}


	@Override
	public boolean insertForJob(ScoreRecordEntity scoreRecordEntity) {
		scoreRecordEntity.setCreateTime(new Date());
		scoreRecordDao.insert(scoreRecordEntity);
		return true;
	}
}
