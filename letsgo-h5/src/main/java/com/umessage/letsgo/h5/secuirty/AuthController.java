package com.umessage.letsgo.h5.secuirty;

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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by gaofei on 2016/12/8.
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    @Resource
    private AuthenticationManager authenticationManager;
    protected AuthenticationDetailsSource<HttpServletRequest,?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @RequestMapping(value = "/redirectToLogin", method = RequestMethod.GET)
    public String login( String targetUrl,Model model) {
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("url",targetUrl);
        model.addAttribute("response", hashMap);
        return "weixin/loginweixin";
    }

    @RequestMapping(value="/login",method= RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request){

        String targetUrl = request.getParameter("url");

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
        return targetUrl;
    }
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    @RequestMapping(value = "/redirectToLoginFailPage", method = RequestMethod.GET)
    public String loginFail(Model model) {
        //TODO 添加登陆失败的页面
        return "";
    }


}
