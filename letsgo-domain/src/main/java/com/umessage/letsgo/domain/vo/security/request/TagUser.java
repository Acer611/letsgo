package com.umessage.letsgo.domain.vo.security.request;

import java.util.List;

import com.umessage.letsgo.domain.po.security.UserEntity;

public class TagUser {

	private String tagName;
	
	private Integer count;
	
	private List<UserEntity> users;

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}
	
}
