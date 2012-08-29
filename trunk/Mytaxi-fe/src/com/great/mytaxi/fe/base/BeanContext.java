/**
 * 
 */
package com.great.mytaxi.fe.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author xu.jianpu
 * 
 * 2012-8-10  下午03:08:46
 */
public class BeanContext implements ApplicationContextAware{

    private static ApplicationContext applicationContext;
    
    public static Object getBean(String beanId){
        return applicationContext.getBean(beanId);
    }
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        BeanContext.applicationContext = applicationContext;
    }

}
