package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.service.BoardService;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;



/*************
 * 
 * 
 * @author YDH
 *
 *************/

@Controller
@RequestMapping("/board")//상위 서브도메인
public class BoardController {
	@Inject
	private BoardService boardService;
	
	@GetMapping("/community")//커뮤니티 게시판 목록 /board/community
	public String community(@RequestParam Map<Object,Object> map,Model model){
		model.addAttribute("map",map);
		boardService.community(model);
		return "board/list";
	}
	
	@GetMapping("/content/{id}")//글내용 보기 /board/content
	public String content(@PathVariable String id,Model model){
		model.addAttribute("id",id);
		boardService.content(model);
		return "board/content";
	}
	
	@GetMapping("/write")//글작성 화면 /board/write
	public String writeView(){
		return "board/write";
	}
	
	@PostMapping("/write")//글작성 요청 /board/write
	public String Write(@RequestParam Map<Object,Object> map,Model model){
		model.addAttribute("map",map);
		boardService.write(model);
		return "redirect:community";
	}
	
}
