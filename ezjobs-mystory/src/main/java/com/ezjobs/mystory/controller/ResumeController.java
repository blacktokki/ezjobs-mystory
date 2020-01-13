package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.ResumeService;
import com.ezjobs.mystory.service.SplitService;
import com.ezjobs.mystory.util.LoginUser;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/resume")//상위 서브도메인
public class ResumeController {
	
	@Inject
	ResumeService resumeService;
	
	@Inject
	SplitService splitService;
	
	@GetMapping("")//글작성 화면 
	public String resume(Model model){
		User user=LoginUser.get();
		model.addAttribute("loginId",user.getId());
		model.addAttribute("isAdmin",user.getIsAdmin());
		resumeService.list(model);
		//autoLabelService.spliterResumes(model);
		return "resume/resume";
	}
	
	@GetMapping("content")
	public String list(Authentication auth,@RequestParam  Map<Object, Object> map,Model model){
		User user=LoginUser.get();
		model.addAttribute("loginId",user.getId());
		model.addAttribute("isAdmin",user.getIsAdmin());
		model.addAttribute("map",map);
		resumeService.list(model);
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
	public String write(@PathVariable String id,Model model){
		model.addAttribute("loginId",LoginUser.getId());
		model.addAttribute("method","put");
		model.addAttribute("id",id);
		resumeService.content(model);
		return "resume/write";
	}
	
	@ResponseBody
	@PostMapping("content")
	public ResponseEntity<?> content(@RequestParam Map<Object, Object> map,Model model){
		model.addAttribute("loginId",LoginUser.getId());
		model.addAttribute("map", map);
		resumeService.write(model);
		return ResponseEntity.ok(model);
	}
	
	@ResponseBody
	@PutMapping("content/{id}")
	public ResponseEntity<?> content(@PathVariable String id,@RequestParam Map<Object, Object> map,Model model){
		model.addAttribute("id",id);
		model.addAttribute("map", map);
		resumeService.edit(model);
		return ResponseEntity.ok(model);
	}
	
	
	@ResponseBody
	@PutMapping("state/{id}")
	public ResponseEntity<?> content(@PathVariable String id,@RequestParam String state,Model model){
		//model.addAttribute("loginId",getLoginId());
		model.addAttribute("id",id);
		model.addAttribute("state", state);
		resumeService.editState(model);
		return ResponseEntity.ok(model);
	}
	
	@ResponseBody
	@DeleteMapping("content")
	public ResponseEntity<?> delete(@RequestParam Map<Object, Object> map,Model model){
		model.addAttribute("loginId",LoginUser.getId());
		model.addAttribute("map", map);
		resumeService.delete(model);
		return ResponseEntity.ok(model);
	}
	
	@ResponseBody
	@GetMapping("auto")
	public ResponseEntity<?> auto(@RequestParam Map<Object, Object> map,Model model){
		model.addAttribute("map",map);
		resumeService.autoComplete(model);
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("changelist")
	public String changeList(@RequestParam String answer,Model model){
		model.addAttribute("answer",answer);
		splitService.spliterAnswer(model);
		splitService.changeSynonym(model);
		return "resume/changelist";
	}
	
	@PostMapping("synonym")
	public ResponseEntity<?> synonym(@RequestParam Map<Object, Object> map,Model model) {
		model.addAttribute("loginId",LoginUser.getId());
		model.addAttribute("map", map);
		resumeService.addSynonym(model);
		return ResponseEntity.ok(model);
	}
	
	
	@GetMapping("comparelist")
	public String compareList(@RequestParam String answer,Model model){
		model.addAttribute("answer",answer);
		splitService.spliterAnswer(model);
		return "resume/changelist";
	}
	
	@GetMapping("compare")
	public String compare(@RequestParam String sentence,Model model){
		model.addAttribute("sentence",sentence);
		resumeService.compareAll(model);
		return "resume/compare";
	}
	
}
