package com.blackiceinc.config;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@SuppressWarnings("rawtypes")
@Component  
public class MyEventListener implements ApplicationListener {


	@Override
	public void onApplicationEvent(ApplicationEvent event) {
//		System.out.println("\n\n\n\n\n APPLICATION STARTED ");
//		System.out.println(event.getTimestamp());
//		System.out.println("\n\n\n\n");
		
	}

}
