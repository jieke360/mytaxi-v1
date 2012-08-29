/**
 * 
 */
package com.great.mytaxi.fe.business;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.great.mytaxi.fe.common.config.SysInfo;
import com.great.mytaxi.fe.common.exception.BusinessException;
import com.great.mytaxi.fe.message.ClientMessage;

/**
 * 	业务处理接口
 * 
 * @author xu.jianpu
 * 
 * 2012-8-10  下午03:12:47
 */
public abstract class BaseProcess {
	
	private static final Log log = LogFactory.getLog(BaseProcess.class);

	/**
	 * 业务处理调用
	 * @param clientMessage
	 * @return	返回处理结果，一定不为空。
	 */
	public final ClientMessage process(ClientMessage clientMessage )
	{
		ClientMessage returnMsg = new ClientMessage();
		try {
			businessAuditing(clientMessage);
			returnMsg = businessOpr(clientMessage);
		} catch (Exception e) {
			return  doInCatchDBException(returnMsg,e);
		}
		return returnMsg;
	}
	
	/**
	 * 异常处理
	 */
	private ClientMessage  doInCatchDBException(ClientMessage returnMsg,Exception e) {
		if(e instanceof BusinessException){
			return doInCatchDBException(returnMsg,(BusinessException)e);
		}
		log.error("业务处理异常", e);
		returnMsg.setResponseCode(ClientMessage.FAIL);
		returnMsg.setResponseDescription("系统无法响应！");
		return returnMsg;
	}

	
	/**
	 *  业务异常处理
	 */
	private ClientMessage  doInCatchDBException(ClientMessage returnMsg,BusinessException e) {
		String errCode = e.getCode();
		returnMsg.setResponseCode(ClientMessage.FAIL);
		String errMsg = SysInfo.getValueByCode(errCode,"系统异常");
		log.info("业务处理异常，返回异常信息为："+errMsg);
		returnMsg.setResponseDescription(errMsg);
		return returnMsg;
	}
	
	/**
	 * 交易业务检查
	 */
	public abstract void businessAuditing(ClientMessage msg);
	
	/**
	 * 核心业务处理
	 * @return
	 */
	public abstract ClientMessage businessOpr(ClientMessage msg);
}
