package com.umessage.letsgo.service.api.security;

import com.jrmf360.vo.response.WalletCommonResponse;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.LeaderEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.notice.request.InformationRequest;
import com.umessage.letsgo.domain.vo.notice.respone.InformationResponse;
import com.umessage.letsgo.domain.vo.security.request.DeviceRequest;
import com.umessage.letsgo.domain.vo.security.respone.TXAccountResponse;
import com.umessage.letsgo.domain.vo.security.respone.TagListResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserBindingWxResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.system.request.CheckRegisterRequest;
import com.umessage.letsgo.domain.vo.system.request.CheckRequest;
import com.umessage.letsgo.domain.vo.system.request.LogManageRequest;
import com.umessage.letsgo.domain.vo.system.request.RegisterRequest;
import com.umessage.letsgo.domain.vo.system.respone.UnreadAndUnconfirmedResponse;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IUserService {
	int create(UserEntity user);

	int delete(UserEntity user);

	int update(UserEntity user);

	void memberUpdateForUser(MemberEntity member);
	void leaderUpdateForUser(LeaderEntity leader);
	
	UserEntity getUserById(Long id);

	UserEntity getUserByInviteCode(String code);

	/**
	 * 通过登录账号获取当前登录的用户，带缓存
     */
	UserResponse getCurrentUser(String loginAccount);
	/**
	 * 通过登录账号获取当前登录的用户，不带缓存
	 */
	UserResponse getUserByLoginAccount(String loginAccount);

	UserResponse getUserByUsername(String username, Long userId);

	UserEntity getUserByPhone(String phone);

	UserEntity createAccountByTeamMember(String loginAccount);

	UserEntity createLeaderAccount(RegisterRequest request);

	CommonResponse setPassword(UserEntity user, String password, String code);

	CommonResponse modifyPassword(UserEntity user, String oldPassword, String newPassword);

	CommonResponse retrievePassword(String phone, String newPassword);

	TXAccountResponse updateDevice(DeviceRequest request, UserEntity user);

	InformationResponse getInformationResponse(InformationRequest request);

	CommonResponse sendInvitationMessage(Long memberId, String phone) throws UnsupportedEncodingException;

	CommonResponse sendInvitationMessage2(Long memberId, String phone, UserEntity user) throws UnsupportedEncodingException;

	/**
	 * 获取正在出行的用户。
     */
	List<UserEntity> getUsersIsTravel(Integer status);

	UnreadAndUnconfirmedResponse getUnreadCountAndUnconfirmedCount(List<Integer> types, String teamId, Long userId);
	/**
	 IM交互：发送邀请加入信息
	 */
	void sendInvitationMessage(MemberEntity member, String teamId);

	/**
	 * 领队注册
	 * @param request
	 * @return
     */
	CommonResponse registerLeader(RegisterRequest request);

	/**
	 * 验证表单中的验证码及邀请码
	 * @param request
     * @return
     */
	CommonResponse checkInviteCodeAndSecurityCode(CheckRequest request);


	/**
	 * 验证表单中的验证码及邀请码及手机
	 * @param request
	 * @return
	 */
	CommonResponse checkInviteCodeAndVerificationCode(CheckRegisterRequest request);

	// 手机号是否存在
	boolean existPhone(String phone);

	// 是否存在邀请码
	boolean existInvitationCode(String invitationCode);
	//上传用户头像
	CommonResponse uploadFacePhoto(UserEntity userEntity,String photoUrl);

	Long findUserId(String txId);

	CommonResponse saveTag(Long currentUserId, String tagName, String userIds);

	TagListResponse queryTagList(Long id);

	UserResponse selectByphone(String phone);

	UserEntity selectUserIfoByphone(String phone);

	//通过腾讯组id获取用户对象
	public UserEntity selecUserByUserName(String userName);

	boolean checkUserRole(String clientId, String phone);

	WalletCommonResponse openWalletForUser(UserEntity user);

	boolean checkcheckCode(String checkcode,String inviteCode);

	WalletCommonResponse batchOpenWalletForUser();

	com.qq.tim.vo.response.CommonResponse batchPortraitSetAllowType();

	WalletCommonResponse batchCreateInvitationCode();

	/**
	 * 用户注册
	 * @param request
	 * @return
     */
	CommonResponse registerUser(RegisterRequest request);

	/**
	 * 修改擅长字段
	 * @param userId
	 * @param type
	 * @param content
	 * @return
     * @throws Exception
     */
	CommonResponse updateUserContent(Long userId, String type, String content)throws Exception;
	//根据旅行社ID 以及旅行社用户角色 获取用户userID集合
	 List<Long> getUserIdsByTravelerId(LogManageRequest request);
	UserEntity getCurrentUserEntity();

	//根据用户ID 获取用户角色
	UserEntity getUserRole(Long userId);
	//用户绑定微信接口
	UserBindingWxResponse toBindingFirst(String openId,String  utoken,String  phone,String  code);
	//用户绑定微信完善资料接口
	UserBindingWxResponse toBindingSecond(String openId,String  utoken,String  phone,String  name,Integer sex,String invitationCode);
}
