package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.service.AutoLabelService;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;


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
	AutoLabelService autoLabelService;
	
	@GetMapping("")//글작성 화면 
	public String resume(){
		return "resume/resume";
	}
	@GetMapping("/test") 
	public void test(){
		autoLabelService.temp();
	}

}
