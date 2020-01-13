package com.ezjobs.mystory.security;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

public class CaptchaAuthenticationDetails implements Serializable {

	private static final long serialVersionUID = 8047091036777813803L;

	private final String answer;

	private final String captcha;
	
	public CaptchaAuthenticationDetails(HttpServletRequest req) {
		this.answer = req.getParameter("captcha");
		this.captcha = (String)WebUtils.getSessionAttribute(req, "captcha");
	}

	public String getAnswer() {
		return answer;
	}

	public String getCaptcha() {
		return captcha;
	}
	
	public Boolean equals() {
		return captcha.equals(answer);
	}

}