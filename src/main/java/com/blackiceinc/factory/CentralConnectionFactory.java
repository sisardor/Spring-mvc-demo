package com.blackiceinc.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CentralConnectionFactory {
	private static DataSource dataSource;

	public static DataSource getPooledFactoryInstance() {		
		if (dataSource == null) {
			dataSource = setupDataSource("jdbc:mysql://gcd.blackiceinc.com:3306/blackinc_gcd");
			return dataSource;
		} else {
			return dataSource;
		}
	}

	public Connection getPooledConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public static DataSource setupDataSource(String connectURI) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername("blackinc_admin");
		ds.setPassword("Blackice@2014");
		ds.setUrl(connectURI);
		return ds;
	}

	public static void printDataSourceStats(DataSource ds) {
		BasicDataSource bds = (BasicDataSource) ds;
		System.out.println("NumActive: " + bds.getNumActive());
		System.out.println("NumIdle: " + bds.getNumIdle());
	}

	public static void shutdownDataSource(DataSource ds) throws SQLException {
		BasicDataSource bds = (BasicDataSource) ds;
		bds.close();
	}
}
