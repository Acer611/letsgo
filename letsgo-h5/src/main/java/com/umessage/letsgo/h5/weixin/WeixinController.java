package com.umessage.letsgo.h5.weixin;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.QueryUtils;
import com.umessage.letsgo.domain.po.security.ThirdPartyAccountEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.security.WxInfoEntity;
import com.umessage.letsgo.domain.po.security.WxTeamEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.system.request.RegisterRequest;
import com.umessage.letsgo.service.api.security.*;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import com.umessage.letsgo.service.common.security.WxUser;
import com.weixin.service.ICoreService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaofei on 2016/11/24.
 */

@Controller
@RequestMapping(value = "/wechat")
public class WeixinController {

    private static Logger logger = Logger.getLogger(WeixinController.class);
    @Resource
    private IWxInfoService wxInfoService;
    @Resource
    private IThirdPartyAccountService thirdPartyAccountService;
    @Resource
    private IWxTeamService wxTeamService;
    @Resource
    private ITravelUserService travelUserService;
    @Resource
    private IUserService userService;
    @Resource
    private UserLoginHelper userLoginHelper;
    @Resource
    protected ITeamService teamService;
    @Autowired
    protected ICoreService coreService;
    @Autowired
    protected WxMpService wxMpService;


    /**
     * 根据openID 获取到微信的信息并跳转到填写手机号验证码的界面
     * @param model
     * @return
     */

    @RequestMapping(value = "/getWeChatInfoByOpenID", method = RequestMethod.GET)
    public String getWeChatInfoByOpenID( Model model) {

        UserResponse loginUser = userLoginHelper.getLoginUser();
        //TODO 存在app用微信登陆后，可能无法录入微信信息表信息，无法推送模板消息
        if (loginUser.getUserEntity() != null) {
            return "weixin/success";
        }else{
            return "weixin/index";
        }
    }


    // 发送短信验证码 已存在 确认用那个接口

    /**
     *
     * 验证手机号验证码
     * 验证手机号验证码是否正确，同时确定用户是否是我们的用户，返回结果根据code 判断
     * 若是 0 表示用户存在  然后调用绑定的接口 binDingUserAndTeam，
     * 若是 404 表示用户不存在 跳转注册界面 调用redirectsToBindingPage 接口 跳转到binDingUser页面
     * 若code 是 1001 页面提示：输入的验证码不正确或者已过期 让用户确认后重新验证
     *
     * @param phone 手机号
     * @param code 验证码
     * @return
     */
    @RequestMapping(value = "/validateMobileCode", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse validateMobileCode(String phone, String code) {
        CommonResponse commonResponse = new CommonResponse();
        String sphone = QueryUtils.getPhone(phone);
        if(travelUserService.checkCodeIsError(code,sphone)){
            commonResponse.setRetCode(ErrorConstant.BAD_SMS_VERIFICATION_CODE) ;
            commonResponse = CommonResponse.createBadSmsVerificationCodeResponse();
            return commonResponse;
        }

        boolean flag = userService.existPhone(phone);
        if(flag){
            commonResponse.setRetCode(ErrorConstant.SUCCESS);
            commonResponse.setRetMsg("用户存在，且验证码正确");
        }else{
            commonResponse.setRetCode(ErrorConstant.PHONE_IS_NOT_EXIST);
            commonResponse.setRetMsg("您是未注册用户，请先注册");
        }
        return commonResponse;
    }


    /**
     *
     * 跳转到绑定的页面
     * @return
     */

    @RequestMapping(value = "/redirectsToBindingPage", method = RequestMethod.GET)
    public String redirectsToBindingPage() {
        return "weixin/binDingUser";
    }

    /**
     * 用户注册接口 copy form openApi
     * value = "phone:手机号,name:姓名,sex:性别{1:男，2：女}"
     * @param registerRequest
     * @return
     */
    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse userRegister(@RequestBody RegisterRequest registerRequest) {

        if (StringUtils.isEmpty(registerRequest.getPhone())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：手机号【phone】不能为空！");
        }

        if (StringUtils.isEmpty(registerRequest.getName())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：姓名【name】不能为空！");
        }
        CommonResponse commonResponse = userService.registerUser(registerRequest);
        return commonResponse;
    }

    /**
     * 绑定用户
     * 分为两步，1.绑定微信用户到跟上用户
     * 2.检查微信用户下是否有团信息和行程信息若存在 将微信名下的团信息绑定到跟上用户名下
     * @param phoneNumber 手机号
     * @return
     */

    @RequestMapping(value = "/binDingUserAndTeam", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse binDingUserAndTeam(String phoneNumber,Model model,HttpServletRequest request){
        CommonResponse resopnse = new CommonResponse();
        WxUser wxUser = userLoginHelper.getWxUser();
        if(null == wxUser){
            resopnse.setRetCode(ErrorConstant.INTERNAL_SERVER_ERROR);
            resopnse.setRetMsg("获取微信信息出错，请重新登陆！");
            return resopnse;
        }
        //判断微信信息表中是否有当前用户，若不存在进行补录
        //WxInfoEntity wxInfoEntity = wxInfoService.selectUserInfoByOpenID(wxUser.getUsername());
        String unionId = wxUser.getUnionId();
        WxInfoEntity wxInfoEntity = wxInfoService.selectWeixinInfoByOpenIDAndUnionID(unionId,wxUser.getUsername());
        if(null == wxInfoEntity){
            logger.info("进行微信信息的数据补录");
            String appID = wxMpService.getWxMpConfigStorage().getAppId();
            String openID = wxUser.getUsername();
            WxMpUser wxMpUser = coreService.getUserInfo(openID, "zh_CN");
            wxInfoService.subscribe(unionId,wxMpUser,false,appID);
            logger.info("进行微信信息补录完成");
        }

        try {
            boolean flag = true;
            List<WxTeamEntity> wxTeamEntityList = wxTeamService.selectWxTeamInfoListByUnionid(unionId);
            long teamID = -1;
            for (WxTeamEntity wxTeamEntity:wxTeamEntityList) {
                teamID = wxTeamEntity.getTeamId();
                TeamEntity teamEntity = teamService.selectById(teamID);
                flag = thirdPartyAccountService.bindingUser(unionId,phoneNumber,teamID,teamEntity.getTxGroupid());
            }
            //如果当前微信账号下没有团信息，进行单独的用户绑定
           if( wxTeamEntityList.isEmpty()){
               UserEntity userEntity = userService.selectUserIfoByphone(phoneNumber);
               thirdPartyAccountService.bindingWxUserToOurUser(unionId,userEntity);
           }

            if(flag){
                resopnse.setRetCode(ErrorConstant.SUCCESS);
                resopnse.setRetMsg("微信账号绑定成功！");

            }else{
                resopnse.setRetCode(ErrorConstant.INTERNAL_SERVER_ERROR);
                resopnse.setRetMsg("系统服务错误，请重试！");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorConstant.INTERNAL_SERVER_ERROR, "微信绑定失败");
        }
        HttpSession session = request.getSession();
        // 这个非常重要，否则验证后将无法登陆
       // session.removeAttribute("SPRING_SECURITY_CONTEXT");
        session.invalidate();
        logger.info("清除session .." );

        model.addAttribute("response",resopnse);
        return resopnse;
    }


}
