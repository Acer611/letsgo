package com.umessage.letsgo.service.impl.system;

import com.qcloud.sms.SmsSingleSender;
import com.qcloud.sms.SmsSingleSenderResult;
import com.umessage.letsgo.core.annotation.logger.Loggable;
import com.umessage.letsgo.core.config.constant.ConfigConstant;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.CountryCode;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.core.utils.MailUtil;
import com.umessage.letsgo.dao.system.MessageDao;
import com.umessage.letsgo.domain.po.system.MessageEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.OnlookersUserEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.system.request.MessageRequest;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.security.IValidationCodeService;
import com.umessage.letsgo.service.api.system.IMessageService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.IOnlookersUserService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.common.constant.SmsConstant;
import com.umessage.letsgo.service.common.helper.SmsHelper;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Albert on 2015/7/24.
 */
@Service
public class MessageServiceImpl implements IMessageService {
	@Resource
	private MessageDao messageMapper;
	@Resource
	private IMemberService memberService;
	@Resource
	private IUserService userService;
	@Resource
	private IValidationCodeService validationCodeService;
	@Resource
	private RestTemplate restTemplate;
	@Resource
	private ITravelAgencyService travelService;
	@Resource
	private IOnlookersUserService onlookersUserService;
	@Autowired
	protected SmsSingleSender smsSingleSender;

	@Override
	@Loggable(desc="添加短信信息")
	public int insert(MessageEntity message) {
		message.setCreateTime(new Date());
		message.setVersion(0l);
		return messageMapper.insert(message);
	}

	@Override
	@Loggable(desc="批量添加短信信息")
	public int batchInsert(List<MessageEntity> payments) {
		return 0;
	}

	@Override
	@Loggable(desc="修改短信信息")
	public int update(String phone) {
		return messageMapper.updateValidWithPhone(phone);
	}

	@Override
	public int delete(Map<String, Object> conditions) {
		return 0;
	}

	@Override
	@Loggable(desc="查询短信信息列表")
	public List<MessageEntity> findList(MessageRequest request) {
		List<MessageEntity> messageEntityList = messageMapper.selectMessageListWithCondition(request);
		if (CollectionUtils.isEmpty(messageEntityList)) {
			return new ArrayList<MessageEntity>();
		}
		return messageEntityList;
	}

	@Override
	@Loggable(desc="条件查询短信数量")
	public int findCount(Map<String, Object> conditions) {
		return 0;
	}

	/**
	 * 创建验证码短信
	 * @param phone
	 * @param content
	 * @param mark
	 * @param checkCode
     */
	public void createMessage(String phone, String content, String mark, String checkCode, String smsStatus) {
		MessageEntity message = new MessageEntity();
		message.setContent(content);
		message.setPhone(phone);
		message.setSendTime(new Date());
		if (!StringUtils.isEmpty(checkCode)) {
			message.setParam(checkCode);
		}
		message.setMark(mark);
		message.setSmsType(0);
		message.setValid(1);
		message.setSmsStatus(smsStatus);	// 短信接口返回的码值，为0是成功发送，其他数值则为错误
		this.insert(message);
	}

	/**
	 * 创建短信
	 * @param phone
	 * @param content
	 * @param mark
	 * @param params
	 */
//	public void createMessage(String phone, String content, String mark, String params) {
//		MessageEntity message = new MessageEntity();
//		message.setContent(content);
//		message.setPhone(phone);
//		message.setSendTime(new Date());
//		if (!StringUtils.isEmpty(params)) {
//			message.setParam(params);
//		}
//		message.setMark(mark);
//		message.setSmsType(0);
//		message.setValid(1);
//		this.insert(message);
//	}

	/**
	 * 把验证码短信设置为无效
	 * @param phone
     */
	private void updateInvalidMessage(String phone) {
		MessageRequest request = new MessageRequest();
		request.setPhone(phone);
		// 根据手机号查询数据库中是否有已经存在的验证码
		List<MessageEntity> list = this.findList(request);

		// 如果有，把有效性改为无效
		if (list.size() > 0) {
			this.update(phone);
		}
	}

