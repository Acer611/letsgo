package com.umessage.letsgo.domain.vo.security.respone;

import java.util.List;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.request.UserVo;

public class UserListResponse extends CommonResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1544911768232960878L;
	
	private List<UserVo> userList;
    private long totals;
    private int pages;

	public List<UserVo> getUserList() {
		return userList;
	}

	public void setUserList(List<UserVo> userList) {
		this.userList = userList;
	}

	public long getTotals() {
		return totals;
	}

	public void setTotals(long totals) {
		this.totals = totals;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
}
