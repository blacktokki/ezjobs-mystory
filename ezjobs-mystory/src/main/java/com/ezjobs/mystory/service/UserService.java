package com.ezjobs.mystory.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.activation.CommandMap;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ezjobs.mystory.entity.Board;
import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {
	
	@Inject
	UserRepository userRepository;
	
	@Inject
	ObjectMapper mapper;
	
	

	public void userLogin(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
	      Map<?,?> map=(Map<?, ?>)modelMap.get("map");

	}


	 public void user(Model model){
	      Map<String,Object> modelMap=model.asMap();
	      Map<?,?> map=(Map<?, ?>)modelMap.get("map");
	      String id=(String)map.get("loginId");
	      String pw=(String)map.get("loginPw");
	      System.out.println("입력값:"+id+"\n"+pw);
	      List<User> list=userRepository.findByLoginIdAndLoginPw(id, pw);
	      System.out.println(list.size());
	   }

	public void write(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		User user=mapper.convertValue(map, User.class);//board로 변환
		userRepository.save(user);
		
	}

}
