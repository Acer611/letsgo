package com.umessage.letsgo.login.controller;

import com.alibaba.fastjson.JSONObject;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by zhajl on 2016/6/3.
 */
@Controller
public class AuthController {
    @Resource
    private UserLoginHelper userLoginHelper;
    @Resource
    private AuthenticationManager authenticationManager;
    protected AuthenticationDetailsSource<HttpServletRequest,?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @RequestMapping(value="/login",method= RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request){
        /*
        if(!checkValidateCode(request)){
            return "验证码错误！";
        }
        */

        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authentication = authenticationManager.authenticate(authRequest); //调用loadUserByUsername
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆

            // Allow subclasses to set the "details" property
            setDetails(request, authRequest);

        } catch (AuthenticationException ex) {
            return "用户名或密码错误";
        }

        return "";
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * 验证码判断
     * @param request
     * @return
     */
    protected boolean checkValidateCode(HttpServletRequest request) {
        String result_verifyCode = request.getSession().getAttribute("verifyResult")
                .toString(); // 获取存于session的验证值
        // request.getSession().setAttribute("verifyResult", null);
        String user_verifyCode = request.getParameter("verifyCode");// 获取用户输入验证码
        if (null == user_verifyCode || !result_verifyCode.equalsIgnoreCase(user_verifyCode)) {
            return false;
        }
        return true;
    }
}
