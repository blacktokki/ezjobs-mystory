package com.ezjobs.mystory.service.board;

import java.util.Map;

import com.ezjobs.mystory.entity.Board;
import com.ezjobs.mystory.service.page.DefaultPageService;

public interface BoardService<T extends Board> extends DefaultPageService{
	
	public Iterable<T> list(Map<String,Object> map);
	public T content(Integer id);
	public void write(Map<String,Object> map);
	public void edit(Map<String,Object> map);
	public boolean isWrited(Integer id,String loginId);
	public void delete(Integer id);
	
}
