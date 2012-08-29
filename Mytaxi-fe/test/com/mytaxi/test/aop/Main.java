/**
 * 
 */
package com.mytaxi.test.aop;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.great.mytaxi.fe.business.BaseProcess;
import com.great.mytaxi.fe.message.ClientMessage;

/**
 * @author xu.jianpu
 * 
 * 2012-8-23  下午02:05:42
 */
public class Main {

	public static void main(String[] args) throws Exception {
		XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));  	//装载配置
		IAopService iAop = (IAopService) factory.getBean("aopDemo");
		iAop.withAop();
		iAop.withoutAop();
		
//		IRequestProcess iProcess = null;
//		try {
//			iProcess = (IRequestProcess) factory.getBean("T_LoginRequest");
//			iProcess.process(new ClientMessage());
//		} catch (Exception e) {
//	
//		}
	}
}
