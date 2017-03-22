package com.umessage.letsgo.service.common.security;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.dao.security.WxInfoDao;
import com.umessage.letsgo.domain.po.security.*;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.security.respone.WeixinUserInfoResponse;
import com.umessage.letsgo.service.api.security.IThirdPartyAccountService;
import com.umessage.letsgo.service.api.security.IUserRoleService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.security.IValidationCodeService;
import com.umessage.letsgo.service.common.constant.WxConstant;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhajl on 2016/6/2.
 */
public class UserAccountUserDetailsService implements UserDetailsService {

    private Logger logger = Logger.getLogger(UserAccountUserDetailsService.class);
    @Resource
    private IUserService userService;
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private IValidationCodeService validationCodeService;
    @Resource
    private WxInfoDao wxInfoDao;
    @Resource
    private IThirdPartyAccountService thirdPartyAccountService;
    @Resource
    private RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserResponse userResponse = null;
        try {
            userResponse = userService.getUserByLoginAccount(username);
        } catch (Exception e) {
            throw new BusinessException(ErrorConstant.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        if (userResponse.getRetCode().equals(ErrorConstant.NOT_FOUND) || userResponse.getUserEntity() == null) {
            throw new UsernameNotFoundException(userResponse.getRetMsg());
        }
        if (userResponse.getUserEntity().getUserStatus() != 1){
            throw new BusinessException(ErrorConstant.USER_DISABLED,userResponse.getUserEntity().getUserStatusDesc());
        }

        UserEntity userEntity = userResponse.getUserEntity();
        // 用户角色权限
        List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(userEntity);

        return new User(userEntity.getUserName(), StringUtils.defaultString(userEntity.getPassword()), grantedAuthorities);
    }

    private List<GrantedAuthority> getGrantedAuthorities(UserEntity userEntity) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        List<UserRoleEntity> userRoles = userRoleService.getUserRoleByUserId(userEntity.getId());
        for (UserRoleEntity userRoleEntity : userRoles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRoleEntity.getRole().getRolename());
            grantedAuthorities.add(authority);
        }
        return grantedAuthorities;
    }

    public boolean isSmsCodeValid(String username, String code){
        ValidationCodeEntity codeEntity = validationCodeService.getValidCodeByPhone(username, 1);
        if (codeEntity.getId() == null) {
            codeEntity = validationCodeService.getValidCodeByPhone(username);
        }

        // 对比当前有效的验证码和用户输入的验证码
        if (code.equals(codeEntity.getCode())) {
           return true;
        } else{
            return false;
        }
    }

    public UserDetails loadUserAndCreateByUsername(String username) throws UsernameNotFoundException {

        UserResponse userResponse = null;
        try {
            userResponse = userService.getUserByLoginAccount(username);
        } catch (Exception e) {
            throw new BusinessException(ErrorConstant.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        UserEntity userEntity = userResponse.getUserEntity();
        if (userResponse.getRetCode().equals(ErrorConstant.NOT_FOUND) || userResponse.getUserEntity() == null) {
            userEntity = userService.createAccountByTeamMember(username);
            if (userEntity == null || userEntity.getId() == null )
                throw new BusinessException(ErrorConstant.NOT_JOIN, "您的手机号尚未注册，请注册后重新登录。");
        }

        if (userEntity.getUserStatus() != 1){
            throw new BusinessException(ErrorConstant.USER_DISABLED, userEntity.getUserStatusDesc());
        }

        // 用户角色权限
        List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(userEntity);

        return new User(userEntity.getUserName(), StringUtils.defaultString(userEntity.getPassword()), grantedAuthorities);
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
        String requestUrl = WxConstant.WX_APP_USERINFO_URL;
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
            } catch (Exception e) {
                e.printStackTrace();
                WeixinUserInfoResponse.createNotFoundResponse("获取微信用户信息失败");
            }
        }
        return weixinUserInfo;
    }

    /**
     * 根据openId和用户token获取用户信息
     */
    public UserDetails loadUserByWxUnionId(String unionId) {
        //1 根据unionID查询第三方账号信息
        logger.info("UserAccountUserDetailsService 类 当前的unionid是：" + unionId);

        ThirdPartyAccountEntity thirdPartyAccountEntity = thirdPartyAccountService.selectByUnionID(unionId);


        if (null == thirdPartyAccountEntity || thirdPartyAccountEntity.getUserId() == null) {
            // 用户未绑定微信则返回
            throw new BusinessException(ErrorConstant.USER_NOT_BINDING, "用户尚未绑定微信");
        }

        //3 根据用户ID查询绑定的用户信息
        UserEntity userEntity = userService.getUserById(thirdPartyAccountEntity.getUserId());

        if (userEntity == null) {
            throw new BusinessException(ErrorConstant.NOT_FOUND, "未找到该微信绑定的用户信息");
        }

        if (userEntity.getUserStatus() != 1){
            throw new BusinessException(ErrorConstant.USER_DISABLED, userEntity.getUserStatusDesc());
        }

        // 用户角色权限
        List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(userEntity);
        return new User(userEntity.getUserName(), StringUtils.defaultString(userEntity.getPassword()), grantedAuthorities);
    }

    public final WxUser createWxUserDetails(String username, String password, String unionId){
        logger.info("获取当前的用户信息username  password  unionId"+username + " ,  "+password + " ,  "+unionId + "   ");
        // 用户角色权限
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_WX_USER");
        grantedAuthorities.add(authority);
        return new WxUser(username,StringUtils.defaultString(password), grantedAuthorities, unionId);
    }

}
