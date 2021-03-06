package com.blackiceinc.config;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.PooledDataSource;


@Configuration
@ComponentScan(basePackages = {"com.blackiceinc.web.contollers","com.blackiceinc.beans", "com.blackiceinc.web.exceptions.contollers"})
@EnableWebMvc
@Import({ SecurityConfig.class, PresistenceConfig.class, MyEventListener.class })
@ImportResource( { "classpath*:beans.xml" } )
public class WebMvcConfig extends WebMvcConfigurerAdapter  {

	private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/***").addResourceLocations("/resources/");
    }
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    	
        configurer.enable();
    }
    
    @Bean
    public SimpleMappingExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        //exceptionMappings.put("net.petrikainulainen.spring.testmvc.todo.exception.TodoNotFoundException", "error/404");
        exceptionMappings.put("org.springframework.dao.EmptyResultDataAccessException", "error/warning");
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");
        exceptionMappings.put("com.blackiceinc.exceptions.CustomerNotRegisteredException", "register/signup");
        

        exceptionResolver.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();

        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");
        statusCodes.put("error/warning", "400");
        statusCodes.put("register/signup", "200");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }
    
    
    
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);

        return viewResolver;
    }
//   @Bean
//    public CommonsMultipartResolver multipartResolver (){
//        
//        System.out.println("=======>>>>> DispatcherContext multipartResolver Called :");
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(10000000);
//        return multipartResolver;
//    }
//   @Bean
// MultipartConfigElement multipartConfigElement() {
//     MultipartConfigFactory factory = new MultipartConfigFactory();
//     factory.setMaxFileSize("128KB");
//     factory.setMaxRequestSize("128KB");
//     return factory.createMultipartConfig();
// }
    
//    public WebConfig() {
//        super();
//    }
//
//    public void addResourceHandlers(){
//    	
//    }
    // API
    
    
    @PostConstruct
    public void populateMovieCache() {
    	
    }

    @PreDestroy
    public void clearMovieCache() {
        
        
    }
    
   

}
