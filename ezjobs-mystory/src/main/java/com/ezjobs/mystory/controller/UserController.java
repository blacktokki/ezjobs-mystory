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
		userService.user(model);
		return "user/login";
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
	
	
}
