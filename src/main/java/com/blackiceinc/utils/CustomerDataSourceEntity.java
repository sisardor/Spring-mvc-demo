package com.blackiceinc.utils;

import org.apache.commons.dbcp2.BasicDataSource;

public class CustomerDataSourceEntity extends BasicDataSource  {
	private String URL;
	private String dbName;
	private String username;
	private String password;
	private String driverClass;
	private String validationQuery;
	private boolean testOnBorrow;
	private String removeAbandoned;
	

	
	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public boolean getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public String getRemoveAbandoned() {
		return removeAbandoned;
	}

	public void setRemoveAbandoned(String removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}

	public int getMaxActive() {
		return 100;
	}

	public int getMaxIdle() {
		return 30;
	}

	public int getMinIdle() {
		return 25;
	}

	public int getMaxWait() {
		return 10000;
	}

	public long getValidationIntervalMillis() {
		return 300;
	}

	public int getRemoveAbandonedTimeout() {
		return 300;
	}

	public long getMinEvictableIdleTimeMillis() {
		return 300;
	}

	public int getTimeBetweenIdleTimeMillis() {
		return 300;
	}

}
