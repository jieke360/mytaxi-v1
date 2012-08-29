/**
 * 
 */
package com.great.mytaxi.fe.business.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.great.mytaxi.fe.business.BaseProcess;
import com.great.mytaxi.fe.message.ClientMessage;

/**
 * 
 * 	登录请求处理类
 * 
 * @author xu.jianpu
 * 
 * 2012-8-13  下午12:23:28
 */
public class LoginRequestImpl extends BaseProcess{
	
	private static final Log log = LogFactory.getLog(LoginRequestImpl.class);

	@Override
	public void businessAuditing(ClientMessage msg) {
		log.info("登录业务开始！");
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClientMessage businessOpr(ClientMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
