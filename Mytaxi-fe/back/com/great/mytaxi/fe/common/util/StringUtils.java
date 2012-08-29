/**
 * 
 */
package com.great.mytaxi.fe.common.util;

/**
 * @author xu.jianpu
 * 
 * 2012-8-8  下午01:38:52
 */
public class StringUtils {

	public static boolean isEmpty(String s) {
		return (s == null) || (s.length() == 0);
	}
	
	
	/**
	 * 
	 */
	public static boolean isSame(byte[] s, int soff, byte[] d, int doff, int len) {
		for (int i = 0; i < len; i++) {
			if (s[soff++] != d[doff++])
				return false;
		}

		return true;
	}
	
	
    /**
     * 将整形数组转换为以“,”间隔的字符串

     * @param vals
     * @return
     */
    public static String combIntString(Integer[] vals)
    {
        StringBuffer sb = new StringBuffer();
        for(Integer val:vals)
        {             
            sb.append(","+val);            
        }
        return sb.toString().replaceFirst(",", "");
    }
}
