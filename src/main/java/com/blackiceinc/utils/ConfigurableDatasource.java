package com.blackiceinc.utils;

import org.apache.commons.dbcp2.BasicDataSource;



public class ConfigurableDatasource extends BasicDataSource {
	private String url;
	private String username;
	private String password;
	private int maxActive;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
		
	}

	public void setInitialSize(int i) {
		
		
	}

	public void setMaxIdle(int maxIdle) {
		
		
	}

	public void setMinIdle(int minIdle) {
		
		
	}

	public void setMaxWait(int maxWait) {
		
		
	}

	public void setDriverClassName(String driverClassName) {
		
		
	}

	public void setDefaultAutoCommit(boolean b) {
		
	}

	public void setValidationQuery(String validationQuery) {
		
	}

	public void setTestOnBorrow(String testOnBorrow) {

		
	}

	public void setValidationIntervalMillis(long intervalMillis) {
		
	}

	public void setRemoveAbandoned(String removeAbandoned) {
		
		
	}

	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
		
		
	}

	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		// TODO Auto-generated method stub
		
	}

	public void setTimeBetweenIdleTimeMillis(int timeBetweenIdleTimeMillis) {
		// TODO Auto-generated method stub
		
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

}
