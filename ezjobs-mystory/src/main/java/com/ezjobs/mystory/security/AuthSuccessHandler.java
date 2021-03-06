package com.ezjobs.mystory.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ezjobs.mystory.service.UserService;
import com.ezjobs.mystory.util.LoginUser;
 
@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
 
	@Inject
	private UserService userService;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
    	System.out.println("suc");
    	userService.clearFailureCount(LoginUser.getId());
    	userService.visit(LoginUser.getId());
    	super.onAuthenticationSuccess(request, response, authentication);
    }
 
}