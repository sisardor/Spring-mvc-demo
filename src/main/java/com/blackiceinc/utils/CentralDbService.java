package com.blackiceinc.utils;

import java.util.HashSet;
import java.util.Set;

public class CentralDbService  {
	public CentralDbService() {
		super();
	}

	public Set<String> getActiveCustomerTokens() {
		Set<String> tokens = new HashSet<>();
		tokens.add("adminxx");
		tokens.add("con_id");
		return tokens;
	}

	public CustomerDataSourceEntity getActiveCustomerDataSource() {
		CustomerDataSourceEntity customerDbProperties =	new CustomerDataSourceEntity();
		customerDbProperties.setURL("jdbc:mysql://localhost:3306/test");
		customerDbProperties.setDbName("com.mysql.jdbc.Driver");
		customerDbProperties.setPassword("root");
		customerDbProperties.setUsername("root");
		customerDbProperties.setRemoveAbandoned("true");
		customerDbProperties.setTestOnBorrow(false);
		customerDbProperties.setValidationQuery("select 1");
		return customerDbProperties;
	}

	public void init() {
		System.out.println("Bean is going through init.");
	}

	public void destroy() {
		System.out.println("Bean will destroy now.");
	}

}
