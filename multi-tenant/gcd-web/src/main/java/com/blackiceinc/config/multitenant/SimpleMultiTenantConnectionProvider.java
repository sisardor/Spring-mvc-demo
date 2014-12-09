package com.blackiceinc.config.multitenant;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blackiceinc.beans.UserInfoBean;
import com.blackiceinc.utils.SpringUtils;
import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;

public class SimpleMultiTenantConnectionProvider implements MultiTenantConnectionProvider {
	private static final long serialVersionUID = -7732669278535141397L;
	private static Logger log = LoggerFactory.getLogger(SimpleMultiTenantConnectionProvider.class );

	private ComboPooledDataSource defaultDataSource;

	public SimpleMultiTenantConnectionProvider() {
		super();
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		defaultDataSource = (ComboPooledDataSource) context.getBean("sharedDataSource");
		log.info("****** SETTING UP POOL");
		
		// if not initialized, create DataSource
		if (C3P0Registry.pooledDataSourceByName("shared") == null) { 
				defaultDataSource = new ComboPooledDataSource("shared");
				defaultDataSource.setJdbcUrl("jdbc:mysql://192.241.215.114:3306/gcd_master");
				defaultDataSource.setUser("blackinc_admin");
				defaultDataSource.setPassword("Blackice@2014");
				defaultDataSource.setInitialPoolSize(1);
				defaultDataSource.setMaxPoolSize(1);
				defaultDataSource.setMaxConnectionAge(10000);
			try {	
				defaultDataSource.setDriverClass("com.mysql.jdbc.Driver");
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
		((ClassPathXmlApplicationContext) context).close();
			
	}



	@SuppressWarnings("rawtypes")
	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return ConnectionProvider.class.equals(unwrapType) || MultiTenantConnectionProvider.class.equals(unwrapType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		if ( isUnwrappableAs( unwrapType ) ) {
			return (T) this;
		}
		else {
			throw new UnknownUnwrapTypeException( unwrapType );
		}
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		System.out.println("_________  getAnyConnection()");
		//PooledDataSource pds = C3P0Registry.pooledDataSourceByName("shared"); 
		// TO-DO: check for pds == null and handle
		return C3P0Registry.pooledDataSourceByName("shared").getConnection();
	}

	
	@SuppressWarnings("unused")
	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {

		System.out.println("_________ getConnection(" +tenantIdentifier +")");
		UserInfoBean  user = SpringUtils.getBean("userInfoBean");
		PooledDataSource pds = C3P0Registry.pooledDataSourceByName(tenantIdentifier);

		return pds.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		if(!connection.isClosed()) {
			connection.close();
		}
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		if(!connection.isClosed()) {
			connection.close();
		}
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return true;
	}
	

}
