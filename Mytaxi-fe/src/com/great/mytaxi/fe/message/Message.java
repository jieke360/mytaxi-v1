/**
 * 
 */
package com.great.mytaxi.fe.message;

/**
 * @author xu.jianpu
 * 
 * 2012-8-9  下午05:13:11
 */
public interface Message {
	/** 解析消息包
	 */
	public	boolean	unpack(byte[] b);
	public	boolean	unpack(byte[] b, int off, int len); //abstract

	/** 构造消息包
	 */
	public	int	size();  //消息长度
	
	/**
	 * 消息打包
	 * @return
	 */
	public	byte[]	pack();  //abstract
	public	int	pack(byte[] b, int off);

	
	
	
	
	
	
	
	//后期扩展
//	
//
//	/** 解密核验CRC...
//	 */
//	public	boolean	checkCRC();
//	/** 计算设置CRC...
//	 */
//	public	boolean	calcCRC();
//	
//	//
//	/** 
//	 * 数据加密
//	 */
//	public  boolean encrypt(byte[] privateKey);
//	
//	//
//	/** 
//	 * 数据解密
//	 */
//	public  boolean decrypt(byte[] privateKey);  
}
