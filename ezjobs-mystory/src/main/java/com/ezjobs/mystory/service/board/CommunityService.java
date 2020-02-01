package com.ezjobs.mystory.service.board;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezjobs.mystory.entity.Board;
import com.ezjobs.mystory.entity.BoardArchive;
import com.ezjobs.mystory.entity.BoardImpl;
import com.ezjobs.mystory.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommunityService implements BoardService<BoardImpl> {

	BoardRepository<BoardImpl> boardRepository;

	BoardRepository<BoardArchive> boardArchiveRepository;

	ObjectMapper mapper;

	@Override
	public Page<BoardImpl> list(Map<String, Object> map) {
		PageRequest pr = getPageRequest(map, Sort.by(Sort.Direction.DESC, "editDate"));
		BoardImpl board = new BoardImpl();
		board.setBoardType("Board");
		return boardRepository.findAll(Example.of(board), pr);// pr을 기준으로 검색
	}

	@Override
	public BoardImpl content(Integer id) {
		return boardRepository.findById(id).get();// id로 board 찾기
	}

	@Override
	public void write(Map<String, Object> map) {
		BoardImpl board = mapper.convertValue(map, BoardImpl.class);// board로 변환
		board.setEditDate(new Date());
		boardRepository.save(board);
		map.put("id", board.getId());
	}

	@Override
	public void edit(Map<String, Object> map) {
		BoardImpl board = mapper.convertValue(map, BoardImpl.class);// board로 변환
		boardRepository.update(board);
	}

	@Override
	public boolean isWrited(Integer id, String loginId) {
		BoardImpl board = new BoardImpl();
		board.setId(id);
		board.setUserId(loginId);
		return boardRepository.findOne(Example.of(board)).orElse(null) != null;
	}

	@Override
	public void delete(Integer id) {// moveArchive
		Board board = (Board) boardRepository.findById(id).get();
		BoardArchive boardArchive = mapper.convertValue(board, BoardArchive.class);// board로 변환
		boardArchive.setRemoveDate(new Date());
		boardArchiveRepository.save(boardArchive);
		boardRepository.deleteById(id);
	}
}
