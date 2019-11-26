package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.service.SearchService;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;


@Controller
@RequestMapping("/search")
public class SearchController {
	@Inject
	private SearchService searchService;
	
	@GetMapping("list")
	public String search(@RequestParam(value = "searchText", required = false, defaultValue = "")String userSearchWord, @RequestParam(value="page", required=false, defaultValue="1") String nowPage, @RequestParam(value = "searchWay", required = false, defaultValue = "1")int searchWay, @RequestParam(value = "numberOfSeeSentence", required = false, defaultValue = "3")int numberOfSeeSentence,@RequestParam(value = "searchTags", required = false, defaultValue = "")String searchTags, Model model){
		
		
		model.addAttribute("nowPage", nowPage); // 유저가 선택한 현재 페이지(default 1)
		model.addAttribute("userSearchWord", userSearchWord); // 유저가 검색한 단어
		model.addAttribute("searchWay", searchWay); // 유저가 검색하고자 하는 범위 조건 지정
		model.addAttribute("numberOfSeeSentence", numberOfSeeSentence); // 유저가 한 페이지에 볼 자소서 수
		model.addAttribute("searchTags", searchTags);
		int tagsCount = StringUtils.countOccurrencesOf(searchTags, ",");
		
		if(tagsCount<3)
		{
			searchService.searchAct(model);
		}else {
			searchService.searchPauseByTags(model);
		}
		
		return "search/list";
	}
	
	@GetMapping("dashboard")
	public String dashboard(Model model){
		return "search/dashboard";
	}
	
	@GetMapping("wordCloudAndChartJson")
	public String wordCloud(Model model){
		searchService.searchWordCloud(model);
		return "search/wordCloudAndChartJson";
	}
	
	@GetMapping("treeMapJson")
	public String treeMap(Model model){
		return "search/treeMapJson";
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