package com.umessage.letsgo.core.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.velocity.tools.config.DefaultKey;
import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;

@DefaultKey("vt")
public class VelocityTool {
    /**
     * 将对象转换成json字符串
     * @param obj
     * @return
     */
    public String toJson(Object obj) {
        if(obj==null) return "{}";
        return JsonUtils.obj2json(obj);
    }
    public int index(Object[] objs,Object obj){
        if(objs==null) return -1;
        return ArrayUtils.indexOf(objs, obj);
    }
    public int index(List objs,Object obj){
        if(CollectionUtils.isEmpty(objs)) return -1;
        return objs.indexOf(obj);
    }
    public Date nextSaturday(){
        DateTime dt=new DateTime(new Date());
        return  dt.plusDays((13-dt.getDayOfWeek())%7).toDate();
    }
    
//    public TeachPlaceType teachPlaceType(Integer mode){
//        return TeachPlaceType.fromCode(mode);
//    }
    
    public String timeTitle(Date date){
        StringBuffer sb=new StringBuffer();
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        sb.append(DateFormatUtils.format(c, "M月dd日"));
        c.add(Calendar.DATE, 6);
        sb.append(" - ");
        sb.append(DateFormatUtils.format(c, "M月dd日"));
        return sb.toString();
    }
}
