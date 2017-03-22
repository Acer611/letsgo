package com.umessage.letsgo.service.impl.security;

import com.jrmf360.IStandardWallet;
import com.jrmf360.vo.request.OpenWalletRequest;
import com.jrmf360.vo.response.WalletCommonResponse;
import com.qq.tim.ITimRestAPI;
import com.qq.tim.constant.TimConstant;
import com.qq.tim.util.TlsSignUtil;
import com.qq.tim.vo.request.*;
import com.qq.tim.vo.response.AddFriendResponse;
import com.qq.tim.vo.response.AddGroupMemberResponse;
import com.qq.tim.vo.response.PortraitSetResponse;
import com.tls.tls_sigature.tls_sigature;
import com.umessage.letsgo.core.annotation.dataitem.DataItem;
import com.umessage.letsgo.core.config.constant.ConfigConstant;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.Base32Util;
import com.umessage.letsgo.core.utils.JsonUtils;
import com.umessage.letsgo.core.utils.ShaPasswordUtil;
import com.umessage.letsgo.dao.security.UserDao;
import com.umessage.letsgo.dao.security.UserTagDao;
import com.umessage.letsgo.dao.security.WxInfoDao;
import com.umessage.letsgo.domain.po.notice.AnnouncementEntity;
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.po.security.*;
import com.umessage.letsgo.domain.po.team.LeaderEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.OnlookersUserEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.common.respone.CustomMessageResponse;
import com.umessage.letsgo.domain.vo.common.respone.CustomMsg;
import com.umessage.letsgo.domain.vo.notice.request.InformationRequest;
import com.umessage.letsgo.domain.vo.notice.respone.InformationResponse;
import com.umessage.letsgo.domain.vo.security.request.DeviceRequest;
import com.umessage.letsgo.domain.vo.security.respone.*;
import com.umessage.letsgo.domain.vo.system.request.*;
import com.umessage.letsgo.domain.vo.system.respone.Information;
import com.umessage.letsgo.domain.vo.system.respone.Message;
import com.umessage.letsgo.domain.vo.system.respone.UnreadAndUnconfirmedResponse;
import com.umessage.letsgo.service.api.notice.IAnnouncementService;
import com.umessage.letsgo.service.api.notice.INoticeDetailService;
import com.umessage.letsgo.service.api.notice.INoticeService;
import com.umessage.letsgo.service.api.security.*;
import com.umessage.letsgo.service.api.system.IMessageService;
import com.umessage.letsgo.service.api.team.ILeaderService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.IOnlookersUserService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.helper.QCloudHelper;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements IUserService {
	@Resource
	private UserDao userDao;
	@Resource
	private UserTagDao userTagDao;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private IRoleService roleService;
	@Resource
	private IValidationCodeService validationCodeService;
	@Resource
	private IMemberService memberService;
	@Resource
	private ITeamService teamService;
	@Resource
	private INoticeService noticeService;
	@Resource
	private IAnnouncementService announcementService;
	@Resource
	private INoticeDetailService noticeDetailService;
	@Resource
	private IDeviceService deviceService;
	@Resource
	private IMessageService messageService;
	@Resource
	private ITimRestAPI timRestAPI;
	@Resource
	private Mapper dozerBeanMapper;
	@Resource
	private ShaPasswordEncoder shaPasswordEncoder;
	@Resource
	private IInvitationCodeService invitationCodeService;
	@Resource
	private ITagsService tagsService;
	@Resource
	private IFriendsService friendsService;
	@Resource
	private ILeaderService leaderService;
	@Resource
	private IRewardDetailsService rewardDetailsService;
	@Resource
	private IStandardWallet iStandardWallet;
	@Resource
	private IOnlookersUserService onlookersUserService;
	@Resource
	private UserLoginHelper userLoginHelper;
	@Resource
	private RestTemplate restTemplate;
	@Resource
	private WxInfoDao wxDao;
	@Resource
	private IThirdPartyAccountService thirdPartyAccountService;

	private Logger logger = Logger.getLogger(UserServiceImpl.class);
   @Transactional
	public int create(UserEntity user) {
		user.setInviteCount(0);
	   	user.setTotalReward(0.0);
		user.setCreateTime(new java.util.Date());
		user.setVersion(0l);
	   	user.setLoginCount(0);
	   	user.setLoginTotalCount(0);
		return userDao.insert(user);
	}

	@Caching(evict = {
			@CacheEvict(value = "userCache", key = "#user.userName"),
			@CacheEvict(value = "userCache", key = "#user.phone"),
			@CacheEvict(value = "userCache", key = "#user.mail") })
	public int delete(UserEntity user) {
		return userDao.delete(user.getId());
	}

	@Caching(evict = {
			@CacheEvict(value = "userCache", key = "#user.userName"),
			@CacheEvict(value = "userCache", key = "#user.phone"),
			@CacheEvict(value = "userCache", key = "#user.mail") })
	public int update(UserEntity user) {
		user.setUpdateTime(new java.util.Date());
		System.out.println(user.toString());
		return userDao.update(user);
	}

	@Override
	public void memberUpdateForUser(MemberEntity member){
		Long userId = member.getUserId();
		if(userId != null && userId != -1){
			UserEntity user = getUserById(userId);
			user.setRealName(member.getRealName());
			user.setPhone(member.getPhone());
			user.setPhotoUrl(member.getPhotoUrl());
			user.setSex(member.getSex());
			user.setBirthday(member.getBirthday());
			update(user);
		}
	}

	@Override
	public void leaderUpdateForUser(LeaderEntity leader){
		Long userId = leader.getUserId();
		if(userId != null && userId != -1){
			UserEntity user = getUserById(userId);
			user.setRealName(leader.getName());
			user.setPhone(leader.getPhone());
			user.setPhotoUrl(leader.getPhotoUrl());
			user.setSex(leader.getSex());
			user.setBirthday(getBirthday(leader.getBirthday()));
			update(user);
		}
		List<MemberEntity> memberList = memberService.getLeaderMembers(leader.getId());
		for (MemberEntity member: memberList){
			member.setRealName(leader.getName());
			member.setPhone(leader.getPhone());
			member.setPhotoUrl(leader.getPhotoUrl());
			member.setSex(leader.getSex());
			member.setBirthday(getBirthday(leader.getBirthday()));
			memberService.updateMember(member);
		}
	}

	public String getBirthday(Date date){
		if (date == null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	@Override
	public UserEntity getUserById(Long id) {
		UserEntity userEntity = userDao.select(id);
		if (userEntity == null)
			return new UserEntity();
		return userEntity;
	}

	@Override
	public UserEntity getUserByInviteCode(String code) {
		UserEntity userEntity = userDao.getUserByInviteCode(code);
		if (userEntity == null) {
			return new UserEntity();
		}
		return userEntity;
	}

	@Override
	public UserEntity getUserByPhone(String phone){
		return userDao.selectWithAccount(phone);
	}

	@Override
	@Cacheable(value = "userCache", key = "#loginAccount", unless="#result.userEntity == null")
	@DataItem
	public UserResponse getCurrentUser(String loginAccount){
		return getUserByLoginAccount(loginAccount);
	}

	@Override
	@DataItem
	public UserResponse getUserByLoginAccount(String loginAccount) {
		UserEntity userEntity = userDao.selectWithAccount(loginAccount);
		if (userEntity == null) {
			return UserResponse.createNotFoundResponse();
		}

		// 判断用户是否有密码
		this.getIsPassword(userEntity);

		// 获取用户的证件信息
		this.getCardList(userEntity);

		// 获取用户身份信息
		List<UserRoleEntity> userRoleEntityList = userRoleService.getUserRoleByUserId(userEntity.getId());
		List<RoleEntity> roleList = new ArrayList<>();
		for (UserRoleEntity userRoleEntity : userRoleEntityList) {
			RoleEntity role = roleService.get(userRoleEntity.getRoleId());
			roleList.add(role);
		}
		userEntity.setRoleList(roleList);

		// 获取擅长线路、擅长语种、自我评价
		for (UserRoleEntity userRoleEntity : userRoleEntityList) {
			if (userRoleEntity.getRoleId() == 1 || userRoleEntity.getRoleId() == 2) {
				userEntity.setUserInfo(userEntity);
				break;
			}
		}

		// 处理实体对象成VO对象，方便前台展示
		UserResponse userResponse = new UserResponse();
		//保存二维码信息
		userEntity.setQrURL(ConfigConstant.QR_URL);
		userEntity.setQrType("u");//二维码类型：u：个人中心二维码用于通讯录加好友
		userEntity.setQrParam(userEntity.getUserName());
		userResponse.setUserEntity(userEntity);

		// 设置邀请码
		String inviteCode = userEntity.getInviteCode();
		userResponse.setInviteUrl(Constant.API_REGISTER_GETINVITECODE_URL + inviteCode);
		return userResponse;
	}

	private void getIsPassword(UserEntity userEntity) {
		if (StringUtils.isEmpty(userEntity.getPassword())) {
			userEntity.setIsPass(0);
		} else {
			userEntity.setIsPass(1);
		}
	}

	private void getRoleList(UserEntity userEntity, List<UserRoleEntity> userRoleEntityList) {
		List<RoleEntity> roleList = new ArrayList<>();
		for (UserRoleEntity userRoleEntity : userRoleEntityList) {
			RoleEntity role = roleService.get(userRoleEntity.getRoleId());
			roleList.add(role);
		}
		userEntity.setRoleList(roleList);
	}

	/**
	 * 获取该用户的所有证件信息
	 * @param userEntity
	 */
	private void getCardList(UserEntity userEntity) {
		// 获取用户的证件信息
		List<MemberEntity> memberList = memberService.getMemberListByUserId(userEntity.getId());
		List<CardResponse> cards = new ArrayList<>();
		for (MemberEntity mem : memberList) {
			if (StringUtils.isEmpty(mem.getCardType())) {
				continue;
			}
			CardResponse card = new CardResponse();
			card.setCardType(mem.getCardType());
			card.setCardNum(mem.getCardNum());
			card.setCardTime(mem.getCardTime());
			cards.add(card);
		}
		// 去重
		List<CardResponse> lastCards = new ArrayList<>(new HashSet(cards));
		userEntity.setCardInfoList(lastCards);
	}

	@Override
	public UserResponse getUserByUsername(String username, Long userId) {
		UserEntity userEntity = userDao.selectWithAccount(username);
		if (userEntity == null) {
			return UserResponse.createNotFoundResponse();
		}

		// 判断用户是否有密码
		this.getIsPassword(userEntity);

		// 获取用户的证件信息
		this.getCardList(userEntity);

		// 获取用户身份信息
		List<UserRoleEntity> userRoleEntityList = userRoleService.getUserRoleByUserId(userEntity.getId());
		this.getRoleList(userEntity, userRoleEntityList);

		// 如果是领队或者导游，获取擅长线路、擅长语种、自我评价
		for (UserRoleEntity userRoleEntity : userRoleEntityList) {
			if (userRoleEntity.getRoleId() == 1 || userRoleEntity.getRoleId() == 2) {
				userEntity.setUserInfo(userEntity);
				userEntity.setRole(userRoleEntity.getRoleId().intValue());
				break;
			}else{
			userEntity.setRole(3);
		    }
		}

		// 判断当前登录用户和查看详情用户是否为好友关系
		FriendsEntity friendsEntity = friendsService.getFriendByUserId(userId, username);
		if (friendsEntity != null) {
			// 为好友关系
			userEntity.setIsFriend(1);
			userEntity.setFriendType(friendsEntity.getFriendType());

			// 获取标签
			List<String> tagNameList = new ArrayList<>();
			tagNameList = tagsService.getTagNameByUserId(userEntity.getId(), userId);
			userEntity.setTagList(tagNameList);
		} else {
			// 不是好友
			userEntity.setIsFriend(0);
		}

		// 获取同团记录
		List<String> teamList = new ArrayList<>();
		teamList = teamService.getSameTeamByUserId(userEntity.getId(), userId);
		userEntity.setSameTeamList(teamList);

		UserResponse response = new UserResponse();
		response.setUserEntity(userEntity);
		return response;
	}

    @Transactional
	public UserEntity createAccountByTeamMember(String loginAccount) {
		UserEntity userEntity = new UserEntity();
		List<MemberEntity> members = memberService.hasMemberInTeam(loginAccount);
		List<OnlookersUserEntity> onlookersUserList = onlookersUserService.getOnlookersUserByPhone(loginAccount);
		//判断手机号用户是否参团 或者 该用户是否被邀请围观
		if (CollectionUtils.isEmpty(members) && CollectionUtils.isEmpty(onlookersUserList)) {
			return userEntity;
        }

		UserEntity user = null;
		if (!CollectionUtils.isEmpty(members)){
			// 1、根据团队成员创建App用户
			user = this.createUserByMembers(members);

			// 2、创建腾讯云通讯的用户
			this.createTXUser(user);

			// 3、把腾讯用户加到对应的团队群组中，并添加好友关系
			this.addAllTXGroup(members, user);

			// 4、创建并关联领队
			/*
			Integer leadType = getLeaderType(members);
			if (leadType > 0){
				leaderService.associateLeaderAndUser(user, leadType);
			}
			*/
			// 5、为用户创建钱包
			this.openWalletForUser(user);

			//6、新创建用户 判断是否被邀请围观    如果被邀请了则关联围观
			onlookersUserService.setUserId(user);

		} else if (!CollectionUtils.isEmpty(onlookersUserList)){
			// 1、根据围观邀请创建App用户
			user = this.createOnlookerUserByRequest(onlookersUserList.get(0));
			// 2、创建腾讯云通讯的用户
			this.createTXUser(user);

			// 3、为用户创建钱包
			this.openWalletForUser(user);

			//4、新创建用户 判断是否被邀请围观    如果被邀请了则关联围观
			onlookersUserService.setUserId(user);
		}

		return user;
	}

	private int getLeaderType(List<MemberEntity> members){
		int result  = 0;
		Set<Integer> roles = new HashSet<Integer>();
		for (MemberEntity member : members
			 ) {
			if ((member.getRole() == 1) || (member.getRole() == 2)){
				roles.add(member.getRole());
			}
		}

		if (roles.contains(1) && roles.contains(2)){
			result = 3;
		} else if (roles.contains(1)){
			result = 1;
		} else if (roles.contains(2)){
			result = 2;
		}

		return result;
	}

	/**
	 * IM交互（操作腾讯云通讯）：根据团队成员创建App用户
	 * @param members
	 * @return
	 */
	private UserEntity createUserByMembers(List<MemberEntity> members) {
		// 1、新增用户对象，并保存
		UserEntity newUser = new UserEntity();
		newUser.setPhone(members.get(0).getPhone());
		newUser.setRealName(members.get(0).getRealName());
		newUser.setUserName(Base32Util.encode(System.currentTimeMillis()+members.get(0).getPhone()));    // 根据手机号，经过一定规则算法，生成用户名
		newUser.setPhotoUrl(members.get(0).getPhotoUrl());
		newUser.setSex(members.get(0).getSex());
		newUser.setBirthday(members.get(0).getBirthday());
		newUser.setType(members.get(0).getType());
		newUser.setUserStatus(1);//可用
		this.create(newUser);

		// 2、创建用户邀请码
		this.createInvitationCodeAndUpdateUser(newUser);

		Set<Integer> roleSet = new HashSet<>();
		for (MemberEntity memberEntity : members) {
			TeamEntity team = teamService.getTeamById(memberEntity.gettId());
			// 3、建立关联关系：用户关联成员，成员关联团队（群组）
			memberEntity.setUserId(newUser.getId());
			memberEntity.setJoinStatus(1);
			memberEntity.setTeamId(team.getTxGroupid());
			memberService.updateMember(memberEntity);

		}

		// 4、添加用户角色关系
		userRoleService.createUserRoleByUserId(newUser.getId(), Long.valueOf(1));//改为普通用户

		return newUser;
	}

	/**
	 * IM交互（操作腾讯云通讯）：创建腾讯云用户
	 * @param user
     */
	private void createTXUser(UserEntity user) {
		// 1、调用腾讯Rest API的独立模式账号同步接口
		AccountImportRequest request = new AccountImportRequest(user.getUserName(), user.getRealName(), user.getPhotoUrl());
		com.qq.tim.vo.response.CommonResponse commonResponse = timRestAPI.registerAccount(request);

		//腾讯云通信设置资料_设置加好友验证方式为AllowType_Type_NeedConfirm
		PortraitSetResponse portraitSetResponse = timRestAPI.portraitSetAllowType(user.getUserName());
		if (!"OK".equals(portraitSetResponse.getActionStatus())) {
			logger.error("用户设置加好友验证方式失败，id：" + user.getId().toString() + "，失败原因：" + portraitSetResponse.toString());
		}
	}

	/**
	 * IM交互（操作腾讯云通讯）：加入所有团队群组中
	 * @param members
	 * @param user
     */
	private void addAllTXGroup(List<MemberEntity> members, UserEntity user) {
		// 调用腾讯Rest API的增加群组成员接口
		List<MemberList> memberList = new ArrayList<MemberList>();
		MemberList mem = new MemberList();
		List<AppMemberDefinedData> appMemberDefinedDatas = new ArrayList<>();
		AppMemberDefinedData appMemberDefinedData = new AppMemberDefinedData();

		for (MemberEntity member : members) {
			// 设置成员自定义字段
			appMemberDefinedData.setKey("MemberRole");
			appMemberDefinedData.setValue(String.valueOf(member.getRole()));
			appMemberDefinedDatas.add(appMemberDefinedData);

			mem.setAppMemberDefinedData(appMemberDefinedDatas);
			mem.setMemberAccount(user.getUserName());    // 设置用户名
			memberList.add(mem);

			AddGroupMemberRequest request = new AddGroupMemberRequest(member.getTeamId(), memberList);
			AddGroupMemberResponse response = timRestAPI.addGroupMember(request);

			// 发送一条自定义系统信息，告知客户端，有邀请信息
			this.sendInvitationMessage(member, member.getTeamId());
		}
	}

	/**
	 * 领队注册
	 * @param request
	 * @return
     */
	@Override
	public CommonResponse registerLeader(RegisterRequest request) {
		// 验证手机号
		if (existPhone(request.getPhone())) {
			return CommonResponse.createExistPhoneResponse();
		}

		// 验证邀请码
		if (!StringUtils.isBlank(request.getInvitationCode()) &&
				!existInvitationCode(request.getInvitationCode())) {
				return CommonResponse.createBadInvitationCodeResponse();
		}

		// 1、创建用户账户
		UserEntity user = createLeaderAccount(request);

		// 5、如果有邀请码，添加邀请奖励明细
		if (!StringUtils.isEmpty(request.getInvitationCode())) {
			addRewardDetails(request, user);
		}

		CommonResponse response = new CommonResponse();
		response.setRetMsg("注册成功");
		return response;
	}

	public UserEntity createLeaderAccount(RegisterRequest request) {
		UserEntity userEntity = new UserEntity();
		// 1、根据请求创建App用户
		UserEntity user = this.createUserByRequest(request);

		// 2、创建腾讯云通讯的用户
		this.createTXUser(user);

		// 根据手机号用户获取已参团行程
		List<MemberEntity> members = memberService.hasMemberInTeam(request.getPhone());
		// 3、把腾讯用户加到对应的团队群组中，并添加好友关系
		if (!CollectionUtils.isEmpty(members)){
			this.addAllTXGroup(members, user);
		}

		// 4、创建并关联领队
		leaderService.associateLeaderAndUser(user, 1);

		// 5、为用户创建钱包
		this.openWalletForUser(user);

		//6、新创建用户 判断是否被邀请围观    如果被邀请了则关联围观
		onlookersUserService.setUserId(user);

		return user;
	}

	/**
	 * 根据注册请求创建App用户
	 * @param request
	 * @return
     */
	private UserEntity createUserByRequest(RegisterRequest request) {
		// 1、创建用户信
		UserEntity user = new UserEntity();
		String phone = request.getPhone();
		phone = phone.trim();
		user.setPhone(phone);
		user.setUserName(Base32Util.encode(System.currentTimeMillis()+request.getPhone()));
		user.setRealName(request.getName());
		if(!StringUtils.isEmpty(request.getPassword())){
		String encodePassword = shaPasswordEncoder.encodePassword(request.getPassword(), user.getUserName());
		user.setPassword(encodePassword);
		}
		user.setSex(request.getSex());
		try {
			if(request.getSex()!=null&&request.getSex()==1){
				user.setPhotoUrl(ConfigConstant.MALE_HEAD_URL);
			}else{
				user.setPhotoUrl(ConfigConstant.FEMALE_HEAD_URL);
			}
//			String photoUrl = PhotoHelper.createMemberImage(request.getName(), request.getSex());
//			user.setPhotoUrl(photoUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setType(1);
		user.setUserStatus(1);	// 设置可用
		this.create(user);

		// 2、创建用户邀请码
		this.createInvitationCodeAndUpdateUser(user);

		// 3、添加用户角色关系
		userRoleService.createUserRoleByUserId(user.getId(), 1l);
		return user;
	}


	/**
	 * 保存邀请用户的奖励明细
	 * @param request
	 */
	private void addRewardDetails(RegisterRequest request, UserEntity fromUser) {
		// 根据邀请码获取用户
		UserEntity userEntity = this.getUserByInviteCode(request.getInvitationCode());

		// 添加奖励详情
		RewardDetailsEntity rewardDetailsEntity = new RewardDetailsEntity();
		rewardDetailsEntity.setPhone(fromUser.getPhone());
		rewardDetailsEntity.setUserId(userEntity.getId());
		rewardDetailsEntity.setFromId(fromUser.getId());
		rewardDetailsEntity.setAmount(RewardType.getAmount("JOIN"));
		rewardDetailsEntity.setDescn(RewardType.getDescn("JOIN"));
		rewardDetailsEntity.setIsInto(0);
		rewardDetailsEntity.setRewardType(1);
		rewardDetailsService.add(rewardDetailsEntity);

		// 更新用户表中的邀请人数及奖励总金额
		userEntity.setInviteCount(userEntity.getInviteCount() + 1);
		//userEntity.setTotalReward(userEntity.getTotalReward() + RewardType.getAmount("JOIN"));
		userDao.update(userEntity);
	}

	private void createInvitationCodeAndUpdateUser(UserEntity user) {
		// 给用户分配邀请码并设置
		InvitationCodeEntity code = invitationCodeService.getLatestInvitationCode();
		if (code.getCode() == null) {
			// 生成
			invitationCodeService.createAndSaveInvitationCode();
		}

		// 更新手机号及用户ID到邀请码表
		code.setPhone(user.getPhone());
		code.setUserId(user.getId());
		invitationCodeService.update(code);

		// 更新邀请码到用户表
		user.setInviteCode(code.getCode());
		this.update(user);
	}

	/**
	 * IM交互：发送邀请加入信息
	 * @param member
	 * @param teamId
	 */
	@Override
	public void sendInvitationMessage(MemberEntity member, String teamId) {
		List<MsgBody> bodyList = new ArrayList<>();
		MsgContent content = new MsgContent();

		// 封装邀请加入响应对象
		CustomMessageResponse response = new CustomMessageResponse();
		List<CustomMsg> customMsgs = new ArrayList<>();
		CustomMsg msg = new CustomMsg();
		StringBuilder str =new StringBuilder("欢迎 ");
		if(!StringUtils.isEmpty(member.getRoleDescn())){
			str.append(" "+member.getRoleDescn()+" ").append(" - ");
		}
		str.append(" "+member.getRealName()+" ").append(" 加入团队");
		msg.setContent(str.toString());
		customMsgs.add(msg);

		response.setMsg(customMsgs);
		response.setMsgType(2);

		logger.info(response);

		content.setData(JsonUtils.obj2json(response));
		content.setDesc(str.toString());

		MsgBody body = new MsgBody();
		body.setMsgType(MsgType.getTIMCustomElem());
		body.setMsgContent(content);

		bodyList.add(body);

		// 使用IM 发送邀请消息
		int random = (int) (Math.random() * 1000000);
		SendGroupMsgRequest request = new SendGroupMsgRequest(teamId, random, bodyList);
		timRestAPI.sendGroupMsg(request);
	}

	/**
	 * 验证表单信息：手机号是否存在，验证码是否正确，邀请码是否正确
	 * @param request
	 * @return
     */
	@Override
	public CommonResponse checkInviteCodeAndSecurityCode(CheckRequest request) {
		// 1、验证手机号
		if (existPhone(request.getPhone())) {
			return CommonResponse.createExistPhoneResponse();
		}

		// 2、验证验证码
		if (!validationCodeService.checkValidCode(request.getPhone(), request.getCaptcha(), request.getCaptchaType())) {
			return CommonResponse.createBadSmsVerificationCodeResponse();
		}

		// 3、验证邀请码
		if (!StringUtils.isBlank(request.getInvitationCode())) {
			if (!existInvitationCode(request.getInvitationCode())) {
				return CommonResponse.createBadInvitationCodeResponse();
			}
		}

		CommonResponse response = new CommonResponse();
		response.setRetMsg("验证通过！");
		return response;
	}


	/**
	 * H5页面注册验证表单信息：手机号是否存在，验证码是否正确，邀请码是否正确
	 * @param request
	 * @return
	 */
	@Override
	public CommonResponse checkInviteCodeAndVerificationCode(CheckRegisterRequest request){
		// 1、验证手机号
		if (existPhone(request.getPhone())) {
			return CommonResponse.createExistPhoneResponse();
		}

		// 2、验证验证码
		if (!validationCodeService.checkValidCode(request.getPhone(), request.getCaptcha(), request.getCaptchaType())) {
			return CommonResponse.createBadSmsVerificationCodeResponse();
		}

		// 3、验证邀请码
		if (!StringUtils.isBlank(request.getInvitationCode())) {
			if (!existInvitationCode(request.getInvitationCode())) {
				return CommonResponse.createBadInvitationCodeResponse();
			}
		}

		// 4、验证校验码
		if (!checkcheckCode(request.getCheckcode(),request.getInvitationCode())) {
			return CheckcheckCodeResponse.createCheckCheckCodeResponse();
		}

		CommonResponse response = new CommonResponse();
		response.setRetMsg("验证通过！");
		return response;
	}


	@Override
	public boolean checkcheckCode(String checkcode,String inviteCode) {

		String code = ShaPasswordUtil.encodePassword(inviteCode, "inviteCode");

		if (checkcode.equals(code)){
			return true;
		}
		return false;
	}


	@Override
	public boolean existPhone(String phone) {
		UserEntity userEntity = userDao.selectByphone(phone);
		if (userEntity != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean existInvitationCode(String invitationCode) {
		UserEntity userEntity = userDao.selectWithInvitationCode(invitationCode);
		if (userEntity != null) {
			return true;
		}
		return false;
	}

	@Override
	public Long findUserId(String txId) {
		return userDao.findUserId(txId);
	}

	@Override
	public UserResponse selectByphone(String phone) {
		UserResponse userResponse=new UserResponse();
		UserEntity userEntity=userDao.selectByphone(phone);
		if(userEntity!=null&&userEntity.getUserName()!=null){
			userResponse.setRetCode(0);
			userResponse.setRetMsg("");
			userResponse.setUserEntity(userEntity);
		}
		else{
			userResponse.setRetCode(1);
			userResponse.setRetMsg("当前手机号无对应用户");
		}
		return userResponse;
	}

	@Override
	public UserEntity selectUserIfoByphone(String phone) {
		return userDao.selectByphone(phone);
	}

	/**
	 * IM交互：添加与跟上小二的好友关系
	 * @param username
     */
	private void addGenShangFriend(String username) {
		List<AddFriendItem> addFriendItems = new ArrayList<>();

		//添加‘跟上小二’为好友（系统用户【App客服人员】，用来给游客推送系统消息）
		AddFriendItem addFriendItem = new AddFriendItem();
		addFriendItem.setToAccount("letsgo@gsxe");
		addFriendItem.setAddSource(AddSource.getAddSource_Type_Service());
		addFriendItems.add(addFriendItem);

		AddFriendRequest request = new AddFriendRequest();
		request.setFromAccount(username);
		request.setAddFriendItem(addFriendItems);
		AddFriendResponse response = timRestAPI.addFriend(request);
	}

	/**
	 * 设置密码
	 * @param user
	 * @param password
	 * @param code
     * @return
     */
	@Override
	/*@Caching(evict = {
			@CacheEvict(value = "userCache", key = "#user.userName"),
			@CacheEvict(value = "userCache", key = "#user.phone"),
			@CacheEvict(value = "userCache", key = "#user.mail") })*/
	@CacheEvict(value = {"userCache"}, allEntries = true)
	public CommonResponse setPassword(UserEntity user, String password, String code) {
		// 1、检验验证码是否正确
		ValidationCodeEntity validationCodeEntity = validationCodeService.getValidCodeByPhone(user.getPhone(), 2);
		if (validationCodeEntity.getId() == null) {
			validationCodeEntity = validationCodeService.getValidCodeByPhone(user.getPhone());
		}

		// 对比当前有效的验证码和用户输入的验证码
		if (!code.equals(validationCodeEntity.getCode())) {
			return CommonResponse.createBadSmsVerificationCodeResponse();
		}

		// 2、设置密码，更新用户信息
		String encodePassword = shaPasswordEncoder.encodePassword(password, user.getUserName());
		user.setPassword(encodePassword);
		this.update(user);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setRetMsg("成功设置密码！");
		return commonResponse;
	}

	/**
	 * 修改密码
	 * @param user
	 * @param oldPassword
	 * @param newPassword
     * @return
     */
	@Override
	/*@Caching(evict = {
			@CacheEvict(value = "userCache", key = "#user.userName"),
			@CacheEvict(value = "userCache", key = "#user.phone"),
			@CacheEvict(value = "userCache", key = "#user.mail") })*/
	@CacheEvict(value = {"userCache"}, allEntries = true)
	public CommonResponse modifyPassword(UserEntity user, String oldPassword, String newPassword) {
		// 1、检验旧密码是否正确
		if (!shaPasswordEncoder.isPasswordValid(user.getPassword(), oldPassword, user.getUserName())) {
			return CommonResponse.createNotSamePassResponse();
		}

		// 2、设置新密码，更新用户信息
		String newEncodePassword = shaPasswordEncoder.encodePassword(newPassword, user.getUserName());
		user.setPassword(newEncodePassword);
		this.update(user);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setRetMsg("成功修改密码！");
		return commonResponse;
	}

	/**
	 * 找回密码
	 * @param phone
	 * @param newPassword
     * @return
     */
	@Override
	@CacheEvict(value = {"userCache"}, allEntries = true)
	public CommonResponse retrievePassword(String phone, String newPassword) {
		UserResponse userResponse = new UserResponse();
		// 1、检验该手机号是否存在
		UserEntity user = userDao.selectByphone(phone);
		if (null == user) {
			return userResponse.createNotFoundResponse();
		}

		// 2、设置新密码，更新用户信息
		String newEncodePassword = shaPasswordEncoder.encodePassword(newPassword, user.getUserName());
		user.setPassword(newEncodePassword);
		this.update(user);

		userResponse.setRetMsg("成功修改密码！");
		return userResponse;
	}

	/**
	 * 更新当前登录用户的设备信息，返回腾讯云通讯IM及云存储等信息
	 * @param request
	 * @return
     */
	@Override
	public TXAccountResponse updateDevice(DeviceRequest request, UserEntity user) {
		this.clearRegIdAndAlias(request.getRegid());	// 验证是否存在小米注册ID，如果存在，则清除

		// 1、获取用户设备信息
		DeviceEntity device = deviceService.getDeviceByUserId(user.getId());

		// 2、判断是否存在设备信息，如果没有，则保存设备信息，否则更新设备信息
		if (device.getId() == null) {
			DeviceEntity newDevice = new DeviceEntity();
			newDevice = dozerBeanMapper.map(request, DeviceEntity.class);
			newDevice.setAlias(user.getUserName());	// 设置小米别名
			newDevice.setUserId(user.getId()); // 设置当前用户ID
			deviceService.create(newDevice);
		} else {
			device.setDeviceUuid(request.getDeviceUuid());
			device.setDeviceModel(request.getDeviceModel());
			device.setDeviceType(request.getDeviceType());
			device.setDeviceVersion(request.getDeviceVersion());
			device.setRegid(request.getRegid());
			device.setAlias(user.getUserName());	// 设置小米别名
			device.setUserId(user.getId());
			device.setIsHuaWei(request.getIsHuaWei());
			deviceService.update(device);
		}

		// 3、获取腾讯云通信（IM）应用信息及签名等信息
		TXAccountResponse response = new TXAccountResponse();

		// 获取应用配置
		response.setSdkAppId(TimConstant.TIM_SDK_APP_ID);
		response.setAccountType(TimConstant.TIM_ACCOUNT_TYPE);
		response.setAppIdAt3rd(TimConstant.TIM_SDK_APP_ID);

		// 获取账号信息
		response.setIdentifier(user.getUserName());

		String userSig = TlsSignUtil.strSigature(user.getUserName());
		tls_sigature.CheckTLSSignatureResult result = TlsSignUtil.checkSigature(user.getUserName(), userSig);
		response.setUsersig(userSig);
		response.setExpireTime(result.expireTime);

		// 4、获取腾讯云存储的应用信息及签名等信息
		response.setCloudSign(QCloudHelper.getSign());

		response.setH5BaseLink(Constant.WEB_BASE_URL);
		response.setApiBaseLink(Constant.API_BASE_URL);
		response.setLoginBaseLink(Constant.LOGIN_BASE_URL);
		return response;
	}

	private boolean existDeviceBySameRegId(DeviceEntity deviceEntity) {
		if (deviceEntity.getId() != null) {
			return true;
		}
		return false;
	}

	private void clearRegIdAndAlias(String regId) {
		DeviceEntity deviceEntity = deviceService.getDeviceByRegId(regId);
		if (existDeviceBySameRegId(deviceEntity)) {
			deviceEntity.setRegid(null);
//			deviceEntity.setAlias(null);	// 别名不需要清除
			deviceService.update(deviceEntity);
		}
	}

	/**
	 * 获取重要消息
	 * @param request
	 * @return
     */
	@Override
	public InformationResponse getInformationResponse(InformationRequest request) {
		InformationResponse response = new InformationResponse();

		// 1、获取最新的通知信息，并统计未读及未确认的通知总数量
		response = this.setNoticeAndNoticeCount(request.getUserId(), request.getLastNoticeId(), response);

		// 2、获取最新的集合信息，并统计未读及未确认的通知总数量
		response = this.setGatherAndGatherCount(request.getUserId(), request.getLastGatherId(), response);

		// 3、获取最新的公告信息，并统计未读及未确认的通知总数量
		response = this.setAnnouncementAndAnnouCount(request.getUserId(), request.getLastAnnouncementId(), response);

		// 4、计算全部的未读及未确认的信息总数量
		int totalCount = response.getNoticeCount() + response.getGatherCount() + response.getAnnouCount();
		response.setTotalCount(totalCount);
		return response;
	}

	/**
	 * 设置最新的通知和通知数量
	 * @param userId
	 * @param noticeId
     * @return
     */
	private InformationResponse setNoticeAndNoticeCount(Long userId, Long noticeId, InformationResponse response) {
		response.setNotice(noticeService.getLatestNotice(userId, 2, null));

		response.setNoticeCount(noticeService.getNoticeCount(userId, noticeId, 2));

		return response;
	}

	/**
	 * 设置最新的集合和集合数量
	 * @param userId
	 * @param noticeId
	 * @return
	 */
	private InformationResponse setGatherAndGatherCount(Long userId, Long noticeId, InformationResponse response) {

		response.setGather(noticeService.getLatestNotice(userId, 1, null));
		response.setGatherCount(noticeService.getNoticeCount(userId, noticeId, 1));

		return response;
	}

	/**
	 * 设置最新的公告和公告数量
	 * @param userId
	 * @return
     */
	private InformationResponse setAnnouncementAndAnnouCount(Long userId, Long annouId, InformationResponse response) {
		//　获取最新公告
		response.setAnnouncement(announcementService.getLatestAnnouncement(userId, null));

		// 获取未读公告数量
		response.setAnnouCount(announcementService.getAnnouncementCount(userId, annouId));
		return response;
	}

	/**
	 * 发送邀请加入短信【领队端】
	 * @param memberId
	 * @return
     */
	@Override
	public CommonResponse sendInvitationMessage(Long memberId, String phone) throws UnsupportedEncodingException {
		// 判断是发送给导游还是发送给游客
		MemberEntity member = memberService.getMember(memberId);
		TeamEntity team = teamService.getTeamById(member.gettId());

		MessageRequest request = new MessageRequest();
		CommonResponse response = new CommonResponse();
		int type = 2;
		if (member.getRole() == 3) {	// 领队给游客发送邀请短信
			request.setInvitee(member.getRealName());
			request.setTeamName(team.getName());
			request.setDownloadURL(ConfigConstant.TOURIST_URL);	// 游客端链接

			type = 2;
		} else {	// 领队给导游发送邀请短信
			request.setInvitee(member.getRealName());
			request.setTravelAgency(team.getTravelName());
			request.setTeamName(team.getName());
			request.setDownloadURL(ConfigConstant.LEADER_URL);	// 领队端链接

			type = 4;
		}

		// 发送短信
		if (!StringUtils.isEmpty(member.getPhone())) {
			response = messageService.sendInvestmentMessage(member.getPhone(), type, request);
		} else if (!StringUtils.isEmpty(member.getMarkPhone())) {
			response = messageService.sendInvestmentMessage(member.getMarkPhone(), type, request);
		} else {
			String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(phone);
			boolean isPhone=m.matches();
			if (!isPhone) {
				return CommonResponse.createNotFoundResponse("手机号不正确，不能发送邀请短信！");
			}
			if (!phone.startsWith("+86")) {
				phone ="+86"+phone;
			}
			response = messageService.sendInvestmentMessage(phone, type, request);
		}

		return response;
	}

	/**
	 * 发送邀请加入短信【游客端】
	 * @param memberId
	 * @return
	 */
	@Override
	public CommonResponse sendInvitationMessage2(Long memberId, String phone, UserEntity user)throws UnsupportedEncodingException  {
		MemberEntity member = memberService.getMember(memberId);
		MemberEntity member2 = memberService.getMemberWithTIdAndUserId(member.gettId(), user.getId());

		// 如果不是同组的成员，不允许发送邀请短信
		if (member.getGroupId() != member2.getGroupId()) {
			CommonResponse.createNotSendMassageResponse();
		}

		TeamEntity team = teamService.getTeamById(member.gettId());

		MessageRequest request = new MessageRequest();
		request.setInvitee(member.getRealName());
		request.setInviter(user.getRealName());
		request.setTravelAgency(team.getTravelName());
		request.setTeamName(team.getName());
		request.setDownloadURL(ConfigConstant.TOURIST_URL);

		CommonResponse response = new CommonResponse();
		// 发送短信
		if (!StringUtils.isEmpty(member.getPhone())) {
			response = messageService.sendInvestmentMessage(member.getPhone(), 3, request);
		} else if (!StringUtils.isEmpty(member.getMarkPhone())) {
			response = messageService.sendInvestmentMessage(member.getMarkPhone(), 3, request);
		} else {
			if (phone == null) {
				return CommonResponse.createNotFoundResponse("手机号为空，不能发送邀请短信！");
			}

			String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(phone);
			if (!m.matches()) {
				return CommonResponse.createNotFoundResponse("手机号不正确，不能发送邀请短信！");
			}
			if (!phone.startsWith("+86")) {
				phone ="+86"+phone;
			}
			response = messageService.sendInvestmentMessage(phone, 3, request);
		}
		return response;
	}

	@Override
	public List<UserEntity> getUsersIsTravel(Integer status){
		List<UserEntity> userList = userDao.getUsersIsTravel(status);
		if(CollectionUtils.isEmpty(userList)){
			userList = Collections.emptyList();
		}
		HashSet userSet= new HashSet(userList);
		return new ArrayList<>(userSet);
	}

	/**
	 * 获取未读数量及未确认数量
	 * @param types
	 * @param teamId
     * @return
     */
	@Override
	public UnreadAndUnconfirmedResponse getUnreadCountAndUnconfirmedCount(List<Integer> types, String teamId, Long userId) {
		List<Information> informations = new ArrayList<>();
		int total = 0;
		for (Integer type : types) {
			int unreadCount = noticeDetailService.getUnreadCount(type, userId, teamId);
			int unconfirmedCount = noticeDetailService.getUnconfirmedCount(type, userId, teamId);
			total = total + unreadCount + unconfirmedCount;	// 计算总数量
			Message message = getLatestMessge(type, userId, teamId);

			Information information = new Information();
			information.setUnreadCount(unreadCount);
			information.setUnconfirmedCount(unconfirmedCount);
			information.setMessage(message);
			informations.add(information);
		}

		UnreadAndUnconfirmedResponse response = new UnreadAndUnconfirmedResponse();
		response.setInfo(informations);
		response.setType(types);
		response.setTotal(total);
		return response;
	}

	private Message getLatestMessge(Integer type, Long userId, String teamId) {
		Message message = new Message();
		if (type == 1) {	// 集合
			NoticeEntity noticeEntity = noticeService.getLatestNotice(userId, 1, teamId);
			message.setContent(noticeEntity.getContent());
			message.setPubdate(noticeEntity.getCreateTime());
			message.setSender(noticeEntity.getSenderName());
			message.setVideoUrl(noticeEntity.getVideoUrl());
			message.setVideoLen(noticeEntity.getVideoLen());
		} else if (type == 2) {	// 通知
			NoticeEntity noticeEntity = noticeService.getLatestNotice(userId, 2, teamId);
			message.setContent(noticeEntity.getContent());
			message.setPubdate(noticeEntity.getCreateTime());
			message.setSender(noticeEntity.getSenderName());
			message.setVideoUrl(noticeEntity.getVideoUrl());
			message.setVideoLen(noticeEntity.getVideoLen());
		} else if (type == 3) {	// 公告
			AnnouncementEntity announcementEntity = announcementService.getLatestAnnouncement(userId, teamId);
			message.setContent(announcementEntity.getContent());
			message.setPubdate(announcementEntity.getCreateTime());
			message.setSender(announcementEntity.getSenderName());
			message.setTitle(announcementEntity.getTitle());
		}
		return message;
	}

	
	public CommonResponse uploadFacePhoto(UserEntity userEntity,String photoUrl) {
		String userName = "";
		if (null != userEntity){
			userName = userEntity.getUserName();
		}
		//腾讯云通信设置资料_设置头像URL
		PortraitSetResponse portraitSetResponse = timRestAPI.portraitSetImage(userName, photoUrl);
		if ("OK".equals(portraitSetResponse.getActionStatus())){
			userDao.uploadFacePhoto(userEntity.getId(),photoUrl);
		}
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setRetCode(portraitSetResponse.getErrorCode());
		commonResponse.setRetMsg(portraitSetResponse.getErrorInfo());

		return commonResponse;
	}

	public CommonResponse saveTag(Long currentUserId, String tagName, String userIds) {
		TagsEntity tagsEntity = new TagsEntity();
		tagsEntity.setName(tagName);
		tagsEntity.setUserId(currentUserId);
		tagsService.add(tagsEntity);

		userIds = userIds.replace(" ", "");
		String[] userIdsArray = StringUtils.split(userIds, ";");

		for(String userId : userIdsArray){
			UserTagEntity userTagEntity = new UserTagEntity();
			userTagEntity.setLabeledUserId(Long.parseLong(userId));
			userTagEntity.setLabellingUserId(currentUserId);
			userTagEntity.setTagId(tagsEntity.getId());
			userTagEntity.setCreateTime(new Date());
			userTagEntity.setVersion(0l);
			userTagDao.insert(userTagEntity);
		}
		return new CommonResponse();
	}

	public TagListResponse queryTagList(Long userId) {
		TagListResponse response = new TagListResponse();
		List<TagsResultEntity> tagsResult = userTagDao.getTagListByUserId(userId);
		if(CollectionUtils.isEmpty(tagsResult)){
			return response;
		}
		response.setTagResult(tagsResult);

		return response;
	}

    //通过腾讯组id获取用户对象
	@Override
	public UserEntity selecUserByUserName(String userName){
      return userDao.selectWithAccount1(userName);
	}

	@Override
	public boolean checkUserRole(String clientId, String phone) {
		Map<String, String> roleMap = new HashMap<>();
		roleMap.put("BG9CJ9WRB", "captain,guide");
		roleMap.put("C8BC3D6EE", "captain,guide,tourist");
		roleMap.put("D915BB82B", "tourist");
		roleMap.put("F8F3BNKNQ", "captain,guide");
		roleMap.put("FB40093DP", "tourist");

		UserEntity userEntity = this.getUserByPhone(phone);

		if (userEntity == null) {
			return true;
		}

		// 如果用户不为空，则验证用户身份
		List<UserRoleEntity> userRoleEntityList = userRoleService.getUserRoleByUserId(userEntity.getId());
		for (UserRoleEntity userRoleEntity : userRoleEntityList) {
			RoleEntity role = roleService.get(userRoleEntity.getRoleId());
			String value = roleMap.get(clientId);
			String roleName = role.getRolename().replace("ROLE_", "").toLowerCase();
			if (value.contains(roleName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 为用户开通钱包
	 * @param user
	 * @return
	 */
	@Override
	public WalletCommonResponse openWalletForUser(UserEntity user) {
		OpenWalletRequest openWalletRequest = new OpenWalletRequest();
		WalletCommonResponse response = null;
		String phone = user.getPhone();
		if (!StringUtils.isEmpty(phone)){
			openWalletRequest.setCustUid(user.getId().toString());
			openWalletRequest.setCustMobile(phone);
			openWalletRequest.setCustNickname(user.getNickName());
			openWalletRequest.setCustImg(user.getPhotoUrl());
			response = iStandardWallet.openWallet(openWalletRequest);
			if(!"0000".equals(response.getRespstat())){
				logger.error("用户Id:" + user.getId().toString() + "，开通钱包失败, 原因：" +
						response.getRespstat() + "," + response.getRespmsg());
			}else {
				logger.error("用户Id:" + user.getId().toString() + "，开通钱包成功");
			}
		}else {
			logger.error("用户Id:" + user.getId().toString() + "，开通钱包失败, 原因：手机号为空");
		}
		return response;
	}

	/**
	 * 批量为用户开通钱包
	 * @return
     */
	@Override
	public WalletCommonResponse batchOpenWalletForUser() {
		List<UserEntity> userEntities = userDao.selectAll();
		for (UserEntity userEntity:userEntities) {
			if (userEntity != null){
				this.openWalletForUser(userEntity);
			}
		}

		WalletCommonResponse walletCommonResponse = new WalletCommonResponse();
		walletCommonResponse.setRespstat("0000");
		walletCommonResponse.setRespmsg("批量开通钱包完成");
		return walletCommonResponse;
	}

	/**
	 * 批量设置加好友验证
	 * @return
     */
	@Override
	public com.qq.tim.vo.response.CommonResponse batchPortraitSetAllowType() {
		List<UserEntity> userEntities = userDao.selectAll();
		for (UserEntity userEntity:userEntities) {
			if (userEntity != null){
				PortraitSetResponse response = timRestAPI.portraitSetAllowType(userEntity.getUserName());
				if(!"OK".equals(response.getActionStatus())){
					logger.error("用户名称userName:" + userEntity.getUserName() + "，设置加好友验证失败, 原因：" +
							response.getErrorCode() + "," + response.getErrorInfo());
				}else {
					logger.info("用户名称userName:" + userEntity.getUserName() + "，设置加好友验证成功");
				}
			}
		}
		PortraitSetResponse portraitSetResponse = new PortraitSetResponse();
		portraitSetResponse.setActionStatus("OK");
		return portraitSetResponse;
	}

	/**
	 * 批量创建用户邀请码
	 * @return
     */
	@Override
	public WalletCommonResponse batchCreateInvitationCode() {
		WalletCommonResponse walletCommonResponse = new WalletCommonResponse();
		List<UserEntity> userEntities = userDao.selectAll();
		for (UserEntity userEntity:userEntities) {
			if (userEntity != null){
				String inviteCode = userEntity.getInviteCode();
				if (StringUtils.isEmpty(inviteCode)){
					String phone = userEntity.getPhone();
					if (!StringUtils.isEmpty(phone)){
						// 2、创建用户邀请码
						this.createInvitationCodeAndUpdateUser(userEntity);
						logger.info("用户Id:" + userEntity.getId().toString() + "，创建用户邀请码成功" );
					}else {
						walletCommonResponse.setRespstat("-1");
						walletCommonResponse.setRespmsg("创建用户邀请码失败，用户手机号为空");
						logger.error("用户Id:" + userEntity.getId().toString() + "，创建用户邀请码失败, 原因：" + walletCommonResponse.getRespmsg());
					}
				}else {
					logger.error("用户Id:" + userEntity.getId().toString() + "，创建用户邀请码失败, 原因：已经存在邀请码" );
				}
			}
		}
		walletCommonResponse.setRespstat("0000");
		walletCommonResponse.setRespmsg("批量创建用户邀请码完成");
		return walletCommonResponse;
	}


	/**
	 * 根据注册请求创建有围观的App用户
	 * @param request
	 * @return
	 */
	private UserEntity createOnlookerUserByRequest(OnlookersUserEntity request) {
		// 1、创建用户信
		UserEntity user = new UserEntity();
		user.setPhone(request.getPhone());
		user.setUserName(Base32Util.encode(System.currentTimeMillis()+request.getPhone()));
		user.setRealName(request.getName());
		user.setSex(request.getSex());
		try {
			if(request.getSex()!=null&&request.getSex()==1){
				user.setPhotoUrl(ConfigConstant.MALE_HEAD_URL);
			}else{
				user.setPhotoUrl(ConfigConstant.FEMALE_HEAD_URL);
			}
//			String photoUrl = PhotoHelper.createMemberImage(request.getName(), request.getSex());
//			user.setPhotoUrl(photoUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setType(1);
		user.setUserStatus(1);	// 设置可用
		this.create(user);

		// 2、创建用户邀请码
		this.createInvitationCodeAndUpdateUser(user);

		// 3、添加用户角色关系
		userRoleService.createUserRoleByUserId(user.getId(), 1L);
		return user;
	}

	public static void main(String[] args) {
		/*
		ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
		String encodePassword = passwordEncoder.encodePassword("123456", "ge1domjvha2tcnztgaztikzygyytgnbyha2tqnrsgy2a");
		System.out.println("password:" + encodePassword);
		*/

		//String userIds = "123;456;;abc;efg";
		//String userIds = "123;456; ;abc;efg";
		String userIds = "123";
		userIds = userIds.replace(" ", "");
		String[] userIdsArray = StringUtils.split(userIds, ";");
		for (String userId : userIdsArray
			 ) {
			System.out.println("userId:" + userId);
		}

	}


	/**
	 * 用户注册
	 * @param request
	 * @return
	 */
	@Override
	public CommonResponse registerUser(RegisterRequest request) {
		// 验证手机号
		if (existPhone(request.getPhone())) {
			return CommonResponse.createExistPhoneResponse();
		}

		// 验证邀请码
		if (!StringUtils.isBlank(request.getInvitationCode()) &&
				!existInvitationCode(request.getInvitationCode())) {
			return CommonResponse.createBadInvitationCodeResponse();
		}

		//判断手机号用户是否参团 或者 该用户是否被邀请围观，如果是则直接登录即可
		List<MemberEntity> members = memberService.hasMemberInTeam(request.getPhone());
		List<OnlookersUserEntity> onlookersUserList = onlookersUserService.getOnlookersUserByPhone(request.getPhone());
		if (!CollectionUtils.isEmpty(members) && !CollectionUtils.isEmpty(onlookersUserList)) {
			CommonResponse response = new CommonResponse();
			response.setRetCode(ErrorConstant.USER_REPEAT);
			response.setRetMsg("您已经是我们的用户，请通过验证码登录。");
			return response;
		}

		// 1、创建用户账户
		UserEntity user = createUserAccount(request);

		// 5、如果有邀请码，添加邀请奖励明细
		if (!StringUtils.isEmpty(request.getInvitationCode())) {
			addRewardDetails(request, user);
		}

		CommonResponse response = new CommonResponse();
		response.setRetMsg("注册成功");
		return response;
	}

	public UserEntity createUserAccount(RegisterRequest request) {
		// 1、根据请求创建App用户
		UserEntity user = this.createUserByRequest(request);

		// 2、创建腾讯云通讯的用户
		this.createTXUser(user);

		// 根据手机号用户获取已参团行程
		List<MemberEntity> members = memberService.hasMemberInTeam(request.getPhone());
		// 3、把腾讯用户加到对应的团队群组中，并添加好友关系
		if (!CollectionUtils.isEmpty(members)){
			this.addAllTXGroup(members, user);
		}

		// 4、为用户创建钱包
		this.openWalletForUser(user);

		//5、新创建用户 判断是否被邀请围观    如果被邀请了则关联围观
		onlookersUserService.setUserId(user);

		return user;
	}

	/**
	 * 修改用户擅长路线，擅长语言和评论
	 * @param userId
	 * @param type
	 * @param content
	 * @return
     * @throws Exception
     */
	@Override
	public CommonResponse updateUserContent(Long userId, String type, String content)throws Exception {
		CommonResponse commonResponse = new CommonResponse();
		UserEntity userEntity = userDao.select(userId);
		if(userEntity==null){
			commonResponse = new CommonResponse(ErrorConstant.INTERNAL_SERVER_ERROR,"未查询到用户信息");
		}else{
			if("1".equals(type)){
				userEntity.setEvaluationTags(content);
			}else if("2".equals(type)){
				userEntity.setLanguages(content);
			}else if("3".equals(type)){
				userEntity.setTourRoutes(content);
			}
			userEntity.setUpdateTime(new Date());
			userDao.update(userEntity);
		}
		return commonResponse;
	}

	//根据旅行社ID 以及旅行社用户角色 获取用户userID集合
	public List<Long> getUserIdsByTravelerId(LogManageRequest request){
		return userDao.getUserIdsByTravelerId(request);
	}
	@Override
	public UserEntity getCurrentUserEntity() {
		UserResponse userResponse = userLoginHelper.getLoginUser();
		UserEntity user = userResponse.getUserEntity();
		return user;
	}
	//根据用户ID 获取用户角色
	public UserEntity getUserRole(Long userId){
		LogManageRequest request=new LogManageRequest();
		request.setUserId(userId);
		return userDao.getUserRole(request);
	}
	//验证手机验证码是否正确
	public boolean isSmsCodeValid(String phone, String code){
		ValidationCodeEntity codeEntity = validationCodeService.getValidCodeByPhone(phone, 1);
		if (codeEntity.getId() == null) {
			codeEntity = validationCodeService.getValidCodeByPhone(phone);
		}
		// 对比当前有效的验证码和用户输入的验证码
		if (code.equals(codeEntity.getCode())) {
			return true;
		} else{
			return false;
		}
	}
	//用户绑定微信接口
	public UserBindingWxResponse toBindingFirst(String openId,String  utoken,String  phone,String  code){
		UserBindingWxResponse response =new UserBindingWxResponse();
		// 对用户输入的验证码进行有效性验证
		if (!this.isSmsCodeValid(phone, code)) {
			throw new BusinessException(ErrorConstant.BAD_SMS_VERIFICATION_CODE, "手机验证码不正确或已过期");
		}
		//判断该手机号是否已经用户
		UserEntity userEntity = userDao.selectByphone(phone);
		if (userEntity==null){
			throw new BusinessException(ErrorConstant.NOT_FOUND, "用户不存在，需注册");
		}
        //用户绑定微信
		WeixinUserInfoResponse weixinUserInfoResponse=getWxAppUserInfo(utoken,openId);
        if(weixinUserInfoResponse==null||StringUtils.isEmpty(weixinUserInfoResponse.getUnionid())){
			throw new BusinessException(ErrorConstant.THIRD_ACCOUNT_NOT_FOUND, "第三方用户未找到");
		}
		//判断该微信号是否已经绑定账号
		ThirdPartyAccountEntity thirdPartyAccountEntity = thirdPartyAccountService.selectByUnionID(weixinUserInfoResponse.getUnionid());
		if (thirdPartyAccountEntity != null) {
			// 用户已绑定微信则返回
			throw new BusinessException(ErrorConstant.USER_IS_BINDING, "该微信已经绑定用户");
		}
        //给第三方账户表保存绑定信息
		this.saveThirdPartyAccount(weixinUserInfoResponse,userEntity);
		this.saveWxInfo(weixinUserInfoResponse);
		return  response;
	}
	//用户绑定微信完善资料接口
	public UserBindingWxResponse toBindingSecond(String openId,String  utoken,String  phone,String  name,Integer sex,String invitationCode){
		UserBindingWxResponse response =new UserBindingWxResponse();
		// 验证手机号
		if (existPhone(phone)) {
			return UserBindingWxResponse.createExistPhoneResponse();
		}
		// 验证邀请码
		if (!StringUtils.isBlank(invitationCode) &&
				!existInvitationCode(invitationCode)) {
			return UserBindingWxResponse.createBadInvitationCodeResponse();
		}
		//用户绑定微信
		WeixinUserInfoResponse weixinUserInfoResponse=getWxAppUserInfo(utoken,openId);
		if(weixinUserInfoResponse==null||StringUtils.isEmpty(weixinUserInfoResponse.getUnionid())){
			throw new BusinessException(ErrorConstant.THIRD_ACCOUNT_NOT_FOUND, "第三方用户未找到");
		}
		//判断该微信号是否已经绑定账号
		ThirdPartyAccountEntity thirdPartyAccountEntity = thirdPartyAccountService.selectByUnionID(weixinUserInfoResponse.getUnionid());
		if (thirdPartyAccountEntity != null ) {
			// 用户已绑定微信则返回
			throw new BusinessException(ErrorConstant.USER_IS_BINDING, "该微信已经绑定用户");
		}
		//注册用户
		RegisterRequest request =new RegisterRequest();
		request.setPhone(phone);
		request.setSex(sex);
		request.setName(name);
		request.setInvitationCode(invitationCode);
		// 创建用户账户
		UserEntity userEntity = this.createUserAccount(request);
		if (userEntity==null){
			throw new BusinessException(ErrorConstant.NOT_FOUND, "用户不存在，需注册");
		}
		// 5、如果有邀请码，添加邀请奖励明细
		if (!StringUtils.isEmpty(invitationCode)) {
			addRewardDetails(request, userEntity);
		}
		//给第三方账户表保存绑定信息
		this.saveThirdPartyAccount(weixinUserInfoResponse,userEntity);
		this.saveWxInfo(weixinUserInfoResponse);
		return  response;
	}
	/**
	 * 获取微信APP登录的用户信息
	 *
	 * @param accessToken 接口访问凭证
	 * @param openId      用户标识
	 * @return WeixinUserInfoResponse
	 */
	public WeixinUserInfoResponse getWxAppUserInfo(String accessToken, String openId) {
		WeixinUserInfoResponse weixinUserInfo = null;
		// 拼接请求地址https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 获取用户信息
		String result = restTemplate.getForObject(requestUrl, String.class);
		JSONObject jsonObject = JSONObject.fromObject(result);
		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeixinUserInfoResponse();
				//用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的
				String unionid = jsonObject.getString("unionid");
				weixinUserInfo.setUnionid(unionid);
				// 微信用户openid
				weixinUserInfo.setOpenid(jsonObject.getString("openid"));
				// 昵称
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfo.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				weixinUserInfo.setCity(jsonObject.getString("city"));
				// 用户头像
				weixinUserInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
			} catch (Exception e) {
				e.printStackTrace();
				WeixinUserInfoResponse.createNotFoundResponse("获取微信用户信息失败");
			}
		}
		return weixinUserInfo;
	}
	public void saveThirdPartyAccount(WeixinUserInfoResponse response,UserEntity userEntity){
		ThirdPartyAccountEntity thirdPartyAccountEntity =new ThirdPartyAccountEntity();
		thirdPartyAccountEntity.setUserId(userEntity.getId());
		//账号类型 0 微信 1 微博
		thirdPartyAccountEntity.setAccountType(0);
		thirdPartyAccountEntity.setUnionID(response.getUnionid());
		thirdPartyAccountService.create(thirdPartyAccountEntity);
	}

	public void saveWxInfo(WeixinUserInfoResponse response) {
		WxInfoEntity wxInfoEntity = new WxInfoEntity();
		wxInfoEntity.setOpenid(response.getOpenid());
		try {
			wxInfoEntity.setNickname(new String(response.getNickname().getBytes("ISO-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		wxInfoEntity.setCountry(response.getCountry());
		wxInfoEntity.setProvince(response.getProvince());
		wxInfoEntity.setCity(response.getCity());
		//修改微信信息表中sex 字段的类型为string 和微信保持一致
		wxInfoEntity.setSex(Integer.toString(response.getSex()));
		wxInfoEntity.setUnionID(response.getUnionid());
		wxInfoEntity.setHeadImgUrl(response.getHeadimgurl());
		wxInfoEntity.setBinding(1);
		wxInfoEntity.setStatus(0);
		wxInfoEntity.setAppId(ConfigConstant.WX_APP_KEY);
		wxDao.insert(wxInfoEntity);
	}
}
