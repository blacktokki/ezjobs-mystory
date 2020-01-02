package com.ezjobs.mystory.service;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.repository.UserRepository;
import com.ezjobs.mystory.security.UserSha256;
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
	
	public User findByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}

	public void clearFailureCount(String loginId) {	
		userRepository.clearLoginFailureCount(loginId);
	}
	
	public void addFailureCount(String loginId) {	
		userRepository.addLoginFailureCount(loginId);
	}
	
	public void write(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		User user=mapper.convertValue(map, User.class);//board로 변환
		String pw=user.getLoginPw();
		user.setLoginPw(UserSha256.encrypt(pw));
		userRepository.save(user);		
	}

	public void edit(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String loginId=modelMap.get("loginId").toString();
		User user=mapper.convertValue(map, User.class);//board로 변환
		user.setLoginId(loginId);
		userRepository.updateWithoutPw(user);
	}

	public void info(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
		String loginId=modelMap.get("loginId").toString();
		User user=new User();
		user.setLoginId(loginId);
		user=userRepository.findOne(Example.of(user)).get();
		user.setLoginPw("");
		model.addAttribute("user",user);
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
	
	public void changePw(Model model) {
		Map<String,Object> modelMap=model.asMap();
		User user=new User();
		user.setLoginId((String)modelMap.get("loginId"));
		String loginPw=(String)modelMap.get("newPw");
		//System.out.println(loginPw);
		user.setLoginPw(UserSha256.encrypt(loginPw));
		userRepository.updatePw(user);
	}
	
	public static String getRamdomPassword(int len) {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		int idx = 0;
		StringBuffer sb = new StringBuffer();
		// System.out.println("charSet.length :::: " + charSet.length);

		for (int i = 0; i < len; i++) {
			idx = (int) (charSet.length * Math.random()); // 36 * 생성된 난수를 Int로 추출 (소숫점제거)
			// System.out.println("idx :::: " + idx);
			sb.append(charSet[idx]);
		}
		return sb.toString();
	}
}
