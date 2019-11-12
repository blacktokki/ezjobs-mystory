package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.service.BoardService;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/*************
 * 
 * 
 * @author YDH
 *
 *************/

@Controller
@RequestMapping("/board") // 상위 서브도메인
public class BoardController {
	@Inject
	private BoardService boardService;

	@GetMapping("/community") // 커뮤니티 게시판 목록 /board/community
	public String community(@RequestParam Map<Object, Object> map, Model model) {
		model.addAttribute("map", map);
		boardService.community(model);
		return "board/list";
	}

	@GetMapping("/content/{id}") // 글내용 보기 /board/content
	public String content(@PathVariable String id, Model model) {
		model.addAttribute("id", id);
		boardService.content(model);
		return "board/content";
	}

	@GetMapping("/write") // 글작성 화면 /board/write
	public String writeView(HttpSession session,Model model) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "redirect:/temp/login/fail";//로그인 필요화면으로 리다이렉트
		model.addAttribute("method","POST");//jsp->form의 메소드를 결정
		return "board/write";
	}

	@GetMapping("/write/{id}") // 글수정 화면/board/write/1
	public String writeView(@PathVariable String id, HttpSession session, Model model) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "redirect:/temp/login/fail";
		model.addAttribute("id", id);
		model.addAttribute("method","put");
		boardService.content(model);
		return "board/write";
	}

	@PostMapping("/write") // 글작성 요청 /board/write
	public String Write(@RequestParam Map<Object, Object> map, HttpSession session, Model model) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "redirect:/temp/login/fail";
		model.addAttribute("userId", loginId);
		model.addAttribute("map", map);
		boardService.write(model);
		return "redirect:../community";
	}

	@PutMapping("/write/{id}") // 글수정요청 /board/write/1
	public String Write(@PathVariable String id, @RequestParam Map<Object, Object> map, HttpSession session, Model model) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "redirect:/temp/login/fail";
		model.addAttribute("id", id);
		model.addAttribute("map", map);
		boardService.edit(model);
		return "redirect:../community";
	}

	@DeleteMapping("/write/{id}") // 글삭제요청 /board/write
	public String Write(@PathVariable String id, HttpSession session, Model model) {
		Object loginId = session.getAttribute("loginId");
		if (loginId == null)
			return "redirect:/temp/login/fail";
		model.addAttribute("id", id);
		//boardService.remove(model);//미구현
		return "redirect:../community";
	}
}
