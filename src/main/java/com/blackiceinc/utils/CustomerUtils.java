package com.blackiceinc.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomerUtils {

	public static String getCustomer() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null) {
			return "shared";
		} else {
			return authentication.getName();
		}
		
	}

}
