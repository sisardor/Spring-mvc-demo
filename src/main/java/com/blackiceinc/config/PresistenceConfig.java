package com.blackiceinc.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.blackiceinc.config.multitenant.SimpleMultiTenantConnectionProvider;
import com.blackiceinc.config.multitenant.TenantIdentifierResolver;
import com.google.common.base.Preconditions;
@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:persistence-mysql.properties" })
@ComponentScan({ "com.blackiceinc.account" })
@EnableJpaRepositories(basePackages = "com.blackiceinc.account.dao")

//@Configuration
//@EnableTransactionManagement
//@ComponentScan({ "com.blackiceinc.account" })
//@ImportResource({ "classpath:persistence.xml" })

public class PresistenceConfig {
    public PresistenceConfig() {
        super();
    }
    
	@Autowired
	private Environment env;
	
	@Bean
	public Map<String, Object> jpaProperties() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		//props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		
		
		props.put("hibernate.tenant_identifier_resolver", TenantIdentifierResolver.class.getName());
		props.put("hibernate.multi_tenant_connection_provider", SimpleMultiTenantConnectionProvider.class.getName());
		props.put("hibernate.multiTenancy", "DATABASE");
		
		props.put("hibernate.cache.use_second_level_cache", "true");
		props.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		props.put("hibernate.cache.use_query_cache", "true");
		props.put("hibernate.generate_statistics", "true");
//		props.put("hibernate.c3p0.min_size", "5");
//		props.put("hibernate.c3p0.max_size", "20");
//		props.put("hibernate.c3p0.timeout", "300");
//		props.put("hibernate.c3p0.max_statements", "50");
//		props.put("hibernate.c3p0.idle_test_period", "3000");
		return props;
	}

	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
		System.out.println("*****************entityManagerFactory************************ ");
		final LocalContainerEntityManagerFactoryBean em =  new LocalContainerEntityManagerFactoryBean();//SpringUtils.getBean("entityManagerFactory");
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] {"com.blackiceinc.account.model"});
		em.setPersistenceUnitName("gcdPersistenceUnit");
		final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		
		em.setJpaVendorAdapter(jpaVendorAdapter);
		em.setJpaPropertyMap(this.jpaProperties());	
		
		return em;
	}

	@Bean
	public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
        
     
        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("jdbc.url")));
		dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("jdbc.user")));
		dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("jdbc.pass")));

        return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        
//        final JtaTransactionManager tr = new JtaTransactionManager();
//        tr.setTransactionManager(null);
//        tr.setUserTransaction(userTransaction);
        

        return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
