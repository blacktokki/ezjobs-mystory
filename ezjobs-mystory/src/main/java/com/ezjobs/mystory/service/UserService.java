package com.ezjobs.mystory.service;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ezjobs.mystory.repository.UserRepository;

@Service
public class UserService {
	
	@Inject
	UserRepository userRepository;

	public void userLogin(Map<Object, Object> map) {
		// TODO Auto-generated method stub

	}

}
