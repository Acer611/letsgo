package com.umessage.letsgo.core.annotation.dataitem;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented //文档  
@Retention(RetentionPolicy.RUNTIME) //在运行时可以获取  
@Target({ ElementType.TYPE,ElementType.FIELD}) //作用到需要翻译的model类、以及包含该model类的类关联字段上
@Inherited //子类会继承 
public @interface Catalog {
	public String code() default ""; //数据字典的类目编号，使用示例 @Catalog(code="xxx")
}
