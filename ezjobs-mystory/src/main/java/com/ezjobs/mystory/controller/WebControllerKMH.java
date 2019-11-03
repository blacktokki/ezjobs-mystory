package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;

@Controller
public class WebControllerKMH {
	
	@GetMapping("templatekmh")
	public String templatekmh(){
		return "templatekmh";
	}

	@GetMapping("user/userLogin")
	public String userLogin(){
		return "userLogin";
	}

	@GetMapping("user/userJoin")
	public String userJoin(){
		return "userJoin";
	}
	
}
