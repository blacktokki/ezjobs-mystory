package com.ezjobs.mystory.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@GetMapping("/login")//로그인,로그아웃후 인덱스 페이지로 넘어감 /temp/login
	public String login(HttpSession session,Model model) {
		if(session.getAttribute("loginId")==null)
			session.setAttribute("loginId","DUMMY_LOGIN_ID");
		else
			session.removeAttribute("loginId");
		System.out.println(session.getAttribute("loginId"));
		return "redirect:/index";
	}
	
	@GetMapping("/login/fail")//로그인 필요 메세지      /user/fail로 옮겨야 함
	public String loginFail(RedirectAttributes redirectAttr){
		redirectAttr.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
		return "redirect:/user/login";//    /user/login으로 옮겨야 함
	}
	
	/*
	@GetMapping("/autolabel")//키워드 분석기
	public void autolabel(){
		keywordAnalysisService.execute();
	}*/
	
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
	}
	
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