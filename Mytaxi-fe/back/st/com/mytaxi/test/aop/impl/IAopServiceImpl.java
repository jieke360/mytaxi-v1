/**
 * 
 */
package com.mytaxi.test.aop.impl;

import javax.security.auth.login.AccountException;

import com.mytaxi.test.aop.IAopService;

/**
 * @author xu.jianpu
 * 
 * 2012-8-23  下午05:06:09
 */
public class IAopServiceImpl implements	IAopService{
	
	private String name ;

	/* (non-Javadoc)
	 * @see com.mytaxi.test.aop.IAopService#withAop()
	 */
	public void withAop() throws Exception {
		
		System.out.println(" withAop  我在运行啦");
		if(name.trim().length()==0) throw new AccountException("name属性不可以为空！");
	}

	/* (non-Javadoc)
	 * @see com.mytaxi.test.aop.IAopService#withoutAop()
	 */
	public void withoutAop() throws Exception {
		System.out.println(" withoutAop  我在运行啦 , 没有AOP在切入");
		
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
