package com.blackiceinc.config.multitenant;

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

public class SimpleMultiTenantConnectionProvider implements MultiTenantConnectionProvider {
	private static Logger log = LoggerFactory.getLogger(SimpleMultiTenantConnectionProvider.class );

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
		System.out.println("______ getAnyConnection()");
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername("blackinc_admin");
		ds.setPassword("Blackice@2014");
		ds.setUrl("jdbc:mysql://localhost:3306/master");
		return ds.getConnection();
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
//		DataSource tenantDatSource = SpringUtils.getBean(AdminBean.dataSource);
//		return tenantDatSource.getConnection();
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername("blackinc_admin");
		ds.setPassword("Blackice@2014");
		ds.setUrl("jdbc:mysql://localhost:3306/master");
		return ds.getConnection();
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
