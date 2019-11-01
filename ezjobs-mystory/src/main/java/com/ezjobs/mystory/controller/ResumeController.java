package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

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
	
	@GetMapping("")//글작성 화면 
	public String resume(){
		return "resume/resume";
	}

	
}
