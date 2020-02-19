package com.ezjobs.mystory.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
		// 수정 부분,미구현
		if (String.valueOf(map.get("upTagId")) != "null") {
			String upTagId = map.get("upTagId").toString();
			//String upTag = map.get("upTag").toString();//새이름
			String[] tagTypeName=upTagId.split(";");
			Tag tag = new Tag();// board로 변환
			tag.setType(tagTypeName[0]);
			tag.setName(tagTypeName[1]);
			//tagRepository.update(tag);
		}

		// 삭제 부분,미구현
		if (String.valueOf(map.get("delTagId")) != "null") {
			String delTagId = map.get("delTagId").toString();
			String[] tagTypeName=delTagId.split(";");
			Tag tag = new Tag();// board로 변환
			tag.setType(tagTypeName[0]);
			tag.setName(tagTypeName[1]);
			//tagRepository.delete(tag);
		}
		
		PageRequest pr = getPageRequest(map,Sort.by(Sort.Direction.DESC,"name"));
		String sch = String.valueOf(map.get("keyword"));
		
		Page<Tag> re;
		if (sch.equals("null")) {
			re = tagRepository.findAll(pr);
		} else {
			re = tagRepository.findByNameContaining(pr, sch);
		}
		
		return re;
	}
	
	public List<Tag> autoComplete(Map<String,Object> map){
		String keyword=(String)map.get("keyword");
		String searchType=(String)map.get("searchType");
		Integer page=0;
		Integer size=30;
		PageRequest pr=PageRequest.of(page,size);
		Page<Tag> pageList=tagRepository.findByTypeAndNameLike(searchType,keyword+"%", pr);
		pageList.getContent().forEach(p->p.setResumes(null));
		return pageList.getContent();
	}
	
	public Set<Tag> writeResumeTags(Map<String,Object> map){
		String tagsStr=(String)map.get("tagsStr");
		Tag[] resumeTags=new Tag[0];
		try {
			resumeTags=mapper.readValue(tagsStr, Tag[].class);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		List<Tag> tags=new ArrayList<>();
		List<String> tagsName=new ArrayList<>();
		for(Tag tag:resumeTags) {
			tags.add(tag);
			tagsName.add(tag.getName());
		}
		tagRepository.saveAll(tags);
		if (tagsName.size()>0)
			return tagRepository.findByNames(tagsName);
		else
			return new HashSet<Tag>();
	}
}
