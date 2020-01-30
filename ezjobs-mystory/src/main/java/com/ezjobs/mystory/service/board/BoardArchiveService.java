package com.ezjobs.mystory.service.board;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ezjobs.mystory.entity.BoardArchive;
import com.ezjobs.mystory.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BoardArchiveService implements BoardService<BoardArchive>{
	
	@Inject
	BoardRepository<BoardArchive> boardRepository;
	
	@Inject
	ObjectMapper mapper;
	
	@Override
	public Page<BoardArchive> list(Map<String,Object> map){
		String op = String.valueOf(map.get("op"));
		String keyword = String.valueOf(map.get("keyword"));
		
		BoardArchive board=new BoardArchive();
		if (op.equals("userId")) {
			board.setUserId(keyword);
		}
		else if(op.equals("title")) {
			board.setTitle(keyword);
		}	
		return boardRepository.findAll(Example.of(board), getPageRequest(map));//pr을 기준으로 검색
	}

	@Override
	public BoardArchive content(Integer id) {
		return boardRepository.findById(id).get();//id로 board 찾기
	}

	@Override
	public void write(Map<String,Object> map){
	}
	
	@Override
	public void edit(Map<String,Object> map){
	}
	
	@Override
	public boolean isWrited(Integer id,String loginId){
		BoardArchive board=new BoardArchive();
		board.setId(id);
		board.setUserId(loginId);
		return boardRepository.findOne(Example.of(board)).orElse(null)!=null;
	}

	@Override
	public void delete(Integer id) {//moveArchive
		boardRepository.deleteById(id);
	}
}
