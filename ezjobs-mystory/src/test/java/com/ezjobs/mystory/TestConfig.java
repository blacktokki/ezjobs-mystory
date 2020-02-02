package com.ezjobs.mystory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.ezjobs.mystory.entity.User;


@TestConfiguration
public class TestConfig {
	
	@Autowired
	Environment env;
	
	@Bean
    public DataSource dataSource(){
		
	   /*
	   for(Entry<Object, Object> e:System.getProperties().entrySet()) {
		   System.out.println(e.getKey()+":"+e.getValue());
	   };*/
	   String[] cmd=System.getProperty("sun.java.command").split("-");
	   Map<String,String> map=new HashMap<>();
	   for(int i=0;i<cmd.length;i++) {
		   if(cmd[i].equals("mysql")) {
			   String[] entry=cmd[i+1].split("=");
			   map.put(entry[0].trim(),entry[1].trim());
		   }
	   }
	   
	   //System.out.println(map.get("host"));
	   //System.out.println(map.get("username"));
	   //System.out.println(map.get("password"));
       DriverManagerDataSource dataSource = new DriverManagerDataSource();
       dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
       dataSource.setUrl("jdbc:mysql://"+map.get("host")+"/mystory?serverTimezone=Asia/Seoul&useUnicode=true&characterEncoding=utf-8");
       dataSource.setUsername(map.get("username"));
       dataSource.setPassword(map.get("password"));
       return dataSource;
    }
	
	private MockHttpSession mockHttpSession(User user,String... authorities) {
		MockHttpSession session = new MockHttpSession();
		ArrayList<GrantedAuthority> list= new ArrayList<>();
		for(String e:authorities) {
			list.add(new SimpleGrantedAuthority(e));
		}
		Authentication auth = new UsernamePasswordAuthenticationToken(
			user, user.getLoginPw(),list);
		session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, 
            new SecurityContextImpl(auth));
		return session;
	}
	
	@Bean
	public MockHttpSession loginSession(){
		User user=new User();
		user.setId("_admin");
		user.setIsAdmin(true);
		return mockHttpSession(user,"ROLE_USER");
	}
}