	//发送短信邀请。
	@Override
	public void sendMessage(TeamEntity teamEntity,List<MemberEntity> memberList) throws UnsupportedEncodingException {
		TravelAgencyEntity travel = travelService.getTravelAgency(teamEntity.getTravelId());
		for (MemberEntity member: memberList){

			if (StringUtils.isBlank(member.getPhone())) {	// 如果手机号为空，则跳出这层循环
				continue;
			}

			if(member.getRole() == 1){
				MessageRequest messageRequest = new MessageRequest(member.getPhone(),member.getRealName(),travel.getName(),teamEntity.getName(), ConfigConstant.LEADER_URL);
				if(member.getUserId() == -1){
					sendInvestmentMessage(member.getPhone(), 1, messageRequest);
				}else{
					sendInvestmentMessage(member.getPhone(), 5, messageRequest);
				}
			}
			if(member.getRole() == 2){
				MessageRequest messageRequest = new MessageRequest(member.getPhone(),member.getRealName(),travel.getName(),teamEntity.getName(),ConfigConstant.LEADER_URL);
				if(member.getUserId() == -1){
					sendInvestmentMessage(member.getPhone(), 4, messageRequest);
				}else{
					sendInvestmentMessage(member.getPhone(), 6, messageRequest);
				}
			}
			if(member.getRole() == 3){
				MessageRequest messageRequest = new MessageRequest(member.getPhone(),member.getRealName(),travel.getName(),teamEntity.getName(),ConfigConstant.TOURIST_URL);
				if(member.getUserId() == -1){
					sendInvestmentMessage(member.getPhone(), 2, messageRequest);
				}else{
					sendInvestmentMessage(member.getPhone(), 7, messageRequest);
				}
			}
		}

	}

	@Override
	@Loggable(desc="发送验证码短信")
	@Transactional
	public CommonResponse sendCheckCodeMessage (String phone, Integer scope) throws UnsupportedEncodingException {
		// 如果是发送注册用的验证码，可以不对用户进行验证。
		if (scope != null && scope != 3 && scope != 4) {
			UserResponse user = userService.getUserByLoginAccount(phone);
			if (user.getRetCode() == ErrorConstant.NOT_FOUND) {
				// 判断手机号用户是否参团
				List<MemberEntity> members = memberService.hasMemberInTeam(phone);
				//判断手机号  该用户是否被邀请围观
				List<OnlookersUserEntity> onlookersUserList = onlookersUserService.getOnlookersUserByPhone(phone);
				if (CollectionUtils.isEmpty(members) && CollectionUtils.isEmpty(onlookersUserList)) {
					return CommonResponse.createNotJOINResponse();
				}
			}
		}

		this.updateInvalidMessage(phone);

		Long checkCode = Math.round(Math.random() * 900000 + 100000);
		String content = SmsConstant.getContentById(SmsConstant.CODE_ID).replace("%P%", checkCode + "");
		String params = "{checkCode:" + checkCode + "}";

		// 为了方便IOS APP 在苹果应用市场审核通过而加入的代码
		if (phone.equals("+8618511403672") || phone.equals("+8618710167572")) {
			CommonResponse response = new CommonResponse();
			response.setRetMsg("短信发送成功，请注意查收！");
			return response;
		}

		// 发送短信并记录短信
		String result =  this.sendMsgAndSave(phone, content, params, SmsConstant.SMS_TYPE_CODE);

		if (!"0".equals(result)) {
			return CommonResponse.createNotSendMassageResponse();
		}

		// 保存手机号和验证码到数据库
		validationCodeService.createValidCode(checkCode.toString(), phone, scope);

		CommonResponse response = new CommonResponse();
		response.setRetMsg("短信发送成功，请注意查收！");
		return response;
	}

	@Override
	public CommonResponse sendCodeMessageForTravel(String phone) throws UnsupportedEncodingException {
		Long checkCode = Math.round(Math.random() * 900000 + 100000);
		String content = SmsConstant.getContentById(SmsConstant.CODE_ID).replace("%P%", checkCode + "");
		String params = "{checkCode:" + checkCode + "}";

		// 发送短信并记录短信
		String result =  this.sendMsgAndSave(phone, content, params, "CODE");

		if (!"0".equals(result)) {
			return CommonResponse.createNotSendMassageResponse();
		}

		// 保存手机号和验证码到数据库
		validationCodeService.createValidCode(checkCode.toString(), phone, null);

		CommonResponse response = new CommonResponse();
		response.setRetMsg("短信发送成功，请注意查收！");
		return response;
	}

