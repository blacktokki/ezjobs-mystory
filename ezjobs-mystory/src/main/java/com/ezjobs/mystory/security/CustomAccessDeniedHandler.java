package com.ezjobs.mystory.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.ezjobs.mystory.entity.User;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	 
    @Override
    public void handle(HttpServletRequest request,HttpServletResponse response, AccessDeniedException exc) throws IOException, ServletException {
         
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            System.out.println("User: " + auth.getName() 
              + " attempted to access the protected URL: "
              + request.getRequestURI());
            if(auth.getAuthorities().size()==0 && ((User)auth.getPrincipal()).getRegistDate()==null) {
            	response.sendRedirect("/user/join/social");
            }
        }
 
        //response.sendRedirect(request.getContextPath() + "/accessDenied");
    }
}