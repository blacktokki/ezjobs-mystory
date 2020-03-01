package com.ezjobs.mystory.service;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.repository.UserRepository;
import com.ezjobs.mystory.util.UserSha256;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements AdminService<User>{

	UserRepository userRepository;
	
	ObjectMapper mapper;
	
	/*//회원탈퇴 미구현
	public void out(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> modelMap=model.asMap();
	     Map<?,?> map=(Map<?, ?>)modelMap.get("map");

	}*/
	
	public User checkId(String id) {
		return userRepository.findById(id).orElse(null);
	}

	public void clearFailureCount(String id) {	
		userRepository.clearLoginFailureCount(id);
	}
	
	public void addFailureCount(String id) {
		userRepository.addLoginFailureCount(id);
	}
	
	public void visit(String id) {
		User user=new User();
		user.setId(id);
		user.setVisitDate(new Date());
		userRepository.updateVisitDate(user);
	}
	
	public void write(Map<String,Object> map) {
		User user=mapper.convertValue(map, User.class);//board로 변환
		String pw=user.getLoginPw();
		user.setLoginPw(UserSha256.encrypt(pw));
		user.setIsAdmin(false);
		userRepository.save(user);
	}

	public void edit(Map<String,Object> map) {
		User user=mapper.convertValue(map, User.class);//board로 변환
		userRepository.updateWithoutPw(user);
	}

	public User info(String id) {
		User user=checkId(id);
		user.setLoginPw("");
		return user;
	}
	
	@Override
	public Page<User> adminListAll(Map<String,Object> map) {
		PageRequest pr = getPageRequest(map);
		String op = String.valueOf(map.get("op"));
		String keyword = String.valueOf(map.get("keyword"));
		
		Page<User> re;
		if (op.equals("loginSearch")) {
			re = userRepository.findByIdContaining(pr, keyword);
		}
		else {
			re = userRepository.findAll(pr);
		}
		return re;
	}
	
	@Override
	public User adminContentById(String id) {
		return info(id);
	}
	
	public void changePw(Map<String,Object> map) {
		User user=new User();
		user.setId((String)map.get("id"));
		String loginPw=(String)map.get("newPw");
		user.setLoginPw(UserSha256.encrypt(loginPw));
		userRepository.updatePw(user);
	}
	
	@Transactional
	public void changePw(Map<String, Object> map, EmailService emailService) {
		String email=(String)map.get("email");
		String newPw=(String)map.get("newPw");
		changePw(map);
		emailService.sendSimpleMessage(email, "[Ezjbos]패스워드 재설정 안내", "임시 비밀번호 발급 안내 \r\n"
				+ "임시 비밀번호가 아래와 같이 발급 되었습니다. \r\n" + "아래 비밀번호로 로그인 후 변경해 주세요. \r\n" + "임시 비밀번호:" + newPw);
	}
	
	
	public String getRamdomPassword(int len) {
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
