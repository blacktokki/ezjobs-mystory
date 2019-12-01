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
	
	@GetMapping("/personal/{id}")//글내용 보기 /board/content
	public String content(@PathVariable String id,Model model){
		model.addAttribute("id",id);
		adminService.personal(model);
		return "admin/personal";
	}
	
	@GetMapping("/user")
	public String user(@RequestParam Map<Object,Object> map, Model model){
		model.addAttribute("map",map);
		adminService.user(model);
		return "admin/user";
	}
	
	@PostMapping("/user")
	public String user(@RequestParam Map<Object,Object> map, Model model, String op, String sch, int showNum){
		model.addAttribute("map",map);
		model.addAttribute("op",op);
		model.addAttribute("sch",sch);
		model.addAttribute("showNum", showNum);
		adminService.user(model);
		return "admin/user";
	}
	
	@GetMapping("/tag")
	public String tag(@RequestParam Map<Object,Object> map, Model model){
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
	
	@GetMapping("/resume")
	public String resume(@RequestParam Map<Object,Object> map, Model model){
		model.addAttribute("map",map);
		adminService.resume(model);
		//adminService.tag(model);
		return "admin/resume";
	}
	
	@PostMapping("/resume")
	public String resume(@RequestParam Map<Object,Object> map, Model model, String op, String sch, int showNum){
		model.addAttribute("map",map);
		model.addAttribute("op",op);
		model.addAttribute("sch",sch);
		model.addAttribute("showNum", showNum);
	//	adminService.resume(model);
		adminService.tag(model); // 아직 자소서관리 안돌아가서 일단 이렇게 해놧어요 ㅠㅠ
		return "admin/resume";
	}
}
