package com.umessage.letsgo.travel.controller.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jrmf360.vo.response.WalletCommonResponse;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.QueryUtils;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.TravelsValidateResponse;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.IMessageService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.security.request.UserVo;
import com.umessage.letsgo.domain.vo.security.respone.UserListResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.security.ITravelUserService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

/**
 * Created by ZhaoYidong on 2016/6/17.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {



    @Resource
    private ITravelUserService travelUserService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;
    @Resource
    private IUserService userService;
    @Resource
    private IMessageService messageService;

    @Resource
    private ShaPasswordEncoder shaPasswordEncoder;


/*
跳到编辑账号页面:修改
 */
    @RequestMapping(value = "/userEdit",method = RequestMethod.GET)
    public String userEdit(Model model){
        UserResponse userResponse =oauth2LoginHelper.getLoginUser();
        model.addAttribute("response",userResponse);
        return "team/userEdit";
    }
    @RequestMapping(value = "userUpdatePassword",method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse userUpdatePassword(UserEntity user){
        return travelUserService.updateTravelUser(user);
    }
    /*
跳到编辑账号页面:修改
 */
    @RequestMapping(value = "/userEdits",method = RequestMethod.GET)
    public String userEdits(Model model){
        UserResponse userResponse =oauth2LoginHelper.getLoginUser();
        model.addAttribute("response",userResponse);
        return "childAccount/Edit";
    }
    /*
跳到编辑账号页面:新增
 */
    @RequestMapping(value = "/userAdd",method = RequestMethod.GET)
    public String edit(Model model){
        UserResponse userResponse =oauth2LoginHelper.getLoginUser();
        model.addAttribute("response",userResponse);
        return "childAccount/addAccount";
    }
    /*
    更新账号
     */
    @RequestMapping(value = "/userUpdate",method = RequestMethod.GET)
    public String userUpdate(UserEntity user){
        travelUserService.updateTravelUser(user);
        return "childAccount/accountManagement";
    }
    /*
    添加或者编辑账号
     */
    @RequestMapping(value = "/saveOrUpdatenAccount",method = RequestMethod.POST)
    public String saveOrUpdatenAccount(UserVo userVo, Model model)throws Exception{
        String encodePassword = shaPasswordEncoder.encodePassword(userVo.getPassword(), userVo.getUserName());
        userVo.setPassword(encodePassword);
		travelUserService.saveOrUpdatenAccount(userVo);
        return "redirect:getSonAccountList?travelerId=&pageNo=1" ;
    }
    /*
    获取子账号列表
     */
    @RequestMapping(value = "/getSonAccountList",method = RequestMethod.GET)
    public String getSonAccountList(Integer pageNo,Model model){

        UserListResponse userListResponse= travelUserService.getSonAccountList(pageNo);
        model.addAttribute("userListResponse",userListResponse);
        return "childAccount/accountManagement";
    }
    /*
    删除账号
     */
    @RequestMapping(value = "/deleteAccount",method = RequestMethod.GET)
    public String deleteAccount(Long accountId,Model model)throws Exception{
		travelUserService.updateAccount(accountId);
		return "redirect:getSonAccountList?travelerId=&pageNo=1";
    }
    /*
    查询账号
     */
    @RequestMapping(value = "/queryAccount",method = RequestMethod.GET)
    public String queryAccount(Long accountId,Model model){

        UserVo userVo= travelUserService.queryAccount(accountId);
        model.addAttribute("userVo",userVo);
        return "childAccount/Edit";
    }

    /**
     * 发送验证码短信
     * @param phone
     * @param scope
     * @param req
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/sendValidCode", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse sendValidCode(@RequestParam(value = "phone") String phone, @RequestParam(value = "scope", required = false) Integer scope,
            HttpServletRequest req
    ) throws UnsupportedEncodingException {

        if (org.apache.commons.lang.StringUtils.isBlank(phone)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "接收人手机号不能为空！");
        }

        String client = req.getParameter("client_id");
        // 验证用户是游客端还是领队端
        if ((scope !=null && scope !=3) && !userService.checkUserRole(client, phone)) {
            return CommonResponse.createUnauthorizedUser("用户身份不符，不予以发送短信！");
        }

        return messageService.sendCheckCodeMessage(phone, scope);
    }

    /**
     * 找回登录密码
     * @param phone
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/retrievePassword", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse retrievePassword(@RequestParam(value = "phone") String phone, @RequestParam(value = "newPassword") String newPassword) {

        if (StringUtils.isBlank(phone)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：手机号【phone】不能为空！");
        }

        if (StringUtils.isBlank(newPassword)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：新密码【newPassword】不能为空！");
        }

        String sphone = QueryUtils.getPhone(phone);
        return userService.retrievePassword(sphone,newPassword);
    }

    /*
    跳转忘记密码页面
     */
    @RequestMapping(value = "/userForgotPassword",method = RequestMethod.GET)
    public String userRetrievePassword(){
        return "change";
    }

    /*
    跳转新密码页面
     */
    @RequestMapping(value = "/userNewPassword",method = RequestMethod.GET)
    public String userNewPassword(@RequestParam(value = "phone") String phone, Model model){
        model.addAttribute("phone",phone);
        return "changeNew";
    }

    /**
     * 验证用户手机短信验证码
     * @param phone
     * @param code
     * @return
     */
    @RequestMapping(value = "/validateMobileCode", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse validateMobileCode(@RequestParam String phone, @RequestParam String code) {
        CommonResponse commonResponse = new CommonResponse();
        String sphone = QueryUtils.getPhone(phone);
        if(travelUserService.checkCodeIsError(code,sphone)){
            return CommonResponse.createBadSmsVerificationCodeResponse();
        }
        commonResponse.setRetMsg("验证码正确");
        return commonResponse;
    }

}

