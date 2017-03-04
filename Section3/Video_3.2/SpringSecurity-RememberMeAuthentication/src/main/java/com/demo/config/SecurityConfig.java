package com.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

/**
 * @author ankidaemon
 *
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.demo.config")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

/*	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("ankidaemon").password("password").roles("USER").and().withUser("test")
				.password("test").roles("USER");
	}*/

	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("ankidaemon").password("password").roles("CHIEF").build());
		manager.createUser(User.withUsername("test").password("test").roles("USER").build());
		return manager;
	}
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().regexMatchers("/chief/.*").hasRole("CHIEF")// .hasAuthority("ROLE_USER")
				.regexMatchers("/agent/.*").access("hasRole('AGENT') and principal.name='James Bond'").anyRequest()
				.authenticated()
				
			//	.and().rememberMe().key("HashTokenkeyName").tokenValiditySeconds(100);
		
				.and().rememberMe().key("PersistenceTokenkeyName").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(100);

		http.formLogin().loginPage("/login").permitAll();
	}
	
	@Autowired
	DataSource dataSource;

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl  db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
	
}
