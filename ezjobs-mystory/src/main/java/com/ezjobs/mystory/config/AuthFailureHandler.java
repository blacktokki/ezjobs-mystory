package com.ezjobs.mystory.config;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.ezjobs.mystory.service.UserService;
 
@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    
	@Inject
	private UserService userService;
	
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
    	System.out.println("fal");
    	System.out.println(exception.getMessage());
    	System.out.println(request.getParameter("loginId"));
    	if(exception.getMessage().equals("invailed username")) {
    		userService.addFailureCount(request.getParameter("loginId"));
    	}
    	response.sendRedirect("/user/login");
    	//super.onAuthenticationFailure(request, response,exception);
    }
}