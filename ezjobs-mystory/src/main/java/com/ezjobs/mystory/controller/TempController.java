package com.ezjobs.mystory.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezjobs.mystory.service.AutoLabelService;

@Controller
@RequestMapping("/temp")//상위 서브도메인
public class TempController{
	
	@Inject
	AutoLabelService autoLabelService;

	@GetMapping("/login")//로그인,로그아웃후 인덱스 페이지로 넘어감 /temp/login
	public String login(HttpSession session,Model model) {
		if(session.getAttribute("loginId")==null)
			session.setAttribute("loginId","DUMMY_LOGIN_ID");
		else
			session.removeAttribute("loginId");
		return "/index";
	}
	
	@GetMapping("/login/fail")//로그인 필요 메세지      /user/fail로 옮겨야 함
	public String loginFail(RedirectAttributes redirectAttr){
		redirectAttr.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
		return "redirect:/index";//    /user/login으로 옮겨야 함
	}
	
	@GetMapping("/autolabel")//오토라벨링, 신경쓰지마세요
	public void autolabel(){
		autoLabelService.temp();
	}
	
	
}