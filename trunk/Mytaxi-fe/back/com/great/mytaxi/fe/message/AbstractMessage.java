 /**
 * 
 */
package com.great.mytaxi.fe.message;
import java.sql.Connection;
/**
 * 
 * 	通信报文积累
 * 
 * @author xu.jianpu
 * 
 * 2012-8-8  下午12:44:54
 */
public abstract class	AbstractMessage implements Message {
	public	AbstractMessage() {
		
	}
	public	abstract byte[] 	pack();
	public	abstract boolean	unpack(byte[] b, int off, int len);
	
	/**
	 * added by chenshan 2010-03-06 解包（解密）
	 * @param b
	 * @param off
	 * @param len
	 * @param conn
	 * @param phoneNo
	 * @return
	 */
	public	boolean	unpack(byte[] b, int off, int len,Connection conn,String phoneNo){
		return true;
	}

	public	int	pack(byte[] b, int off) {
		byte[]	b0 = pack();
		if (b0==null)
			return	-1;
		System.arraycopy(b0, 0, b, off, b0.length);
		return	b0.length;
	}

	public	boolean	unpack(byte[] b) {
		return	unpack(b, 0, b.length);
	}
	
	public	int	size() {
		byte[]	b0 = pack();
		if (b0==null)
			return	-1;
		return	b0.length;
	}

	public	boolean	checkCRC() {
		return	true;
	}
	public	boolean	calcCRC() {
		return	true;
	}
	
	public	boolean	encrypt(byte[] privateKey) {
		return	true;
	}
	public	boolean	decrypt(byte[] privateKey) {
		return	true;
	}
	

}
