package com.demo.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomDigestAuthenticationEntryPoint extends DigestAuthenticationEntryPoint{

    @Override
    public void commence
      (HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) 
      throws IOException, ServletException {
    	response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("DigestAuthentication : You are not authorized. " + exception.getMessage());
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("Spring");
        setKey("acegi");
        setNonceValiditySeconds(10);
        super.afterPropertiesSet();
    }
    
}
