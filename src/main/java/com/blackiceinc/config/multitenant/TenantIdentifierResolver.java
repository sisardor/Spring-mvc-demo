package com.blackiceinc.config.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class TenantIdentifierResolver implements
		CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		// TODO Auto-generated method stub
		return false;
	}

}
