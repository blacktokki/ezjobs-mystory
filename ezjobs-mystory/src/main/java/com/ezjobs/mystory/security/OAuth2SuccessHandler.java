package com.ezjobs.mystory.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.UserService;
 
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
 
	@Inject
	private UserService userService;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
    	
    	OAuth2AuthenticationToken auth=(OAuth2AuthenticationToken) authentication;
    	String registrationId = auth.getAuthorizedClientRegistrationId();
    	String name=auth.getName();
    	User user = userService.findByLoginId(name);
    	List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
    	if(user==null) {
    		Map<String, Object> map=auth.getPrincipal().getAttributes();
    		user=new User();
    		user.setId(name);
    		user.setName((String)map.get("name"));
    		user.setEmail((String)map.get("email"));
    		user.setIsAdmin(false);
    		user.setLoginRel(registrationId);
    		setDefaultTargetUrl("/user/join/social");
    	}
    	else {
    		// 로그인한 계정에게 권한 부여
    		grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
    		grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_SOCIAL"));
    		if (user.getIsAdmin()) {
    			grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    		}
    	}
    	Authentication newAuth = new UsernamePasswordAuthenticationToken(user, user.getLoginPw(), grantedAuthorityList);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
    	super.onAuthenticationSuccess(request, response, authentication);
    }
 
}