package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.service.UserService;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
public class WebControllerKMH {

	@Inject
	private UserService userService;


	@GetMapping("/userLogin")
	public String userLogin(@RequestParam String page,Map<Object, Object> map){
		map.put("page",page);
		userService.userLogin(map);
		return "user/userLogin";
	}

	@GetMapping("/userJoin")
	public String userJoin(@RequestParam String page,Map<Object, Object> map){
		map.put("page",page);
		userService.userLogin(map);
		return "user/userJoin";
	}
	
}
