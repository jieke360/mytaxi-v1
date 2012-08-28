package com.great.mytaxi.common.util;
import java.net.InetAddress;
import java.util.Random;

/**
 * 唯一主键生成办法。从Hibernate中提取出来。
 * @version : V1.0 
 * @author : lzb
 * @date : 2012-08-11 
 */
public class UUID {

	private static final int IP;

	private static final String DEFAULT_ENTITY = "default_entity";
	
	private static int IptoInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	static {
		int ipadd;
		try {
			ipadd = IptoInt(InetAddress.getLocalHost().getAddress());
			System.out.println(ipadd);
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	private static short counter = (short) 0;
	
	private static Random random = new Random();

	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	private UUID() {
	}

	/**
	 * Unique across JVMs on this machine (unless they load this class in the
	 * same quater second - very unlikely)
	 */
	protected int getJVM() {
		return JVM;
	}

	/**
	 * Unique in a millisecond for this JVM instance (unless there are >
	 * Short.MAX_VALUE instances created in a millisecond)
	 */
	protected short getCount() {
		synchronized (UUID.class) {
			if (counter < 0)
				counter = 0;
			return counter++;
		}
	}

	/**
	 * Unique in a local network
	 */
	protected int getIP() {
		return IP;
	}

	/**
	 * Unique down to millisecond
	 */
	protected short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	private final static String sep = "";

	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	public String generate() {
		return new StringBuffer(36).append(format(getIP())).append(sep).append(
				format(getJVM())).append(sep).append(format(getHiTime()))
				.append(sep).append(format(getLoTime())).append(sep).append(
						format(getCount())).toString();
	}

	protected int getRandom(String entity){
		synchronized (UUID.class) {
			if( entity == null){
				entity = DEFAULT_ENTITY;
			}
			long seed = System.currentTimeMillis()<<entity.hashCode();
			random.setSeed(seed);
			return random.nextInt();
		}
	}
	
	
	public static String getUUID(){
		return instance.generate();
	}
	
	private static UUID instance = new UUID();
	
	
	public static void main(String[] args){
		System.out.println(UUID.getUUID());
	}

}

