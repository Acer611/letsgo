package com.umessage.letsgo.domain.vo.system.respone;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.system.AppVerEntity;
import com.umessage.letsgo.domain.po.system.BaseDataVerEntity;
import com.umessage.letsgo.domain.po.system.LogManage;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

public class LogManageResponse extends CommonResponse {
    //日志对象集合
	private Page<LogManage> logManageList;
	//总条数
	private long totals;
	//总页数
	private int pages;

	public Page<LogManage> getLogManageList() {
		return logManageList;
	}

	public void setLogManageList(Page<LogManage> logManageList) {
		this.logManageList = logManageList;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public long getTotals() {
		return totals;
	}

	public void setTotals(long totals) {
		this.totals = totals;
	}

	public static LogManageResponse createNotFoundResponse(String retMsg){
		class NotFoundResponse extends LogManageResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.NOT_FOUND;
			}
		}

		LogManageResponse response = new NotFoundResponse();
		response.setRetMsg(retMsg);
		return response;
	}
}
