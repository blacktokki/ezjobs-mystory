package com.ezjobs.mystory.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ezjobs.mystory.entity.User;
import com.ezjobs.mystory.service.UserService;

@Component("authProvider")
public class AuthProvider implements AuthenticationProvider  {
    
    @Autowired
    UserService userService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        String password = (String)authentication.getCredentials();
        CaptchaAuthenticationDetails details=(CaptchaAuthenticationDetails) authentication.getDetails();
        User user = userService.checkId(id);
        // id에 맞는 user가 없거나 비밀번호가 맞지 않는 경우.
        if (null == user || !user.checkLoginPw(password)) {
        	System.out.println("notexist");
        	throw new UsernameNotFoundException("가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.");
        }
        
        if (!details.equals()) {
        	throw new BadCredentialsException("잘못된 자동입력 방지문자 입니다.");
        }

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        
        // 로그인한 계정에게 권한 부여
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (user.getIsAdmin()) {
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
 
        // 로그인 성공시 로그인 사용자 정보 반환
        return new UsernamePasswordAuthenticationToken(user, user.getLoginPw(),  grantedAuthorityList);
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
 
}

