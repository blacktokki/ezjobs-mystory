package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.service.TagService;
import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.ResumeService;
import com.ezjobs.mystory.service.UserService;
import com.ezjobs.mystory.service.board.BoardService;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.context.SecurityContextHolder;
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
	
	@GetMapping("/personal/{loginId}")//글내용 보기 /board/content
	public String content(@PathVariable String loginId,Model model){
		model.addAttribute("loginId",loginId);
		userService.info(model);
		return "admin/personal";
	}
	
	@GetMapping("/user")
	public String user(@RequestParam Map<String,Object> map, Model model){
		model.addAttribute("map",map);
		userService.list(model);
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
		User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("isAdmin",user.getIsAdmin());
		model.addAttribute("map",map);
		resumeService.list(model);
		return "admin/resume";
	}
	
	@GetMapping("/board")
	public String board(@RequestParam Map<String,Object> map, Model model){
		model.addAttribute("boards",boardService.list(map));
		model.addAttribute("page", map.get("page"));
		model.addAttribute("size", map.get("size"));
		model.addAttribute("op", map.get("op"));
		model.addAttribute("keyword", map.get("keyword"));
		return "admin/board";
	}
	
}
