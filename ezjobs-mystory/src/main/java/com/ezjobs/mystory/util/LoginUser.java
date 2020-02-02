package com.ezjobs.mystory.util;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ezjobs.mystory.entity.User;

@Component
public class LoginUser {
	
	private static Authentication getAuthentication(){
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public static void refreshAuthentication(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities){
		Authentication newAuth=new UsernamePasswordAuthenticationToken(
				principal,credentials,authorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}
	
	public static void refreshAuthentication(Object principal, Object credentials) {
		refreshAuthentication(principal,credentials,
				getAuthentication().getAuthorities());
	}
	
	public static User get() {//추후 수정->getUser
		return (User)getAuthentication().getPrincipal();
	}
	
	public static String getId() {
		return get().getId();
	}
}