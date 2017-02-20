package com.demo.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@Import({ RepositoryConfig.class })
public class RootConfig {
	@Bean
	public static PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer()
	{
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocation(new ClassPathResource("application.properties"));
		ppc.setIgnoreUnresolvablePlaceholders(true);
		return ppc;
	}
}
