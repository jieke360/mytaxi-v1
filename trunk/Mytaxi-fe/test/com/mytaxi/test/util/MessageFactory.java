/**
 * 
 */
package com.mytaxi.test.util;

/**
 * @author xu.jianpu
 * 
 * 2012-8-21  下午12:33:03
 */
public class MessageFactory {
	
	public static  String getReq(String type,String function){
		StringBuilder strBui = new StringBuilder();
		strBui.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		strBui.append("<simcoupon>");
		strBui.append("<head>");
		strBui.append("<tradeType>");
		strBui.append(type);
		strBui.append("</tradeType>");
		strBui.append("</head>");
		strBui.append("<body>");
		strBui.append("<function>");
		strBui.append(function);
		strBui.append("</function>");
		strBui.append("</body>");
		strBui.append("</simcoupon>");
		return strBui.toString();
	}
}
