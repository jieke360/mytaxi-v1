package com.mytaxi.test;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import sun.net.www.http.HttpClient;

import com.mytaxi.test.util.URLConnectionClient;
import com.mytaxi.test.util.URLReqProps;

/**
 * 
 */

/**
 * @author xu.jianpu
 * 
 * 2012-8-14  下午12:30:22
 */
public class TestSuite {
	
	private static final Log log = LogFactory.getLog(TestSuite.class);
	
	private URLReqProps props  ;

	@Before
	public void ready(){
		props = new URLReqProps();
		props.setAddress("http://192.168.1.42:8080/Mytaxi-fe/servlet/RequestServlet");
		props.setConnectTimeout(10000);
		props.setDoOutput(true);
		props.setReadTimeout(20000);
		props.setRequestMethod("POST");
		props.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
		props.setRequestProperty("Accept", "text/xml; charset=UTF-8");
	}
	
	@Test
	public void loginRequest(){
		URLConnectionClient httpClient  =null ;
		try {
			httpClient = new URLConnectionClient(props);
			httpClient.writeMessage(getXmlStr());
			String msg = httpClient.readMessage();
			log.info(msg);
		} catch (IOException e) {
			log.info("", e);
		}finally{
			
		}
	}
	
	public String getXmlStr(){
		String str = "<mytaxi><function>"+
						"<tradeType>LoginRequest</tradeType>"+
						"</function>"+
			"<body>"+
				"<SDId>5180000052D01213</SDId>"+
				"<password>123456</password>"+
			"</body>"+
		"</mytaxi>";
		
		return str ;
	}
}
