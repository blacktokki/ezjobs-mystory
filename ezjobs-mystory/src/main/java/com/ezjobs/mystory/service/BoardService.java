package com.ezjobs.mystory.service;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezjobs.mystory.entity.Board;
import com.ezjobs.mystory.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BoardService {
	
	@Inject
	BoardRepository boardRepository;
	
	@Inject
	ObjectMapper mapper;
	
	public void community(Map<Object,Object> map){
		Optional<String> page=Optional.ofNullable((String)map.get("page"));//String으로 담음
		int pageNum=Integer.parseInt(page.orElse("1"))-1;//값이없을경우 0
		PageRequest pr=PageRequest.of(pageNum, 3,Sort.by(Sort.Direction.DESC,"editDate"));
		Page<Board> boards=boardRepository.findAll(pr);//pr을 기준으로 검색
		map.put("boards",boards);
		map.put("pageNavNumber",boards.getNumber()/5);//페이징바의 번호
		//System.out.println(boards.getNumberOfElements());
		//System.out.println(boards.getSize());
		//System.out.println(boards.getNumber());
		//System.out.println(boards.getTotalElements());
		//System.out.println(boards.getTotalPages());
		
	}

	public void content(Map<Object, Object> map) {
		int id=Integer.parseInt(map.get("id").toString());
		Board board=boardRepository.findById(id).get();//id로 board 찾기
		map.put("board",board);
	}

	public void write(Map<Object, Object> map) {
		Board board=mapper.convertValue(map, Board.class);//map을 board로 변환
		boardRepository.save(board);
	}
}
