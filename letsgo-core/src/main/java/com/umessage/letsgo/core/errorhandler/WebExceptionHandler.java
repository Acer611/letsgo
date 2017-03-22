package com.umessage.letsgo.core.errorhandler;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.errorhandler.utils.AjaxUtils;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.UtilMisc;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class WebExceptionHandler extends SimpleMappingExceptionResolver {
    Logger logger = Logger.getLogger(WebExceptionHandler.class);

    private Map<String, String> properties = new HashMap<String, String>();

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                              Exception ex) {
        String targetUrl = "";
        Integer code = ErrorConstant.INTERNAL_SERVER_ERROR;
        if (ex instanceof BusinessException) {
            code = ((BusinessException) ex).getCode();
            targetUrl = ((BusinessException) ex).getTargetUrl();
            logger.warn("系统捕捉到业务异常！\r\n", ex);
        } else if (ex instanceof InvalidGrantException) {
            code = ErrorConstant.BAD_CREDENTIALS;
            logger.warn("系统捕捉到无效的授权异常！\r\n", ex);
        } else {
            logger.debug("系统捕捉到未处理异常！\r\n", ex);
        }

        if (StringUtils.isEmpty(targetUrl)){
            targetUrl = "/error";
        }

        String message = ex.getMessage();
        if(StringUtils.isEmpty(message)){
            message = "系统服务错误";
        }

        if (isHtmlResponse(request, handler)) {
            request.setAttribute("message", ex.getMessage());
            ModelAndView modelAndView = new ModelAndView(targetUrl);
            addCommonModelData(modelAndView);
            return modelAndView;
        } else {
            ModelAndView mav = new ModelAndView();
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            view.setAttributesMap(UtilMisc.toMap("retCode", code, "retMsg", message));
            mav.setView(view);
            return mav;
        }
    }

    /**
     * 根据Controller方法返回类型判断是json响应还是html响应
     * @param request
     * @param handler
     * @return
     */
    private boolean isHtmlResponse(HttpServletRequest request, Object handler){
        if (AjaxUtils.isAjaxRequest(request)) {
            return false;
        }

        if (handler != null && (handler instanceof HandlerMethod)){
            Method method = ((HandlerMethod) handler).getMethod();
            if (method == null){
                return false;
            }

            Class<?> returnType = method.getReturnType();
            if (returnType == null){
                return false;
            }

            if ("java.lang.String".equals(returnType.getName()) ||
                    "org.springframework.web.servlet.ModelAndView".equals(returnType.getName()) ){
                return true;
            }
        }

        return false;
    }

    private void addCommonModelData(ModelAndView mav) {
        mav.getModel().putAll(properties);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
