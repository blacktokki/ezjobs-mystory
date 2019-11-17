package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.service.AutoLabelService;
import com.ezjobs.mystory.service.ResumeService;

import java.util.ArrayList;
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
	
	@Inject
	AutoLabelService autoLabelService;
	
	@GetMapping("")//글작성 화면 
	public String resume(HttpSession session,Model model){
		Object loginId=session.getAttribute("loginId");
		if(loginId==null) 
			return "redirect:/temp/login/fail";
		model.addAttribute("loginId",loginId);
		resumeService.list(model);
		//autoLabelService.spliterResumes(model);
		return "resume/resume";
	}
	
	@GetMapping("content")
	public String list(HttpSession session,Model model){
		Object loginId=session.getAttribute("loginId");
		if(loginId==null) 
			return "redirect:/temp/login/fail";
		model.addAttribute("loginId",loginId);
		resumeService.list(model);
		//autoLabelService.spliterResumes(model);
		return "resume/list";
	}
	
	@GetMapping("content/{id}")
	public String content(Model model){
		resumeService.content(model);
		return "resume/content";
	}
	
	@GetMapping("write")
	public String write(Model model){
		model.addAttribute("method","post");
		return "resume/write";
	}
	@GetMapping("write/{id}")
	public String write(@PathVariable String id,HttpSession session,Model model){
		Object loginId=session.getAttribute("loginId");
		if(loginId==null) 
			return "redirect:/temp/login/fail";
		model.addAttribute("method","put");
		model.addAttribute("id",id);
		resumeService.content(model);
		return "resume/write";
	}
	
	@ResponseBody
	@PostMapping("content")
	public ResponseEntity<?> content(@RequestParam Map<Object, Object> map,HttpSession session,Model model){
		Object loginId=session.getAttribute("loginId");
		if(loginId==null)
			return ResponseEntity.badRequest().build();
		model.addAttribute("loginId",loginId);
		model.addAttribute("map", map);
		resumeService.write(model);
		return ResponseEntity.ok(model);
	}
	
	@ResponseBody
	@PutMapping("content/{id}")
	public ResponseEntity<?> content(@PathVariable String id,@RequestParam Map<Object, Object> map,HttpSession session,Model model){
		Object loginId=session.getAttribute("loginId");
		if(loginId==null) 
			return ResponseEntity.badRequest().build();
		model.addAttribute("id",id);
		model.addAttribute("map", map);
		System.out.println(map.get("tags"));
		resumeService.edit(model);
		return ResponseEntity.ok(model);
	}
	
	@ResponseBody
	@GetMapping("auto")
	public ResponseEntity<?> auto(@RequestParam String keyword,Model model){
		model.addAttribute("keyword",keyword);
		resumeService.autoComplete(model);
		return ResponseEntity.ok(model);
	}
	
	@PostMapping("changelist")
	public String changeList(@RequestParam String answer,Model model){
		model.addAttribute("answer",answer);
		autoLabelService.spliterAnswer(model);
		return "resume/changelist";
	}
	
	@ResponseBody
	@GetMapping("compare")
	public String compare(@RequestParam String sentence,Model model){
		model.addAttribute("sentence",sentence);
		//autoLabelService.spliterAnswer(model);
		return "resultValue";
	}

}
