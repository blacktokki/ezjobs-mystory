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
import com.ezjobs.mystory.entity.BoardArchive;
import com.ezjobs.mystory.entity.BoardImpl;
import com.ezjobs.mystory.repository.BoardArchiveRepository;
import com.ezjobs.mystory.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BoardService {
	
	@Inject
	BoardRepository boardRepository;
	
	@Inject
	BoardArchiveRepository boardArchiveRepository;
	
	@Inject
	ObjectMapper mapper;
	
	public void community(Model model){
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String page=Optional.ofNullable((String)map.get("page")).orElse("1");//String으로 담음
		int pageNum=Integer.parseInt(page)-1;//값이없을경우 0
		PageRequest pr=PageRequest.of(pageNum, 5,Sort.by(Sort.Direction.DESC,"editDate"));
		BoardImpl board=new BoardImpl();
		board.setBoardType("Board");
		Page<BoardImpl> boards=boardRepository.findAll(Example.of(board), pr);//pr을 기준으로 검색
		model.addAttribute("boards",boards);
		model.addAttribute("pageNavNumber",boards.getNumber()/5);//페이징바의 번호
		
	}

	public void content(Model model) {
		Map<String,Object> modelMap=model.asMap();
		int id=Integer.parseInt(modelMap.get("id").toString());
		Board board=boardRepository.findById(id).get();//id로 board 찾기
		model.addAttribute("board",board);
	}

	public void write(Model model){
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String userId=(String)modelMap.get("userId");
		BoardImpl board=mapper.convertValue(map, BoardImpl.class);//board로 변환
		board.setUserId(userId);
		board.setEditDate(new Date());
		board.setBoardType("Board");
		boardRepository.save(board);
		model.addAttribute("id",board.getId());
	}
	
	public void edit(Model model){
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		int id=Integer.parseInt(modelMap.get("id").toString());
		BoardImpl board=mapper.convertValue(map, BoardImpl.class);//board로 변환
		board.setId(id);
		boardRepository.update(board);
	}
	
	public boolean isWrited(Integer id,String loginId){
		BoardImpl board=new BoardImpl();
		board.setId(id);
		board.setUserId(loginId);
		return boardRepository.findOne(Example.of(board)).orElse(null)!=null;
	}

	public void moveArchive(Model model) {
		Map<String,Object> modelMap=model.asMap();
		int id=Integer.parseInt(modelMap.get("id").toString());
		Board board=(Board)boardRepository.findById(id).get();
		BoardArchive boardArchive=mapper.convertValue(board, BoardArchive.class);//board로 변환
		boardArchive.setRemoveDate(new Date());
		boardArchiveRepository.save(boardArchive);
		boardRepository.deleteById(id);
	}
	
	public void archive(Model model){
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String page=Optional.ofNullable((String)map.get("page")).orElse("1");//String으로 담음
		int pageNum=Integer.parseInt(page)-1;//값이없을경우 0
		String size = Optional.ofNullable((String) map.get("size")).orElse("20");
		int sizeNum = Integer.parseInt(size);
		model.addAttribute("size", sizeNum);
		PageRequest pr=PageRequest.of(pageNum, sizeNum,Sort.by(Sort.Direction.DESC,"editDate"));
		String op = String.valueOf(map.get("op"));
		String keyword = String.valueOf(map.get("keyword"));
		model.addAttribute("op", op);
		model.addAttribute("keyword", keyword);
		
		BoardArchive board=new BoardArchive();
		if (op.equals("userId")) {
			board.setUserId(keyword);
		}
		else if(op.equals("title")) {
			board.setTitle(keyword);
		}
		Page<BoardArchive> boards=boardArchiveRepository.findAll(Example.of(board), pr);//pr을 기준으로 검색
		model.addAttribute("boards",boards);
		model.addAttribute("pageNavNumber",boards.getNumber()/5);//페이징바의 번호
		
	}
}
