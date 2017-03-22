package com.umessage.letsgo.domain.vo.team.requset;

import com.umessage.letsgo.domain.po.team.ShoppingAgencyEntity;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

import java.util.List;

public class ShoppingAgencyRequest extends CommonRequest {

    private List<ShoppingAgencyEntity> shoppingAgencys;

	public List<ShoppingAgencyEntity> getShoppingAgencys() {
		return shoppingAgencys;
	}

	public void setShoppingAgencys(List<ShoppingAgencyEntity> shoppingAgencys) {
		this.shoppingAgencys = shoppingAgencys;
	}
}
