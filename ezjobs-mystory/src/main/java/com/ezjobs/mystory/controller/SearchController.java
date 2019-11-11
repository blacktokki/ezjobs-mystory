package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.service.SearchService;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/search")
public class SearchController {
	@Inject
	private SearchService searchService;
	
	@GetMapping("list")
	public String search(@RequestParam(value = "searchText", required = false, defaultValue = "")String userSearchWord, @RequestParam(value="page", required=false, defaultValue="1") String nowPage, @RequestParam(value = "searchWay", required = false, defaultValue = "1")int searchWay, @RequestParam(value = "numberOfSeeResume", required = false, defaultValue = "3")int numberOfSeeResume, Model model){
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("userSearchWord", userSearchWord);
		model.addAttribute("searchWay", searchWay);
		model.addAttribute("numberOfSeeResume", numberOfSeeResume);
		searchService.searchAct(model);
		/*
		 * searchService.makeLastPage(model); searchService.makeBlock(model, nowPage);
		 */
		return "search/list";
	}
	
	/* 혹시나 유저 편의로 주소맵핑
	 * @GetMapping("list") public String dsearch1() { return "redirect:list/1"; }
	 * 
	 * @GetMapping("list/") public String dsearch2() { return "redirect:1"; }
	 * 
	 * @GetMapping("") public String dsearch3() { return "redirect:search/list/1"; }
	 * 
	 * @GetMapping("/") public String dsearch4() { return "redirect:list/1"; }
	 */
}