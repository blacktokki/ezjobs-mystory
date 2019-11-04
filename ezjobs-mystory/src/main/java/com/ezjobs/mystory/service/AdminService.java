package com.ezjobs.mystory.service;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.Board;
import com.ezjobs.mystory.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AdminService {
	
	@Inject
	BoardRepository AdminRepository;
	
	@Inject
	ObjectMapper mapper;
	
	public void user(Model model){
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String page=Optional.ofNullable((String)map.get("page")).orElse("1");//String으로 담음
		int pageNum=Integer.parseInt(page)-1;//값이없을경우 0
		PageRequest pr=PageRequest.of(pageNum, 20,Sort.by(Sort.Direction.DESC,"id"));
		Page<Board> users=AdminRepository.findAll(pr);
		model.addAttribute("users",users);
		model.addAttribute("pageNavNumber",users.getNumber()/5);//페이징바의 번호		
	}

	public void personal(Model model) {
		Map<String,Object> modelMap=model.asMap();
		int id=Integer.parseInt(modelMap.get("id").toString());
		Board user=AdminRepository.findById(id).get();
		model.addAttribute("user",user);
	}
}
