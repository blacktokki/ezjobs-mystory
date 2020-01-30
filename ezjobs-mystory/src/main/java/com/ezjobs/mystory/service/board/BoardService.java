package com.ezjobs.mystory.service.board;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.ezjobs.mystory.entity.Board;

public interface BoardService<T extends Board> {
	
	public Page<T> list(Map<String,Object> map);
	public T content(Integer id);
	public void write(Map<String,Object> map);
	public void edit(Map<String,Object> map);
	public boolean isWrited(Integer id,String loginId);
	public void delete(Integer id);
	
	default PageRequest getPageRequest(Map<String,Object> map) {
		String page=Optional.ofNullable((String)map.get("page")).orElse("1");//String으로 담음
		int pageNum=Integer.parseInt(page)-1;//값이없을경우 0
		String size = Optional.ofNullable((String) map.get("size")).orElse("10");
		int sizeNum = Integer.parseInt(size);
		map.put("page", pageNum);
		map.put("size", sizeNum);
		return PageRequest.of(pageNum, sizeNum,Sort.by(Sort.Direction.DESC,"editDate"));
	};
}
