package com.umessage.letsgo.service.api.notice;

import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.notice.request.NoticeParamRequest;
import com.umessage.letsgo.domain.vo.notice.request.NoticeRequest;
import com.umessage.letsgo.domain.vo.notice.respone.LastMessageResponse;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeListResponse;

import java.util.List;

/**
 * 通知Service接口
 * 
 * @author Administrator
 * 
 */
public interface INoticeService {
	// 新建通知
	int insertNotice(NoticeEntity noticeEntity);

	// 更新通知
	int updateNotice(NoticeEntity noticeEntity);

	// 根据ID，获取单条通知
	NoticeEntity getNotice(Long id);

	// 获取通知列表
	List<NoticeEntity> getNoticesWithConditions(NoticeParamRequest request);

	// 获取未确认通知列表
	List<NoticeEntity> getUnconfirmNoticeList(List<String> teamIds, List<Long> memberIds, Integer type);

	// 获取未读通知列表
	List<NoticeEntity> getUnreadNoticeList(List<String> teamIds, List<Long> memberIds, Long noticeId, Integer type);

	// 保存通知
	CommonResponse saveNotice(NoticeRequest noticeRequest) throws Exception;

	// 获取通知列表
	NoticeListResponse getNoticeList(String teamId, UserEntity user, Integer type);
	// 标记已读
	CommonResponse markReads(String teamId, UserEntity user, Integer type);
	CommonResponse remindNoctice(Long noticeId, List<Long> memberIds) throws Exception;

	int getNoticeCount(Long userId, Long noticeId, Integer type);

	NoticeEntity getLatestNotice(Long userId, Integer type, String teamId);

	//获取最新消息领队端 、游客端
	LastMessageResponse getLastMessage(NoticeRequest request);

	NoticeEntity getWxGatherById(Long id,Long userID);

	NoticeEntity getWxGatherByIdOne(Long id);

	/**
	 * 获取通知列表
	 * @param teamId 腾讯组ID
	 * @param userEntity 当前用户的实体
	 * @param type 通知的类型 2
     * @return
     */
	NoticeListResponse getNoticeMessages(String teamId, UserEntity userEntity, int type);

	/**
	 * 更新签名图片数
	 * @param notice
     */
	void updateNoticeSignCount(NoticeEntity notice);
}
