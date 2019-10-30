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
		Optional<String> page=Optional.ofNullable(map.get("page").toString());
		int pageNum=Integer.parseInt(page.orElse("0"));
		PageRequest pr=PageRequest.of(pageNum, 3,Sort.by(Sort.Direction.DESC,"editDate"));
		Page<Board> boards=boardRepository.findAll(pr);
		map.put("boards",boards);
	}

	public void content(Map<Object, Object> map) {
		int id=Integer.parseInt(map.get("id").toString());
		Board board=boardRepository.findById(id).get();
		map.put("board",board);
	}

	public void write(Map<Object, Object> map) {
		Board board=mapper.convertValue(map, Board.class);
		boardRepository.save(board);
	}
}
