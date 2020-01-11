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
import com.ezjobs.mystory.util.UserSha256;
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
		return userRepository.findById(loginId).get();
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
		user.setId(loginId);
		userRepository.updateWithoutPw(user);
	}

	public void info(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
		String loginId=(String)modelMap.get("loginId");
		User user=new User();
		user.setId(loginId);
		user=userRepository.findOne(Example.of(user)).get();
		user.setLoginPw("");
		model.addAttribute("user",user);
	}
	
	public void list(Model model) {
		Map<String, Object> modelMap = model.asMap();
		Map<?, ?> map = (Map<?, ?>) modelMap.get("map");
		String page = Optional.ofNullable((String) map.get("page")).orElse("1");// String으로 담음
		int pageNum = Integer.parseInt(page) - 1;// 값이없을경우 0 //shownum->size, 
		String size = Optional.ofNullable((String) map.get("size")).orElse("20");
		int sizeNum = Integer.parseInt(size);
		model.addAttribute("size", sizeNum);
		PageRequest pr = PageRequest.of(pageNum, sizeNum, Sort.by(Sort.Direction.DESC, "id"));
		String op = String.valueOf(map.get("op"));
		String keyword = String.valueOf(map.get("keyword"));
		model.addAttribute("op", op);
		model.addAttribute("keyword", keyword);
		
		Page<User> re;
		if (op.equals("loginSearch")) {
			re = userRepository.findByIdContaining(pr, keyword);
		}
		else {
			re = userRepository.findAll(pr);
			System.out.println("s:" + re.getSize());
		}
		model.addAttribute("users", re);
		model.addAttribute("pageNavNumber", re.getNumber() / 5);
	}
	
	public void changePw(Model model) {
		Map<String,Object> modelMap=model.asMap();
		User user=new User();
		user.setId((String)modelMap.get("loginId"));
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
