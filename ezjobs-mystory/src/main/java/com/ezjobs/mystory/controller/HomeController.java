package com.ezjobs.mystory.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezjobs.mystory.service.board.BoardService;
import com.ezjobs.mystory.service.help.HelpService;

@Controller
public class HomeController{
	
	@Inject
	private HelpService noticeService;
	
	@Inject
	@Named("communityService")
	private BoardService<?> boardService;
	
	@GetMapping({"index","/"})
	public String index(@RequestParam Map<String, Object> map, Model model) {
		model.addAttribute("notice",noticeService.list(map));
		model.addAttribute("boards",boardService.list(map));
		return "index";
	}
	// 태그 검색 처리 소스 넣을 공간.
	
}// 깃 허브 재 업로드용 소스