/**
 * 
 */
package com.great.mytaxi.fe.common.exception;

/**
 * 
 * 	异常基类
 * 
 * @author xu.jianpu
 * 
 * 2012-8-23  下午03:27:22
 */
public class BusinessException extends Exception{
	
	/**
	 * 异常级别     业务异常    2
	 */
	public static final int LEVEL_BIZ = 2 ;			//TODO 用枚举代替这些常量
	
	/**
	 * 异常级别     参数异常    1
	 */
	public static final int LEVEL_PARAMS = 1 ;
	
	/**
	 * 异常级别     系统异常    3
	 */
	public static final int LEVEL_SYS = 3 ;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6366505058749695239L;

	private String code ;
	
	private int level ;
	
	/**
	 * 
	 * @param level		异常级别
	 * @param code		异常代码
	 * @param msg		异常消息
	 * @param e			异常信息
	 */
	public BusinessException(int level  , String code , String msg ,Throwable e ){
		super(msg,e);
		this.level = level;
		this.code = code;
		
	}
	
	public BusinessException( String msg ,Throwable e ){
		super(msg,e);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
