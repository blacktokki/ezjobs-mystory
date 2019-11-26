package com.ezjobs.mystory.controller;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezjobs.mystory.service.HelpService;

@Controller
public class HomeController{
	@Inject
	private HelpService helpService;
	
	@GetMapping("index")
	public String index(@RequestParam Map<Object, Object> map, Model model) {
		model.addAttribute("map", map);
		helpService.notice(model);
		helpService.community(model);
		return "index";
	}
	// 태그 검색 처리 소스 넣을 공간.
	
}// 깃 허브 재 업로드용 소스