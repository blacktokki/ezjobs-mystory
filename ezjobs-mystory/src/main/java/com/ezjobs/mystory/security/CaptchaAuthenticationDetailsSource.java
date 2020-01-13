package com.ezjobs.mystory.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class CaptchaAuthenticationDetailsSource
		implements AuthenticationDetailsSource<HttpServletRequest, CaptchaAuthenticationDetails> {

	@Override
	public CaptchaAuthenticationDetails buildDetails(HttpServletRequest req) {
		return new CaptchaAuthenticationDetails(req);
	}
}