package com.umessage.letsgo.core.annotation.dataitem.processor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.annotation.dataitem.ItemName;
import com.umessage.letsgo.core.annotation.dataitem.ItemValue;
import com.umessage.letsgo.core.init.constant.InitConstant;

@Aspect
@Component
public class DateItemAnnotationProcessor {
	
    //缩进深度  
    int levelDepth = 0;  
    //防止循环引用的检查者，循环引用现象如：a包含b，而b又包含a  
    Collection<Object> cyclicChecker = new ArrayList();  

	//Service层切点    
    @Pointcut("@annotation(com.umessage.letsgo.core.annotation.dataitem.DataItem)")    
     public  void  dataItemAspect() {    
    }
    
    @Around("dataItemAspect()")    
	public Object handleDataItem(ProceedingJoinPoint pjp) throws Throwable {
		Object obj = null;
		try {
			Object[] args = pjp.getArgs();
			String methodName = pjp.getSignature().getName();
			obj = pjp.proceed();// 直接调用目标方法
			try {
				if (obj != null){
					handleCatalogAnnotation(obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Throwable e) {
			throw e;
		}

		return obj;
	}
        
    /** 
     * 查找数据字典注解，并对照数据字典进行翻译
     * 如果对象类型为Collection和Map类型及数组对象，则需要递归调用injectionDataItem进行处理  
     * @param obj 
     * @throws Exception 
     */  
    public void handleCatalogAnnotation(Object obj) throws Exception {
		if (obj == null){
			return;
		}
        //循环引用现象处理  
        if (cyclicChecker.contains(obj)) {  
            return;  
        }  
        cyclicChecker.add(obj);  
  
        //首先处理Collection和Map类型 及数组
        if (obj instanceof Collection) {  
            for (Iterator i = ((Collection) obj).iterator(); i.hasNext();) {  
                handleCatalogAnnotation(i.next());  
            }  
        } else if (obj instanceof Map) {  
            for (Iterator i = ((Map) obj).keySet().iterator(); i.hasNext();) {  
                handleCatalogAnnotation(i.next());  
            }  
        } else if (obj.getClass().isArray()) {
        	Object[] array = (Object[])obj;
        	for (int i = 0; i <  array.length; i++) {
        		handleCatalogAnnotation(array[i]);  
			}
        } else {
			// 处理Catalog嵌套注解字段
			handleCatalogFieldAnnotation(obj);
            //如果obj没有被Catalog Annotation修饰过了（注意annotation是具有继承性的），
        	// 则不需要使用数据字典
            if (!obj.getClass().isAnnotationPresent(Catalog.class)) {
            	return;
            } 
        	
        	Catalog catalog = obj.getClass().getAnnotation(Catalog.class);  
            String catalogCode = null;
            if (catalog.code().length() > 0) {  
                catalogCode = catalog.code();  
            }
            
            //如果没有被修饰，则直接输出其toString()作为元素值  
            if (StringUtils.isNotBlank(catalogCode)){  
                handleItemAnnotation(obj, catalogCode);  
            }    
        }  
        cyclicChecker.remove(obj);  
    }
    
    /** 
     * 返回对象的成员变量的值（字符串类型） 
     * @param field 对象的成员变量 
     * @param obj 对象 
     * @return 对象的成员变量的值（字符串类型） 
     */  
    private String getFieldValue(Field field, Object obj) {  
        String value = null; 
		Class fieldTypeClass = field.getType();
        try {  
            if (fieldTypeClass == String.class) {  
                value = (String) field.get(obj);  
            } else if (fieldTypeClass == int.class) {  
                value = Integer.toString(field.getInt(obj));  
            } else if (fieldTypeClass == Integer.class) {
				value = field.get(obj).toString();
			}else if (fieldTypeClass == long.class) {
                value = Long.toString(field.getLong(obj));  
            }else if (fieldTypeClass == Long.class) {
				value = field.get(obj).toString();
			} else if (fieldTypeClass == short.class) {
                value = Short.toString(field.getShort(obj));
			} else if (fieldTypeClass == Short.class) {
				value = field.get(obj).toString();
            } else if (fieldTypeClass == float.class) {
                value = Float.toString(field.getFloat(obj));
			} else if (fieldTypeClass == Float.class) {
				value = field.get(obj).toString();
            } else if (fieldTypeClass == double.class) {
                value = Double.toString(field.getDouble(obj));
			} else if (fieldTypeClass == Double.class) {
				value = field.get(obj).toString();
            } else if (fieldTypeClass == byte.class) {
                value = Byte.toString(field.getByte(obj));
			} else if (fieldTypeClass == Byte.class) {
				value = field.get(obj).toString();
			} else if (fieldTypeClass == char.class) {
                value = Character.toString(field.getChar(obj));
			} else if (fieldTypeClass == Character.class) {
				value = field.get(obj).toString();
			} else if (fieldTypeClass == boolean.class) {
                value = Boolean.toString(field.getBoolean(obj));
			} else if (fieldTypeClass == Boolean.class) {
				value = field.get(obj).toString();
			}
        } catch (Exception ex) {  
            value = null;  
        }  
        return value;  
    }

	public void handleCatalogFieldAnnotation(Object obj) throws Exception {
		// 取出对象的成员变量
		Field[] fields = obj.getClass().getDeclaredFields();

		// 获取注解
		for (Field field : fields) {
			// 判断是否有标注
			if (!field.isAnnotationPresent(Catalog.class)){
				continue;
			}

			// 重要:避免java虚拟机检查对私有成员的访问权限
			field.setAccessible(true);
			Object value = field.get(obj);
			if (value != null){
				handleCatalogAnnotation(value);
			}
		}
	}

    /** 
     * 输出对象的字段，当对象的字段为Collection或者Map类型时， 
     * 要调用exportObject方法继续处理 
     * @param obj 被处理的对象 
     * @throws Exception 
     */  
	public void handleItemAnnotation(Object obj, String catalogCode) throws Exception {
		// 取出对象的成员变量
		Field[] fields = obj.getClass().getDeclaredFields();
		Map<String, String> valueMap = new HashMap<String, String>();

		// 获取注解
		for (Field field : fields) {
			// 判断是否有标注
			if (!field.isAnnotationPresent(ItemValue.class)){
				continue;
			}
			
			ItemValue itemValueAnnotation = field.getAnnotation(ItemValue.class);
			String itemCode = itemValueAnnotation.code();
			// 有标注，但是code未空
			if (StringUtils.isBlank(itemCode)){
				continue;
			}
			
			// 重要:避免java虚拟机检查对私有成员的访问权限
			field.setAccessible(true);
			String value = getFieldValue(field, obj);

			//如果能获得成员变量的值，则说明是基本类型
            if (value != null) { 
            	valueMap.put(itemCode, value);
			}
		}
		
		for (Field field : fields) {
			// 获得成员变量的标注
			if (!field.isAnnotationPresent(ItemName.class)){
				continue;
			}			

			ItemName itemNameAnnotation = field.getAnnotation(ItemName.class);
			String itemCode = itemNameAnnotation.code();
			// 有标注，但是code未空			
			if (StringUtils.isBlank(itemCode)){
				continue;
			}
			
			String value = valueMap.get(itemCode);
			// 根据code找不到对应ItemValue
			//valueMap.containsKey(itemCode);
			if (StringUtils.isBlank(value)){
				continue;
			}			
			
			// 获取数据字典key，并根据key找到对应的value
			// key格式  catalogCode + "_" + itemCode + "_" + value
			String key = catalogCode + "_" + itemCode + "_" + value;
			String name = InitConstant.DATA_ITEM_MAP.get(key);
			if (StringUtils.isBlank(name)){
				continue;
			}	
			
			// 重要:避免java虚拟机检查对私有成员的访问权限
			field.setAccessible(true);
			Class typeClass = field.getType();

			//如果是String类型，则设置itemName
            if (typeClass == String.class) {
            	field.set(obj, name);
            }
		}
	} 
    
}
