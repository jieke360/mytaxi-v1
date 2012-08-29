/**
 * 
 */
package com.great.mytaxi.fe.common.enumeration;


/**
 * 
 * 	请求业务类型枚举类
 * 
 * @author xu.jianpu
 * 
 * 2012-8-10  上午09:14:27
 */
public enum ETranType {

	Login("LoginRequst","LoginResponse","com.great.mytaxi.fe");
	
	private String type;
	private String resp;
	private String clazz;
	
	/**
	 * @param type	请求名称
	 * @param resp	响应名称
	 * @param clazz	处理类
	 */
	private ETranType(String type , String resp , String clazz){
		this.type = type;
		this.resp = resp;
		this.clazz = clazz;
	}

	/**
	 * 通过交易类型获取枚举
	 * @param type	交易类型
	 * @return
	 * @throws NoSuchFieldException  如果没有交易类型对应的枚举，会抛出异常
	 */
	public static ETranType getType(String type) throws Exception{
		ETranType[] value = ETranType.values();
		for(ETranType e : value ){
			if(e.getType().equalsIgnoreCase(type)){
				return e ;
			}
		}
		throw new NoSuchFieldException();
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResp() {
		return resp;
	}

	public void setResp(String resp) {
		this.resp = resp;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	public static void main(String[] args) {
		ETranType[] values = ETranType.values();
		for (ETranType value : values) {
			System.out.println(value.getType());
		}
//		System.out.println(ETranType.BindsSearch.getResp());
	}
}
