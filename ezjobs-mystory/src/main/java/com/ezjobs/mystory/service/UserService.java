package com.ezjobs.mystory.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {

	@Inject
	UserRepository userRepository;
	
	@Inject
	ObjectMapper mapper;
	
	/*
	public void out(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
	     Map<?,?> map=(Map<?, ?>)modelMap.get("map");

	}*/
	
	public User getUser(User user){
	      //System.out.println("입력값:"+id+"\n"+pw);
	      return userRepository.findOne(Example.of(user)).orElse(null);
	   }

	public void write(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		User user=mapper.convertValue(map, User.class);//board로 변환
		user.setRegistDate(new Date());
		userRepository.save(user);		
	}

	public void edit(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String loginId=modelMap.get("loginId").toString();
		User user=mapper.convertValue(map, User.class);//board로 변환
		user.setLoginId(loginId);
		userRepository.update(user);
	}

	public void info(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
		String userId=modelMap.get("userId").toString();
		User user=new User();
		System.out.println(userId);
		user.setLoginId(userId);
		List<User> users=userRepository.findAll(Example.of(user));
		user=userRepository.findOne(Example.of(user)).get();
		model.addAttribute("user",user);
		System.out.println(users.size());
	}

	
	public void list(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
		String userId=modelMap.get("loginId").toString();
		String name=modelMap.get("name").toString();
		Resume resume=new Resume();
		resume.setUserId(userId);
		resume.setUserId(name);
	}
}
