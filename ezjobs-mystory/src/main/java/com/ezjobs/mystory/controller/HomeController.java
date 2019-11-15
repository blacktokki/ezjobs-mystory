package com.ezjobs.mystory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController{
	
	@GetMapping("index")
	public String index() {
		return "index";
	}
	// 태그 검색 처리 소스 넣을 공간.
	
}// 깃 허브 재 업로드용 소스