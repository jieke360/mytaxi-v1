package com.great.mytaxi.common.cond;

public class KeyCond {

	/**
	 * 普通用户
	 */
	public static final String USER_FALG="1";
	/**
	 * 司机用户
	 */
	public static final String USER_CHAUFFEUR_FALG="2";
	/**
	 * 用户所在位置
	 */
	public static final String USER_LOCALE="3";
	/**
	 * 呼叫的士
	 */
	public static final String USER_CALL_CAR="4";			
	/**
	 * 预约的士
	 */
	public static final String USER_PREENGAGE="5";
	
	private String name;
	private String flagStr;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFlagStr() {
		return flagStr;
	}
	public void setFlagStr(String flagStr) {
		this.flagStr = flagStr;
	}
		
	
}
