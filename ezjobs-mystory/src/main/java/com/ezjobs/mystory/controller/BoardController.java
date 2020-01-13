package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.BoardService;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

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
	public String writeView(Model model) {
		model.addAttribute("method","POST");//jsp->form의 메소드를 결정
		return "board/write";
	}

	@GetMapping("/write/{id}") // 글수정 화면/board/write/1
	public String writeView(Authentication auth, @PathVariable String id, Model model) {
		User user = (User) auth.getPrincipal();
		if(boardService.isWrited(Integer.parseInt(id),user.getId())){
			model.addAttribute("id", id);
			model.addAttribute("method","put");
			boardService.content(model);
			return "board/write";
		}
		else {
			return "redirect:../content/"+id;
		}
	}

	@PostMapping("/write") // 글작성 요청 /board/write
	public String Write(Authentication auth, @RequestParam Map<Object, Object> map, Model model) {
		User user = (User) auth.getPrincipal();
		model.addAttribute("userId", user.getId());
		model.addAttribute("map", map);
		boardService.write(model);
		return "redirect:../content/"+model.getAttribute("id");
	}

	@PutMapping("/write/{id}") // 글수정요청 /board/write/1
	public String Write(Authentication auth, @PathVariable String id, @RequestParam Map<Object, Object> map, Model model) {
		User user = (User) auth.getPrincipal();
		if(boardService.isWrited(Integer.parseInt(id),user.getId())){
			model.addAttribute("id", id);
			model.addAttribute("map", map);
			boardService.edit(model);
		}
		return "redirect:../content/"+id;
	}
	@PostMapping("/archive/{id}") // 아카이브이동
	public String archive(Authentication auth, @PathVariable String id, Model model) {
		User user = (User) auth.getPrincipal();
		if(boardService.isWrited(Integer.parseInt(id),user.getId())||user.getIsAdmin()){
			model.addAttribute("id", id);
			boardService.moveArchive(model);
			return "redirect:../community";
		}
		else {
			return "redirect:../content/"+id;
		}
	}
	
	/*
	@DeleteMapping("/write/{id}") // 글삭제요청 /board/write
	public String Write(Authentication auth, @PathVariable String id, Model model) {
		User user = (User) auth.getPrincipal();
		if(boardService.isWrited(Integer.parseInt(id),user.getId())){
			model.addAttribute("id", id);
			//boardService.remove(model);//미구현
			return "redirect:../community";
		}
		else {
			return "redirect:../content/"+id;
		}
	}*/
}
