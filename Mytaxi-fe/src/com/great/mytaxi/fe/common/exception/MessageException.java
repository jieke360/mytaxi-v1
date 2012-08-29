/**
 * 
 */
package com.great.mytaxi.fe.common.exception;

/**
 * @author xu.jianpu
 * 
 * 2012-8-23  下午03:23:38
 */
public class MessageException extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1352055034469184757L;
	private String type ;
	
	public MessageException(int level  , String code , String msg ,Throwable e ){
		super(level,code,msg, e);
		this.type = "报文异常";
		
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
