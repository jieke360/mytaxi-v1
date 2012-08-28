package com.great.mytaxi.common;

public class TaxiException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public static final int MOBILE_ISUSED = 1000;		//手机号码已存在
	
	
	private int errorCode;
	
	public TaxiException(int errorCode){
		super();
		this.errorCode=errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
