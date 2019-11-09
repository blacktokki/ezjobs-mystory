package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.service.ResumeService;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("")//글작성 화면 
	public String resume(HttpSession session,Model model){
		Object loginId=session.getAttribute("loginId");
		if(loginId==null) 
			return "redirect:/temp/login/fail";
		model.addAttribute("loginId",loginId);
		resumeService.list(model);
		return "resume/resume";
	}
	
	@GetMapping("content")
	public String list(HttpSession session,Model model){
		Object loginId=session.getAttribute("loginId");
		if(loginId==null) 
			return "redirect:/temp/login/fail";
		model.addAttribute("loginId",loginId);
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
	
	@ResponseBody
	@PostMapping("content")
	public ResponseEntity<?> write(@RequestParam Map<Object, Object> map,HttpSession session,Model model){
		Object loginId=session.getAttribute("loginId");
		if(loginId==null) {
			
		}
		else {
			model.addAttribute("loginId",loginId);
			model.addAttribute("map", map);
			resumeService.write(model);
		}
		return ResponseEntity.ok(model);
	}


}
