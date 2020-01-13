package com.ezjobs.mystory.service;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.Tag;
import com.ezjobs.mystory.repository.TagRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TagService {

	@Inject
	ObjectMapper mapper;
	
	@Inject
	TagRepository tagRepository;

	public void list(Model model) {
		Map<String, Object> modelMap = model.asMap();
		Map<?, ?> map = (Map<?, ?>) modelMap.get("map");
		String page = Optional.ofNullable((String) map.get("page")).orElse("1");// String으로 담음
		int pageNum = Integer.parseInt(page) - 1;// 값이없을경우 0
		String showNum2 = Optional.ofNullable((String) map.get("showNum")).orElse("20");
		int showNum = Integer.parseInt(showNum2);

		// 수정 부분
		if (String.valueOf(model.getAttribute("upTagId")) != "null") {
			int upTagId = Integer.parseInt(model.getAttribute("upTagId").toString());
			String upTag = model.getAttribute("upTag").toString();
			Tag tag = mapper.convertValue(map, Tag.class);// board로 변환
			tag.setId(upTagId);
			tag.setName(upTag);
			tagRepository.update(tag);
		}

		// 삭제 부분
		if (String.valueOf(model.getAttribute("delTagId")) != "null") {
			int delTagId = Integer.parseInt(model.getAttribute("delTagId").toString());
			Tag tag = mapper.convertValue(map, Tag.class);// board로 변환
			tag.setId(delTagId);
			tagRepository.delete(tag);
		}

		model.addAttribute("showNum", showNum);

		PageRequest pr = PageRequest.of(pageNum, showNum * 2, Sort.by(Sort.Direction.ASC, "id"));

		String sch = String.valueOf(model.getAttribute("sch"));

		if (sch.equals("null")) {
			Page<Tag> re = tagRepository.findAll(pr);
			model.addAttribute("tags", re);
			model.addAttribute("pageNavNumber", re.getNumber() / 5);// 페이징바의 번호
		} else {
			Page<Tag> re = tagRepository.findByNameContaining(pr, sch);
			model.addAttribute("tags", re);
			model.addAttribute("pageNavNumber", re.getNumber() / 5);// 페이징바의 번호
		}

	}

}