	public CommonResponse sendInvestmentMessage(String phone, Integer type, MessageRequest request) throws UnsupportedEncodingException{
		String content = "";
		String params = "";
		String result="";
		String sms_model = "";
		if (type == 1) {	// 旅行社给领队发
			sms_model = SmsConstant.SMS_CID_1;

		} else if (type == 2) {	// 旅行社给游客发|领队给游客发
			sms_model = SmsConstant.SMS_CID_2;

		} else if (type == 3) {	// 游客给同组的游客发
			sms_model = SmsConstant.SMS_CID_3;

		} else if (type == 4) {	// 领队给导游发
			sms_model = SmsConstant.SMS_CID_4;

		}else if (type == 5) {	// 旅行社给领队发（已有用户的领队）
			sms_model = SmsConstant.SMS_CID_5;

		}else if (type == 6) {	// 旅行社给导游发（已有用户的导游）
			sms_model = SmsConstant.SMS_CID_6;

		}else if (type == 7) {	// 旅行社给游客发（已有用户的游客）
			sms_model = SmsConstant.SMS_CID_7;

		}else if (type == 8) {	// 游客邀请围观
			sms_model = SmsConstant.SMS_CID_8;

		}

		// 替换短信模板中的可变内容
		content = SmsConstant.getContentById(sms_model);

		String invitee = StringUtils.isBlank(request.getInvitee()) ? "" : request.getInvitee();
		content = content.replace("%P1%", invitee);

		String inviter = StringUtils.isBlank(request.getInviter()) ? "" : request.getInviter();
		content = content.replace("%P2%", inviter);

		String travelAgency = StringUtils.isBlank(request.getTravelAgency()) ? "" : "，"+ request.getTravelAgency();
		content = content.replace("%P3%", travelAgency);

		String teamName = StringUtils.isBlank(request.getTeamName()) ? "" : request.getTeamName();
		content = content.replace("%P4%", teamName);

		String downloadURL = StringUtils.isBlank(request.getDownloadURL()) ? "" : request.getDownloadURL();
		content = content.replace("%P5%", downloadURL);

		params = "{";
		params += "invitee:" + request.getInvitee();
		params += ",inviter:" + request.getInviter();
		params += ",travelAgency:" + request.getTravelAgency();
		params += ",teamName:" + request.getTeamName();
		params += ",downloadUrl:" + request.getDownloadURL();
		params += "}";

		// 发送短信并记录短信
		result = this.sendMsgAndSave(phone, content, params, SmsConstant.SMS_TYPE_INVITE);

		if (!"0".equals(result)) {
			return CommonResponse.createNotSendMassageResponse();
		}

		CommonResponse response = new CommonResponse();
		response.setRetMsg("邀请加入短信发送成功！");
		return response;
	}

	/**
	 * 替换特殊字符
	 * @param content
	 * @return
     */
	private String checkContentWithSpechars(String content) {
		if (content.contains("【") || content.contains("】")) {
			content = content.replace("【", "<");
			content = content.replace("】", ">");
		}
		return content;
	}

	/**
	 * 发送短信并保存到数据库的短信表中
	 * @param phone
	 * @param content
	 * @param params
	 * @param type
	 * @return
	 * @throws UnsupportedEncodingException
     */
	private String sendMsgAndSave(String phone, String content, String params, String type) throws UnsupportedEncodingException {
		// 验证短信内容是否有特殊字符
		content = this.checkContentWithSpechars(content);
		String result = null;

		//获取发送短信的方式
		//国内的方式
		String chSMSType =  SmsConstant.SMS_TYPE;
		//国际的方式
		String i18SMSType = SmsConstant.I18SMS_TYPE;
		//国内手机发送发短信方式
		if (phone.startsWith("+86")) {
			result = sendMessageToPhone(chSMSType,restTemplate,phone,content);
		}
		//国际手机发送短信方式
		else{
			result =sendMessageToPhone(i18SMSType,restTemplate,phone,content);
		}

		// 保存短信
		createMessage(phone, content, type, params, result);
		return result;
	}

