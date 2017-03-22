package com.umessage.letsgo.service.api.notice;

import com.umessage.letsgo.domain.po.notice.RemindEntity;

import java.util.List;

/**
 * 提醒Service接口
 * @author Administrator
 *
 */
public interface IRemindService {
	// 新建提醒
	int addRemind(RemindEntity remindEntity);

	int updateRemind(RemindEntity remindEntity);
	
	// 获取单个提醒
	RemindEntity getRemindEntity(Long id);

	List<RemindEntity> getRemindList();

	boolean remindNoticeToMember(RemindEntity remindEntity);
}
