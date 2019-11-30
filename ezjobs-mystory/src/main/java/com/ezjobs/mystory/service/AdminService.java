package com.ezjobs.mystory.service;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.repository.UserRepository;
import com.ezjobs.mystory.entity.Tag;
import com.ezjobs.mystory.repository.TagRepository;
import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.repository.ResumeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AdminService {

	@Inject
	UserRepository userRepository;

	@Inject
	ObjectMapper mapper;

	public void user(Model model) {
		Map<String, Object> modelMap = model.asMap();
		Map<?, ?> map = (Map<?, ?>) modelMap.get("map");
		String page = Optional.ofNullable((String) map.get("page")).orElse("1");// String으로 담음
		int pageNum = Integer.parseInt(page) - 1;// 값이없을경우 0
		String showNum2 = Optional.ofNullable((String) map.get("showNum")).orElse("20");
		int showNum = Integer.parseInt(showNum2);

		model.addAttribute("showNum", showNum);
		
		PageRequest pr = PageRequest.of(pageNum, showNum, Sort.by(Sort.Direction.DESC, "id"));
		String op = String.valueOf(model.getAttribute("op"));
		String sch = String.valueOf(model.getAttribute("sch"));

		System.out.println(op);
		System.out.println(showNum);
		if (op.equals("idSearch")) {
			Page<User> re = userRepository.findById(pr, Integer.parseInt(sch));
			model.addAttribute("users", re);
			model.addAttribute("pageNavNumber", re.getNumber() / 5);// 페이징바의 번호
		}
		else if (op.equals("loginSearch")) {
			Page<User> re = userRepository.findByLoginIdContaining(pr, sch);
			model.addAttribute("users", re);
			model.addAttribute("pageNavNumber", re.getNumber() / 5);// 페이징바의 번호
		}
		else {
			Page<User> re = userRepository.findAll(pr);
			System.out.println("s:" + re.getSize());
			model.addAttribute("users", re);
			model.addAttribute("pageNavNumber", re.getNumber() / 5);// 페이징바의 번호
		}
	}

	public void personal(Model model) {
		Map<String, Object> modelMap = model.asMap();
		int id = Integer.parseInt(modelMap.get("id").toString());
		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
	}

	@Inject
	TagRepository tagRepository;

	public void tag(Model model) {
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

	@Inject
	ResumeRepository resumeRepository;
	
	public void resume(Model model) {
		Map<String, Object> modelMap = model.asMap();
		Map<?, ?> map = (Map<?, ?>) modelMap.get("map");
		String page = Optional.ofNullable((String) map.get("page")).orElse("1");// String으로 담음
		int pageNum = Integer.parseInt(page) - 1;// 값이없을경우 0
		String showNum2 = Optional.ofNullable((String) map.get("showNum")).orElse("20");
		int showNum = Integer.parseInt(showNum2);

		model.addAttribute("showNum", showNum);
		
		PageRequest pr = PageRequest.of(pageNum, showNum, Sort.by(Sort.Direction.ASC, "id"));
		String op = String.valueOf(model.getAttribute("op"));
		String sch = String.valueOf(model.getAttribute("sch"));

		System.out.println(op);
		System.out.println(showNum);
		
		if (op.equals("tagSearch")) { // 태그명 검색
			Page<Resume> re = resumeRepository.findByTagsContaining(pr, sch);
			model.addAttribute("resumes", re);
			model.addAttribute("pageNavNumber", re.getNumber() / 5);// 페이징바의 번호
		}
		else if (op.equals("question")) { // 자소서 문항 검색
			Page<Resume> re = resumeRepository.findByQuestionContaining(pr, sch);
			model.addAttribute("resumes", re);
			model.addAttribute("pageNavNumber", re.getNumber() / 5);// 페이징바의 번호
		}
		else if (op.equals("answer")) { // 자소서 내용 검색
			Page<Resume> re = resumeRepository.findByUserIdContaining(pr, sch);
			model.addAttribute("resumes", re);
			model.addAttribute("pageNavNumber", re.getNumber() / 5);// 페이징바의 번호
		}
		else if (op.equals("userId")) { // 작성자 검색
			Page<Resume> re = resumeRepository.findByUserIdContaining(pr, sch);
			model.addAttribute("resumes", re);
			model.addAttribute("pageNavNumber", re.getNumber() / 5);// 페이징바의 번호
		}
		else if (op.equals("company")) { // 회사명 검색
			Page<Resume> re = resumeRepository.findByCompanyContaining(pr, sch);
			model.addAttribute("resumes", re);
			model.addAttribute("pageNavNumber", re.getNumber() / 5);// 페이징바의 번호
		}
		else { // 전체 출력
			Resume resume=new Resume();
			Page<Resume> re=resumeRepository.findAll(Example.of(resume), pr);
			model.addAttribute("resumes", re);
			model.addAttribute("pageNavNumber", re.getNumber() / 5);// 페이징바의 번호
		}
	}
}
