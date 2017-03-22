package com.umessage.letsgo.domain.vo.system.respone;

import com.umessage.letsgo.domain.po.system.AppVerEntity;
import com.umessage.letsgo.domain.po.system.BaseDataVerEntity;
import io.swagger.annotations.ApiModelProperty;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

public class LasterVerResponse extends CommonResponse {
	/**
     * App版本信息
     */
    @ApiModelProperty(value="App版本信息")
	private AppVerEntity appVerEntity;
	
    /**
     * 基础数据版本信息
     */
    @ApiModelProperty(value="基础数据版本信息")
	private BaseDataVerEntity baseDataVerEntity;

	public AppVerEntity getAppVerEntity() {
		return appVerEntity;
	}

	public void setAppVerEntity(AppVerEntity appVerEntity) {
		this.appVerEntity = appVerEntity;
	}

	public BaseDataVerEntity getBaseDataVerEntity() {
		return baseDataVerEntity;
	}

	public void setBaseDataVerEntity(BaseDataVerEntity baseDataVerEntity) {
		this.baseDataVerEntity = baseDataVerEntity;
	}

	@Override
	public String toString() {
		return "{"
				+ "                        \"appVerEntity\":" + appVerEntity
				+ ",                         \"baseDataVerEntity\":" + baseDataVerEntity
				+ "}";
	}
}
