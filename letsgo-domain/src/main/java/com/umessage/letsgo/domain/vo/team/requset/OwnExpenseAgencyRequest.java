package com.umessage.letsgo.domain.vo.team.requset;

import com.umessage.letsgo.domain.po.team.OwnExpenseAgencyEntity;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

import java.util.List;

public class OwnExpenseAgencyRequest extends CommonRequest {

    private List<OwnExpenseAgencyEntity> ownExpenseAgencys;

	public List<OwnExpenseAgencyEntity> getOwnExpenseAgencys() {
		return ownExpenseAgencys;
	}

	public void setOwnExpenseAgencys(List<OwnExpenseAgencyEntity> ownExpenseAgencys) {
		this.ownExpenseAgencys = ownExpenseAgencys;
	}
}
