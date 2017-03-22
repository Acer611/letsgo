package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.TouristResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserAuthorityResponse;
import com.umessage.letsgo.domain.vo.team.requset.MemberAddRequest;
import com.umessage.letsgo.domain.vo.team.requset.MemberRequest;
import com.umessage.letsgo.domain.vo.team.respone.MemberListResponse;
import com.umessage.letsgo.domain.vo.team.respone.MemberResponse;

import java.util.List;

public interface IMemberService {
	// 新增成员
	int addMember(MemberEntity memberEntity);

	// 删除成员
	int deleteMember(MemberEntity memberEntity);

	// 更新成员
	int updateMember(MemberEntity memberEntity);

	// 根据查询条件，获取成员列表
	List<MemberEntity> getMemberList(MemberRequest request);

	// 根据主键ID，获取单个成员
	MemberEntity getMember(Long id);

	// 根据团队的腾讯群组ID和用户ID，获取团队中的某一个成员
	MemberEntity getMemberWithTeamIdAndUserId(String teamId, Long userId);
	// 根据团队的ID和用户ID，获取团队中的某一个成员
	MemberEntity getMemberWithTIdAndUserId(Long teamId, Long userId);

	// 获取某个团队的领队
	List<MemberEntity> getLeader(String teamId);
	//获取某个团队中的领队通过团队id
	List<MemberEntity> getLeaderByTId(Long teamId);
	//获取某个团队中的领队的用户ID通过团队id
	List<Long> getLeaderUserIdByTId(Long teamId);
	// 根据用户ID，获取用户参加的腾讯群组ID
	List<String> getTeamIdsByUserId(Long userId);

	List<String> getTeamIdsByUserIdAndAdmin(Long userId);

	// 获取当前用户的成员列表
	List<MemberEntity> getMemberListByUserId(Long userId);

	// 获取该团队的所有成员列表
	List<MemberEntity> getMemberListByTeamId(String teamId);

	// 获取该团队的所有成员列表
	List<MemberEntity> getMemberListByTId(Long tId);

	// 获取游客列表
	List<MemberEntity> getTouristList(String teamId);

	//新的获取团队的游客列表（无论角色是领队还是导游发公告，除了自己本身之外，团队的其他人都要接收到公告。）
	List<MemberEntity> getTouristListOne(String teamId,Long userId);


	// 获取游客列表
	List<MemberEntity> getTouristList(Long tId);
	//获取团队的游客列表的用户ID
	List<Long> getMemberUserIdList(Long tId);

	// 获取导游
	MemberEntity getMemberForGuide(Long teamId, String phone);
	// 获取领队&导游列表
	List<MemberEntity> getTourGuideList(String teamId, Integer isAdmin);

	List<MemberEntity> getTourGuideList(Long teamId, Integer isAdmin);

	// 根据小组ID，获取小组的组员列表
	List<MemberEntity> getGroupMembertList(Long groupId);

	// 判断该手机号的用户是否参团
	List<MemberEntity> hasMemberInTeam(String phone);

	// 更改成员的管理状态
	CommonResponse setAdministrator(Long id, Integer isAdmin);

	// 获取团队成员的详情
	MemberResponse getMemberDetail(Long id, Long userId);

	// 给游客设置备用手机号码
	CommonResponse setMarkPhone(Long id, String phone);
	// 判断手机号在团队中重复
	boolean memberPhoneIsRepeat(String phone,Long tId);
	// 判断手机号在所有游客中重复
	boolean memberPhoneIsRepeat(String phone);
	// 判断手机号在所有领队或导游中重复
	boolean leaderPhoneIsRepeat(String phone,Long tId);

	UserAuthorityResponse getUserAuthority(String teamId, Long userId);

	List<MemberEntity> getLeaderMembers(Long tId);

	// 获取某个团队中的有账号的游客的账号ID
	List<Long> getUserIdInTId(Long tId);

	List<MemberEntity> getMemberByTIds(List<Long> tIds);

	// 分析数据
	TouristResponse analyzeTourist(List<Long> tIds);

	// 根据userId获取团队ID集合
	List<Long> getTeamIds(Long userId);

	// 获取不同行程状态中的游客数量
	int getMemberCountInTravel(Long travelId, Integer status);

	int insertMember(MemberEntity memberEntity);

	//领队可修改行程 游客不可修改行程
	MemberEntity getMemberRole(String teamId, Long userId);

	//获取没有电话号码的团员
	MemberListResponse getNoPhoneMembers(Long teamId);

    //通过扫描二维码快速入团
	CommonResponse addMemberByQR(MemberAddRequest request);
	CommonResponse updateMemberByQR(MemberAddRequest request);

	//根据成员ID移除团队
	CommonResponse deleteMemberById(Long id, UserEntity user);

	//根据分房的房间ID查询成员
	List<MemberEntity> getByRoomId(Long roomId);

	/**
	 * 根据姓名性别和团号查找用户
	 * @param name
	 * @param sex
	 * @param teamId
     * @return
     */
	MemberEntity findMemberByNameSexAndTeamID(String name,int sex,long teamId);

	/**
	 * 根据行程状态和行程时间查询成员
	 * @return
     */
	List<MemberEntity> selectMemberByScheduleDate();


	List<MemberEntity> selectMemberByScheduleDetailId(Long sdId, Integer role);

	/**
	 * 根据团队id查询所有团队成员手机号
	 */
	List<String> selectMemberByTidConditons(MemberRequest request);

	//根据行程结束日期查询行程已经结束的成员
	List<MemberEntity> selectMemberByScheduleEnd();

	//根据团队id和用户id获取团队成员的详情
	MemberResponse getMemberDetailByUserId(String teamId, Long mUserId, Long userId);

	//根据团队id和username获取团队成员的详情
	MemberResponse getMemberDetailByUserName(String teamId, String userName, Long userId);

}
