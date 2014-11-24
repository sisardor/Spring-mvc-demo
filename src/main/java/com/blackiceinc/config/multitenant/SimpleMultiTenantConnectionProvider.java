package com.blackiceinc.config.multitenant;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackiceinc.beans.AdminBean;
import com.blackiceinc.utils.SpringUtils;
import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;

public class SimpleMultiTenantConnectionProvider implements MultiTenantConnectionProvider {
	private static Logger log = LoggerFactory.getLogger(SimpleMultiTenantConnectionProvider.class );
	private ComboPooledDataSource defaultDataSource;

	public SimpleMultiTenantConnectionProvider() {
		super();
		defaultDataSource = new ComboPooledDataSource("shared");
		defaultDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/gcd_master");
		defaultDataSource.setUser("blackinc_admin");
		defaultDataSource.setPassword("Blackice@2014");
		defaultDataSource.setInitialPoolSize(5);
		defaultDataSource.setMaxConnectionAge(10000);
		try {
			defaultDataSource.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = -7732669278535141397L;

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
		return defaultDataSource.getConnection();
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		
		PooledDataSource pds = C3P0Registry.pooledDataSourceByName(tenantIdentifier);
//		DataSource tenantDatSource = SpringUtils.getBean(AdminBean.dataSource);
//		return tenantDatSource.getConnection();
		System.out.println("______ getConnection(" +tenantIdentifier+")");
		if (pds == null) {
			System.out.println("______ NULL");
		}
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
