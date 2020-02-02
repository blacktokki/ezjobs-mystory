package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.AdminService;
import com.ezjobs.mystory.service.page.PageService;
import com.ezjobs.mystory.util.LoginUser;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/admin")//상위 서브도메인
public class AdminController {
	@Inject
	private AdminService<?> tagService;
	
	@Inject
	private AdminService<?> userService;
	
	@Inject
	private AdminService<?> resumeService;
	
	@Inject
	@Named("boardArchiveService")
	private AdminService<?> boardService;
	
	@GetMapping("/personal/{id}")//글내용 보기 /board/content
	public String content(@PathVariable String id,Model model){
		model.addAttribute("user",userService.adminContentById(id));
		return "admin/personal";
	}
	
	@GetMapping("/user")
	public String user(@RequestParam Map<String,Object> map, Model model){
		model.addAttribute("users",userService.adminListAll(map));
		PageService.addPageAttributes(map,model);
		return "admin/user";
	}
	
	@GetMapping("/tag")
	public String tag(@RequestParam Map<String,Object> map, Model model){
		model.addAttribute("tags",tagService.adminListAll(map));
		return "admin/tag";
	}
	
	@PostMapping("/tag")
	public String tag(@RequestParam Map<String,Object> map, Model model, String sch, int showNum, String upTag, String upTagId, String delTagId){
		model.addAttribute("tags",tagService.adminListAll(map));
		return "admin/tag";
	}
	
	@GetMapping("/resume")
	public String resume(@RequestParam Map<String,Object> map, Model model){
		User user=LoginUser.get();
		map.put("id",user.getId());
		map.put("isAdmin",user.getIsAdmin());
		model.addAttribute("resumes",resumeService.adminListAll(map));
		PageService.addPageAttributes(map,model);
		return "admin/resume";
	}
	
	@GetMapping("/board")
	public String board(@RequestParam Map<String,Object> map, Model model){
		model.addAttribute("boards",boardService.adminListAll(map));
		PageService.addPageAttributes(map,model);
		return "admin/board";
	}
	
}
