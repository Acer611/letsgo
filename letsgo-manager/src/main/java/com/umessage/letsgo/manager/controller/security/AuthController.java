package com.umessage.letsgo.manager.controller.security;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.QueryUtils;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.team.respone.TravelAgencyResponse;
import com.umessage.letsgo.manager.controller.system.CaptchaController;
import com.umessage.letsgo.service.api.security.ITravelUserService;
import com.umessage.letsgo.service.api.security.IUserRoleService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.IMessageService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private ITravelAgencyService travelAgencyService;
    @Resource
    private IMessageService messageService;
    @Resource
    private ITravelUserService travelUserService;
    @Resource
    private IUserService userService;
    @Resource
    private IUserRoleService userRoleService;

    protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @RequestMapping("/login")
    public String loginPage(@RequestParam(value = "targetUrl", required = false) String targetUrl, Model model) {
        model.addAttribute("targetUrl", targetUrl);
        return "login";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String singnin(@RequestParam String username, @RequestParam String password, @RequestParam String random, Model model, HttpServletRequest request) {
        model.addAttribute("username",username);

        CaptchaController captcha = new CaptchaController();
        CommonResponse response = captcha.validate(random, request);
        if (response.getRetCode() != ErrorConstant.SUCCESS) {
            model.addAttribute("response", response);
            return "login";
        }

        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        try {
            //调用loadUserByUsername
            Authentication authentication = authenticationManager.authenticate(authRequest);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession();
            // 这个非常重要，否则验证后将无法登陆
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // Allow subclasses to set the "details" property
            setDetails(request, authRequest);
        } catch (AuthenticationException ex) {
            response = CommonResponse.createBadCredentialsResponse();
            model.addAttribute("response", response);
            return "login";
        }

        return "redirect:/schedule/main";
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /*
    @RequestMapping(value = "/signout", method = RequestMethod.POST)
    public String signout(HttpServletRequest request){
        HttpSession session = request.getSession();
        // 这个非常重要，否则验证后将无法登陆
        session.removeAttribute("SPRING_SECURITY_CONTEXT");
        return "redirect:/login";
    }
    */

    @RequestMapping(value = "/registerInit",method =RequestMethod.GET)
    public String registerInit(){
        return "register";
    }

    @RequestMapping(value = "/registerAdd",method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse registerAdd(TravelAgencyEntity travel, UserEntity user, String validationCode){
        if(travel == null || user == null || StringUtils.isEmpty(validationCode)){
            return CommonResponse.createEmptyParameterResponse("请求参数不能为空");
        }
        TravelAgencyResponse travelAgencyResponse = travel.dataVerify();
        if(travelAgencyResponse.getRetCode() != 0){
            return travelAgencyResponse;
        }
        String phone = QueryUtils.getPhone(travel.getContactPhone());
        if(travelUserService.checkCodeIsError(validationCode,phone)){
            return CommonResponse.createBadSmsVerificationCodeResponse();
        }
        if(travelUserService.accountIsRepeated(user.getUserName())){
            return CommonResponse.createUserRepeatResponse();
        }
        // 创建旅行社信息
        travelAgencyService.addTravelAgency(travel);

        user.setRealName(travel.getName());
        user.setTravelerId(travel.getId()); // 在用户表中存储旅行社ID
//        user.setPhone(phone);
        travelUserService.addTravelUser(user);//创建用户
        userRoleService.createUserRoleByUserId(user.getId(),5L);//创建用户角色关系

        /*travel.setUserId(user.getId());
        travelAgencyService.addTravelAgency(travel);*/

        messageService.sendMailMessage(travel.getName()+"注册",getMailContent(travel));
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setRetMsg("旅行社注册成功,正在进入审核中···");
        return commonResponse;
    }

    private String getMailContent(TravelAgencyEntity travel){
        StringBuffer sb = new StringBuffer();
        sb.append("<html>");
        sb.append("<body>");
        sb.append("<table>");
        sb.append("<tr><td>旅行社名称：</td>").append("<td>").append(travel.getName()).append("</td>").append("</tr>");
        sb.append("<tr><td>联系人姓名：</td>").append("<td>").append(travel.getContactPerson()).append("</td>").append("</tr>");
        sb.append("<tr><td>联系人电话：</td>").append("<td>").append(travel.getContactPhone()).append("</td>").append("</tr>");

        sb.append("<tr><td>email：</td>").append("<td>").append(travel.getEmail()).append("</td>").append("</tr>");
        sb.append("<tr><td>旅行社地址：</td>").append("<td>").append(travel.getAddress()).append("</td>").append("</tr>");
        sb.append("<tr><td>简介：</td>").append("<td>").append(travel.getDesc()).append("</td>").append("</tr>");
        sb.append("<tr><td>营业执照图片地址：</td>").append("<td>").append(travel.getLicenseUrl()).append("</td>").append("</tr>");

        sb.append("</table>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    @RequestMapping(value = "sendCodeMessage",method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse sendCodeMessage(String phone) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(phone)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "接收人手机号不能为空！");
        }
        return messageService.sendCodeMessageForTravel(QueryUtils.getPhone(phone));
    }
    @RequestMapping(value = "usernameRepeatCheck",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Boolean> usernameIsRepeated(String userName){
        boolean isRepeated= travelUserService.accountIsRepeated(userName);
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        map.put("valid",isRepeated);
        return map;
    }
    @RequestMapping(value = "travelPhoneRepeatCheck",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Boolean> travelPhoneIsRepeat(String phone){
        UserEntity user= userService.getUserByPhone(QueryUtils.getPhone(phone));
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        map.put("valid",user != null);
        return map;
    }
}
