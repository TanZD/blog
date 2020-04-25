package com.zd.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
public class LogAspect {
	private static final Logger logger = LogManager.getLogger();

	public void conAspect() {
	}

	public Object takeSeats(ProceedingJoinPoint call) throws Throwable {
		Object result = null;
		String className = call.getTarget().getClass().getName();
		String methodName = call.getSignature().getName();
		logger.info("开始执行" + className + "." + methodName + "()方法");
		try {
			result = call.proceed();
		} catch (Exception e) {
			e.printStackTrace();			
			StackTraceElement stackTraceElement = e.getStackTrace()[e.getStackTrace().length - 1];
			logger.error(e.getStackTrace());
			logger.error("方法: " + className + "." + methodName);
			logger.error("错误位置: "+stackTraceElement.getLineNumber());
			logger.error("错误信息: " + e);
		}
		return result;
	}

}
