package com.umessage.letsgo.core.errorhandler.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.WebRequest;

public class AjaxUtils {

	public static boolean isAjaxRequest(WebRequest request) {
		boolean isAjax = false;
		if (request.getHeader("X-Requested-With") != null
				&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1) {
			isAjax = true;
		}
		return isAjax;
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		boolean isAjax = false;
		if (request.getHeader("X-Requested-With") != null
				&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1) {
			isAjax = true;
		}
		return isAjax;
	}
    
	public static boolean isAjaxUploadRequest(WebRequest webRequest) {
		return webRequest.getParameter("ajaxUpload") != null;
	}
	
	private AjaxUtils() {}

}