	private String  sendMessageToPhone(String smsType, RestTemplate restTemplate, String phone, String content) throws UnsupportedEncodingException{
		String result = null;
		String phoneNum = null;
		if(smsType.equals(SmsConstant.SMS_SMC)){
			//使用SMC的方式发送短信
			if (phone.startsWith("+86")) {
				phoneNum = phone.substring(phone.length() - 11);
				result = SmsHelper.sendMessage(restTemplate, phoneNum, content);
			}
		}else  if(smsType.equals(SmsConstant.SMS_MT)){
			//使用MT的方式发送短信
			phoneNum = phone.substring(1,phone.length());
			result = SmsHelper.sendI18Message(restTemplate, phoneNum, content);
		}else if(smsType.equals(SmsConstant.SMS_TX)){
			String countryCode = CountryCode.HandleCountryCodeOne(phone);

			if(countryCode.contains("+")){
				phone = phone.replace(countryCode,"");
				countryCode = countryCode.replace("+","");
			}

			if(phone.startsWith("0")){
				phone = phone.substring(1,phone.length());
			}
			//使用腾讯的方式发送短信
			try{
				SmsSingleSenderResult senderResult = smsSingleSender.send(0,countryCode,phone,content,"","");
				result = String.valueOf(senderResult.result) ;
			}catch (Exception e){
				e.printStackTrace();
				e.getMessage();
			}
		}
		return result;
	}


    /**
     * 检验手机验证码
     */
	@Override
	@Loggable(desc="检验手机验证码")
	public CommonResponse validateMobileCode(String phone, String code, Long userId) {
		if (StringUtils.isBlank(phone)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "手机号码不能为空！");
		}
		if (StringUtils.isBlank(code)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "手机验证码不能为空！");
		}

		CommonResponse respone = new CommonResponse();
		MessageRequest request = new MessageRequest();
		request.setPhone(phone);
		request.setValid(1);
		request.setMark("CODE");
		List<MessageEntity> mlist = this.findList(request);
		if (mlist.size() <= 0) {
			respone.setRetCode(ErrorConstant.BAD_SMS_VERIFICATION_CODE);
			respone.setRetMsg("手机验证码错误！");
			return respone;
		}
		MessageEntity message = mlist.get(0);
		// 获取当前时间
		Date nowDate = new Date();
		// 获取创建时间的之后30分钟
		Date createTime = DateUtils.addMinute(message.getCreateTime(), 30);
		// 如果创建时间后的30分钟小于当前时间，则返回验证码过期
		if (nowDate.getTime() > createTime.getTime()) {
			// 更新手机号对应短信验证码状态
			this.update(phone);

			respone.setRetCode(ErrorConstant.BAD_SMS_VERIFICATION_CODE);
			respone.setRetMsg("验证码已过期！");
			return respone;
		}

		String param = message.getParam();
		JSONObject obj = JSONObject.fromObject(param);
		if (code.equals(obj.get("checkCode").toString())) {
			// 更新手机号对应短信验证码状态
			this.update(phone);

			respone.setRetCode(ErrorConstant.SUCCESS);
			respone.setRetMsg("手机验证码正确！");
		} else {
			respone.setRetCode(ErrorConstant.BAD_SMS_VERIFICATION_CODE);
			respone.setRetMsg("手机验证码错误！");
		}
		return respone;
	}

	@Override
	public CommonResponse sendMailMessage(String title, String content) {
		String[] to = ConfigConstant.MAIL_RECIPIENT.split(",");
		try {
			MailUtil.mail(title, content, to);
			return new CommonResponse(0,"邮件发送成功！");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return CommonResponse.createNotSendMailResponse();
	}

	public String sendLeaveaMailMessage(String title, String content) {
		String[] to = ConfigConstant.MAIL_LEAVE_MESSAGE_RECIPIENT.split(",");
		try {
			MailUtil.mail(title, content, to);
			return new String("0,邮件发送成功！");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return new String("1004,邮件发送失败！");
	}
}
