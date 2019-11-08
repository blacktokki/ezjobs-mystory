package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.service.AutoLabelService;
import com.ezjobs.mystory.service.ResumeService;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


/*************
 * 
 * 
 * @author YDH
 *
 *************/

@Controller
@RequestMapping("/resume")//상위 서브도메인
public class ResumeController {
	
	@Inject
	ResumeService resumeService;
	
	@Inject
	AutoLabelService autoLabelService;
	
	@GetMapping("")//글작성 화면 
	public String resume(Model model){
		//resumeService.group(model);
		resumeService.list(model);
		return "resume/resume";
	}
	
	@GetMapping("content")
	public String list(Model model){
		resumeService.list(model);
		return "resume/list";
	}
	
	@GetMapping("content/{id}")
	public String content(Model model){
		resumeService.content(model);
		return "resume/content";
	}
	
	@GetMapping("write")
	public String write(){
		return "resume/write";
	}

	@GetMapping("/test") 
	public void test(){
		autoLabelService.temp();
	}

}
