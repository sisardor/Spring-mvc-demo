package com.blackiceinc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import com.blackiceinc.account.sevice.AccountService;
import com.blackiceinc.utils.CsrfTokenGeneratorFilter;
import com.blackiceinc.utils.GCDLoginSuccessHandler;
import com.blackiceinc.utils.GCDLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AccountService customUserDetailsService;

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		http.authorizeRequests().antMatchers("/admin/**")
//			.access("hasRole('ROLE_ADMIN')").and().formLogin()
//			.loginPage("/login").failureUrl("/login?error")
//				.usernameParameter("username")
//				.passwordParameter("password")
//				.and().logout().logoutSuccessUrl("/login?logout")
//				.and().csrf()
//				.and().exceptionHandling().accessDeniedPage("/403");
//	}
	
	 @Autowired
	 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		 	auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	 }

	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// AuthenticationSuccessHandler successHandler;
		GCDLoginSuccessHandler successHandler = new GCDLoginSuccessHandler();
		GCDLogoutSuccessHandler logoutHandler = new GCDLogoutSuccessHandler();

		http.userDetailsService(customUserDetailsService)
		   .headers() 
		   		//.frameOptions().disable()
		   		//.addHeaderWriter(new XFrameOptionsHeaderWriter(new WhiteListedAllowFromStrategy(Arrays.asList("www.dzone.com"))))
		   		.addHeaderWriter(new StaticHeadersWriter("X-Frame-Options","SAMEORIGIN"))
		 	.and()
		  .addFilterAfter(new CsrfTokenGeneratorFilter(), CsrfFilter.class)
	      .authorizeRequests()
	        //.antMatchers("/signup","/about", "/upload_test","/api/**", "resources/includes/pdf.html").permitAll() // #4
	        .antMatchers("/guest", "/","/main*").permitAll() // #4
	        .antMatchers("/admin/**", "/demo1*").hasRole("ADMIN") // #6
	        .antMatchers("/reg/**").hasRole("REGULATOR") // #6
	        .anyRequest().authenticated() // 7
	        .and()
	    .formLogin()  // #8
	        .loginPage("/login").failureUrl("/login?error") // #9
	        .successHandler(successHandler)
	        .usernameParameter("username").passwordParameter("password")
	        .permitAll() // #5
		.and()
			.logout()
				.logoutUrl("logout")
				.logoutSuccessHandler(logoutHandler)
				.logoutSuccessUrl("/login?logout");
		//.and().csrf().disable();

	}

	
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**"); // #3
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
////protected void registerAuthentication(AuthenticationManagerBuilder auth)
////throws Exception {
////auth.jdbcAuthentication().dataSource(dataSource);
////
////}
	
////@Autowired
////public void configAuthentication(AuthenticationManagerBuilder auth) 
////	throws Exception {
////
////	auth.jdbcAuthentication().dataSource(dataSource)
////		.passwordEncoder(passwordEncoder())
////		.usersByUsernameQuery("sql...")
////		.authoritiesByUsernameQuery("sql...");
////}	
////
////@Bean
////public PasswordEncoder passwordEncoder(){
////	PasswordEncoder encoder = new BCryptPasswordEncoder();
////	return encoder;
////}
}
