package com.ezjobs.mystory.controller;

import org.springframework.web.bind.annotation.*;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.EmailService;
import com.ezjobs.mystory.service.UserService;
import com.ezjobs.mystory.util.LoginUser;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

	private static String authorizationRequestBaseUri = "oauth2/authorization";

	private ClientRegistrationRepository clientRegistrationRepository;
	
	private UserService userService;

	private EmailService emailService;

	@GetMapping("/login")
	public String userView(Model model) {
		Iterable<ClientRegistration> clientRegistrations = null;
		if (clientRegistrationRepository instanceof InMemoryClientRegistrationRepository)
			clientRegistrations= (InMemoryClientRegistrationRepository)clientRegistrationRepository;
		
		Map<String, Object> oauth2AuthenticationUrls = new HashMap<>();
		clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(),
				authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
		model.addAttribute("urls", oauth2AuthenticationUrls);
		return "user/login";
	}

	// 탈퇴,미구현
	@GetMapping("/out")
	public String outView() {
		return "user/out";
	}

	@ResponseBody
	@GetMapping("/check_id")
	public Boolean checkId(@RequestParam String id) {
		return userService.checkId(id) == null;
	}

	@GetMapping("/join")
	public String writeView() {
		return "user/join";
	}

	@PostMapping("/join") // 회원가입 요청
	public String Write(@RequestParam Map<String, Object> map, Model model) {
		userService.write(map);
		return "redirect:login";
	}

	@GetMapping("/info")
	public String infoView(Model model) {
		model.addAttribute("user",userService.info(LoginUser.getId()));
		return "user/info";
	}

	@PutMapping("/info") // 정보수정 요청
	public String edit(@RequestParam Map<String, Object> map, Model model) {
		String id=LoginUser.getId();
		map.put("id",id);
		userService.edit(map);
		
		User user = userService.checkId(id);
		LoginUser.refreshAuthentication(user, user.getLoginPw());
		return "redirect:/index";
	}

	@GetMapping("/password/new")
	public String passwordNew(Model model) {
		return "user/password/new";
	}

	@PostMapping("/password")
	public String passwordNew(@RequestParam Map<String, Object> map,HttpSession session,Model model) {
		map.put("newPw",userService.getRamdomPassword(10));
		User user = userService.checkId((String)map.get("id"));
		if (user != null && user.getEmail().equals((String)map.get("email"))) {
			userService.changePw(map,emailService);
			session.setAttribute("changePwMessage", "새 비밀번호가 이메일로 전송되었습니다.");
			return "redirect:/user/login";
		} else {
			session.setAttribute("changePwException", "아이디 또는 이메일이 올바르지 않습니다.");
			return "redirect:/user/password/new";
		}
	}

	@GetMapping("/password/change")
	public String passwordChange(Model model) {
		return "user/password/change";
	}

	@PutMapping("/password")
	public String passwordChange(@RequestParam Map<String, Object> map,HttpSession session, Model model) {
		String id = LoginUser.getId();
		User user = userService.checkId(id);
		if (user != null && user.checkLoginPw((String)map.get("loginPw"))) {
			map.put("id", id);
			userService.changePw(map);
			user=userService.checkId(id);
			LoginUser.refreshAuthentication(user, user.getLoginPw());
			return "redirect:/index";
		} else {
			session.setAttribute("changePwException", "기존 비밀번호가 올바르지 않습니다.");
			return "redirect:/user/password/change";
		}
	}
	
	@GetMapping("/join/social")
	public String writeSocialView(Model model) {
		model.addAttribute("user",LoginUser.get());
		return "user/joinsocial";
	}
	
	@PostMapping("/join/social") // 회원가입 요청(소셜)
	public String writeSocial(@RequestParam Map<String, Object> map, Model model) {
		User user = LoginUser.get();
		map.put("id",user.getId());
		map.put("loginPw","**********");
		map.put("name",user.getName());
		map.put("email",user.getEmail());
		map.put("isAdmin",user.getIsAdmin());
		map.put("loginRel",user.getLoginRel());
		userService.write(map);
		
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
		grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
		grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_SOCIAL"));
		if (user.getIsAdmin()) {
			grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		LoginUser.refreshAuthentication(user, user.getLoginPw(),grantedAuthorityList);
		return "redirect:/index";
	}
}
