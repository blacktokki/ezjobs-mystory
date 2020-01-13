package com.ezjobs.mystory.service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.Board;
import com.ezjobs.mystory.entity.BoardImpl;
import com.ezjobs.mystory.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HelpService{
	
	@Inject
	BoardRepository boardRepository;
	@Inject
	ObjectMapper mapper;
	
	public void notice(Model model) {
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String page=Optional.ofNullable((String)map.get("page")).orElse("1");//String으로 담음
		int pageNum=Integer.parseInt(page)-1;//값이없을경우 0
		PageRequest pr=PageRequest.of(pageNum, 5);
		
		BoardImpl board=new BoardImpl();
		board.setBoardType("notice");
		Page<BoardImpl> boards=boardRepository.findAll(Example.of(board), pr);
		
		model.addAttribute("boards",boards);
	}
	
	public void qna(Model model) {
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String page=Optional.ofNullable((String)map.get("page")).orElse("1");//String으로 담음
		int pageNum=Integer.parseInt(page)-1;//값이없을경우 0
		PageRequest pr=PageRequest.of(pageNum, 5);
		
		BoardImpl board=new BoardImpl();
		board.setBoardType("qna");
		Page<BoardImpl> boards=boardRepository.findAll(Example.of(board), pr);
		
		model.addAttribute("boards",boards);
	}
	
	public void faq(Model model) {
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String page=Optional.ofNullable((String)map.get("page")).orElse("1");//String으로 담음
		int pageNum=Integer.parseInt(page)-1;//값이없을경우 0
		PageRequest pr=PageRequest.of(pageNum, 5);
		
		BoardImpl board=new BoardImpl();
		board.setBoardType("faq");
		Page<BoardImpl> boards=boardRepository.findAll(Example.of(board), pr);
		System.out.println(boards.getContent().size());
		model.addAttribute("boards",boards);
	}
	
	public void noticecontent(Model model){
		Map<String,Object> modelMap=model.asMap();
		int id=Integer.parseInt(modelMap.get("id").toString());
		Board board=boardRepository.findById(id).get();//id로 board 찾기
		model.addAttribute("board",board);
	}	

	public void qnacontent(Model model){
		Map<String,Object> modelMap=model.asMap();
		int id = Integer.parseInt(modelMap.get("id").toString());
		Board board=boardRepository.findById(id).get();
		model.addAttribute("board", board);
	}	
	
	public void write (Model model){
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String userId=(String)modelMap.get("userId");
		BoardImpl board=mapper.convertValue(map, BoardImpl.class);//board로 변환
		board.setUserId(userId);
		board.setBoardType("Qna");
		board.setGoodCnt(0);
		board.setEditDate(new Date());
		boardRepository.save(board);
	}
	
	public void edit(Model model){
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		int id=Integer.parseInt(modelMap.get("id").toString());
		BoardImpl board=mapper.convertValue(map, BoardImpl.class);//board로 변환
		board.setId(id);
		boardRepository.update(board);
	}
	
	public void community(Model model){
		
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String page=Optional.ofNullable((String)map.get("page")).orElse("1");//String으로 담음
		int pageNum=Integer.parseInt(page)-1;//값이없을경우 0
		PageRequest pr=PageRequest.of(pageNum, 5,Sort.by(Sort.Direction.DESC,"editDate"));
				
		BoardImpl board=new BoardImpl();
		board.setBoardType("Board");
		Page<BoardImpl> boards=boardRepository.findAll(Example.of(board), pr);
		
		model.addAttribute("boardscommunity",boards);
	}

}

//깃 허브 재 업로드용 소스