package com.ezjobs.mystory.service;

import java.util.Map;
import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ezjobs.mystory.entity.Tag;
import com.ezjobs.mystory.repository.TagRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TagService implements AdminService<Tag>{

	@Inject
	ObjectMapper mapper;
	
	@Inject
	TagRepository tagRepository;

	@Override
	public Page<Tag> adminListAll(Map<String,Object> map) {
		// 수정 부분
		if (String.valueOf(map.get("upTagId")) != "null") {
			int upTagId = Integer.parseInt(map.get("upTagId").toString());
			String upTag = map.get("upTag").toString();
			Tag tag = mapper.convertValue(map, Tag.class);// board로 변환
			tag.setId(upTagId);
			tag.setName(upTag);
			tagRepository.update(tag);
		}

		// 삭제 부분
		if (String.valueOf(map.get("delTagId")) != "null") {
			int delTagId = Integer.parseInt(map.get("delTagId").toString());
			Tag tag = mapper.convertValue(map, Tag.class);// board로 변환
			tag.setId(delTagId);
			tagRepository.delete(tag);
		}
		
		PageRequest pr = getPageRequest(map);
		String sch = String.valueOf(map.get("sch"));
		
		Page<Tag> re;
		if (sch.equals("null")) {
			re = tagRepository.findAll(pr);
		} else {
			re = tagRepository.findByNameContaining(pr, sch);
		}
		
		return re;
	}

}
