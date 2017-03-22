package com.umessage.letsgo.core.annotation.logger.processor;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.umessage.letsgo.core.annotation.logger.Loggable;

@Aspect
@Component
public class MethodLoggerProcessor {
	private Logger logger = LoggerFactory.getLogger(MethodLoggerProcessor.class);

	//Service层切点    
    @Pointcut("@annotation(com.umessage.letsgo.core.annotation.logger.Loggable)")
    //@Pointcut("execution(* *(..)) && @annotation(Loggable)")
     public  void  methodLoggerAspect() {    
    }
    
    @Around("methodLoggerAspect()")    
	public Object methodLogger(ProceedingJoinPoint pjp) throws Throwable {
    	long start = System.currentTimeMillis();
		Object obj = null;
		try {
			Object[] args = pjp.getArgs();
			String className = pjp.getTarget().getClass().getName();
			Method method= ((MethodSignature) pjp.getSignature()).getMethod();
			String methodName = method.getName();
			className = method.getDeclaringClass().getName();
			Loggable loggable = method.getAnnotation(Loggable.class);
			String logDesc = loggable.desc();
			/*
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession();
			// 读取session中的用户
			User user = (User) session.getAttribute(WebConstants.CURRENT_USER);
			// 请求的IP
			String ip = request.getRemoteAddr();
			*/
			logger.info("[{}] -- [{}], 方法描述：{}, 参数：{}", className, methodName, logDesc, Arrays.toString(args));

			obj = pjp.proceed();// 直接调用目标方法

			Long costtime = (System.currentTimeMillis() - start);
			logger.info("[{}] -- [{}], 方法描述：{}, 耗时：{}ms，返回：{}", className, methodName, logDesc, costtime.toString(),
					obj == null ? "" : obj.toString());
		} catch (Throwable e) {
			throw e;
		}

		return obj;
	}
    
 
    
   
}
