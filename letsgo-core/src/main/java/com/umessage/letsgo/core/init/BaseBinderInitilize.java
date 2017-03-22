package com.umessage.letsgo.core.init;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * controller的基础类，仅提供各种基础功能
 * BaseFunctionalController
 *
 * @author yongteng.huo 2015年3月3日
 * @see
 * @since 1.0
 */
public abstract class BaseBinderInitilize {

  @InitBinder  
  protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {  
         binder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
                 private String format_10="yyyy-MM-dd";
                 private String format_13="yyyy-MM-dd HH";
                 private String format_16="yyyy-MM-dd HH:mm";
                 private String format_19="yyyy-MM-dd HH:mm:ss";
                 public String getAsText() {
                     return new SimpleDateFormat(format_19)
                     .format((Date) getValue());
                 }
                 public void setAsText(String text) throws IllegalArgumentException {
                     try {
                         if(StringUtils.isEmpty(text)) setValue(null);
                         switch (text.length()) {
                            case 10:setValue(new SimpleDateFormat(format_10).parse(text));break;
                            case 13:setValue(new SimpleDateFormat(format_13).parse(text));break;
                            case 16:setValue(new SimpleDateFormat(format_16).parse(text));break;
                            case 19:setValue(new SimpleDateFormat(format_19).parse(text));break;
                            default:setValue(null);break;
                         }
                     } catch (ParseException e) {
                         throw new IllegalArgumentException(e);
                     }
                 }

         });  
  }
}
