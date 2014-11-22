package com.blackiceinc.utils;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class SpringUtils{

	@SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
		AbstractApplicationContext  context = new ClassPathXmlApplicationContext("beans.xml");
		context.registerShutdownHook();
		
        return (T) context.getBean(beanName);
    }
	

}
