package com.ezjobs.mystory.service;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ezjobs.mystory.entity.User;

@Component("authProvider")
public class AuthProvider implements AuthenticationProvider  {
    
    @Autowired
    UserService userService;
    static int a=0;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        String password = authentication.getCredentials().toString();//HashUtil.sha256(authentication.getCredentials().toString());
        User target =new User();
        target.setLoginId(id);
        target.setLoginPw(password);
        System.out.println("\n"+id);
        System.out.println(password);
        User user = userService.getUser(target);
        // id에 맞는 user가 없거나 비밀번호가 맞지 않는 경우.
        a++;
        int[] b=new int[3];
        /*
        if(a==1) {
        	b[4]=10;
        }*/
        	
        if (null == user) {
        	System.out.println("notexist");
        	throw new UsernameNotFoundException("invailed username");
        }
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        
        // 로그인한 계정에게 권한 부여
        if (user.getIsAdmin()) {
            grantedAuthorityList.add(new SimpleGrantedAuthority("ADMIN"));
        } else {
            grantedAuthorityList.add(new SimpleGrantedAuthority("USER"));
        }
 
        // 로그인 성공시 로그인 사용자 정보 반환
        return new UsernamePasswordAuthenticationToken(user, password,  grantedAuthorityList);
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
 
}

