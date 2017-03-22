package com.umessage.letsgo.core.annotation.dataitem;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Documented //文档
@Retention(RetentionPolicy.RUNTIME) //在运行时可以获取
@Target({ElementType.PARAMETER, ElementType.METHOD}) //作用到需要翻译的Service方法上
public @interface DataItem {

}
