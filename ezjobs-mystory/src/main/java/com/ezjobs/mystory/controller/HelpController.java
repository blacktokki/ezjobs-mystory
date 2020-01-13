package com.ezjobs.mystory.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezjobs.mystory.service.HelpService;

@Controller
@RequestMapping("/help")
public class HelpController{
	@Inject
	private HelpService helpService;
	
	@GetMapping("/faq")
	public String helpfaq(@RequestParam Map<Object, Object> map, Model model) {
		model.addAttribute("map", map);
		helpService.faq(model);
		return "help/faq";
	}
	
	@GetMapping("/notice")
	public String helpnotice(@RequestParam Map<Object, Object> map, Model model) {
		model.addAttribute("map", map);
		helpService.notice(model);
		return "help/notice";
	}
	
	@GetMapping("/noticecontent/{id}")
	public String noticecontent(@PathVariable String id, Model model) {
		model.addAttribute("id", id);
		helpService.noticecontent(model);
		return "help/noticecontent";
	}
	
	@GetMapping("/qna")
	public String helpqna(@RequestParam Map<Object, Object> map, HttpSession session, Model model) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "redirect:/temp/login/fail";//로그인 필요화면으로 리다이렉트
		model.addAttribute("map", map);
		helpService.qna(model);
		return "help/qna";
	}
	@GetMapping("/qnacontent/{id}")
	public String qnacontent(@PathVariable String id, HttpSession session, Model model) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "redirect:/temp/login/fail";//로그인 필요화면으로 리다이렉트
		model.addAttribute("id", id);
		helpService.noticecontent(model);
		return "help/qnacontent";
	}
	
	@GetMapping("/qnawrite")
	public String qnawrite(HttpSession session) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "redirect:/temp/login/fail";//로그인 필요화면으로 리다이렉트
		return "help/qnawrite";
	}
	
	@PostMapping("/qnawrite") // 글작성 요청 /help/qnawrite
	public String Write(@RequestParam Map<Object, Object> map,HttpSession session, Model model) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "redirect:/temp/login/fail";//로그인 필요화면으로 리다이렉트
		model.addAttribute("map", map);
		helpService.write(model);
		return "redirect:../qna";
	}
	@PutMapping("/qnawrite/{id}") // 글수정요청 /board/write/1
	public String Write(@PathVariable String id, @RequestParam Map<Object, Object> map,HttpSession session, Model model) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "redirect:/temp/login/fail";//로그인 필요화면으로 리다이렉트
		model.addAttribute("id", id);
		model.addAttribute("map", map);
		helpService.edit(model);
		return "redirect:../qnacontent/{id}";
	}
	
	@GetMapping("/qnawrite/{id}") // 글수정 화면/board/write/1
	public String writeView(@PathVariable String id,HttpSession session, Model model) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "redirect:/temp/login/fail";//로그인 필요화면으로 리다이렉트
		model.addAttribute("method","put");
		model.addAttribute("id", id);
		helpService.qnacontent(model);
		return "help/qnawrite";
	}
	
// 깃 허브 재 업로드용 소스
}