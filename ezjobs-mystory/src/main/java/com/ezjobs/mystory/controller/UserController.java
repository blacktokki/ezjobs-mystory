package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.UserService;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	//탈퇴,미구현
	@GetMapping("/out")
	public String outView(){
		return "user/out";
	}
	
	@ResponseBody
	@GetMapping("/check_id")
	public Boolean checkId(@RequestParam String loginId) {
		User user=new User();
		user.setLoginId(loginId);
		userService.getUser(user);
		return userService.getUser(user)==null;
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
		model.addAttribute("userId",user.getLoginId());
		userService.info(model);
		return "user/info";
	}
	
	@PutMapping("/info") //정보수정 요청 
	public String Write(Authentication auth, @RequestParam Map<Object, Object> map, Model model) {
		User user = (User) auth.getPrincipal();
		model.addAttribute("userId",user.getLoginId());
		model.addAttribute("map", map);
		userService.edit(model);
		User newUser = (User)model.getAttribute("user");
		Authentication newAuth = new UsernamePasswordAuthenticationToken(newUser, newUser.getLoginPw(), auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(newAuth);
		return "redirect:/index";
	}
}
