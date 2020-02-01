package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.service.TagService;
import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.ResumeService;
import com.ezjobs.mystory.service.UserService;
import com.ezjobs.mystory.service.board.BoardService;
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
	private TagService tagService;
	
	@Inject
	private UserService userService;
	
	@Inject
	private ResumeService resumeService;
	
	@Inject
	@Named("boardArchiveService")
	private BoardService<?> boardService;
	
	@GetMapping("/personal/{id}")//글내용 보기 /board/content
	public String content(@PathVariable String id,Model model){
		model.addAttribute("user",userService.info(id));
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
		model.addAttribute("map",map);
		tagService.list(model);
		return "admin/tag";
	}
	
	@PostMapping("/tag")
	public String tag(@RequestParam Map<String,Object> map, Model model, String sch, int showNum, String upTag, String upTagId, String delTagId){
		model.addAttribute("map",map);
		model.addAttribute("sch",sch);
		model.addAttribute("showNum", showNum);
		model.addAttribute("upTagId",upTagId);
		model.addAttribute("upTag",upTag);
		model.addAttribute("delTagId",delTagId);
		tagService.list(model);
		return "admin/tag";
	}
	
	@GetMapping("/resume")
	public String resume(@RequestParam Map<String,Object> map, Model model){
		User user=LoginUser.get();
		map.put("id",user.getId());
		map.put("isAdmin",user.getIsAdmin());
		model.addAttribute("resumes",resumeService.list(map));
		PageService.addPageAttributes(map,model);
		return "admin/resume";
	}
	
	@GetMapping("/board")
	public String board(@RequestParam Map<String,Object> map, Model model){
		model.addAttribute("boards",boardService.list(map));
		PageService.addPageAttributes(map,model);
		return "admin/board";
	}
	
}
