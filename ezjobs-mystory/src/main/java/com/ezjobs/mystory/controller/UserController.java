package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.UserService;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
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
	
	//탈퇴
	@GetMapping("/out")
	public String outView(){
		return "user/out";
	}
	
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
	
	@GetMapping("/info")
	public String infoView(Authentication auth,Model model){
		User user = (User) auth.getPrincipal();
		model.addAttribute("loginId",user.getLoginId());
		userService.info(model);
		return "user/info";
	}
	
	@PutMapping("/info") //정보수정 요청 
	public String Write(Authentication auth, @RequestParam Map<Object, Object> map, Model model) {
		User user = (User) auth.getPrincipal();
		model.addAttribute("userId",user.getLoginId());
		model.addAttribute("map", map);
		userService.edit(model);
		return "redirect:/index";
	}
}
