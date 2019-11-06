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
	public String search(@RequestParam(value = "searchText", required = false, defaultValue = "")String act, @RequestParam(value="page", required=false, defaultValue="1") String npg, Model model){
		
		model.addAttribute("npg", npg);
		model.addAttribute("act", act);
		searchService.searchAct(model);
		/*
		 * searchService.makeLastPage(model); searchService.makeBlock(model, nowPage);
		 */

		return "search/list";
	}
	
	/*
	 * @GetMapping("list") public String dsearch1() { return "redirect:list/1"; }
	 * 
	 * @GetMapping("list/") public String dsearch2() { return "redirect:1"; }
	 * 
	 * @GetMapping("") public String dsearch3() { return "redirect:search/list/1"; }
	 * 
	 * @GetMapping("/") public String dsearch4() { return "redirect:list/1"; }
	 */
}