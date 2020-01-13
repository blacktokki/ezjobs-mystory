package com.ezjobs.mystory.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ezjobs.mystory.entity.User;

@Component
public class LoginUser {
	public static User get() {
		return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public static String getId() {
		return get().getId();
	}
}