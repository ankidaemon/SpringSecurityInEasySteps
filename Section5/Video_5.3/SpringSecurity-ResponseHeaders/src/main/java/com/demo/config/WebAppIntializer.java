package com.demo.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.*;

/**
 * @author ankidaemon
 *
 */
public class WebAppIntializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

}
