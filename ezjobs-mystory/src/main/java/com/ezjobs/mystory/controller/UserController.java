package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.EmailService;
import com.ezjobs.mystory.service.UserService;
import com.ezjobs.mystory.util.UserSha256;

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
	
	@Inject
	private EmailService emailService;
	
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
		return userService.findByLoginId(loginId)==null;
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
		String loginId=((User)auth.getPrincipal()).getLoginId();
		model.addAttribute("loginId",loginId);
		model.addAttribute("map", map);
		userService.edit(model);
		User user=userService.findByLoginId(loginId);
		Authentication newAuth = new UsernamePasswordAuthenticationToken(user, user.getLoginPw(), auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(newAuth);
		return "redirect:/index";
	}
	
	@GetMapping("/password/new")
	public String passwordNew(Model model){
		return "user/password/new";
	}
	
	@PostMapping("/password")
	public String passwordNew(@RequestParam Map<Object, Object> map,Model model){
		String loginId=(String)map.get("loginId");
		String newPw=UserService.getRamdomPassword(10);
		String email=(String)map.get("email");
		User user=userService.findByLoginId(loginId);
		
		if(user!=null&&user.getEmail().equals(email)) {
			model.addAttribute("loginId",loginId);
			model.addAttribute("newPw",newPw);
			userService.changePw(model);
			emailService.sendSimpleMessage(email, "[Ezjbos]패스워드 재설정 안내",
					"임시 비밀번호 발급 안내 \r\n" + 
					"임시 비밀번호가 아래와 같이 발급 되었습니다. \r\n" + 
					"아래 비밀번호로 로그인 후 변경해 주세요. \r\n" + 
					"임시 비밀번호:"+ newPw);
			return "redirect:/user/login";
		}
		else {
			System.out.println("mismatch");
			return "redirect:/user/password/new";
		}
	}
	
	@GetMapping("/password/change")
	public String passwordChange(Model model){
		return "user/password/change";
	}
	
	@PutMapping("/password")
	public String passwordChange(Authentication auth,@RequestParam Map<Object, Object> map,Model model){
		String loginId=((User)auth.getPrincipal()).getLoginId();
		String loginPw=UserSha256.encrypt((String)map.get("loginPw"));
		String newPw=(String)map.get("newPw");
		User user=userService.findByLoginId(loginId);
		
		if(user!=null && user.getLoginPw().equals(loginPw)) {
			model.addAttribute("loginId",loginId);
			model.addAttribute("newPw",newPw);
			userService.changePw(model);
			user.setLoginPw(UserSha256.encrypt(newPw));
			Authentication newAuth = new UsernamePasswordAuthenticationToken(user, user.getLoginPw(), auth.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(newAuth);
			return "redirect:/index";
		}
		else {
			System.out.println("mismatch");
			return "redirect:/user/password/change";
		}
	}
}
