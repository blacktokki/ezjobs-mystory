package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.ResumeService;
import com.ezjobs.mystory.service.SplitService;
import com.ezjobs.mystory.service.TagService;
import com.ezjobs.mystory.service.page.PageService;
import com.ezjobs.mystory.util.LoginUser;

import lombok.AllArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@AllArgsConstructor
@RequestMapping("/resume")//상위 서브도메인
public class ResumeController {
	
	ResumeService resumeService;
	
	TagService tagService;
	
	SplitService splitService;
	
	@GetMapping("")//글작성 화면 
	public String resume(@RequestParam Map<String, Object> map,Model model){
		User user=LoginUser.get();
		map.put("id",user.getId());
		map.put("isAdmin",user.getIsAdmin());
		model.addAttribute("resumes",resumeService.list(map));
		PageService.addPageAttributes(map,model);
		return "resume/resume";
	}
	
	@GetMapping("content")
	public String list(@RequestParam  Map<String, Object> map,Model model){
		User user=LoginUser.get();
		map.put("id",user.getId());
		map.put("isAdmin",user.getIsAdmin());
		model.addAttribute("resumes",resumeService.list(map));
		PageService.addPageAttributes(map,model);
		return "resume/list";
	}
	
	@GetMapping("content/{id}")
	public String content(@PathVariable Integer id,Model model){
		model.addAttribute("resume",resumeService.content(id));
		return "resume/content";
	}
	
	@GetMapping("write")
	public String write(Model model){
		model.addAttribute("method","post");
		return "resume/write";
	}
	
	@GetMapping("write/{id}")
	public String write(@PathVariable Integer id,Model model){
		//model.addAttribute("loginId",LoginUser.getId());회원별 검증
		model.addAttribute("method","put");
		model.addAttribute("resume",resumeService.content(id));
		return "resume/write";
	}
	
	@ResponseBody
	@PostMapping("content")
	public ResponseEntity<?> content(@RequestParam Map<String, Object> map,Model model){
		map.put("userId",LoginUser.getId());
		resumeService.write(map,tagService.writeResumeTags(map));
		model.addAttribute("id", map.get("id"));
		return ResponseEntity.ok(model);
	}
	
	@ResponseBody
	@PutMapping("content/{id}")
	public ResponseEntity<?> content(@PathVariable Integer id,@RequestParam Map<String, Object> map,Model model){
		map.put("userId",LoginUser.getId());
		resumeService.edit(map,tagService.writeResumeTags(map));
		model.addAttribute("id", id);
		return ResponseEntity.ok(model);
	}
	
	
	@ResponseBody
	@PutMapping("state/{id}")
	public ResponseEntity<?> content(@PathVariable Integer id,@RequestParam String state,Model model){
		resumeService.editState(id,state);
		return ResponseEntity.ok(model);
	}
	
	@ResponseBody
	@DeleteMapping("content")
	public ResponseEntity<?> delete(@RequestParam Map<String, Object> map,Model model){
		resumeService.delete(map);
		return ResponseEntity.ok(model);
	}
	
	@ResponseBody
	@GetMapping("auto")
	public ResponseEntity<?> auto(@RequestParam Map<String, Object> map,Model model){
		String searchType=(String)map.get("searchType");
		if(searchType.equals("")) {
			model.addAttribute("list",resumeService.autoComplete(map));
		}
		else {
			model.addAttribute("list",tagService.autoComplete(map));
		}
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("changelist")
	public String changeList(@RequestParam String answer,Model model){
		model.addAttribute("sentences",splitService.changeSynonym(answer));
		return "resume/changelist";
	}
	
	@PostMapping("synonym")
	public ResponseEntity<?> synonym(@RequestParam Map<String, Object> map,Model model) {
		map.put("loginId",LoginUser.getId());
		resumeService.addSynonym(map);
		return ResponseEntity.ok(model);
	}
	
	
	@GetMapping("comparelist")
	public String compareList(@RequestParam String answer,Model model){
		model.addAttribute("sentences",splitService.spliterAnswer(answer));
		return "resume/changelist";
	}
	
	@GetMapping("compare")
	public String compare(@RequestParam String sentence,Model model){
		model.addAllAttributes(resumeService.compareAll(sentence));
		return "resume/compare";
	}
	
}
