package com.blackiceinc.utils;

import java.util.HashSet;
import java.util.Set;

public class CentralDbService  {
	public CentralDbService() {
		super();
	}

	public Set<String> getActiveCustomerTokens() {
		Set<String> tokens = new HashSet<>();
		tokens.add("admin");
		tokens.add("sardor");
		tokens.add("anonymousUser");
		tokens.add("shared");
		
		return tokens;
	}

	public CustomerDataSourceEntity getActiveCustomerDataSource(String customerName) {
		System.out.println("_________  getActiveCustomerDataSource for \"" + customerName + "\"");
		CustomerDataSourceEntity customerDbProperties =	new CustomerDataSourceEntity();
		if(customerName.equals("sardor")) {
			customerDbProperties.setUsername("tenant_sardor");
			customerDbProperties.setDbName(customerName);
		} else if(customerName.equals("admin")) {
			customerDbProperties.setUsername("tenant_admin");
			customerDbProperties.setDbName(customerName);
		} else {
			customerDbProperties.setUsername("blackinc_admin");
			customerDbProperties.setDbName(customerName);
		}
		customerDbProperties.setURL("jdbc:mysql://localhost:3306/gcd_master");
		customerDbProperties.setPassword("Blackice@2014");
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

	public void clearTenantCache(String customerName) {
		// TODO Auto-generated method stub
		
	}

}
