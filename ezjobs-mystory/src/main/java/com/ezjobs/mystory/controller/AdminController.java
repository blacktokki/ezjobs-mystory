package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.service.AdminService;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


/*************
 * 
 * @author CMS
 *
 *************/

@Controller
@RequestMapping("/admin")//상위 서브도메인
public class AdminController {
	@Inject
	private AdminService adminService;
	
	@GetMapping("/user")// 회원관리 페이지 /admin/user
	public String user(@RequestParam Map<Object,Object> map,Model model){
		model.addAttribute("map",map);
		String nn = "notNull";
		model.addAttribute("op",nn);
		model.addAttribute("sch",nn);
		adminService.user(model);
		return "admin/user";
	}
	
	@PostMapping("/user")// 회원관리 페이지 /admin/user
	public String user(@RequestParam Map<Object,Object> map, Model model, String op, String sch){
		model.addAttribute("map",map);
		model.addAttribute("op",op);
		model.addAttribute("sch",sch);
		adminService.user(model);
		return "admin/user";
	}
	
	@GetMapping("/personal/{id}")//글내용 보기 /board/content
	public String content(@PathVariable String id,Model model){
		model.addAttribute("id",id);
		adminService.personal(model);
		return "admin/personal";
	}
	
	
	@GetMapping("/tag")
	public String tag(@RequestParam Map<Object,Object> map,Model model){
		model.addAttribute("map",map);
		adminService.tag(model);
		return "admin/tag";
	}
	
	@PostMapping("/tag")
	public String tag(@RequestParam Map<Object,Object> map, Model model, String sch, int showNum, String upTag, String upTagId, String delTagId){
		model.addAttribute("map",map);
		model.addAttribute("sch",sch);
		model.addAttribute("showNum", showNum);
		model.addAttribute("upTagId",upTagId);
		model.addAttribute("upTag",upTag);
		model.addAttribute("delTagId",delTagId);
		adminService.tag(model);
		return "admin/tag";
	}
}
