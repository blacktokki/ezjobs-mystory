package com.ezjobs.mystory.service.page;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public interface DefaultPageService extends PageService<Map<String,Object>> {

	@Override
	default PageRequest getPageRequest(Map<String,Object> map) {
		return getPageRequest(map,Sort.by(Sort.Direction.DESC,"id"));
	}
	
	default PageRequest getPageRequest(Map<String,Object> map,Sort sort) {
		String page=Optional.ofNullable((String)map.get("page")).orElse("1");//String으로 담음
		int pageNum=Integer.parseInt(page)-1;//값이없을경우 0
		String size = Optional.ofNullable((String) map.get("size")).orElse("10");
		int sizeNum = Integer.parseInt(size);
		map.put("page",pageNum);
		map.put("size", sizeNum);
		return PageRequest.of(pageNum, sizeNum,sort);
	}
	
}
