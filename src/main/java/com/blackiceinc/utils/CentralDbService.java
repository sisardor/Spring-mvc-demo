package com.blackiceinc.utils;

import java.util.HashSet;
import java.util.Set;



public class CentralDbService {
	public CentralDbService() {
		super();
	}

	public Set<String> getActiveCustomerTokens() {
		Set<String> tokens = new HashSet<>(); 
		tokens.add("admin");
		tokens.add("con_id");
		return tokens;
	}

	public CustomerDataSourceEntity getActiveCustomerDataSource() {
		
		return null;
	}

}
