package com.blackiceinc.config.multitenant;

import java.beans.PropertyVetoException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import com.blackiceinc.beans.AdminBean;
import com.blackiceinc.exceptions.CustomerNotRegisteredException;
import com.blackiceinc.exceptions.FailedBootstrapCusomerAccessException;
import com.blackiceinc.exceptions.RuntimeFaultException;
import com.blackiceinc.utils.ConfigurableDatasourceXX;
import com.blackiceinc.utils.CustomerDataSourceEntity;
import com.blackiceinc.utils.CustomerUtils;
import com.blackiceinc.utils.SpringUtils;
import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;


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
			if(bootstrappedCustomers.contains(customerName)){
				System.out.println("_________ bootstrappedCustomers containts \"" + customerName + "\"");
			}
			
			if(bootstrappedCustomers.contains(customerName) == false){ //bootstrappedCustomers --> Set<String>
				bootstrappedCustomers.add(customerName);
				bootstrapCustomer(customerName); // initialize new customer
				System.out.println("_________ CUSTOMER HASN'T BEEN INITALIZED");

				
			} else {
				if(failedBootstrappedCustomers.contains(customerName)){
					System.out.println("!!!!!!!!!!! Failed Bootstrap Cusomer Access Exception");
					throw new FailedBootstrapCusomerAccessException("Failed Bootstrap Cusomer Access Exception");
				} else {
					System.out.println("_________ failedBootstrappedCustomers doesn't containts \"" + customerName + "\"");
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
		System.out.println("_________ bootstrap customer \""+customerName+"\"");
		
		CentralDbService centralDbService = new CentralDbService();//SpringUtils.getBean(CentralDbService.class);
	
		System.out.print("_________ checking token for customer \""+customerName+"\" .... ");
		if(centralDbService.getActiveCustomerTokens().contains(customerName) == false) {
			System.out.print("no token\n");
			System.out.println("!!!!!!!!!!! Customer Not Registered Exception");
			failedBootstrappedCustomers.add(customerName);
			throw new CustomerNotRegisteredException("Trying to bootstarp but customer wasn't found in the central Database"); 
		} else {
			System.out.print("token found \n");
		}
		
		try {
			CustomerDataSourceEntity customerDbProperties = centralDbService.getActiveCustomerDataSource(customerName);
			System.out.println("_________ initializing Pool customer \""+customerName+"\"");
			initializePool(customerDbProperties);
		} catch (Exception e) {
			failedBootstrappedCustomers.add(customerName);
			throw new RuntimeFaultException("Failed to boostrap customer: " + customerName, e);
		}
	}
	


	private static synchronized void initializePool(CustomerDataSourceEntity customerDbProperties) {
		
		PooledDataSource pds = C3P0Registry.pooledDataSourceByName(customerDbProperties.getDbName());

		if (pds == null) {
			ComboPooledDataSource cpds = new ComboPooledDataSource(customerDbProperties.getDbName());
			cpds.setJdbcUrl(customerDbProperties.getURL());
			cpds.setUser(customerDbProperties.getUsername());
			cpds.setPassword(customerDbProperties.getPassword());
			cpds.setInitialPoolSize(1);
			cpds.setMaxPoolSize(1);
			cpds.setMaxConnectionAge(10000);
			
			try {
				cpds.setDriverClass("com.mysql.jdbc.Driver");
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		} 
	}
	
	@Override
	public String getConversationId() {
		return CustomerUtils.getCustomer();
	}

	@Override
	public void registerDestructionCallback(String arg0, Runnable arg1) {
	}

	@Override
	public Object remove(String name) {
		Map<String, Object> beanMap = customerBeanMap.get(getConversationId());
		return beanMap == null ? null : beanMap.remove(name);
	}
	
	@SuppressWarnings("null")
	public static void removeCustomer(String customerName) {
		Map<String, Object> beanMap = getBeanMap(customerName);
		synchronized (beanMap) {
			failedBootstrappedCustomers.remove(customerName);
			customerBeanMap.remove(customerName);
			bootstrappedCustomers.remove(customerName);
			uninitializePool(customerName);
			
			CentralDbService centralDbService = null;//SpringUtils.getBean(CentralDbService.class);
			centralDbService.clearTenantCache(customerName);
		}
		
	}

	private static void uninitializePool(String customerName) {
		if(bootstrappedCustomers.contains(customerName) == false) {
			return;
		}

		
		ConfigurableDatasourceXX ds = SpringUtils.getBean(AdminBean.dataSource);
		ds.close();
	}

	@Override
	public Object resolveContextualObject(String arg0) {
		return null;
	}

	
}
