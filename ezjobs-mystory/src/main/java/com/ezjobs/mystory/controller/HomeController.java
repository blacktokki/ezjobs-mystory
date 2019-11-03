package com.ezjobs.mystory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController{
	
	@GetMapping("index")
	public String index() {
		return "index";
	}
}// 깃 허브 재 업로드용 소스