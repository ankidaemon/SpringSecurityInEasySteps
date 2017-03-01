package com.demo.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(value = 1)
public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {
 
    public SecurityWebAppInitializer() {
        super(SecurityConfig.class,RootConfig.class);
    }
 
}
