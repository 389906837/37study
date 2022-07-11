package com.cloud.cang.datasource;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;

/**
 * 动态数据源切换拦截器
 * 
 */
@Aspect
public class DataSourceAspect implements Ordered // implements MethodInterceptor 
{

	@Autowired
	@Qualifier("defaultDataSource")
	private String defaultDataSource;
	Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);
	
	@Pointcut("execution(* com.cloud.cang..*.*ServiceImpl.*(..))")
    public void datasourceExecution(){}  
	
	
	@Around("datasourceExecution()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = null;
		MethodSignature ms = (MethodSignature) pjp.getSignature();
		Method method = ms.getMethod();
		DataSource annotation = method.getAnnotation(DataSource.class);
		try {
			if (null ==annotation) {
				Object target=pjp.getTarget();
				annotation = target.getClass().getAnnotation(DataSource.class);
			}
			if(logger.isDebugEnabled()){
				logger.debug("get annotation for  "+pjp.getTarget().getClass()+"  ");
			}
			if(null ==annotation){
				DataSourceContextHolder.setDataSourceType(defaultDataSource);
			}else{
				DataSourceContextHolder.setDataSourceType(annotation.value());
			}
			retVal = pjp.proceed();
		} catch (Throwable e) {
			throw e;
		} finally {
			DataSourceContextHolder.clearDataSourceType();
		}
		return retVal;

	}
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Object retVal = null;
		Method method = invocation.getMethod();
		DataSource annotation = method.getAnnotation(DataSource.class);
		try {
			if (null ==annotation) {
				annotation =AopUtils.getTargetClass(invocation.getThis()).getAnnotation(DataSource.class);
			}
			if(null ==annotation){
				DataSourceContextHolder.setDataSourceType(defaultDataSource);
			}else{
				DataSourceContextHolder.setDataSourceType(annotation.value());
			}
			retVal = invocation.proceed();
		} catch (Throwable e) {
			throw e;
		} finally {
			DataSourceContextHolder.clearDataSourceType();
		}
		return retVal;

	
	}


	@Override
	public int getOrder() {
		return 1;
	}
}
