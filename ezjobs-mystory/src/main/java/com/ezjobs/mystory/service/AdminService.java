package com.ezjobs.mystory.service;

import java.util.Map;

import com.ezjobs.mystory.service.page.DefaultPageService;

public interface AdminService<T> extends DefaultPageService{
	public Iterable<T> adminListAll(Map<String,Object> map);
	
	default T adminContentById(String id) {
		return null;
	}

}
