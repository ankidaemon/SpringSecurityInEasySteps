package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
		auth.inMemoryAuthentication().withUser("ankidaemon").password("password").roles("USER").and().withUser("test")
				.password("test").roles("USER");
	}

	/*
	 * @Bean public UserDetailsService userDetailsService() {
	 * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	 * manager.createUser(User.withUsername("ankidaemon").password("password").
	 * roles("USER").build());
	 * manager.createUser(User.withUsername("test").password("test").roles(
	 * "USER").build()); return manager; }
	 */

	@Autowired
	private CustomBasicAuthenticationEntryPoint authenticationEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().regexMatchers("/chief/*").hasRole("USER")// .hasAuthority("ROLE_USER")
				.regexMatchers("/agent/*").access("hasRole('AGENT') and principal.name='James Bond'")
				.anyRequest().authenticated()
				.and().httpBasic().authenticationEntryPoint(authenticationEntryPoint).and()
				.requiresChannel().regexMatchers("/chief/*").requiresSecure().and().requiresChannel()
				.regexMatchers("/admin/*").requiresInsecure();

		http.formLogin().loginPage("/login").permitAll();
	}
}
