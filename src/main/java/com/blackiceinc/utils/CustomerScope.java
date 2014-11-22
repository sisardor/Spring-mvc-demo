package com.blackiceinc.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.blackiceinc.exceptions.CustomerNotRegisteredException;
import com.blackiceinc.exceptions.RuntimeFaultException;


public class CustomerScope implements Scope{
	private final static Map<String, Map<String, Object>> customerBeanMap = new ConcurrentHashMap<>();
	private final static Set<String> bootstrappedCustomers = new HashSet<String>();
	private final static Set<String> failedBootstrappedCustomers = new HashSet<String>();
	
	@Override
	public Object get(String beanName, ObjectFactory<?> objectFactory) {
		System.out.println("_________ get("+beanName+")");
		String customer = getConversationId();
		return getCustomerBean(beanName, customer, objectFactory);
	}
	
	/**
	 * Method for retrieving and/or creating customer beans.
	 * @throws RuntimeFaultException 
	 * @throws CustomerNotRegisteredException 
	 */
	private static Object getCustomerBean(String beanName, String customerName, ObjectFactory<?> objectFactory) {
		assert beanName != null;
		assert customerName != null;
		assert objectFactory != null;
		
		Map<String, Object> beanMap = getBeanMap(customerName);
		
		synchronized (beanMap) {
			
			if(bootstrappedCustomers.contains(customerName) == false){ //bootstrappedCustomers --> Set<String>
				System.out.println("_________ Before contains " + bootstrappedCustomers.contains(customerName));
				bootstrappedCustomers.add(customerName);
				bootstrapCustomer(customerName);
				System.out.println("_________ CUSTOMER HASN'T BEEN INITALIZED");
				System.out.println("_________ After contains " + bootstrappedCustomers.contains(customerName));
				
			} else {
				System.out.println("_________ bootstrappedCustomers contains true");
				if(failedBootstrappedCustomers.contains(customerName)){
					//throw new FailedBootstrapCusomerAccessException("");
				}
			}
			
			Object bean = beanMap.get(beanName);
			if(bean == null){
				bean = objectFactory.getObject();
				beanMap.put(beanName, bean);
			}
			return bean;
		}
		
		
	}
	
	private static synchronized Map<String, Object> getBeanMap(String customerName) {
		System.out.println("_________ getBeanMap(" +customerName+")" );
		Map<String, Object> beanMap = customerBeanMap.get(customerName);
		if(beanMap == null) { // if not contains create new here
			beanMap = new ConcurrentHashMap<String, Object>();
			customerBeanMap.put(customerName, beanMap);
		}
		return beanMap;
	}
	
	private static void bootstrapCustomer(String customerName) {
		System.out.println("_________ bootstrapCustomer("+customerName+")");
		
		CentralDbService centralDbService = new CentralDbService();//.getBean(CentralDbService.class);
	
		if(centralDbService.getActiveCustomerTokens().contains(customerName) == false) {
			System.out.println("_________ if contains("+customerName +") = "+centralDbService.getActiveCustomerTokens().contains(customerName)+"");
			failedBootstrappedCustomers.add(customerName);
			//throw new CustomerNotRegisteredException("Trying to bootstarp but customer wasn't found in the central Database"); 
		}
		
		try {
			CustomerDataSourceEntity customerDbProperties = centralDbService.getActiveCustomerDataSource();
			initializePool(customerDbProperties);
		} catch (Exception e) {
			failedBootstrappedCustomers.add(customerName);
			//throw new RuntimeFaultException("Failed to boostrap customer: " + customerName, e);
		}
	}
	


	private static synchronized void initializePool(CustomerDataSourceEntity customerDbProperties) {
		ConfigurableDatasource ds = null;// SpringUtils.getBean(AdminBean.dataSource);
		
		ds.setUrl(customerDbProperties.getURL() + customerDbProperties.getDbName());
		ds.setUsername(customerDbProperties.getUsername());
		ds.setPassword(customerDbProperties.getPassword());
		ds.setMaxActive(customerDbProperties.getMaxActive());
		ds.setInitialSize(1);
		ds.setMaxIdle(customerDbProperties.getMaxIdle());
		ds.setMinIdle(customerDbProperties.getMinIdle());
		ds.setMaxWait(customerDbProperties.getMaxWait());
		
		ds.setDriverClassName(customerDbProperties.getDriverClass());
		ds.setDefaultAutoCommit(false);
		ds.setValidationQuery(customerDbProperties.getValidationQuery());
		ds.setTestOnBorrow(customerDbProperties.getTestOnBorrow());
		ds.setValidationIntervalMillis(customerDbProperties.getValidationIntervalMillis());
		
		ds.setRemoveAbandoned(customerDbProperties.getRemoveAbandoned());
		ds.setRemoveAbandonedTimeout(customerDbProperties.getRemoveAbandonedTimeout());
		
		ds.setMinEvictableIdleTimeMillis(customerDbProperties.getMinEvictableIdleTimeMillis());
		ds.setTimeBetweenIdleTimeMillis(customerDbProperties.getTimeBetweenIdleTimeMillis());
		
	}
	
	@Override
	public String getConversationId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//return CustomerUrils.getCustomer();
		return authentication.getName();
	}

	@Override
	public void registerDestructionCallback(String arg0, Runnable arg1) {
		System.out.println("-------> registerDestructionCallback");
	}

	@Override
	public Object remove(String arg0) {
		System.out.println("-------> remove");
		return null;
	}

	@Override
	public Object resolveContextualObject(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
