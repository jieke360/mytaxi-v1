/**
 * 
 */
package com.mytaxi.test.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

/**
 * @author xu.jianpu
 * 
 * 2012-8-23  下午05:11:55
 */
public class MethodAfterInterceptor implements AfterReturningAdvice{

	/**
	 * 调用方法执行后的拦截
	 */
	public void afterReturning(Object value, Method method, Object[] params,
			Object target) throws Throwable {
		System.out.println("方法 : "+method.getName());
		System.err.println("返回值："+value);
	}

}
