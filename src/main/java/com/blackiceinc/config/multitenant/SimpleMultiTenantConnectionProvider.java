package com.blackiceinc.config.multitenant;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		//UserInfoBean  user = SpringUtils.getBean("userInfoBean");
		defaultDataSource = SpringUtils.getBean("sharedDataSource");
		log.info("****** SETTING UP POOL");

		if (C3P0Registry.pooledDataSourceByName("shared") == null) { // if not initialized, create DataSource
				
				defaultDataSource = new ComboPooledDataSource("shared");
				defaultDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/gcd_master");
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
		
			
	}



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
		// return CentralConnectionFacotry.getPooledFactoryInstance().getPooledConeection
		System.out.println("_________  getAnyConnection()");
		PooledDataSource pds = C3P0Registry.pooledDataSourceByName("shared"); // TO-DO: check for pds == null and handle
		return pds.getConnection();
	}

	
	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		System.out.println("_________ getConnection(" +tenantIdentifier +")");
		UserInfoBean  user = SpringUtils.getBean("userInfoBean");
		PooledDataSource pds = C3P0Registry.pooledDataSourceByName(tenantIdentifier);

		
		Set set = C3P0Registry.getPooledDataSources();
		int numPooledDS = C3P0Registry.getNumPooledDataSources();

		System.out.println("_________ C3P0Registry info:");
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			ComboPooledDataSource tmp = (ComboPooledDataSource)iterator.next();
			System.out.println("_________   [" + tmp.getDataSourceName() + " " + tmp.getJdbcUrl() +"]");
		}
	    System.out.println("_________   Num Pooled Data Sources " + numPooledDS );
		return pds.getConnection();
		
//		DataSource tenantDatSource = SpringUtils.getBean(AdminBean.dataSource);
//		return tenantDatSource.getConnection();
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
