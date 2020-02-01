package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.board.BoardService;
import com.ezjobs.mystory.util.LoginUser;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/board") // 상위 서브도메인
public class BoardController {
	
	@Inject
	@Named("communityService")
	private BoardService<?> boardService;

	@GetMapping("/community") // 커뮤니티 게시판 목록 /board/community
	public String community(@RequestParam Map<String,Object> map, Model model) {
		model.addAttribute("boards",boardService.list(map));
		model.addAttribute("page", map.get("page"));
		return "board/list";
	}

	@GetMapping("/content/{id}") // 글내용 보기 /board/content
	public String content(@PathVariable Integer id, Model model) {
		model.addAttribute("board",boardService.content(id));
		return "board/content";
	}

	@GetMapping("/write") // 글작성 화면 /board/write
	public String writeView(Model model) {
		model.addAttribute("method","POST");//jsp->form의 메소드를 결정
		return "board/write";
	}

	@GetMapping("/write/{id}") // 글수정 화면/board/write/1
	public String writeView(@PathVariable Integer id, Model model) {
		if(boardService.isWrited(id,LoginUser.getId())){
			model.addAttribute("method","put");
			model.addAttribute("board",boardService.content(id));
			return "board/write";
		}
		else {
			return "redirect:../content/"+id;
		}
	}

	@PostMapping("/write") // 글작성 요청 /board/write
	public String Write(@RequestParam Map<String, Object> map, Model model) {
		map.put("userId",LoginUser.getId());
		map.put("boardType","Board");
		boardService.write(map);
		return "redirect:../content/"+map.get("id");
	}

	@PutMapping("/write/{id}") // 글수정요청 /board/write/1
	public String Write(@PathVariable Integer id, @RequestParam Map<String, Object> map, Model model) {
		if(boardService.isWrited(id,LoginUser.getId())){
			map.put("id", id);
			boardService.edit(map);
		}
		return "redirect:../content/"+id;
	}
	@PostMapping("/archive/{id}") // 아카이브이동
	public String archive(@PathVariable Integer id, Model model) {
		User user = LoginUser.get();
		if(boardService.isWrited(id,user.getId())||user.getIsAdmin()) {
			boardService.delete(id);
			return "redirect:../community";
		}
		else {
			return "redirect:../content/"+id;
		}
	}
}
