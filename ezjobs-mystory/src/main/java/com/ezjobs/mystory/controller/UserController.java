package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.UserService;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
public class UserController {

	@Inject
	private UserService userService;
	
	
	@GetMapping("/login")
	public String userView(){
		return "user/login";
	}
	
	@PostMapping("/login")
	public String userLogin(HttpSession session ,@RequestParam Map<Object, Object> map, Model model){
		model.addAttribute("map",map);
		try {
			userService.user(model);
			String loginId=(String)map.get("loginId");
			session.setAttribute("loginId", loginId);

		} catch (Exception e) {
			model.addAttribute("login_message", "로그인이 필요합니다.");
			return "user/fail";
		}
		return "/index";
	}
	
	@GetMapping("/fail")
	public String failView(){
		return "user/fail";
	}

	
/*
	@GetMapping("/userJoin")
	public String userJoin(@RequestParam Map<Object, Object> map,Model model){
		model.addAttribute("map",map);
		userService.user(model);
		return "user/userJoin";
	}
	*/
	
	@GetMapping("/join")
	public String writeView(){
		return "user/join";
	}
	

	
	@PostMapping("/join")//회원가입 요청 
	public String Write(@RequestParam Map<Object,Object> map,Model model){
		model.addAttribute("map",map);
		userService.write(model);
		return "redirect:login";
	}
	
/*	
	@GetMapping("/info")
	public String writeView( HttpSession session, Model model) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "user/fail";
		userService.content(model);
		return "user/info";
	}
	*/
	
	@GetMapping("/info")
	public String infoView(HttpSession session){
		if(session.getAttribute("loginId")==null)
			return "user/fail";
		
		return "user/info";
	}
	
	/*
	@PostMapping("/info")//정보수정 요청 
	public String Modify(@RequestParam Map<Object,Object> map,Model model,HttpSession session){
		model.addAttribute("map",map);
		userService.modify(model);
		return "redirect:login";
	}
	*/
	@PutMapping("/info") // 글수정요청 /board/write/1

	public String Write(@RequestParam Map<Object, Object> map, HttpSession session, Model model) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "redirect:/temp/login/fail";
		model.addAttribute("loginId", loginId);
		model.addAttribute("map", map);
		userService.edit(model);
		return "redirect:/index";
	}
	
	@GetMapping("/info/{id}")
	public String info(@PathVariable String id,@RequestParam Map<Object, Object> map, HttpSession session, Model model){
		model.addAttribute("map", map);
		model.addAttribute("id",id);
		userService.info(model);
		return "user/info";
	}
	public String write( @RequestParam Map<Object, Object> map, HttpSession session, Model model) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "redirect:/temp/login/fail";
		model.addAttribute("loginId", loginId);
		model.addAttribute("map", map);
		userService.edit(model);
		return "redirect:/index";

	}
}
