package com.umessage.letsgo.service.api.notice;

import com.umessage.letsgo.domain.po.notice.NoticeDetailEntity;
import com.umessage.letsgo.domain.po.notice.NoticeSignEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleUnreadResponse;
import com.umessage.letsgo.domain.vo.notice.request.DetailParamRequest;
import com.umessage.letsgo.domain.vo.notice.request.DetailRequest;
import com.umessage.letsgo.domain.vo.notice.request.NoticeSignRequest;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeDetailResponse;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeSignDetailResponse;

import java.util.List;

public interface INoticeDetailService {
	// 新建通知明细
	int addNoticeDetail(NoticeDetailEntity noticeDetailEntity);
	
	// 更新通知明细
	int updateNoticeDetail(NoticeDetailEntity noticeDetailEntity);

	// 查找单个通知明细实体
	NoticeDetailEntity getNoticeDetail(Long noticeId, Long memberId,Integer type);
	
	// 根据查询条件获取通知明细列表
	List<NoticeDetailEntity> getNoticeDetailListByConditions(DetailParamRequest request);

	// 根据分类，获取未读数量
	int getUnreadCount(int type, Long userId, String teamId);

	// 根据分类，获取未确认数量
	int getUnconfirmedCount(int type, Long userId, String teamId);

	// 确认通知
	CommonResponse comfirmNotice(DetailRequest detailRequest);

	// 获取通知明细列表
	NoticeDetailResponse getNoticeDetilList(Long noticeId,Integer type);

	// 更新未读数据
	CommonResponse updateUnReads(DetailParamRequest request);

	// 根据条件查询领队未读信息（集合、通知、公告）游客
	int getNumsByTypeAndUserId(int type, Long userId,String txgroupId);

	// 根据条件查询领队未读信息（集合、通知、公告）领队
	int getNumsByTypeAndLeaderUserId(int type, Long userId,String txgroupId);

	int updateNotClick(Long userId, String teamId, Integer teamStatus);

	// 根据用户ID和行程团队状态统计操作数量
	int getNotClickCountByUserAndStatus(Long userId, Integer status, Integer type);

	int getTotalCountByUserId(Long userId, Integer type);

	ScheduleUnreadResponse getScheduleUnread(Long userId);

	//根据类型获取最新的提醒ID
	Long getNewNoticeId(DetailParamRequest request);

	//根据成员id查询通知或集合
	List<NoticeDetailEntity> getNoticeDetailByMemberId(Long memberId, Integer type);

	//删除通知明细
	int delete(Long id);

	Integer getUnReadNum(int i, Long userId, String txGroupId, MemberEntity memberEntity);

	/**
	 * 获取通知详情签名信息
	 * @param teamId
	 * @param noticeId
	 * @param roleStatus
     * @return
     */
	NoticeSignDetailResponse getNoticeSignInfo(String teamId, Long noticeId, Integer roleStatus, UserEntity user);

	/**
	 * 根据detailId获取NoticeSignEntity
	 * @param id
	 * @return
     */
	List<NoticeSignEntity> getNoticeSignByDetailId(Long id);

	/**
	 * 保存签名图片
	 * @param noticeSignRequest
	 * @return
     */
	CommonResponse saveSignImages(NoticeSignRequest noticeSignRequest,UserEntity user);
}
