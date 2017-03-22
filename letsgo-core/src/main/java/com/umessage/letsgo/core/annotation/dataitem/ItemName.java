package com.umessage.letsgo.core.annotation.dataitem;

import java.lang.annotation.*;

@Documented //文档
@Retention(RetentionPolicy.RUNTIME) //在运行时可以获取
@Target({ElementType.FIELD}) //作用到model类翻译后的字段上面
public @interface ItemName {
	public String code() default ""; //数据字典的数据项编码，使用示例 @ItemName(code="xxx")
}
