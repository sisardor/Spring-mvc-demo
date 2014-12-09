package com.blackiceinc.config.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import com.blackiceinc.utils.CustomerUtils;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		return CustomerUtils.getCustomer();
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}

}
