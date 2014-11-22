package com.blackiceinc.config.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		return null;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}

}
