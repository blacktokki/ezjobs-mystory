package com.ezjobs.mystory.service.help;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import com.ezjobs.mystory.service.page.PageService;

public interface HelpService extends PageService<Map<?,?>>{
	public Iterable<?> list(Map<?,?> map);
	
	@Override
	default PageRequest getPageRequest(Map<?,?> map) {
		String page=Optional.ofNullable((String)map.get("page")).orElse("1");//String으로 담음
		int pageNum=Integer.parseInt(page)-1;//값이없을경우 0
		return PageRequest.of(pageNum, 5);
	};
}

//깃 허브 재 업로드용 소스