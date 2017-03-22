package com.umessage.letsgo.dao.journey;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.umessage.letsgo.domain.po.journey.StaticsConsumptionInfo;
import com.umessage.letsgo.domain.po.journey.TallyBookEntity;


public interface TallyBookDao {

	int insert(TallyBookEntity entity);

	List<TallyBookEntity> selectConsumptionList(@Param(value="txGroupId")String txGroupId,@Param(value="typeId")Integer typeId,@Param(value="userId") Long userId);

	List<StaticsConsumptionInfo> statisticsTotalConsumptionList(@Param(value="txGroupId")String txGroupId,@Param(value="userId")Long userId);

}
