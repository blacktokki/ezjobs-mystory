package com.ezjobs.mystory.service;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.Board;
import com.ezjobs.mystory.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BoardService {
	
	@Inject
	BoardRepository boardRepository;
	
	@Inject
	ObjectMapper mapper;
	
	public void community(Model model){
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String page=Optional.ofNullable((String)map.get("page")).orElse("1");//String으로 담음
		int pageNum=Integer.parseInt(page)-1;//값이없을경우 0
		PageRequest pr=PageRequest.of(pageNum, 3,Sort.by(Sort.Direction.DESC,"editDate"));
		Page<Board> boards=boardRepository.findAll(pr);//pr을 기준으로 검색
		model.addAttribute("boards",boards);
		model.addAttribute("pageNavNumber",boards.getNumber()/5);//페이징바의 번호
		//System.out.println(boards.getNumberOfElements());
		//System.out.println(boards.getSize());
		//System.out.println(boards.getNumber());
		//System.out.println(boards.getTotalElements());
		//System.out.println(boards.getTotalPages());
		
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
		Board board=mapper.convertValue(map, Board.class);//board로 변환
		boardRepository.save(board);
	}
}
