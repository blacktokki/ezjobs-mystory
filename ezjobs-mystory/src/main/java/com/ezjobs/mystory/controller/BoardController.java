package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.entity.Board;
import com.ezjobs.mystory.service.BoardService;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;



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
	public String community(@RequestParam String page,Map<Object, Object> map){
		map.put("page",page);
		boardService.community(map);
		return "board/list";
	}
	
	@GetMapping("/content/{id}")//글내용 보기 /board/content
	public String content(@PathVariable String id,Map<Object, Object> map){
		map.put("id",id);
		boardService.content(map);
		return "board/content";
	}
	
	@GetMapping("/write")//글작성 화면 /board/write
	public String writeView(Map<Object, Object> map){
		return "board/write";
	}
	
	@PostMapping("/write")//글작성 요청 /board/write
	public String Write(@RequestParam Map<Object,Object> map){
		boardService.write(map);
		return "redirect:community";
	}
	
}
