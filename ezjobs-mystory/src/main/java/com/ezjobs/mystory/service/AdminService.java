package com.ezjobs.mystory.service;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.repository.UserRepository;
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
		PageRequest pr = PageRequest.of(pageNum, 20, Sort.by(Sort.Direction.DESC, "id"));

		String op = model.getAttribute("op").toString();
		String sch = model.getAttribute("sch").toString();
		System.out.println(op);
		System.out.println(sch);
		if(sch.contentEquals("")) op = "elseIf";
		
		if (op.contentEquals("idSearch")) {
			try {
				Page<User> re = userRepository.findById(pr, Integer.parseInt(sch));
				model.addAttribute("users", re);
				model.addAttribute("pageNavNumber", re.getNumber() / 5);// 페이징바의 번호
			} catch (Exception e) {
				// 오류창
			}
			;
		}

		else if (op.contentEquals("loginSearch")) {
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
}
