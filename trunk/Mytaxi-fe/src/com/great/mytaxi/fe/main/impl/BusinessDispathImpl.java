/**
 * 
 */
package com.great.mytaxi.fe.main.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.great.mytaxi.fe.base.BeanContext;
import com.great.mytaxi.fe.business.BaseProcess;
import com.great.mytaxi.fe.main.BusinessDispath;
import com.great.mytaxi.fe.message.ClientMessage;

/**
 * @author xu.jianpu
 * 
 * 2012-8-9  下午05:33:02
 */
public class BusinessDispathImpl implements BusinessDispath{

	private static final Log log = LogFactory.getLog(BusinessDispathImpl.class);

	/**
	 * 执行业务分发处理
	 */
	public byte[] execute(byte[] ct) {
		log.debug("执行报文分发开始》》》》");
		ClientMessage   requestMsg = parseMessage(ct);
		if(requestMsg==null){
			log.info("解析报文内容异常！");
			return makeReturnMsg(ClientMessage.FAIL, "通信报文解析异常");
		}
		ClientMessage responseMsg = dispath(requestMsg);
		return responseMsg.pack();
	}

	/**
	 * 分发业务
	 * @param clientMsg		报文消息
	 * @return	处理结果
	 */
	private ClientMessage dispath(ClientMessage   clientMsg ){
		String tranType = "T_"+clientMsg.getTransType();
		BaseProcess iProcess =null;
		try {
			iProcess = (BaseProcess) BeanContext.getBean(tranType);
		} catch (Exception e) {
			log.error("获取请求类型为："+tranType +"的业务处理类异常",e);
		}
		if(iProcess==null){
			ClientMessage result = new ClientMessage();
			result.setTransType(clientMsg.getTransType());
			result.setResponseCode(ClientMessage.FAIL);
			result.setResponseDescription("没有对应的请求类型！" + tranType);
			return  result;		//FIXME 需要采用拦截器的方法统一处理返回结果，包装一个返回异常
		}
		return iProcess.process(clientMsg) ;
	}
	
	/**
	 * 组返回消息
	 * @param code
	 * @param desc
	 * @return
	 */
	public byte[] makeReturnMsg( String code ,String desc ){
		ClientMessage   clientMsg = new ClientMessage();
		clientMsg.setResponseCode(code);
		clientMsg.setResponseDescription(desc);
		return clientMsg.pack();
	}

	/**
	 *  解析报文内容
	 */
	public ClientMessage parseMessage(byte[] ct) {
		ClientMessage   clientMsg = new ClientMessage();
		try {
			if(!clientMsg.unpack(ct)){
				return null ; 
			}
		} catch (Exception e) {
			log.error("解析xml报文内容异常",e);
			return null ;
		}
		return clientMsg;
	}
}
