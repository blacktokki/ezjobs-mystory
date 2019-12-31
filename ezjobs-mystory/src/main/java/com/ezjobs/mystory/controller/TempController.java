package com.ezjobs.mystory.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezjobs.mystory.service.KeywordAnalysisService;
import com.ezjobs.mystory.service.ResumeService;
import com.ezjobs.mystory.service.SplitService;
@Controller
@RequestMapping("/temp")//상위 서브도메인
public class TempController{
	
	@Inject
	KeywordAnalysisService keywordAnalysisService;
	
	@Inject
	SplitService splitService;
	
	@Inject
	ResumeService resumeService;
	
	/*
	@GetMapping("/autolabel")//키워드 분석기
	public void autolabel(){
		keywordAnalysisService.execute();
	}*/
	/*
	@GetMapping("/resume/stc")//문장데이터생성기
	public void resumetostc(Model model){
		resumeService.listAll(model);
		splitService.spliterResumes(model);
		splitService.sentenceAddAll(model);
	}
	
	@GetMapping("/resume/tag")//태그생성기
	public void resumetag(Model model){
		resumeService.listAll(model);
		keywordAnalysisService.tagger(model);
	}*/
	
	@GetMapping("/resume/tagcount")//문항유형태그개수
	public void resumetagcount(Model model){
		resumeService.listAll(model);
		keywordAnalysisService.taggerCount(model);
	}
	
	@GetMapping("/tagsearch")//태그검색시험용
	public void tag(Model model){
		resumeService.tagsearch(model);
	}
}