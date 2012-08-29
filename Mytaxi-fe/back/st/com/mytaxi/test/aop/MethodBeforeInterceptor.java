/**
 * 
 */
package com.mytaxi.test.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * @author xu.jianpu
 * 
 * 2012-8-23  下午05:09:12
 */
public class MethodBeforeInterceptor implements MethodBeforeAdvice{

	/**
	 * 执行方法前调用该方法
	 */
	public void before(Method method, Object[] params, Object target)
			throws Throwable {
		System.out.println("进入AOP Before  拦截");
		System.out.println("  即将要执行方法："+method.getName());
		
	}

}
