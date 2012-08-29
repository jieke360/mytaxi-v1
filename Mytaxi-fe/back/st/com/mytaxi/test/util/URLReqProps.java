package com.mytaxi.test.util;

/**
 * Accept 
 *   "text/html"
 * Content-Type
 *   序列化对象："application/x-java-serialized-object"
 *   表单："application/x-www-form-urlencoded"
 *   xml内容："text/xml; charset=gb2312"
 *   html内容："text/html; charset=gb2312"
 *   文本内容："text/plain"
 * 
 * GET、POST 只要开启输出流并写出数据就为POST，
 *           只要设定请求方法为POST，无论设置何种方式，都为POST
 */
import java.util.HashMap;
import java.util.Map;

/**
 * @author @DaiChao
 * @date 2011-7-28
 */
public class URLReqProps {

	private String addr;

	private boolean useCaches;

	private String requestMethod;

	private boolean doInput = true;

	private boolean doOutput;

	private int connectTimeout;

	private int readTimeout;

	private Map<String, String> properties = new HashMap<String, String>();

	/**
	 * 是否启用缓存
	 */
	public void setUseCaches(boolean useCaches) {
		this.useCaches = useCaches;
	}

	/**
	 * 设置请求方法
	 * 
	 * @param requestMethod
	 */
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * 设置输入流
	 * 
	 * @param doInput
	 */
	public void setDoInput(boolean doInput) {
		this.doInput = doInput;
	}

	/**
	 * 设置请求地址
	 * 
	 * @param addr
	 */
	public void setAddress(String addr) {
		this.addr = addr;
	}

	/**
	 * 设置输出流
	 * 
	 * @param doInput
	 */
	public void setDoOutput(boolean doOutput) {
		this.doOutput = doOutput;
	}

	/**
	 * 设置连接超时
	 * 
	 * @param ms
	 */
	public void setConnectTimeout(int ms) {
		this.connectTimeout = ms;
	}

	/**
	 * 设置读取超时
	 * 
	 * @param ms
	 */
	public void setReadTimeout(int ms) {
		this.readTimeout = ms;
	}

	/**
	 * 设置请求属性
	 * 
	 * @param name
	 * @param value
	 */
	public void setRequestProperty(String name, String value) {
		properties.put(name, value);
	}

	// ===========================================

	public String getAddress() {
		return addr;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public boolean isDoInput() {
		return doInput;
	}

	public boolean isDoOutput() {
		return doOutput;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public boolean isUseCaches() {
		return useCaches;
	}

	public Map<String, String> getRequestProperties() {
		return properties;
	}

}
