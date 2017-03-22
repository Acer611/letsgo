package com.umessage.letsgo.domain.vo.team.respone;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.AnalyticalData;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

public class AnalyticalDataResponse extends CommonResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7731838093034229736L;

	private AnalyticalData currentUser;

	private Page<AnalyticalData> analyticalDataList;

	public static AnalyticalDataResponse createUserNotLoginResponse(){
		class UserNotLoginResponse extends AnalyticalDataResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.USER_NOT_LOGIN;
			}

			@Override
			public String getRetMsg() {
				return "用户未登录或登录信息过期";
			}
		}

		return new UserNotLoginResponse();
	}

	public AnalyticalData getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(AnalyticalData currentUser) {
		this.currentUser = currentUser;
	}

	public Page<AnalyticalData> getAnalyticalDataList() {
		return analyticalDataList;
	}

	public void setAnalyticalDataList(Page<AnalyticalData> analyticalDataList) {
		this.analyticalDataList = analyticalDataList;
	}
	
}
