package com.ezjobs.mystory.service.help;

import java.util.Date;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ezjobs.mystory.entity.BoardImpl;
import com.ezjobs.mystory.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QnaService implements HelpService{
	
	@Inject
	BoardRepository<BoardImpl> boardRepository;
	
	@Inject
	ObjectMapper mapper;
	
	@Override
	public Iterable<?> list(Map<?,?> map) {
		BoardImpl board=new BoardImpl();
		board.setBoardType("qna");
		return boardRepository.findAll(Example.of(board), getPageRequest(map));
		
	}
	
	public void write (Map<String,Object> map){
		BoardImpl board=mapper.convertValue(map, BoardImpl.class);//board로 변환
		board.setEditDate(new Date());
		boardRepository.save(board);
		map.put("id",board.getId());
	}

}

//깃 허브 재 업로드용 소스