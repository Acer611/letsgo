package com.umessage.letsgo.core.extensions.springmvc;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhajl on 2016/6/8.
 */
public class GlobalAttributeInterceptor implements HandlerInterceptor {
    private Map<String, String> properties = new HashMap<String, String>();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if(modelAndView == null) return;

        boolean isRedirectView = modelAndView.getView() instanceof RedirectView;
        boolean isViewObject = modelAndView.getView() != null;
        boolean viewNameStartsWithRedirect = (modelAndView.getViewName() == null ? true :
                modelAndView.getViewName().startsWith(UrlBasedViewResolver.REDIRECT_URL_PREFIX));

        if(modelAndView.hasView() && (
                ( isViewObject && !isRedirectView) ||
                        (!isViewObject && !viewNameStartsWithRedirect))){
            addCommonModelData(modelAndView);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

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
