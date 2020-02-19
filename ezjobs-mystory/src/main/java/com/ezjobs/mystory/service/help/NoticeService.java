package com.ezjobs.mystory.service.help;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ezjobs.mystory.entity.BoardImpl;
import com.ezjobs.mystory.repository.BoardRepository;

@Service
public class NoticeService implements HelpService{
	
	@Inject
	BoardRepository<BoardImpl> boardRepository;
	
	@Override
	public Iterable<?> list(Map<?,?> map) {
		BoardImpl board=new BoardImpl();
		board.setBoardType("notice");
		return boardRepository.findAll(Example.of(board), getPageRequest(map));
	}

}

//깃 허브 재 업로드용 소스