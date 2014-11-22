package com.blackiceinc.config.multitenant;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;

import com.blackiceinc.beans.AdminBean;
import com.blackiceinc.utils.SpringUtils;

public class SimpleMultiTenantConnectionProvider implements MultiTenantConnectionProvider {
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
		
		return null;
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		DataSource tenantDatSource = SpringUtils.getBean(AdminBean.dataSource);
		return tenantDatSource.getConnection();
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
