package com.ezjobs.mystory.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezjobs.mystory.service.board.BoardService;
import com.ezjobs.mystory.service.help.HelpService;
import com.ezjobs.mystory.util.LoginUser;

import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
@RequestMapping("/help")
public class HelpController {
	
	private HelpService noticeService;

	private HelpService qnaService;
	
	private HelpService faqService;
	
	private BoardService<?> communityService;
	
	@GetMapping("/faq")
	public String helpfaq(@RequestParam Map<Object, Object> map, Model model) {
		model.addAttribute("boards",faqService.list(map));
		return "help/faq";
	}

	@GetMapping("/notice")
	public String helpnotice(@RequestParam Map<String, Object> map, Model model) {
		model.addAttribute("boards",noticeService.list(map));
		return "help/notice";
	}

	@GetMapping("/noticecontent/{id}")
	public String noticecontent(@PathVariable Integer id, Model model) {
		model.addAttribute("board",communityService.content(id));
		return "help/noticecontent";
	}

	@GetMapping("/qna")
	public String helpqna(@RequestParam Map<Object, Object> map,Model model) {
		model.addAttribute("boards",qnaService.list(map));
		return "help/qna";
	}

	@GetMapping("/qnacontent/{id}")
	public String qnacontent(@PathVariable Integer id, Model model) {
		model.addAttribute("board",communityService.content(id));
		return "help/qnacontent";
	}

	@GetMapping("/qnawrite")
	public String qnawrite() {
		return "help/qnawrite";
	}

	@PostMapping("/qnawrite") // 글작성 요청 /help/qnawrite
	public String Write(@RequestParam Map<String, Object> map, Model model) {
		map.put("userId",LoginUser.getId());
		map.put("boardType","Qna");
		communityService.write(map);
		return "redirect:../content/"+map.get("id");
	}

	@PutMapping("/qnawrite/{id}") // 글수정요청 /board/write/1
	public String Write(@PathVariable Integer id, @RequestParam Map<String, Object> map,Model model) {
		map.put("id", id);
		communityService.edit(map);
		return "redirect:../qnacontent/{id}";
	}

	@GetMapping("/qnawrite/{id}") // 글수정 화면/board/write/1
	public String writeView(@PathVariable Integer id, Model model) {
		model.addAttribute("method", "put");
		model.addAttribute("board",communityService.content(id));
		return "help/qnawrite";
	}

// 깃 허브 재 업로드용 소스
}