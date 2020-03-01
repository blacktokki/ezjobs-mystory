package com.ezjobs.mystory.config;

import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.apache.catalina.core.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JspPreCompilerConfig {
	static final Logger logger =  LoggerFactory.getLogger(JspPreCompilerConfig.class);
	
	@Bean
	public ServletContextInitializer preCompileJspsAtStartup() {
	    return servletContext -> {
	        getDeepResourcePaths(servletContext, "/WEB-INF/jsp/").forEach(jspPath -> {
	            logger.info("Registering JSP: {}", jspPath);
	            ServletRegistration.Dynamic reg = servletContext.addServlet(jspPath, Constants.JSP_SERVLET_CLASS);
	            reg.setInitParameter("jspFile", jspPath);
	            reg.setLoadOnStartup(99);
	            reg.addMapping(jspPath);
	        });
	    };
	}
	
	private static Stream<String> getDeepResourcePaths(ServletContext servletContext, String path) {
	    return (path.endsWith("/")) ? servletContext.getResourcePaths(path).stream().flatMap(p -> getDeepResourcePaths(servletContext, p))
	            : Stream.of(path);
	}
}
