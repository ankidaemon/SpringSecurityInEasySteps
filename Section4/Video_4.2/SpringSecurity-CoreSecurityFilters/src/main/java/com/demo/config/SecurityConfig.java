package com.demo.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

/**
 * @author ankidaemon
 *
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.demo.config")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("ankidaemon").password("password").roles("CHIEF")
		.and().withUser("test").password("test").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
						
		http.authorizeRequests()
				.regexMatchers("/chief/.*").hasRole("CHIEF")
				.regexMatchers("/agent/.*").access("hasRole('USER') and principal.name='James Bond'")
				.anyRequest().authenticated()
				.and().httpBasic()
				.and().requiresChannel().anyRequest().requiresSecure();	
		
		http.exceptionHandling().accessDeniedPage("/accessDenied");
		
		http.formLogin().loginPage("/login").permitAll();
		
		http.logout().logoutUrl("/customlogout");
		
		http.addFilterBefore(securityContextPersistenceFilter(), SecurityContextPersistenceFilter.class);
		http.addFilterAt(exceptionTranslationFilter(), ExceptionTranslationFilter.class);
		//http.addFilter(filterSecurityInterceptor()); // This ensures filter ordering by default
		http.addFilterAfter(new CustomFilter(), FilterSecurityInterceptor.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().regexMatchers("/resources/.*");
	}
	/*
	@SuppressWarnings("all")
	@Bean
	FilterSecurityInterceptor filterSecurityInterceptor() throws Exception{
		FilterSecurityInterceptor fSecInterceptor=new FilterSecurityInterceptor();
		//fSecInterceptor.setAccessDecisionManager(------);
		fSecInterceptor.setAuthenticationManager(super.authenticationManager());
		fSecInterceptor.setSecurityMetadataSource(
				new DefaultFilterInvocationSecurityMetadataSource(
						(LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>) new LinkedHashMap().put(
								new AntPathMatcher("/chief**"), new ArrayList().add("ROLE_CHIEF"))));
		return fSecInterceptor;
	}*/
	
	@Bean
	ExceptionTranslationFilter exceptionTranslationFilter(){
		AuthenticationEntryPoint loginUrlAuthenticationEntryPoint=new LoginUrlAuthenticationEntryPoint("/login.jsp");
		AccessDeniedHandlerImpl accessDeniedHandlerImpl=new AccessDeniedHandlerImpl();
		accessDeniedHandlerImpl.setErrorPage("/accessDenied.jsp");
		ExceptionTranslationFilter eTranslationFilter=new ExceptionTranslationFilter(loginUrlAuthenticationEntryPoint);
		eTranslationFilter.setAccessDeniedHandler(accessDeniedHandlerImpl);
		return eTranslationFilter;
	}
	
	@Bean
	SecurityContextPersistenceFilter securityContextPersistenceFilter(){
		
		SecurityContextRepository sCRepo=new HttpSessionSecurityContextRepository();
		((HttpSessionSecurityContextRepository) sCRepo).setAllowSessionCreation(true); //by default true
		
		SecurityContextPersistenceFilter sCPFilter=new SecurityContextPersistenceFilter(sCRepo);
		
		return sCPFilter;
	}
	
}
