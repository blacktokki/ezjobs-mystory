package com.ezjobs.mystory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/help")
public class HelpController{
	
	@GetMapping("/faq")
	public String helpfaq() {
		return "help/faq";
	}
	
	@GetMapping("/notice")
	public String helpnotice() {
		return "help/notice";
	}
	
	@GetMapping("/qna")
	public String helpqna() {
		return "help/qna";
	}
	
}