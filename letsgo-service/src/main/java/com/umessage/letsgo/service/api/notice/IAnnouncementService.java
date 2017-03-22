package com.umessage.letsgo.service.api.notice;

import com.umessage.letsgo.domain.po.notice.AnnouncementEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.notice.request.AnnouRequest;
import com.umessage.letsgo.domain.vo.notice.request.AnnouncementRequest;
import com.umessage.letsgo.domain.vo.notice.respone.AnnouncementResponse;

import java.util.List;

/**
 * 公告Service接口
 * 
 * @author Administrator
 * 
 */
public interface IAnnouncementService {
	// 新增公告
	int addAnnouncementEntity(AnnouncementEntity announcementEntity);

	// 获取公告列表
	List<AnnouncementEntity> getAnnouncementList(AnnouRequest request);

	// 发布公告
	CommonResponse saveAnnouncement(AnnouncementRequest announcementRequest) throws Exception;

	// 公告列表
	AnnouncementResponse getAnnouncementList(String teamId, Long userId);

	int getAnnouncementCount(Long userId, Long annouId);

	AnnouncementEntity getLatestAnnouncement(Long userId, String teamId);
	//获取最新公告
	AnnouncementEntity selectAnnouncementByUserIdAndTeamId(Long userId, String teamId);
	//获取最新公告游客
	AnnouncementEntity  selectAnnouncementByUserIdAndTeamIdToTourist(Long userId, String teamId);

	AnnouncementEntity getById(Long id);

	AnnouncementEntity selectWxAnnouncementById(Long id);
}
