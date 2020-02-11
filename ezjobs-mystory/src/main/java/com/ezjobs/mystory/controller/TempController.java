package com.ezjobs.mystory.controller;

//import java.util.List;

import javax.inject.Inject;

//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.web.bind.annotation.ResponseBody;

//import com.ezjobs.mystory.repository.ResumeRepository;
//import com.ezjobs.mystory.entity.Resume;
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
	}
	
	@GetMapping("/resume/stc")//문장데이터생성기
	public void resumetostc(Model model){
		List<Resume> resumes=resumeService.listAll();
		splitService.sentenceAddAll(resumes);
	}
	
	@GetMapping("/resume/tag")//태그생성기
	public void resumetag(Model model){
		keywordAnalysisService.tagger();
	}
	
	@GetMapping("/resume/tagcount")//문항유형태그개수
	public void resumetagcount(Model model){
		keywordAnalysisService.taggerCount();
	}
	
	@GetMapping("/t1")
	@ResponseBody
	public ResponseEntity<?> t1(Model model) {
		return ResponseEntity.ok(model);
	}*/
	
}