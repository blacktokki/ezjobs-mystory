package com.ezjobs.mystory.service;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.activation.CommandMap;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Out;
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
	
	private static final PrintStream out = null;

	@Inject
	UserRepository userRepository;
	
	@Inject
	ObjectMapper mapper;
	
	

	public void login(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
	      Map<?,?> map=(Map<?, ?>)modelMap.get("map");

	}
	

	public void fail(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
	      Map<?,?> map=(Map<?, ?>)modelMap.get("map");

	}

	 public void user(Model model) throws Exception{
	      Map<String,Object> modelMap=model.asMap();
	      Map<?,?> map=(Map<?, ?>)modelMap.get("map");
	      String id=(String)map.get("loginId");
	      String pw=(String)map.get("loginPw");
	      System.out.println("입력값:"+id+"\n"+pw);
	      List<User> list=userRepository.findByLoginIdAndLoginPw(id, pw);
	     // System.out.println(list.size());
	      if(0==list.size()) {
	    	  throw new Exception();  
	      }
	   }
	 
	private void alert(String string) {
		// TODO Auto-generated method stub
		
	}


	public void write(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		User user=mapper.convertValue(map, User.class);//board로 변환
		userRepository.save(user);		
	}
	
	public void modify(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		User user=mapper.convertValue(map, User.class);//
		userRepository.save(user);		
	}

}
